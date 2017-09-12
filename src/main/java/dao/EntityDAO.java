package dao;

import model.Company;
import model.Customer;
import model.Developer;
import model.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityDAO<T> {

    private static final String FAILED_TO_DELETE_MSG = "Failed to delete, no rows affected";

    public abstract List<T> readAll() throws SQLException;

    protected abstract void readAllRelationalEntities(T t, Connection connection) throws SQLException;

    List<Developer> readDevelopers(String sql, int id, Connection connection) {
        List<Developer> developerList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Developer developer = new Developer();
                developer.setId(rs.getInt("id"));
                developer.setFirstName(rs.getString("first_name"));
                developer.setLastName(rs.getString("last_name"));
                developer.setSalary(rs.getBigDecimal("salary"));
                developerList.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developerList;
    }

    List<Project> readProjects(String sql, int id, Connection connection) {
        List<Project> projectList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("name"));
                project.setCost(rs.getBigDecimal("cost"));
                projectList.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    List<Customer> readCustomers(String sql, int id, Connection connection) {
        List<Customer> customerList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Company> readCompanies(String sql, int id, Connection connection) {
        List<Company> companyList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt(id));
                company.setName(rs.getString("name"));
                companyList.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    @Nullable
    public abstract T read(int id) throws SQLException;

    public abstract void write(@NotNull T t) throws SQLException;

    void setRelationships
            (String sql, int id, boolean entityIdFirst, List<Integer> ids, Connection connection) throws SQLException {
        for (Integer relationId : ids) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entityIdFirst ? id : relationId);
            ps.setInt(2, entityIdFirst ? relationId : id);
            ps.executeUpdate();
        }
    }

    public abstract void update(@NotNull T t) throws SQLException;

    void clearRelationships(String sql, int id, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(deleteQuery());
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(FAILED_TO_DELETE_MSG);
            }
        }
    }

    protected abstract String deleteQuery();

    protected abstract String deleteAllQuery();

    protected abstract void clearRelationships(int id, Connection connection) throws SQLException;

    public void deleteAll() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            int affectedRows = statement.executeUpdate(deleteAllQuery());
            if (affectedRows == 0) {
                throw new SQLException(FAILED_TO_DELETE_MSG);
            }

        }
    }
}
