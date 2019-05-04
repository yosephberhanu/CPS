package et.artisan.cn.cps.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.2
 * @version 1.2
 */
public class Amendment implements Entity {

    private long id;
    private Payment payment;
    private double amount;
    private String remark;
    private String status;
    private Date incomingDate;
    private Date outgoingDate;

    private String incomingDocumentNo;
    private String outgoingDocumentNo;

    private Timestamp registeredOn;
    private User registeredBy;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(Date incomingDate) {
        this.incomingDate = incomingDate;
    }

    public Date getOutgoingDate() {
        return outgoingDate;
    }

    public void setOutgoingDate(Date outgoingDate) {
        this.outgoingDate = outgoingDate;
    }

    public String getIncomingDocumentNo() {
        return incomingDocumentNo;
    }

    public void setIncomingDocumentNo(String incomingDocumentNo) {
        this.incomingDocumentNo = incomingDocumentNo;
    }

    public String getOutgoingDocumentNo() {
        return outgoingDocumentNo;
    }

    public void setOutgoingDocumentNo(String outgoingDocumentNo) {
        this.outgoingDocumentNo = outgoingDocumentNo;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (amount > payment.getAmount()) {
            returnValue = false;
            validationMessage.add("Overpaying");
        }
        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        return returnValue;
    }
}
