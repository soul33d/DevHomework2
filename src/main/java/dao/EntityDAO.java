package dao;

public interface EntityDAO<T> {
    T read();

    void write(T t);

    void update(T t);
}
