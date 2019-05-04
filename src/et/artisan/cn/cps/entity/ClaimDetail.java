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
public class ClaimDetail implements Entity {

    private long id;
    private String documentId;
    private String paymentName;
    private int paymentLotNo;
    private Payment payment;
    private Claim claim;
    private String paidBy;
    private double amount;
    private java.sql.Date paidOn;
    private String remark;
    private String status;

    private Timestamp registeredOn;
    private long registeredById;
    private User registeredBy;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public int getPaymentLotNo() {
        return paymentLotNo;
    }

    public void setPaymentLotNo(int paymentLotNo) {
        this.paymentLotNo = paymentLotNo;
    }

    
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        this.paymentLotNo = payment.getLotNo();
        this.paymentName = payment.getName();
        Document document = payment.getDocument();
        this.documentId = document.getProject().getCode() + "/" + document.getInComingDocumentNo() + "/" + document.getDocumentYear();
       
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public java.sql.Date getPaidOn() {
        return paidOn;
    }

    public void setPaidOn(java.sql.Date paidOn) {
        this.paidOn = paidOn;
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

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public long getRegisteredById() {
		return registeredById;
	}

	public void setRegisteredById(long registeredById) {
		this.registeredById = registeredById;
	}

	public User getRegisteredBy() {
		if(registeredBy== null) {
			registeredBy = CommonStorage.getRepository().getUser(registeredById);
		}
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
