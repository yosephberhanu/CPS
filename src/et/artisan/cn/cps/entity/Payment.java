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
public class Payment implements Entity {

	private long id;
	private long documentId;
	private Document document;
	private String documentNo;
	private String claimcnnumber;
	private int lotNo;
	private String name;
	private double amount;
	private double remainingAmount;
	private boolean restricted;
	private String remark;
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

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public Document getDocument() {
		if (document == null) {
			CommonStorage.getRepository().getDocument(getDocumentId());
		}
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
		this.documentId = document.getId();
		this.documentNo = document.getProject().getCode() + "/" + document.getInComingDocumentNo() + "/"
				+ document.getDocumentYear();
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public int getLotNo() {
		return lotNo;
	}

	public void setLotNo(int lotNo) {
		assert lotNo <= 0 : "Lot number can not be negative";
		this.lotNo = lotNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public boolean isRestricted() {
		return restricted;
	}

	public void isRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClaimcnnumber() {
		return claimcnnumber;
	}

	public void setClaimcnnumber(String claimcnnumber) {
		this.claimcnnumber = claimcnnumber;
	}

	public String getStatus() {
		return status;
	}

	public String getDecoratedStatus() {
		String returnValue = status;
		String label = "primary";
		switch (status.toLowerCase().trim()) {
			case "paid":
				label = "success";
				break;
			case "active":
				label = "primary";
				break;
			case "partially_paid ":
				label = "info";
				break;

		}
		returnValue = "<span class='label label-" + label + "'>" + status.trim() + "</span>";
		if(claimcnnumber!=null) {
			returnValue +="("+getClaimcnnumber()+")";
		}
		return returnValue;
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
		if (CommonStorage.getRepository().paymentExists(getLotNo(), getDocument().getId())) {
			returnValue = false;
			validationMessage.add("The specifed lot number already registered");
		}
		return returnValue;
	}

	@Override
	public boolean valideForUpdate() {
		boolean returnValue = true;
		validationMessage = new ArrayList<>();
		if (CommonStorage.getRepository().paymentExists(getLotNo(), getDocument().getId(), getId())) {
			returnValue = false;
			validationMessage.add("The specifed lot number already registered");
		}
		return returnValue;
	}

}
