package model;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Skill {
    private int id;
    private String name;

    @Nullable
    private List<Developer> developers;

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
}
