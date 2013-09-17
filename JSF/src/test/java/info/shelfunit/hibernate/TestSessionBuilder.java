package info.shelfunit.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TestSessionBuilder {

    private static SessionFactory sessionFactory;
    private static Session session = null;

    public TestSessionBuilder() {}

    public Session getSession() {

	String prefix = "org/apache/james/admin/webapp/hibernate/pojos/";
	Configuration configuration = new Configuration();
	configuration.addCacheableFile(prefix + "Deadletter.hbm.xml")
	    .addCacheableFile(prefix + "Inbox.hbm.xml")
	    .addCacheableFile(prefix + "Users.hbm.xml")
	    .addCacheableFile(prefix + "Spool.hbm.xml");
	configuration.setProperty("hibernate.dialect",
				  "org.hibernate.dialect.MySQLDialect");
	configuration.setProperty("hibernate.connection.driver_class",
				  "com.mysql.jdbc.Driver");
	configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jamesemailtest");
	// configuration.setProperty("hibernate.hbm2ddl.auto", "create");
 
	sessionFactory = configuration.buildSessionFactory();
	session = sessionFactory.openSession();
	return session;
    }

} // end class
