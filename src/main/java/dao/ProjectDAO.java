package dao;

import model.Project;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends EntityDAO<Project> {
    @Override
    public List<Project> readAll() throws SQLException {
        List<Project> projectList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM projects");
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setCost(rs.getBigDecimal("cost"));
                readAllRelationalIds(project, connection);
                projectList.add(project);
            }
        }
        return projectList;
    }

    @Override
    protected void readAllRelationalIds(Project project, Connection connection) throws SQLException {
        project.setCompaniesIds(readIds("SELECT company_id FROM companies_projects WHERE project_id = ?",
                project.getId(), connection));
        project.setCustomersIds(readIds("SELECT customer_id FROM customers_projects WHERE project_id = ?",
                project.getId(), connection));
        project.setDevelopersIds(readIds("SELECT developer_id FROM projects_developers WHERE project_id = ?",
                project.getId(), connection));
    }

    @NotNull
    public Project read(int id) throws SQLException {
        Project project = new Project();
        project.setId(id);
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                project.setName(rs.getString("name"));
                project.setCost(rs.getBigDecimal("cost"));
            }
            readAllRelationalIds(project, connection);
        }
        return project;
    }

    public void write(@NotNull Project project) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO projects (name, cost) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getName());
            ps.setBigDecimal(2, project.getCost());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to write project, no rows affected");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                project.setId(generatedKeys.getInt(1));
            } else throw new SQLException("Failed to write project, no id obtained");

            setRelationships(project, connection);
        }
    }

    private void setRelationships(@NotNull Project project, Connection connection) throws SQLException {
        setRelationships("INSERT INTO companies_projects (company_id, project_id) VALUES (?, ?)", project.getId(),
                false, project.getCompaniesIds(), connection);
        setRelationships("INSERT INTO projects_developers (project_id, developer_id) VALUES (?, ?)", project.getId(),
                true, project.getDevelopersIds(), connection);
        setRelationships("INSERT INTO customers_projects (customer_id, project_id) VALUES (?, ?)", project.getId(),
                false, project.getCustomersIds(), connection);
    }

    public void update(@NotNull Project project) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    ("UPDATE projects p SET p.name = ?, p.cost = ? WHERE p.id = ?");
            ps.setString(1, project.getName());
            ps.setBigDecimal(2, project.getCost());
            ps.setInt(3, project.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update project, no rows affected");
            }

            clearRelationships(project.getId(), connection);
            setRelationships(project, connection);
        }
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM projects WHERE id = ?";
    }

    @Override
    protected String deleteAllQuery() {
        return "DELETE FROM projects";
    }

    @Override
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM companies_projects WHERE project_id = ?", id, connection);
        clearRelationships("DELETE FROM customers_projects WHERE project_id = ?", id, connection);
        clearRelationships("DELETE FROM projects_developers WHERE project_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
