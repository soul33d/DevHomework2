package dao;

import model.Company;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class CompanyDAO extends EntityDAO<Company> {

    private static final String PROJECT_IDS_FOR_COMPANY = "SELECT project_id FROM companies_projects " +
            "WHERE company_id = ?";

    @NotNull
    public Company read(int id) throws SQLException {
        Company company = new Company();
        company.setId(id);
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM companies WHERE  id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                company.setName(rs.getString("name"));
            }
            company.setCustomersIds(readIds("SELECT customer_id FROM (" + PROJECT_IDS_FOR_COMPANY +
                    ") as cp JOIN customers_projects cusp ON cp.project_id = cusp.project_id", id, connection));
            company.setDevelopersIds(readIds("SELECT developer_id FROM companies_developers WHERE company_id = ?",
                    id, connection));
            company.setProjectsIds(readIds(PROJECT_IDS_FOR_COMPANY, id, connection));
        }
        return company;
    }

    public void write(@NotNull Company company) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO companies (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, company.getName());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to write company, no rows affected");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                company.setId(generatedKeys.getInt(1));
            } else throw new SQLException("Failed to write company, no id obtained");

            setRelationships(company, connection);
        }
    }

    private void setRelationships(@NotNull Company company, Connection connection) throws SQLException {
        setRelationships("INSERT INTO companies_developers (company_id, developer_id) VALUES (?, ?)",
                company.getId(), true, company.getDevelopersIds(), connection);
        setRelationships("INSERT INTO companies_projects (company_id, project_id) VALUES (?, ?)",
                company.getId(), true, company.getProjectsIds(), connection);
    }

    public void update(@NotNull Company company) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE companies SET name = ? WHERE id = ?");
            ps.setString(1, company.getName());
            ps.setInt(2, company.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update, no rows affected");
            }
            clearRelationships("DELETE * FROM companies_developers WHERE company_id = ?",
                    company.getId(), connection);
            clearRelationships("DELETE * FROM companies_projects WHERE company_id = ?",
                    company.getId(), connection);
        }
    }
}
