package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "skills")
public class Skill implements Comparable<Skill> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "name")
    private String name;
    @Nullable
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "skills", fetch = FetchType.EAGER)
    private Set<Developer> developers;

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
            developers.forEach(developer -> sb.append("\n\t")
                    .append("Developer{")
                    .append(developer.getId()).append(", ")
                    .append(developer.getFirstName()).append(", ")
                    .append(developer.getLastName())
                    .append(developer.getSalary()).append("}"));
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(@NotNull Skill o) {
        return id - o.id;
    }
}
