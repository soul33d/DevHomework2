package dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {

    public static SessionFactory getSessionFactory(){
        return Factory.SESSION_FACTORY;
    }

    private static class Factory {
        private static final SessionFactory SESSION_FACTORY;
        static {
            SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
        }
    }
}
