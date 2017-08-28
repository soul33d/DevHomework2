package model;

import java.math.BigDecimal;
import java.util.List;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private List<Integer> skillsIds;
    private List<Integer> projectsIds;
    private List<Integer> companiesIds;

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

    public List<Integer> getSkillsIds() {
        return skillsIds;
    }

    public void setSkillsIds(List<Integer> skillsIds) {
        this.skillsIds = skillsIds;
    }

    public List<Integer> getProjectsIds() {
        return projectsIds;
    }

    public void setProjectsIds(List<Integer> projectsIds) {
        this.projectsIds = projectsIds;
    }

    public List<Integer> getCompaniesIds() {
        return companiesIds;
    }

    public void setCompaniesIds(List<Integer> companiesIds) {
        this.companiesIds = companiesIds;
    }
}
