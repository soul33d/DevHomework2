package dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityDAO<T> {
    @Nullable
    T read(int id);

    void write(@NotNull T t);

    void update(@NotNull T t);
}
