package controller;

import dao.IEntityDAO;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EntityController<T> {
    private AppController appController;
    private IEntityDAO<T> entityDAO;

    public EntityController(@NotNull AppController appController, @NotNull IEntityDAO<T> entityDAO) {
        this.appController = appController;
        this.entityDAO = entityDAO;
    }

    public <E>EntityController<E> getEntityController(Class<E> clazz) {
        return appController.getEntityController(clazz);
    }

    public Set<T> readAll() {
        try {
            return entityDAO.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public T read(int id) {
        try {
            return entityDAO.read(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean write(@NotNull T t) {
        try {
            entityDAO.write(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(@NotNull T t) {
        try {
            entityDAO.update(t);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            entityDAO.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAll() {
        try {
            entityDAO.deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
