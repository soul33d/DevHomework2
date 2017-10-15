package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "developers")
public class Developer implements Comparable<Developer> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "salary")
    private BigDecimal salary;

    @Nullable
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "developers_skills",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id")})
    private Set<Skill> skills;

    @Nullable
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "projects_developers",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects;

    @Nullable
    @ManyToMany(mappedBy = "developers", fetch = FetchType.EAGER)
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
            skills.forEach(skill -> sb.append("\n\t")
                    .append("Skill{")
                    .append(skill.getId()).append(", ")
                    .append(skill.getName()).append(", ")
                    .append(skill).append("}"));
        }
        if (projects != null) {
            projects.forEach(project -> sb.append("\n\t")
                    .append("Project{")
                    .append(project.getId()).append(", ")
                    .append(project.getName()).append(", ")
                    .append(project.getCost())
                    .append("}"));
        }
        if (companies != null) companies.forEach(c -> sb.append("\n\t")
                .append("Company{")
                .append(c.getId()).append(", ")
                .append(c.getName())
                .append(c).append("}"));
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(@NotNull Developer o) {
        return id - o.id;
    }
}
