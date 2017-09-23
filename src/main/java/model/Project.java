package model;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

public class Project {
    private int id;
    private String name;
    private BigDecimal cost;

    @Nullable
    private List<Company> companies;
    @Nullable
    private List<Developer> developers;
    @Nullable
    private List<Customer> customers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Nullable
    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(@Nullable List<Company> companies) {
        this.companies = companies;
    }

    @Nullable
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(@Nullable List<Developer> developers) {
        this.developers = developers;
    }

    @Nullable
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(@Nullable List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Project{");
        sb.append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", cost=").append(cost).append("\"\n");
        if (companies != null) {
            companies.forEach(company -> sb.append('\t').append(company).append(";\n"));
        }
        if (developers != null) {
            developers.forEach(developer -> sb.append("\t").append(developer).append(";\n"));
        }
        if (customers != null) {
            customers.forEach(customer -> sb.append("\t").append(customer).append(";\n"));
        }
        sb.append('}');
        return  sb.toString();
    }
}
