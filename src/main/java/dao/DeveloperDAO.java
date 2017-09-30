package dao;

import model.Company;
import model.Developer;
import model.Project;
import model.Skill;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.TreeSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperDAO extends EntityDAO<Developer> {

    public DeveloperDAO() {
        super("DELETE FROM developers WHERE id = ?", "DELETE FROM developers");
    }

    @Override
    public Set<Developer> readAll() throws SQLException {
        Set<Developer> developerSet = new TreeSet<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM developers");
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getInt("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setLastName(rs.getString("last_name"));
                developer.setSalary(rs.getBigDecimal("salary"));
                readAllRelationalEntities(developer, connection);
                developerSet.add(developer);
            }
        }
        return developerSet;
    }

    @Override
    protected void readAllRelationalEntities(Developer developer, Connection connection) throws SQLException {
        int id = developer.getId();
        developer.setSkills(readSkills(id, connection));
        developer.setCompanies(readCompanies("SELECT * FROM companies c " +
                "JOIN (SELECT company_id FROM companies_developers WHERE developer_id = ?) AS cd " +
                "ON cd.company_id = c.id", id, connection));
        developer.setProjects(readProjects("SELECT * FROM projects p " +
                "JOIN (SELECT project_id FROM projects_developers WHERE developer_id = ?) AS pd " +
                "ON pd.project_id = p.id", id, connection));
    }

    @SuppressWarnings("Duplicates")
    private Set<Skill> readSkills(int id, Connection connection) {
        Set<Skill> skillSet = new TreeSet<>();
        try {
            PreparedStatement ps = connection.prepareStatement
                    ("SELECT * FROM skills s JOIN " +
                            "(SELECT skill_id FROM developers_skills WHERE developer_id = ?) AS ds " +
                            "ON ds.skill_id = s.id");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("id"));
                skill.setName(rs.getString("name"));
                skillSet.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skillSet;
    }

    @Nullable
    public Developer read(int id) throws SQLException {
        Developer developer = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM developers WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                developer = new Developer();
                developer.setId(id);
                developer.setFirstName(rs.getString("first_name"));
                developer.setLastName(rs.getString("last_name"));
                developer.setSalary(rs.getBigDecimal("salary"));
            }
            readAllRelationalEntities(developer, connection);
            return developer;
        }
    }

    public void write(@NotNull Developer developer) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO developers (first_name, last_name, salary) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setDouble(3, developer.getSalary().doubleValue());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating developer failed, no rows affected");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                developer.setId(generatedKeys.getInt(1));
            } else throw new SQLException("Creating developer failed, no id obtained");

            setRelationships(developer, connection);
        }
    }

    public void update(@NotNull Developer developer) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE developers SET first_name = ?, last_name = ?, salary = ? WHERE id = ?");
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setDouble(3, developer.getSalary().doubleValue());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating developer failed, no rows affected");
            }

            clearRelationships(developer.getId(), connection);
            setRelationships(developer, connection);
        }
    }

    @Override
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM developers_skills WHERE developer_id = ?", id, connection);
        clearRelationships("DELETE FROM projects_developers WHERE developer_id = ?", id, connection);
        clearRelationships("DELETE FROM companies_developers WHERE developer_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }

    private void setRelationships(Developer developer, Connection connection) throws SQLException {
        Set<Skill> skills = developer.getSkills();
        if (skills != null) {
            setRelationships("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?)",
                    developer.getId(), true,
                    skills.stream().map(Skill::getId).collect(Collectors.toSet()), connection);
        }
        Set<Project> projects = developer.getProjects();
        if (projects != null) {
            setRelationships("INSERT INTO projects_developers (project_id, developer_id) VALUES (?, ?)",
                    developer.getId(), false,
                    projects.stream().map(Project::getId).collect(Collectors.toSet()), connection);
        }
        Set<Company> companies = developer.getCompanies();
        if (companies != null) {
            setRelationships("INSERT INTO companies_developers (company_id, developer_id) VALUES (?, ?)",
                    developer.getId(), false,
                    companies.stream().map(Company::getId).collect(Collectors.toSet()), connection);
        }
    }
}
