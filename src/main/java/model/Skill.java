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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Skill{");
        sb.append("id=").append(id).append(", name='").append(name).append("\'\n");
        if (developers != null) {
            developers.forEach(developer -> sb.append("\t").append(developer).append(";\n"));
        }
        sb.append('}');
        return  sb.toString();
    }
}
