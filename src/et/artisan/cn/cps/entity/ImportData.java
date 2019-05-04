package et.artisan.cn.cps.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class ImportData implements Entity {

    private long id;
    private Document document;
    private int lotNo;
    private String name;
    private java.sql.Date orderDate;
    private double amount;
    private boolean restricted;
    private String remark;
    private String refNo;
    private String region;
    private String status;

    private Timestamp registeredOn;
    private User registeredBy;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getLotNo() {
        return lotNo;
    }

    public void setLotNo(int lotNo) {
        this.lotNo = lotNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.sql.Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public User getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getProjectCode() {
        return refNo.substring(0,refNo.indexOf("/"));
    }
    public String getDocumentNumber() {
        return refNo.substring(refNo.indexOf("/")+1,refNo.lastIndexOf("/"));
    }
    public int getDocumentYear() {
        return Integer.parseInt(refNo.substring(refNo.lastIndexOf("/")+1));
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
