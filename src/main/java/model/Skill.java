package model;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class Skill extends BaseEntity {
    private String name;

    @Nullable
    private Set<Developer> developers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(@Nullable Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (id != skill.id) return false;
        return name.equals(skill.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Skill{");
        sb.append("id=").append(id).append(", name='").append(name).append("\'");
        if (developers != null) {
            developers.forEach(developer -> sb.append("\n\t").append(developer).append(";"));
        }
        sb.append('}');
        return sb.toString();
    }
}
