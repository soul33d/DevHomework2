package model;

import java.util.List;

public class Skill {
    private int id;
    private String name;
    private List<Integer> developersIds;

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
}
