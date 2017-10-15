package dao.hibernate;

import dao.IEntityDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EntityDAO<T> implements IEntityDAO<T> {
    protected SessionFactory sessionFactory;
    protected Class<T> entityClazz;
    protected String simpleName;

    public EntityDAO(Class<T> entityClazz) {
        sessionFactory = SessionFactorySingleton.getSessionFactory();
        this.entityClazz = entityClazz;
        simpleName = this.entityClazz.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<T> readAll() throws Exception {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from " + simpleName);
        List<T> entities = query.list();
        session.close();
        return new TreeSet<>(entities);
    }

    @Override
    public T read(int id) throws Exception {
        Session session = sessionFactory.openSession();
        T t = session.get(entityClazz, id);
        session.close();
        return t;
    }

    @Override
    public void write(T t) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(T t) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(int id) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from "+ simpleName +" where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAll() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from " + simpleName);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
