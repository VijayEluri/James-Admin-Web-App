/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.shelfunit.james.web;

import javax.faces.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import info.shelfunit.hibernate.james.web.NewHibernateUtil;
import info.shelfunit.james.hibernate.pojos.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ericm
 */
public class UserManagerBeanTest {

    String username;

    public UserManagerBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        UUID uuid = UUID.randomUUID();
        username = uuid.toString();
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of digestString method, of class UserManagerBean.
     */
    @Test
    public void testDigestString() throws Exception {
        System.out.println( "digestString" );

        Integer theInt = new Integer( this.hashCode() );
        String pass = theInt.toString();
        String algorithm = "SHA-256";
        
        String result1 = UserManagerBean.digestString( pass, algorithm );
        String result2 = UserManagerBean.digestString( pass, algorithm );
        System.out.println( "Here is the password: " + pass );
        System.out.println( "result1: " + result1 );
        System.out.println( "result2: " + result2 );
        assertEquals( result1, result2 );
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    } // end method testDigestString()

    /**
     * Test of addUser method, of class UserManagerBean.
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println( "addUser" );
        UserManagerBean instance = new UserManagerBean();

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Integer theInt = new Integer( this.hashCode() );
        String pass = theInt.toString();
        String algorithm = "SHA-256";
        String queryString = "from Users where username like :theName"
        + " order by username";

        Query q = session.createQuery( queryString );
        q.setString( "theName", username + "%" );
        List userList = q.list();
        // user does not exist
        assertEquals( 0, userList.size() );

        String passAlgo = instance.digestString( pass, algorithm );

        Users user = new Users();
        user.setUsername( username );
        user.setPwdAlgorithm( algorithm );
        user.setPwdHash( passAlgo );
        session.save( user );

        session.getTransaction().commit();

        q = session.createQuery( queryString );
        q.setString( "theName", username + "%" );
        List userList2 = q.list();
        session.close();
        // user does exist
        assertEquals( 1, userList2.size() );

    } // end method testAddUser

    /**
     * Test of changeUser method, of class UserManagerBean.
     */
    @Test
    public void testChangeUser() {
        System.out.println( "changeUser" );
        UserManagerBean instance = new UserManagerBean();
        instance.changeUser();
        // TODO review the generated test code and remove the default call to fail.
        fail( "The test case is a prototype." );
    } // end method testChangeUser

    /**
     * Test of dropUser method, of class UserManagerBean.
     */
    @Test
    public void testDropUser() {
        System.out.println( "dropUser" );
        ActionEvent e = null;
        UserManagerBean instance = new UserManagerBean();
        String expResult = "";
        // String result = instance.dropUser( e );
        // assertEquals( expResult, result );
        // TODO review the generated test code and remove the default call to fail.
        fail( "The test case is a prototype." );
    } // end method testDropUser


} // end class UserManagerBeanTest 
