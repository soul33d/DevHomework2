package controller;

import dao.*;
import model.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AppController {
    private Map<Class, EntityDAO> entitiesDAO;

    public AppController() {
        entitiesDAO = new HashMap<>();
        entitiesDAO.put(Developer.class, new DeveloperDAO());
        entitiesDAO.put(Skill.class, new SkillDAO());
        entitiesDAO.put(Company.class, new CompanyDAO());
        entitiesDAO.put(Project.class, new ProjectDAO());
        entitiesDAO.put(Customer.class, new CustomerDAO());
    }

    @SuppressWarnings("unchecked")
    public <T> EntityDAO<T> getEntityDAO(@NotNull Class<T> clazz) {
        return entitiesDAO.get(clazz);
    }

    public void close() {
        ConnectionPool.close();
    }
}
