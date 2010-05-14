package info.shelfunit.util;

import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.faces.model.SelectItem;

/**
 *
 * @author ericm
 */
public final class OptionsPropertiesBean {
  
    private OptionsPropertiesBean() {
        setIntPropSize( 0 );
             
        this.setPassAlgoTotal( 0 );
        this.setUserNameTotal( 0 );
        this.setUserPassTotal( 0 );
        startProp();
        setTotals();
        populateArrayLists();
 
    } // end constructor
    
    private Properties myProp = new Properties();
    private int intPropSize;

    private int passAlgoTotal;
    private int userNameTotal;
    private int userPassTotal;
    
    private static List<SelectItem> passAlgoList = new ArrayList<SelectItem>();
    private static List<String> userNameList     = new ArrayList<String>();
    private static List<SelectItem> userPassList = new ArrayList<SelectItem>();
    
    public String getNamedProperty( String name ) {
        return getMyProp().getProperty( name );
    }    
    
    public void setTotals() {
        try {    
            this.setPassAlgoTotal(
                Integer.parseInt( getMyProp().getProperty( "passAlgoTotal" )  )
            );
            this.setUserNameTotal(
                Integer.parseInt( getMyProp().getProperty( "userNameTotal" )  )
            );
            this.setUserPassTotal(
                Integer.parseInt( getMyProp().getProperty( "userPassTotal" )  )
            );
        } catch ( Exception e ) {
            e.printStackTrace( System.out );
        }
        
    } // end method setTotals
    
    
    public int getIntPropSize() {
        if ( intPropSize == 0 ) {
            startProp();
        }
        intPropSize = getMyProp().size();
        return intPropSize;
    } // getIntPropSize
        
    public void startProp() {
        
        try {
            InputStream is = getClass().getResourceAsStream( "/options.properties" );
            getMyProp().load( is ); // this may throw IOException
        } catch ( IOException ioe ) {
            ioe.printStackTrace( System.out ); 
        } catch ( NullPointerException npEx ) {
            npEx.printStackTrace( System.out );
        } catch ( Exception e ) {
            e.printStackTrace( System.out );
        } // end try/catch
    } // end method startProp
	
    private String getDate() {
        GregorianCalendar theCal = new GregorianCalendar();
        return ( theCal.get( Calendar.YEAR ) + "-" +
            ( theCal.get( Calendar.MONTH ) + 1 ) + "-" +
            theCal.get( Calendar.DATE ) + "-" +
            theCal.get( Calendar.HOUR ) + ":" +
            theCal.get( Calendar.MINUTE ) + ":" +
            theCal.get( Calendar.SECOND )
        );
    } // end method getDate

    public Properties getMyProp() {
        return myProp;
    }

    public void setMyProp( Properties myProp ) {
        this.myProp = myProp;
    }

    public void setIntPropSize( int intPropSize ) {
        this.intPropSize = intPropSize;
    }

    private void populateArrayLists() {
        int x = 0;        
        
        for ( x = 0; x < this.getPassAlgoTotal(); x++ ) {
            SelectItem item = new SelectItem( 
                this.getNamedProperty( "passAlgo_" + x ),
                this.getNamedProperty( "passAlgo_" + x )
            );
            this.getPassAlgoList().add( item );
            System.out.println( 
                "Here is passAlgo_" + x + ": " + this.getNamedProperty( "passAlgo_" + x )
            );
        } // for ( x = 0; x < this.getPassAlgoTotal(); x++ )

        for ( x = 0; x < this.getUserNameTotal(); x++ ) {
            this.getUserNameList().add( this.getNamedProperty( "userName_" + x ) );
            System.out.println( "Here is userName_" + x + ": " +
                this.getNamedProperty( "userName_" + x )
            );
            /*
            SelectItem item = new SelectItem(
                this.getNamedProperty( "userName_" + x ),
                this.getNamedProperty( "userName_" + x )
            );
            this.getUserNameList().add( item );
            */
        } // for ( x = 0; x < this.getUserNameTotal(); x++ )
        
        for ( x = 0; x < this.getUserPassTotal(); x++ ) {
            SelectItem item = new SelectItem( 
                this.getNamedProperty( "userPass_" + x ),
                this.getNamedProperty( "userPass_" + x )
            );
            this.getUserPassList().add( item );
        } // for ( x = 0; x < this.getUserPassTotal(); x++ )
         
    } // end method private void populateArrayLists()

    public String getHashCode() {
        StringBuffer hashBuff = new StringBuffer( this.hashCode() );
        return hashBuff.toString();
    }

    /**
     * @return the passAlgoTotal
     */
    public int getPassAlgoTotal() {
        return passAlgoTotal;
    }

    /**
     * @param passAlgoTotal the passAlgoTotal to set
     */
    public void setPassAlgoTotal( int passAlgoTotal ) {
        this.passAlgoTotal = passAlgoTotal;
    }

    /**
     * @return the userNameTotal
     */
    public int getUserNameTotal() {
        return userNameTotal;
    }

    /**
     * @param userNameTotal the userNameTotal to set
     */
    public void setUserNameTotal( int userNameTotal ) {
        this.userNameTotal = userNameTotal;
    }

    /**
     * @return the userPassTotal
     */
    public int getUserPassTotal() {
        return userPassTotal;
    }

    /**
     * @param userPassTotal the userPassTotal to set
     */
    public void setUserPassTotal( int userPassTotal ) {
        this.userPassTotal = userPassTotal;
    }

        /**
     * @return the passAlgoList
     */
    public static List<SelectItem> getPassAlgoList() {
        return passAlgoList;
    }

    /**
     * @param aPassAlgoList the passAlgoList to set
     */
    public static void setPassAlgoList(List<SelectItem> aPassAlgoList) {
        passAlgoList = aPassAlgoList;
    }

    /**
     * @return the userNameList
     */
    public static List<String> getUserNameList() {
        return userNameList;
    }

    /**
     * @param aUserNameList the userNameList to set
     */
    public static void setUserNameList(List<String> aUserNameList) {
        userNameList = aUserNameList;
    }

    /**
     * @return the userPassList
     */
    public static List<SelectItem> getUserPassList() {
        return userPassList;
    }

    /**
     * @param aUserPassList the userPassList to set
     */
    public static void setUserPassList(List<SelectItem> aUserPassList) {
        userPassList = aUserPassList;
    }

    /**
    * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
    * or the first access to SingletonHolder.INSTANCE, not before.
    */
    private static class SingletonHolder { 
        private final static OptionsPropertiesBean INSTANCE = new OptionsPropertiesBean();
    }
    
    public static OptionsPropertiesBean getInstance() {
        return SingletonHolder.INSTANCE;
    }
 
} // end class info.shelfunit.util.OptionsPropertiesBean
