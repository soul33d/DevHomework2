package dao;

import model.Company;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO extends EntityDAO<Company> {

    private static final String PROJECT_IDS_FOR_COMPANY = "SELECT project_id FROM companies_projects " +
            "WHERE company_id = ?";


    @Override
    public List<Company> readAll() throws SQLException {
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM companies");
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                readAllRelationalIds(company, connection);
                companyList.add(company);
            }
        }
        return companyList;
    }

    @Override
    protected void readAllRelationalIds(Company company, Connection connection) throws SQLException {
        company.setCustomersIds(readIds("SELECT customer_id FROM (" + PROJECT_IDS_FOR_COMPANY +
                ") as cp JOIN customers_projects cusp ON cp.project_id = cusp.project_id", company.getId(), connection));
        company.setDevelopersIds(readIds("SELECT developer_id FROM companies_developers WHERE company_id = ?",
                company.getId(), connection));
        company.setProjectsIds(readIds(PROJECT_IDS_FOR_COMPANY, company.getId(), connection));
    }

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
            readAllRelationalIds(company, connection);
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
            clearRelationships(company.getId(), connection);
            setRelationships(company, connection);
        }
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM companies WHERE id = ?";
    }

    @Override
    protected String deleteAllQuery() {
        return "DELETE FROM companies";
    }

    @Override
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM companies_developers WHERE company_id = ?", id, connection);
        clearRelationships("DELETE FROM companies_projects WHERE company_id = ?", id, connection);
    }
}
