package model;

import java.util.List;

public class Company {
    private int id;
    private String name;
    private List<Integer> developersIds;
    private List<Integer> projectsIds;
    private List<Integer> customersIds;

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

    public List<Integer> getDevelopersIds() {
        return developersIds;
    }

    public void setDevelopersIds(List<Integer> developersIds) {
        this.developersIds = developersIds;
    }

    public List<Integer> getProjectsIds() {
        return projectsIds;
    }

    public void setProjectsIds(List<Integer> projectsIds) {
        this.projectsIds = projectsIds;
    }

    public List<Integer> getCustomersIds() {
        return customersIds;
    }

    public void setCustomersIds(List<Integer> customersIds) {
        this.customersIds = customersIds;
    }
}
