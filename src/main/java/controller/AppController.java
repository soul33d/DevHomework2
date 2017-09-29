package controller;

import dao.*;
import model.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AppController {
    private Map<Class, EntityController> controllers;

    public AppController() {
        controllers = new HashMap<>();
        controllers.put(Developer.class, new EntityController<>(this, new DeveloperDAO()));
        controllers.put(Skill.class, new EntityController<>(this, new SkillDAO()));
        controllers.put(Company.class, new EntityController<>(this, new CompanyDAO()));
        controllers.put(Project.class, new EntityController<>(this, new ProjectDAO()));
        controllers.put(Customer.class, new EntityController<>(this, new CustomerDAO()));
    }

    @SuppressWarnings("unchecked")
    public <T> EntityController<T> getEntityController(@NotNull Class<T> clazz) {
        return controllers.get(clazz);
    }

    public void closeApp() {
        ConnectionPool.close();
        System.exit(0);
    }
}
