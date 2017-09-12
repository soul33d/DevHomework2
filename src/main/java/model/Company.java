package model;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Company {
    private int id;
    private String name;

    @Nullable
    private List<Developer> developers;
    @Nullable
    private List<Project> projects;
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

    @Nullable
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(@Nullable List<Developer> developers) {
        this.developers = developers;
    }

    @Nullable
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(@Nullable List<Project> projects) {
        this.projects = projects;
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
        StringBuilder sb = new StringBuilder("Company{id=");
        sb.append(id).append(", name='").append(name).append("\'\n");
        if (developers != null) {
            developers.forEach(developer -> sb.append("\t").append(developer).append(";\n"));
        }
        if (projects != null) {
            projects.forEach(project -> sb.append("\t").append(project).append(";\n"));
        }
        if (customers != null) {
            customers.forEach(customer -> sb.append("\t").append(customer).append(";\n"));
        }
        sb.append("}");
        return sb.toString();
    }
}
