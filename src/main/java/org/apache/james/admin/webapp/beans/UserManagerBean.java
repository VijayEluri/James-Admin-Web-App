package org.apache.james.admin.webapp.beans;


import info.shelfunit.hibernate.james.web.NewHibernateUtil;
import info.shelfunit.util.ShelfLogger;
import info.shelfunit.james.hibernate.pojos.Users;

import org.apache.log4j.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author ericm
 */
public class UserManagerBean {

    private ShelfLogger shelfLogger;
    private AdminUserBean adminUserBean;

    private String username;
    private String password;
    private String passwordConfirm;
    private String algorithm;
    private String userNameToDrop;
    private String userNameToDropConfirm;
    private String statusMessage;

    /** Creates a new instance of UserManagerBean */
    public UserManagerBean() {
        try {
            this.shelfLogger = ShelfLogger.getInstance();

            Logger logger = this.shelfLogger.getLogger();
            logger.fatal( "In constructor for UserManagerBean" );
        } catch ( Exception e ) {
            e.printStackTrace( System.out );
        } finally {

        } // end try/catch/finally
    } // end constructor


    /**
     * Calculate digest of given String using given algorithm.
     * Encode digest in MIME-like base64.
     * from James class org.apache.james.security.DigestUtil
     * @param pass the String to be hashed
     * @param algorithm the algorithm to be used
     * @return String Base-64 encoding of digest
     *
     * @throws NoSuchAlgorithmException if the algorithm passed in cannot be found
     */
    
    public synchronized static String digestString( String pass, String algorithm )
    throws NoSuchAlgorithmException  {

        MessageDigest md;
        ByteArrayOutputStream bos;

        try {
            md = MessageDigest.getInstance( algorithm );
            byte[] digest = md.digest( pass.getBytes( "iso-8859-1" ) );
            bos = new ByteArrayOutputStream();
            OutputStream encodedStream = MimeUtility.encode( bos, "base64" );
            encodedStream.write( digest );
            return bos.toString("iso-8859-1");
        } catch ( IOException ioe ) {
            throw new RuntimeException( "Fatal error: " + ioe );
        } catch ( MessagingException me ) {
            throw new RuntimeException( "Fatal error: " + me );
        } // end try/catch
    } // end method digestString
    

    public synchronized String addUser(  ) throws NoSuchAlgorithmException  {

        String result = "success";
        Logger logger = this.shelfLogger.getLogger();
        logger.warn( "in method addUser" );
        /*
        logger.warn( "Confirm the password" );
        if ( !this.getPassword().equals( this.getConfirmPassword() ) ) {
            logger.warn( this.getPassword() + " is not equal to " + this.getConfirmPassword() );
            return "passwordsNotEqual";
        } */

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try  {

            // String passAlgo = this.digestString( getPassword(), getAlgorithm() );
            String passAlgo = this.digestString( getPassword(), getAlgorithm() );

            logger.warn( "About to add user " + getUsername() );
            logger.warn( "Algorithm is " + getAlgorithm() + " converted password is: " + passAlgo );

            Users user = new Users();
            user.setUsername( getUsername() );
            user.setPwdAlgorithm( getAlgorithm() );
            user.setPwdHash( passAlgo );
            session.save( user );
            session.getTransaction().commit();
            this.setStatusMessage( "The user " + this.getUsername() + " was added to the database" );
        } catch ( ConstraintViolationException cvEx ) {
            logger.warn( "ConstraintExpception: perhaps user " + getUsername() + " already exists" );
            logger.warn( cvEx.getMessage() );
            result = "duplicateUser";
        } // end try/catch
        
        session.close();
        return result;
    } // end method addUser
    

    public void changeUserPassword() {
        Logger logger = this.shelfLogger.getLogger();
        logger.warn( "in method changeUserPassword" );

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String queryString = "from Users where username like :theName"
        + " order by username";

        Query q = session.createQuery( queryString );
        q.setString( "theName", "hello%" );
        List userList = q.list();

        StringBuffer nameBuff = new StringBuffer();

        for ( int i = 0; i < userList.size(); i++ ) {
            nameBuff.delete( 0, nameBuff.length() );
            Users nextUser = ( Users ) userList.get( i );
            logger.warn( "User: " + nextUser.getUsername() + " Algo: " +
                nextUser.getPwdAlgorithm() + " Pass: " + nextUser.getPwdHash()
            );
            nameBuff.append( nextUser.getUsername() );
            // update the name
            nextUser.setAlias( nameBuff.reverse().toString() );
            session.flush();
        } // for ( int i = 0; i < userList.size(); i++ )

        session.getTransaction().commit();
        session.close();

    } // end method changeUser


