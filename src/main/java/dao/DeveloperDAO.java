package dao;

import model.Company;
import model.Developer;
import model.Project;
import model.Skill;

import java.sql.*;

public class DeveloperDAO implements EntityDAO<Developer> {
    public Developer read() {
        return null;
    }

    public void write(Developer developer) {
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

            setDeveloperRelationships(developer, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Developer developer) {
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

            clearSkillsRelationships(developer, connection);
            clearProjectsRelationships(developer, connection);
            clearCompaniesRelationships(developer, connection);

            setDeveloperRelationships(developer, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setDeveloperRelationships(Developer developer, Connection connection) throws SQLException {
        for (Skill skill : developer.getSkills()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO developers_skills (developer_id, skill_id) VALUES (?, ?)");
            ps.setInt(1, developer.getId());
            ps.setInt(2, skill.getId());
            ps.executeUpdate();
        }

        for (Project project : developer.getProjects()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO projects_developers (project_id, developer_id) VALUES (?, ?)");
            ps.setInt(1, project.getId());
            ps.setInt(2, developer.getId());
            ps.executeUpdate();
        }

        for (Company company : developer.getCompanies()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO companies_developers (company_id, developer_id) VALUES (?, ?)");
            ps.setInt(1, company.getId());
            ps.setInt(2, developer.getId());
            ps.executeUpdate();
        }
    }

    private void clearSkillsRelationships(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("DELETE * FROM developers_skills WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ps.executeUpdate();
    }

    private void clearProjectsRelationships(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("DELETE * FROM projects_developers WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ps.executeUpdate();
    }

    private void clearCompaniesRelationships(Developer developer, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement
                ("DELETE  * FROM companies_developers WHERE developer_id = ?");
        ps.setInt(1, developer.getId());
        ps.executeUpdate();
    }
}
