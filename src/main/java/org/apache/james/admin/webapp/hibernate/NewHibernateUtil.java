/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.james.admin.webapp.hibernate;

// import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author ericm
 */
public class NewHibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) config file.
	    // ORIG: sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.out.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session openSession() {
	return sessionFactory.openSession();
    }

    public static Session getCurrentSession() {
	return sessionFactory.getCurrentSession();
    }

} // end class
