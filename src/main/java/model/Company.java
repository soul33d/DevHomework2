package model;

import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Set;

@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Nullable
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "companies_developers",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "developer_id")})
    private Set<Developer> developers;

    @Nullable
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "companies_projects",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects;

    @Nullable
    private Set<Customer> customers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(@Nullable Set<Developer> developers) {
        this.developers = developers;
    }

    @Nullable
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(@Nullable Set<Project> projects) {
        this.projects = projects;
    }

    @Nullable
    public Set<Customer> getCustomers() {
        return customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    public void setCustomers(@Nullable Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Company{id=");
        sb.append(id).append(", name='").append(name).append("\'");
        if (developers != null) {
            developers.forEach(developer -> sb.append("\n\t").append(developer).append(";"));
        }
        if (projects != null) {
            projects.forEach(project -> sb.append("\n\t").append(project).append(";"));
        }
        if (customers != null) {
            customers.forEach(customer -> sb.append("\n\t").append(customer).append(";"));
        }
        sb.append("}");
        return sb.toString();
    }
}
