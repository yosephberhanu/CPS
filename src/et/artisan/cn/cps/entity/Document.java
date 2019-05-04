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
public class Document implements Entity {

    private long id;
    private String documentNo;
    private ClientRegion clientRegion;
    private long projectId;
    private Project project;

    private String projectCode;
    private String projectName;
    private String clientName;
    
    private long clientRegionId;
    private String clientRegionName;

    private Branch branch;
    private long branchId;
    private String branchName;

    
    private java.sql.Date inComingDate;
    private int documentYear;
    private String inComingDocumentNo;
    private String outGoingDocumentNo;
    private java.sql.Date outGoingDate;
    private java.sql.Date paymentDue;

    private byte[] scannedDocument;
    private String scannedDocumentFormat;

    private double totalAmount;
    private double paidAmount;
    private double registeredAmount;

    private String remark;
    private Timestamp registeredOn;
    private long registeredById;
    private String registeredByName;
    private User registeredBy;
    private String status;

    private Timestamp assignedOn;
    private long assignedToId;
    private String assignedToName;
    private User assignedTo;

    private Timestamp approvedOn;
    private long approvedById;
    private String approvedByName;
    private User approvedBy;

    private Timestamp closedOn;
    private long closedById;
    private String closedByName;
    
    private User closedBy;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public ClientRegion getClientRegion() {
    	if(clientRegion==null) {
    		clientRegion= CommonStorage.getRepository().getClientRegion(clientRegionId);
    	}
        return clientRegion;
    }

    public void setClientRegion(ClientRegion clientRegion) {
        this.clientRegion = clientRegion;
        setClientRegionName(clientRegion.getRegionName());
        setClientName(clientRegion.getClientName());
    }

    public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getClientRegionId() {
		return clientRegionId;
	}

	public void setClientRegionId(long clientRegionId) {
		this.clientRegionId = clientRegionId;
	}

	
	public long getBranchId() {
		return branchId;
	}

	public void setBranchId(long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Project getProject() {
		if(project==null) {
			project = CommonStorage.getRepository().getProject(projectId);
		}
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        setProjectCode(project.getCode());
        setProjectName(project.getName());
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public java.sql.Date getInComingDate() {
        return inComingDate;
    }

    public int getDocumentYear() {
        return documentYear;
    }

    public void setDocumentYear(int documentYear) {
        this.documentYear = documentYear;
    }

    public void setInComingDate(java.sql.Date inComingDate) {
        this.inComingDate = inComingDate;
    }

    public String getInComingDocumentNo() {
        return inComingDocumentNo;
    }

    public void setInComingDocumentNo(String inComingDocumentNo) {
        this.inComingDocumentNo = inComingDocumentNo;
    }

    public String getOutGoingDocumentNo() {
        return outGoingDocumentNo;
    }

    public void setOutGoingDocumentNo(String outGoingDocumentNo) {
        this.outGoingDocumentNo = outGoingDocumentNo;
    }

    public java.sql.Date getOutGoingDate() {
        return outGoingDate;
    }

    public void setOutGoingDate(java.sql.Date outGoingDate) {
        this.outGoingDate = outGoingDate;
    }

    public java.sql.Date getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(java.sql.Date paymentDue) {
        this.paymentDue = paymentDue;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientRegionName() {
        return clientRegionName;
    }

    public void setClientRegionName(String clientRegionName) {
        this.clientRegionName = clientRegionName;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getRegisteredAmount() {
        return registeredAmount;
    }

    public void setRegisteredAmount(double registeredAmount) {
        this.registeredAmount = registeredAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public User getRegisteredBy() {
    	if(registeredBy==null) {
    		registeredBy = CommonStorage.getRepository().getUser(registeredById);
    	}
    	return registeredBy;
    }

    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
    }

    	
    public long getRegisteredById() {
		return registeredById;
	}

	public void setRegisteredById(long registeredById) {
		this.registeredById = registeredById;
	}

	public String getRegisteredByName() {
		return registeredByName;
	}

	public void setRegisteredByName(String registeredByName) {
		this.registeredByName = registeredByName;
	}

	public long getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(long assignedToId) {
		this.assignedToId = assignedToId;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}

	public long getApprovedById() {
		return approvedById;
	}

	public void setApprovedById(long approvedById) {
		this.approvedById = approvedById;
	}

	public String getApprovedByName() {
		return approvedByName;
	}

	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}

	public long getClosedById() {
		return closedById;
	}

	public void setClosedById(long closedById) {
		this.closedById = closedById;
	}

	public String getClosedByName() {
		return closedByName;
	}

	public void setClosedByName(String closedByName) {
		this.closedByName = closedByName;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getAssignedOn() {
        return assignedOn;
    }

    public void setAssignedOn(Timestamp assignedOn) {
        this.assignedOn = assignedOn;
    }

    public User getAssignedTo() {
    	if(assignedTo==null) {
    		assignedTo = CommonStorage.getRepository().getUser(assignedToId);
    	}
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Timestamp getApprovedOn() {
        return approvedOn;
    }

    public void setApprovedOn(Timestamp approvedOn) {
        this.approvedOn = approvedOn;
    }

    public User getApprovedBy() {
    	if(approvedBy==null) {
    		approvedBy = CommonStorage.getRepository().getUser(approvedById);
    	}return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(Timestamp closedOn) {
        this.closedOn = closedOn;
    }

    public User getClosedBy() {
    	if(closedBy==null) {
    		closedBy = CommonStorage.getRepository().getUser(closedById);
    	}
    	return closedBy;
    }

    public void setClosedBy(User closedBy) {
        this.closedBy = closedBy;
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
        if (CommonStorage.getRepository().documentExists(getProject().getId(), getInComingDocumentNo(), getDocumentYear())) {
            returnValue = false;
            validationMessage.add("Username already taken");
        }

        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        if (CommonStorage.getRepository().documentExists(getProject().getId(), getInComingDocumentNo(), getDocumentYear(), getId())) {
            returnValue = false;
            validationMessage.add("Document already taken");
        }

        return returnValue;
    }
}
