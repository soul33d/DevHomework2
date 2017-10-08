package model;

import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Set;

@Table(name = "customers")
public class Customer extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Nullable
    private Set<Company> companies;

    @Nullable
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customers_projects",
            joinColumns = {@JoinColumn(name = "customer_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(@Nullable Set<Company> companies) {
        this.companies = companies;
    }

    @Nullable
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(@Nullable Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (!firstName.equals(customer.firstName)) return false;
        return lastName.equals(customer.lastName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Customer{id=");
        sb.append(id).append(", firstName='").append(firstName).append("\'")
                .append(", lastName='").append(lastName).append("\'");
        if (companies != null) companies.forEach(c -> sb.append("\n\t").append(c).append(";"));
        if (projects != null) projects.forEach(p -> sb.append("\n\t").append(p).append(";"));
        sb.append("}");
        return sb.toString();
    }
}
