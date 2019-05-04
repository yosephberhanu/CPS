package et.artisan.cn.cps.entity;

import java.sql.*;
import java.util.*;

import et.artisan.cn.cps.util.CommonStorage;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Claim implements Entity {

    private long id;
    private String claimNumber;
    private String claimCNNumber;
    private java.sql.Date claimDate;
    private java.sql.Date arrivalDate;
    private Branch payingBranch;
    private String payingBranchName;
    private long payingBranchId;
    private double amount;
    private double registeredAmount;
    private int count;
    private long clientId;
    private String clientName;
    private Client client;
    private byte[] scannedDocument;
    private String scannedDocumentFormat;

    private String remark;
    private String status;

    private Timestamp registeredOn;
    private User registeredBy;
    private long registeredId;
    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClaimNumber() {
        return claimNumber == null?"":claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getClaimCNNumber() {
        return claimCNNumber == null?"":claimCNNumber;
    }

    public void setClaimCNNumber(String claimCNNumber) {
        this.claimCNNumber = claimCNNumber;
    }  
    
    public java.sql.Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(java.sql.Date claimDate) {
        this.claimDate = claimDate;
    }

    public java.sql.Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(java.sql.Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Branch getPayingBranch() {
    	if(payingBranch == null) {
    		CommonStorage.getRepository().getBranch(payingBranchId);
    	}
        return payingBranch;
    }

    public void setPayingBranch(Branch payingBranch) {
        this.payingBranch = payingBranch;
        this.payingBranchId = payingBranch.getId();
    }

    public String getPayingBranchName() {
		return payingBranchName;
	}

	public void setPayingBranchName(String payingBranchName) {
		this.payingBranchName = payingBranchName;
	}

	public long getPayingBranchId() {
		return payingBranchId;
	}

	public void setPayingBranchId(long payingBranchId) {
		this.payingBranchId = payingBranchId;
	}

	public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRegisteredAmount() {
        return registeredAmount;
    }

    public void setRegisteredAmount(double registeredAmount) {
        this.registeredAmount = registeredAmount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Client getClient() {
    	if(client == null) {
    		client = CommonStorage.getRepository().getClient(clientId);
    	}
            return client;
    }

    public void setClient(Client client) {
    	this.client = client;
    	this.clientId = client.getId();
    	this.clientName = client.getName();
    }

    
    public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public byte[] getScannedDocument() {
        return scannedDocument;
    }

    public void setScannedDocument(byte[] scannedDocument) {
        this.scannedDocument = scannedDocument;
    }

    public String getScannedDocumentFormat() {
        return scannedDocumentFormat;
    }

    public void setScannedDocumentFormat(String scannedDocumentFormat) {
        this.scannedDocumentFormat = scannedDocumentFormat;
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

    public User getRegisteredBy() {
    	if(registeredBy == null) {
    		registeredBy = CommonStorage.getRepository().getUser(registeredId);
    	}
        return registeredBy;
    }

    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
        this.registeredId = registeredBy.getId();
    }

    public long getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(long registeredId) {
		this.registeredId = registeredId;
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
