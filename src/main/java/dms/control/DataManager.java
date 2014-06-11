package dms.control;

import dms.exceptions.DatabaseException;
import dms.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Wizard of the magic data-persistence realm
 * @author Dominik Scholz
 * @version 0.2
 */
public class DataManager {

    private static DataManager instance;
    public static DataManager get() { return instance == null ? instance = new DataManager() : instance; }
    
    private final SessionFactory sessionFactory;
    private final ServiceRegistry serviceRegistry;
    
    private DataManager() {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        configuration.buildMappings();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    
    /**
     * Saves the object
     * @param object
     * @throws sigmascheduler.engine.SigmaSchedulerException
     */
    public void save(Object object) throws DatabaseException {
        Session session = buildSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (RuntimeException ex) {
            transaction.rollback();
            throw new DatabaseException("Transaction timeout: " + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    /**
     * Deletes the object
     * @param object 
     * @throws sigmascheduler.engine.SigmaSchedulerException 
     */
    public void delete(Object object) throws DatabaseException {
        Session session = buildSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(object);
            transaction.commit();
        } catch (RuntimeException ex) {
            transaction.rollback();
            throw new DatabaseException("Transaction timeout:" + ex.getMessage());
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns the current session
     * @return
     */
    public Session buildSession() throws DatabaseException {
        Session session = sessionFactory.getCurrentSession();
        if(session != null) session.close();
        try {
            session = sessionFactory.openSession();
        } catch (Exception ex) {
            throw new DatabaseException("Transaction timeout: problem creating session");
        }
        return session;
    }

    /**
     * Executes a query with a parameter
     * @param queryName the name of the query
     * @param param the parameter (inserted for :id)
     * @return the resultlist
     * @throws SigmaSchedulerException 
     */
    public List executeQuery(String queryName, Object param) throws DatabaseException {
        Session session = null;
        List fakeResult;
        List result = new ArrayList<Object>();
        try {
            session = buildSession();
            Query query = session.getNamedQuery(queryName);
            query.setParameter("id", param);
            fakeResult = query.list();
            for(Object o : fakeResult) result.add(o);
        } catch (Exception ex) {
            ex.printStackTrace();
//            throw new DatabaseException(ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * Executes a query
     * @param queryName the name of the query
     * @return the resultlist
     * @throws SigmaSchedulerException 
     */
    List executeQuery(String queryName) throws DatabaseException {
        Session session = null;
        List fakeResult;
        List result = new ArrayList<Object>();
        try {
            session = buildSession();
            Query query = session.getNamedQuery(queryName);
            fakeResult = query.list();
            for(Object o : fakeResult) result.add(o);
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        } finally {
            session.close();
        }
        return result;
    }
}
