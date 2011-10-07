package org.apache.james.admin.webapp.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.james.admin.webapp.hibernate.pojos.Users;
import org.apache.james.admin.webapp.hibernate.NewHibernateUtil;
import org.apache.james.admin.webapp.util.OptionsPropertiesBean;
import org.apache.james.admin.webapp.util.ShelfLogger;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 *
 * @author ericm
 */
@ManagedBean
@SessionScoped
public class AdminUserBean {
    
    private String userName;
    private String password;
    private String passAlgo;
    private int loggedIn = 0;
    private long deadLetterCount = 0;
    private ShelfLogger shelfLogger;
    private OptionsPropertiesBean optionBean;

    private String someVar;
    
    private List< Users > userList;
    private List< InboxCountHolder > inboxCountHolderList;

    /** Creates a new instance of AdminUserBean */
    public AdminUserBean() {

        try {
            this.shelfLogger = ShelfLogger.getInstance();
            this.optionBean  = OptionsPropertiesBean.getInstance();
            
            Logger logger = this.shelfLogger.getLogger();
            logger.fatal( "In constructor for AdminUserBean" );
            // get the DeadletterCount at the beginning
            this.findDeadletterCount();
            this.setDeadLetterCount( this.getDeadLetterCount() );
        } catch ( Exception e ) {
            e.printStackTrace( System.out );
        } finally {
            this.setLoggedIn( 0 );
            this.loggedIn = 0;
        } // end try/catch/finally
    } // end constructor
    
    /**
     * @return the userName
     */
    public synchronized String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public synchronized void setUserName( String userName ) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public synchronized String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public synchronized void setPassword( String password ) {
        this.password = password;
    }

    /**
     * @return the passAlgo
     */
    public synchronized String getPassAlgo() {
        return passAlgo;
    }

    /**
     * @param passAlgo the passAlgo to set
     */
    public synchronized void setPassAlgo( String passAlgo ) {
        this.passAlgo = passAlgo;
    }

    /**
     * @return the loggedIn
     */
    public synchronized int getLoggedIn() {
        return loggedIn;
    }

    /**
     * Variable used to determine in web app if the user is actually logged in
     * or not. 1 == logged in, 0 == not logged in.
     * @param loggedIn the loggedIn to set
     */
    public synchronized void setLoggedIn( int loggedIna ) {
        Logger logger = shelfLogger.getLogger();
        logger.warn( "Incoming arg loggedIn is " + loggedIna );
        logger.warn( "At beginning of setLoggedIn, Bean var loggedIn = " + this.getLoggedIn() );
        if ( ( loggedIna != 1 ) && ( loggedIna != 0 ) ) {
            this.loggedIn = 0;
        } else {
            this.loggedIn = loggedIna;
        }
        logger.warn( "At end of setLoggedIn, Bean var loggedIn = " + this.getLoggedIn() );
    } // end method setLoggedIn

    public synchronized String loginAction() {
        Logger logger = shelfLogger.getLogger();
        String result = "badLoginResult";
        try {
            logger.warn( "Starting loginAction, loggedIn set to " + this.getLoggedIn() +
                " result = " + result
            );

            List userNameList = optionBean.getUserNameList();
            List userPassList = optionBean.getUserPassList();
            logger.warn( "Size of userNameList: " + userNameList.size() );

            @SuppressWarnings( "unchecked" ) // I hate this - perhaps it is time for Spring?
            Iterator< String > iterator = userNameList.iterator();
            logger.warn( "About to try it a bit differently" );
            String temp;
            
            while ( iterator.hasNext() ) {
                temp = iterator.next();
                logger.warn( "temp is " + temp );
                logger.warn( "User name is " + this.userName );
                if ( this.getUserName().equals( temp ) ) {
                    result = "adminMainPage";
                    this.setLoggedIn( 1 );
                    logger.warn( "We have a match: " + this.getUserName() +
                        " is the same as " + temp
                    );
                    logger.warn( "At end of loop, loggedIn == " + this.getLoggedIn() +
                        " result = " + result
                    );
                } // if ( this.getUserName().equals( temp ) )
                logger.warn( "At end of loop, loggedIn == " + this.getLoggedIn() );
            } // end while ( iterator.hasNext() )
            
            logger.warn( "Leaving loginAction, loggedIn == " + this.getLoggedIn() );
            logger.warn( "At end of loginAction, here is result: " + result );
        } catch ( NullPointerException npEx ) {
            logger.info( npEx.getMessage() );
            logger.info( npEx.getClass() );
            npEx.printStackTrace( System.out );
        }//  javax.faces.el.EvaluationException: java.lang.NullPointerException
        return result;
    } // end method loginAction

    public synchronized String logoutAction() {
        Logger logger = shelfLogger.getLogger();
        logger.warn( "Starting UserSessionBean.logoutAction" );
        String result = "success";
        this.setLoggedIn( 0 );
        // this.setSessionUserProfileNum( 0 );
        logger.warn( "Calling this.setLoggedIn( 0 )" );
        return result;
    } // end method logoutAction()

