package model;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Set;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;

    @Nullable
    private Set<Skill> skills;
    @Nullable
    private Set<Project> projects;
    @Nullable
    private Set<Company> companies;

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
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(@Nullable Set<Skill> skills) {
        this.skills = skills;
    }

    @Nullable
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(@Nullable Set<Project> projects) {
        this.projects = projects;
    }

    @Nullable
    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(@Nullable Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Developer developer = (Developer) o;

        if (id != developer.id) return false;
        if (!firstName.equals(developer.firstName)) return false;
        if (!lastName.equals(developer.lastName)) return false;
        return salary.equals(developer.salary);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + salary.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Developer{");
        sb.append("id=").append(id).append(", firstName='").append(firstName).append('\'')
                .append(", lastName='").append(lastName).append('\'')
                .append(", salary=").append(salary).append("\'");
        if (skills != null) {
            skills.forEach(skill -> sb.append("\n\t").append(skill).append(";"));
        }
        if (projects != null) {
            projects.forEach(project -> sb.append("\n\t").append(project).append(";"));
        }
        if (companies != null) {
            companies.forEach(company -> sb.append("\n\t").append(company).append(";"));
        }
        sb.append('}');
        return sb.toString();
    }
}
