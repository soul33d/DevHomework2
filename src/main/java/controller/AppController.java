package controller;


import dao.hibernate.EntityDAO;
import dao.hibernate.SessionFactorySingleton;
import model.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AppController {
    private Map<Class, EntityController> controllers;

    public AppController() {
        controllers = new HashMap<>();
        controllers.put(Developer.class, new EntityController<>(this, new EntityDAO<>(Developer.class)));
        controllers.put(Skill.class, new EntityController<>(this, new EntityDAO<>(Skill.class)));
        controllers.put(Company.class, new EntityController<>(this, new EntityDAO<>(Company.class)));
        controllers.put(Project.class, new EntityController<>(this, new EntityDAO<>(Project.class)));
        controllers.put(Customer.class, new EntityController<>(this, new EntityDAO<>(Customer.class)));
    }

    @SuppressWarnings("unchecked")
    public <T> EntityController<T> getEntityController(@NotNull Class<T> clazz) {
        return controllers.get(clazz);
    }

    public void closeApp() {
        SessionFactorySingleton.getSessionFactory().close();
        System.exit(0);
    }
}
