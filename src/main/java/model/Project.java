package model;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Set;

public class Project {
    private int id;
    private String name;
    private BigDecimal cost;

    @Nullable
    private Set<Company> companies;
    @Nullable
    private Set<Developer> developers;
    @Nullable
    private Set<Customer> customers;

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
    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(@Nullable Set<Company> companies) {
        this.companies = companies;
    }

    @Nullable
    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(@Nullable Set<Developer> developers) {
        this.developers = developers;
    }

    @Nullable
    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(@Nullable Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (!name.equals(project.name)) return false;
        return cost.equals(project.cost);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + cost.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Project{");
        sb.append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", cost=").append(cost).append("\"");
        if (companies != null) {
            companies.forEach(company -> sb.append("\n\t").append(company).append(";"));
        }
        if (developers != null) {
            developers.forEach(developer -> sb.append("\n\t").append(developer).append(";"));
        }
        if (customers != null) {
            customers.forEach(customer -> sb.append("\n\t").append(customer).append(";"));
        }
        sb.append('}');
        return  sb.toString();
    }
}
