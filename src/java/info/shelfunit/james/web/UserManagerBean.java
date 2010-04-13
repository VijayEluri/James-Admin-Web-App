/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.shelfunit.james.web;


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
import org.apache.james.security.DigestUtil;
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
    private String algorithm;

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
     *
     * @param pass the String to be hashed
     * @param algorithm the algorithm to be used
     * @return String Base-64 encoding of digest
     *
     * @throws NoSuchAlgorithmException if the algorithm passed in cannot be found
     */
    public static String digestString( String pass, String algorithm )
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

    public String addUser(  ) throws NoSuchAlgorithmException  {
        
        String result = "success";
        Logger logger = this.shelfLogger.getLogger();
        logger.warn( "in method addUser" );

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try  {

            String passAlgo = this.digestString( getPassword(), getAlgorithm());
            logger.warn( "About to add user " + getUsername() + " with pass " + getPassword() );
            logger.warn( "Algorithm is " + getAlgorithm() + " converted password is: " + passAlgo );

            Users user = new Users();
            user.setUsername( getUsername());
            user.setPwdAlgorithm( getAlgorithm() );
            user.setPwdHash( passAlgo );
            session.save( user );
            session.getTransaction().commit();
        } catch ( org.hibernate.exception.ConstraintViolationException cvEx ) {
            logger.warn( "ConstraintExpception: perhaps user " + getUsername() + " already exists" );
            result = "duplicateUser";
        } // end try/catch
        
        session.close();
        return result;
    } // end method addUser

    public void changeUser() {
        Logger logger = this.shelfLogger.getLogger();
        logger.warn( "in method changeUser" );

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

    
    public String dropUser( String userNameToDrop ) {
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
        session.delete( userToDrop );

        session.getTransaction().commit();
        session.close();
        logger.warn( "Session closed, user " + userNameToDrop + " dropped" );
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
    public void setAdminUserBean( AdminUserBean adminUserBean ) {
        // this.adminUserBean = adminUserBean;
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
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername( String username ) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword( String password ) {
        this.password = password;
    }

    /**
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm the algorithm to set
     */
    public void setAlgorithm( String algorithm ) {
        this.algorithm = algorithm;
    }

} // end class info.shelfunit.hibernate.james.web.UserManagerBean
