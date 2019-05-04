package et.artisan.cn.cps.entity;

import et.artisan.cn.cps.util.CommonStorage;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Notification implements Entity {

    private long id;
    private User messageFrom;
    private User messageTo;
    private String subject;
    private String content;

    private Timestamp sentOn;
    private String status;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(User messageFrom) {
        this.messageFrom = messageFrom;
    }

    public User getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(User messageTo) {
        this.messageTo = messageTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubjectShort() {
        String returnValue = subject;
        if (subject == null || subject.trim().isEmpty()) {
            returnValue = "No Subject";
        } else if (subject.trim().length() > 25) {
            returnValue = subject.trim().substring(0, 25) + "...";
        }
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSentOn() {
        return sentOn;
    }

    public void setSentOn(Timestamp sentOn) {
        this.sentOn = sentOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return validation messages
     */
    @Override
    public ArrayList<String> getValidationMessage() {
        return this.validationMessage;
    }

    @Override
    public boolean valideForSave() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();

        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();

        return returnValue;
    }
}
