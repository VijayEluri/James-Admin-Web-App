package org.apache.james.admin.webapp.beans;

/**
 *
 * @author ericm
 */
public final class InboxCountHolder {

    public InboxCountHolder( String userNameA, Long messageCountA ) {
        userName = userNameA;
        messageCount = new Long( messageCountA.longValue() );
    }

    private String userName;
    private Long   messageCount;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    /*
    public void setUserName( String userName ) {
        this.userName = userName;
    }
     * */
    /**
     * @return the messageCount
     */
    public Long getMessageCount() {
        // return messageCount;
        return new Long( messageCount.longValue() );
    }

    /**
     * @param messageCount the messageCount to set
     */
    /*
    public void setMessageCount( Long messageCount ) {
        this.messageCount = messageCount;
    }
    */
} // end class org.apache.james.admin.webapp.beans.InboxCountHolder

