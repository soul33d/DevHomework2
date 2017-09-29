package dao;

import model.Customer;
import model.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerDAO extends EntityDAO<Customer> {

    public CustomerDAO() {
        super("DELETE FROM customers WHERE id = ?", "DELETE FROM customers");
    }

    @Override
    public Set<Customer> readAll() throws SQLException {
        Set<Customer> customerSet = new HashSet<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                readAllRelationalEntities(customer, connection);
                customerSet.add(customer);
            }
        }
        return customerSet;
    }

    @Override
    protected void readAllRelationalEntities(Customer customer, Connection connection) throws SQLException {
        customer.setCompanies(readCompanies("SELECT * FROM companies c " +
                        "JOIN (SELECT company_id FROM " +
                        "(SELECT project_id FROM customers_projects WHERE customer_id = ?) AS cusp " +
                        "JOIN companies_projects cp ON cusp.project_id = cp.project_id) AS cc ON cc.company_id = c.id",
                customer.getId(), connection));
        customer.setProjects(readProjects("SELECT * FROM projects p " +
                        "JOIN (SELECT project_id FROM customers_projects WHERE customer_id = ?) AS cp " +
                        "ON cp.project_id = p.id",
                customer.getId(), connection));
    }

    @Nullable
    public Customer read(int id) throws SQLException {
        Customer customer = null;
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(id);
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
            }
            readAllRelationalEntities(customer, connection);
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
        Set<Project> projects = customer.getProjects();
        if (projects != null) {
            setRelationships("INSERT INTO customers_projects (customer_id, project_id) VALUES (?, ?)",
                    customer.getId(), true,
                    projects.stream().map(Project::getId).collect(Collectors.toSet()), connection);
        }
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
    protected void clearRelationships(int id, Connection connection) throws SQLException {
        clearRelationships("DELETE FROM customers_projects WHERE customer_id = ?", id, connection);
    }

    @Override
    public void deleteAll() throws SQLException {

    }
}