    // 2010-04-20_07.02.15: Added try/catch block and check for size of userList
    // otherwise, if you call this after deleting a user, you will still see the
    // deleted user on the list
    public synchronized String getListOfUsers() {

        String methodResult = "badLoginResult";
        Logger logger = shelfLogger.getLogger();

        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        logger.warn( "Got session" );
        session.beginTransaction();

        @SuppressWarnings( "unchecked" )
        List< Users > tempList = session.createQuery( "from Users" ).list();
                // java.util.Collections.checkedList(  session.createQuery( "from Users" ).list(), Users.class );
        try {
            if ( this.userList.size() > 0 ) {
                this.userList.clear();
            }
        } catch ( NullPointerException npEx ) {
        }
        this.setUserList( tempList );
        // this.setUserList( ( List< Users > ) session.createQuery( "from Users" ).list() );
        // this.setUserList( java.util.Collections.checkedList( ( List< Users > ) session.createQuery( "from Users" ).list(), Users.class ) );

        session.getTransaction().commit();

        for ( int i = 0; i < getUserList().size(); i++ ) {
            Users nextUser = ( Users ) getUserList().get( i );
            logger.warn( "User: " + nextUser.getUsername() + " Algo: " +
                nextUser.getPwdAlgorithm() + " Pass: " + nextUser.getPwdHash()
            );
        } // for ( int i = 0; i < userList.size(); i++ )

        methodResult = "listUsers";
        return methodResult;
    } // end method getListOfUsers

    /**
     * @return the userList
     */
    public synchronized List< Users > getUserList() {
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public synchronized void setUserList( List< Users > userList ) {
        this.userList = userList;
    }

    public synchronized String findDeadletterCount() {
        String resultString = "success";
        Logger logger = shelfLogger.getLogger();

        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        logger.warn( "Got session" );
        session.beginTransaction();

        String SQL_QUERY = "select count( messageState ) from Deadletter deadletter"; // ok
        Long countR = ( Long ) session.createQuery( SQL_QUERY ).uniqueResult();
        this.setDeadLetterCount( countR.longValue() );
        logger.warn( "Here is count: " + countR );
        session.getTransaction().commit();
        return resultString;

    } // end method findDeadletterCount

    public String deleteDeadLetterContents() {
        String resultString = "success";
        Logger logger = shelfLogger.getLogger();

        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        logger.warn( "Got session" );
        session.beginTransaction();
        logger.warn( "About to create query" );
        Query q = session.createQuery( "delete from Deadletter" );
        q.executeUpdate();
        logger.warn( "About to commit" );
        session.getTransaction().commit();
        // reset the stats
        resultString = this.findDeadletterCount();

        return resultString;

    } // end method deleteDeadLetterContents

    /**
     * @return the deadLetterCount
     */
    public synchronized long getDeadLetterCount() {
        return deadLetterCount;
    }

    /**
     * @param deadLetterCount the deadLetterCount to set
     */
    public synchronized void setDeadLetterCount( long deadLetterCount ) {
        this.deadLetterCount = deadLetterCount;
    }
    
    public synchronized String getListOfInboxMessages( ) {
        String resultString = "listInboxMessages";
        Logger logger   = shelfLogger.getLogger();
        Session session = NewHibernateUtil.getSessionFactory().getCurrentSession();
        logger.warn( "Got session" );
        session.beginTransaction();

        List< InboxCountHolder > icList = new ArrayList< InboxCountHolder >();
        // this.getInboxCountHolderList();
        Iterator results = session.createQuery(
        "select inbox.recipients, count( recipients ) from Inbox inbox " +
        "group by inbox.recipients" )
        .list()
        .iterator();
        logger.warn( "Got the result, about to start the loop" );
        
        while ( results.hasNext() ) {

            Object[] row = ( Object[] ) results.next();
            String recip = ( String ) row[ 0 ];
            Long countR  = ( Long ) row[ 1 ];
            logger.warn( "Here is new object: recip: " + recip + " count: " + countR );
            InboxCountHolder icHolder = new InboxCountHolder( recip, countR );
            icList.add( icHolder );
        } // while ( iterator.hasNext() )
        
        logger.warn( "About to call adminUserBean.setInboxCountHolderList( icList )" );
        this.setInboxCountHolderList( icList );
        logger.warn( "Size of icList: " + icList.size() );
        session.getTransaction().commit();
        if ( icList.size() == 0 ) {
            resultString = "badLoginResult";
        } // if ( icList.size() == 0 )
       
        return resultString;
    } // end method getListOfInboxMessages

    /**
     * @return the inboxCountHolderList
     */
    public List< InboxCountHolder > getInboxCountHolderList() {
        return inboxCountHolderList;
    }

    /**
     * @param inboxCountHolderList the inboxCountHolderList to set
     */
    public void setInboxCountHolderList( List< InboxCountHolder > inboxCountHolderList ) {
        this.inboxCountHolderList = inboxCountHolderList;
    }

    /**
     * @return the someVar
     */
    public String getSomeVar() {
        return someVar;
    }

    /**
     * @param someVar the someVar to set
     */
    public void setSomeVar( String someVar ) {
        this.someVar = someVar;
    }
}
