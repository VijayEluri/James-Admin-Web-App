
package org.apache.james.admin.webapp.beans;

/**
 *
 * @author ericm
 */
public class InboxCountHolder {

    public InboxCountHolder() {

    }

    private String userName;
    private Long messageCount;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName( String userName ) {
        this.userName = userName;
    }

    /**
     * @return the messageCount
     */
    public Long getMessageCount() {
        return messageCount;
    }

    /**
     * @param messageCount the messageCount to set
     */
    public void setMessageCount( Long messageCount ) {
        this.messageCount = messageCount;
    }

} // end class info.shelfunit.james.web.InboxCountHolder
