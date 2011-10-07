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
     * @return the messageCount
     */
    public Long getMessageCount() {
        return new Long( messageCount.longValue() );
    }

} // end class org.apache.james.admin.webapp.beans.InboxCountHolder

