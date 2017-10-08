package model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
public class BaseEntity implements Comparable<BaseEntity> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NotNull BaseEntity o) {
        return id - o.getId();
    }
}
