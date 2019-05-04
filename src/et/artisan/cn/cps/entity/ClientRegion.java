package et.artisan.cn.cps.entity;

import et.artisan.cn.cps.util.CommonStorage;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class ClientRegion implements Entity {

    private long id;
    private Client client;
    private long clientId;
    private String clientName;
    private String regionName;
    private String amharicName;
    private String contactPerson;
    private String phoneNumber;
    private String addressLine;
    private String remark;
    private Timestamp registeredOn;
    private long registeredById;
    private User registeredBy;
    private String status;
    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getAmharicName() {
        return amharicName;
    }

    public void setAmharicName(String amharicName) {
        this.amharicName = amharicName;
    }

    public Client getClient() {
    	if(client == null) {
    		CommonStorage.getRepository().getClient(clientId);
    	}
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        setClientName(client.getName());
        setClientId(client.getId());
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
    
    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
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

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public ArrayList<String> getValidationMessage() {
        return this.validationMessage;
    }

    @Override
    public boolean valideForSave() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        if (CommonStorage.getRepository().clientRegionExists(getRegionName(), getClient().getId())) {
            returnValue = false;
            validationMessage.add("Client Region already registered");
        }
        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        if (CommonStorage.getRepository().clientRegionExists(getRegionName(), getClient().getId(), getId())) {
            returnValue = false;
            validationMessage.add("Client Region already registered");
        }
        return returnValue;
    }

}