    public synchronized String dropUser(  ) {
        String resultString = "success";
        Logger logger = this.shelfLogger.getLogger();
        logger.warn( "in method dropUser with arg " + userNameToDrop );

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String queryString = "from Users where username like :theName"
        + " order by username";
        // "from users" does not work, "from Users" does
        // "from Users order by username"

        Query q = session.createQuery( queryString );
        q.setString( "theName", userNameToDrop );
        Users userToDrop = ( Users ) q.uniqueResult();
        if ( userToDrop == null ) {
            logger.warn( "User " + userNameToDrop + " does not exist" );
            this.setStatusMessage( "The user " + this.getUserNameToDrop() + " does not exist" );
            resultString = "dropUserDoesNotExist";
        } else {
            session.delete( userToDrop );
            session.getTransaction().commit();
            this.setStatusMessage( "The user " + this.getUserNameToDrop() + " was deleted" );
            logger.warn( "The user " + this.getUserNameToDrop() + " was deleted" );
            resultString = "success";
            logger.warn( "Setting resultString to " + resultString );
        } // end if ( userToDrop == null ) / else
        session.close();

        return resultString;

    } // end method dropUser

    /*
    public String dropUser( ActionEvent e ) {
        String resultString = "success";
        Logger logger = this.shelfLogger.getLogger();

        String userNameToDrop = FacesContext.getCurrentInstance().
                getExternalContext().
                getRequestMap().
                get( "userNameToDrop" ).toString();
        logger.warn( "in method dropUser with arg " + userNameToDrop );
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String queryString = "from Users where username like :theName"
        + " order by username";
        // "from users" does not work, "from Users" does
        // "from Users order by username"

        Query q = session.createQuery( queryString );
        q.setString( "theName", userNameToDrop );
        Users userToDrop = ( Users ) q.uniqueResult();
        session.delete( userToDrop );

        session.getTransaction().commit();
        session.close();
        logger.warn( "Session closed, user " + userNameToDrop + " dropped" );
        return resultString;

    } // end method dropUser
    */

    /**
     * @return the adminUserBean
     */
    public AdminUserBean getAdminUserBean() {
        return adminUserBean;
    }

    /**
     * @param adminUserBean the adminUserBean to set
     */
    public void setAdminUserBean(  ) {
        this.adminUserBean =
            ( AdminUserBean ) FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .getSessionMap().
            get( "adminUserBean" );
    } // end method setAdminUserBean

    /**
     * @return the username
     */
    public synchronized String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public synchronized void setUsername( String username ) {
        this.username = username;
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
     * @return the algorithm
     */
    public synchronized String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm the algorithm to set
     */
    public synchronized void setAlgorithm( String algorithm ) {
        this.algorithm = algorithm;
    }

    /**
     * @return the userNameToDrop
     */
    public synchronized String getUserNameToDrop() {
        return userNameToDrop;
    }

    /**
     * @param userNameToDrop the userNameToDrop to set
     */
    public synchronized void setUserNameToDrop( String userNameToDrop ) {
        this.userNameToDrop = userNameToDrop;
    }

    /**
     * @return the userNameToDropConfirm
     */
    public synchronized String getUserNameToDropConfirm() {
        return userNameToDropConfirm;
    }

    /**
     * @param userNameToDropConfirm the userNameToDropConfirm to set
     */
    public synchronized void setUserNameToDropConfirm( String userNameToDropConfirm ) {
        this.userNameToDropConfirm = userNameToDropConfirm;
    }

    /**
     * @return the confirmPassword
     */
    public synchronized String getConfirmPassword() {
        return passwordConfirm;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public synchronized void setConfirmPassword( String confirmPassword ) {
        this.passwordConfirm = confirmPassword;
    }

    /**
     * @return the statusMessage
     */
    public synchronized String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @param statusMessage the statusMessage to set
     */
    public synchronized void setStatusMessage( String statusMessage ) {
        this.statusMessage = statusMessage;
    }

} // end class info.shelfunit.hibernate.james.web.UserManagerBean

