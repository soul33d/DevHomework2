package model;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    @Nullable
    private List<Skill> skills;
    @Nullable
    private List<Project> projects;
    @Nullable
    private List<Company> companies;

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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Nullable
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(@Nullable List<Skill> skills) {
        this.skills = skills;
    }

    @Nullable
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(@Nullable List<Project> projects) {
        this.projects = projects;
    }

    @Nullable
    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(@Nullable List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Developer{");
        sb.append("id=").append(id).append(", firstName='").append(firstName).append('\'')
                .append(", lastName='").append(lastName).append('\'')
                .append(", salary=").append(salary).append("\'\n");
        if (skills != null) {
            skills.forEach(skill -> sb.append('\t').append(skill).append(";\n"));
        }
        if (projects != null) {
            projects.forEach(project -> sb.append('\t').append(project).append(";\n"));
        }
        if (companies != null) {
            companies.forEach(company -> sb.append('\t').append(company).append(";\n"));
        }
        sb.append('}');
        return sb.toString();
    }
}
