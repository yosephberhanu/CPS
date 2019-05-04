package et.artisan.cn.cps.entity;

import java.util.*;

/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class Message {

    public static final int MESSEGE_TYPE_ERROR = 0;
    public static final int MESSEGE_TYPE_WARNING = 1;
    public static final int MESSEGE_TYPE_NOTICE = 2;
    public static final int MESSEGE_TYPE_INFO = 3;
    public static final int MESSEGE_TYPE_SUCCESS = 4;
    private final String messageTypes[] = {"error", "warning", "notice", "info", "success"};

    private String name;
    private int type;
    private ArrayList<String> details;
    private Exception exception;

    public Message() {
        details = new ArrayList<>();
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<String> details) {
        this.details = details;
    }

    public void addDetail(String detail) {
        this.details.add(detail);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (type >= messageTypes.length || type < 0) {
            throw new IllegalArgumentException("Undefined message Type");
        }
        this.type = type;

    }

    public String getTypeText() {
        return messageTypes[type];
    }
}
