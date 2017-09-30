package dao;

import model.Company;
import model.Customer;
import model.Developer;
import model.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.TreeSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectDAO extends EntityDAO<Project> {
    public ProjectDAO() {
        super("DELETE FROM projects WHERE id = ?", "DELETE FROM projects");
    }

    @Override
    public Set<Project> readAll() throws SQLException {
        Set<Project> projectSet = new TreeSet<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM projects");
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setCost(rs.getBigDecimal("cost"));
                readAllRelationalEntities(project, connection);
                projectSet.add(project);
            }
        }
        return projectSet;
    }

    @Override
    protected void readAllRelationalEntities(Project project, Connection connection) throws SQLException {
        project.setCompanies(readCompanies("SELECT * FROM companies c " +
                "JOIN (SELECT company_id FROM companies_projects WHERE project_id = ?) AS cp " +
                "ON cp.company_id = c.id", project.getId(), connection));
        project.setCustomers(readCustomers("SELECT * FROM customers c " +
                        "JOIN (SELECT customer_id FROM customers_projects WHERE project_id = ?) AS cp " +
                        "ON cp.customer_id = c.id",
                project.getId(), connection));
        project.setDevelopers(readDevelopers("SELECT * FROM developers d " +
                        "JOIN (SELECT developer_id FROM projects_developers WHERE project_id = ?) AS pd " +
                        "ON pd.developer_id = d.id",
                project.getId(), connection));
    }

    @Nullable
    public Project read(int id) throws SQLException {
        Project project = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                project = new Project();
                project.setId(id);
                project.setName(rs.getString("name"));
                project.setCost(rs.getBigDecimal("cost"));
            }
            readAllRelationalEntities(project, connection);
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
        Set<Company> companies = project.getCompanies();
        if (companies != null) {
            setRelationships("INSERT INTO companies_projects (company_id, project_id) VALUES (?, ?)", project.getId(),
                    false,
                    companies.stream().map(Company::getId).collect(Collectors.toSet()), connection);
        }
        Set<Developer> developers = project.getDevelopers();
        if (developers != null) {
            setRelationships("INSERT INTO projects_developers (project_id, developer_id) VALUES (?, ?)", project.getId(),
                    true,
                    developers.stream().map(Developer::getId).collect(Collectors.toSet()), connection);
        }
        Set<Customer> customers = project.getCustomers();
        if (customers != null) {
            setRelationships("INSERT INTO customers_projects (customer_id, project_id) VALUES (?, ?)", project.getId(),
                    false,
                    customers.stream().map(Customer::getId).collect(Collectors.toSet()), connection);
        }
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
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM companies_projects WHERE project_id = ?", id, connection);
        clearRelationships("DELETE FROM customers_projects WHERE project_id = ?", id, connection);
        clearRelationships("DELETE FROM projects_developers WHERE project_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
