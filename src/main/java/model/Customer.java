package model;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;

    @Nullable
    private List<Company> companies;
    @Nullable
    private List<Project> projects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(@Nullable List<Company> companies) {
        this.companies = companies;
    }

    @Nullable
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(@Nullable List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Customer{id=");
        sb.append(id).append(", firstName='").append(firstName).append("\'")
                .append(", lastName='").append(lastName).append("\'\n");
        if (companies != null) companies.forEach(c -> sb.append("\t").append(c).append(";\n"));
        if (projects != null) projects.forEach(p -> sb.append("\t").append(p).append(";\n"));
        sb.append("}");
        return sb.toString();
    }
}
