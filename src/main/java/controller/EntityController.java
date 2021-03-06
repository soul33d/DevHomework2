package controller;

import dao.EntityDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Set;

public class EntityController<T> {
    private AppController appController;
    private EntityDAO<T> entityDAO;

    public EntityController(@NotNull AppController appController, @NotNull EntityDAO<T> entityDAO) {
        this.appController = appController;
        this.entityDAO = entityDAO;
    }

    public <E>EntityController<E> getEntityController(Class<E> clazz) {
        return appController.getEntityController(clazz);
    }

    public Set<T> readAll() {
        try {
            return entityDAO.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T read(int id) {
        try {
            return entityDAO.read(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean write(@NotNull T t) {
        try {
            entityDAO.write(t);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(@NotNull T t) {
        try {
            entityDAO.update(t);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            entityDAO.delete(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAll() {
        try {
            entityDAO.deleteAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
