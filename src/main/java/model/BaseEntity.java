package model;

import org.jetbrains.annotations.NotNull;

public class BaseEntity implements Comparable<BaseEntity> {
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
