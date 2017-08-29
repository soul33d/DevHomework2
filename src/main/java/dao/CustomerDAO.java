package dao;

import model.Customer;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends EntityDAO<Customer> {

    private static final String CUSTOMER_PROJECTS_IDS = "SELECT project_id FROM customers_projects WHERE customer_id = ?";

    @Override
    public List<Customer> readAll() throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                readAllRelationalIds(customer, connection);
                customerList.add(customer);
            }
        }
        return customerList;
    }

    @Override
    protected void readAllRelationalIds(Customer customer, Connection connection) throws SQLException {
        customer.setCompaniesIds(readIds("SELECT company_id FROM (" + CUSTOMER_PROJECTS_IDS + ") AS cusp " +
                "JOIN companies_projects cp ON cusp.project_id = cp.project_id", customer.getId(), connection));
        customer.setProjectsIds(readIds(CUSTOMER_PROJECTS_IDS, customer.getId(), connection));
    }

    @NotNull
    public Customer read(int id) throws SQLException {
        Customer customer = new Customer();
        customer.setId(id);
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
            }
            readAllRelationalIds(customer, connection);
        }
        return customer;
    }

    public void write(@NotNull Customer customer) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    ("INSERT INTO customers (first_name, last_name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Writing customer failed, no rows affected");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer.setId(generatedKeys.getInt(1));
            } else throw new SQLException("Writing customer failed, no id obtained");

            setRelationships(customer, connection);
        }
    }

    private void setRelationships(@NotNull Customer customer, Connection connection) throws SQLException {
        setRelationships("INSERT INTO customers_projects (customer_id, project_id) VALUES (?, ?)",
                customer.getId(), true, customer.getProjectsIds(), connection);
    }

    public void update(@NotNull Customer customer) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement
                    ("UPDATE customers SET first_name = ?, last_name = ? WHERE id = ?");
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setInt(3, customer.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to update customer, no rows affected");
            }
            clearRelationships(customer.getId(), connection);
            setRelationships(customer, connection);
        }
    }

    @Override
    protected String deleteQuery() {
        return "DELETE FROM customers WHERE id = ?";
    }

    @Override
    protected String deleteAllQuery() {
        return "DELETE FROM customers";
    }

    @Override
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM customers_projects WHERE customer_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
