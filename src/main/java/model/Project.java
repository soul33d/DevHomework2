package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project implements Comparable<Project> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private BigDecimal cost;

    @Nullable
    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private Set<Company> companies;

    @Nullable
    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private Set<Developer> developers;

    @Nullable
    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
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
        if (companies != null) companies.forEach(c -> sb.append("\n\t")
                .append("Company{")
                .append(c.getId()).append(", ")
                .append(c.getName()).append(c).append("}"));
        if (developers != null) {
            developers.forEach(developer -> sb.append("\n\t")
                    .append("Developer{")
                    .append(developer.getId()).append(", ")
                    .append(developer.getFirstName()).append(", ")
                    .append(developer.getSalary()).append("}"));
        }
        if (customers != null) {
            customers.forEach(customer -> sb.append("\n\t")
                    .append("Customer{")
                    .append(customer.getId()).append(", ")
                    .append(customer.getFirstName()).append(", ")
                    .append(customer.getLastName()).append("}"));
        }
        sb.append('}');
        return  sb.toString();
    }

    @Override
    public int compareTo(@NotNull Project o) {
        return id - o.id;
    }
}
