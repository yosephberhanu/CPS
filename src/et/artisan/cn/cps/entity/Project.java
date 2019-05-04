package et.artisan.cn.cps.entity;

import et.artisan.cn.cps.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public class Project implements Entity {

    private long id;
    private String code;
    private String name;
    private String amharicName;
    private long clientId;
    private long clientRegionId;
    private String clientName;
    private Client client;
    public long getClientRegionId() {
		return clientRegionId;
	}

	public void setClientRegionId(long clientRegionId) {
		this.clientRegionId = clientRegionId;
	}

	public long getRegisteredId() {
		return registeredId;
	}

	public void setRegisteredId(long registeredId) {
		this.registeredId = registeredId;
	}

	private ClientRegion clientRegion;

    private String remark;
    private Timestamp registeredOn;
    private long registeredId;
    private User registeredBy;
    private String status;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmharicName() {
        return amharicName==null?"":amharicName;
    }

    public void setAmharicName(String amharicName) {
        this.amharicName = amharicName;
    }

    public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public Client getClient() {
		if(client == null) {
			client = CommonStorage.getRepository().getClient(clientId);
		}
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientName = client.getName();
        this.clientId = client.getId();
    }

    public ClientRegion getClientRegion() {
        return clientRegion;
    }

    public void setClientRegion(ClientRegion clientRegion) {
        this.clientRegion = clientRegion;
    }

    
    public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemarkShort() {
        return remark.length() > Constants.SHORT_TEXT_LENGTH ? remark.substring(0, Constants.SHORT_TEXT_LENGTH) + "..." : remark;
    }

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getRegisteredOnShort() {
        return new SimpleDateFormat("MM/dd/yyyy").format(registeredOn);
    }

    public User getRegisteredBy() {
    	if(registeredBy == null) {
    		registeredBy = CommonStorage.getRepository().getUser(registeredId);
    	}
        return registeredBy;
    }

    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
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
        if (CommonStorage.getRepository().projectExists(getName(), getCode(), getClient().getId())) {
            returnValue = false;
            validationMessage.add("Project Name/Code already taken");
        }
        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        System.out.println("Validating");
        if (CommonStorage.getRepository().projectExists(getName(), getCode(), getClientId(), getId())) {
            returnValue = false;
            validationMessage.add("Project Name/Code already taken");
        }
        return returnValue;
    }
}
