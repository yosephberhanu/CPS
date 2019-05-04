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
public class Client implements Entity {

    private long id;
    private String name;
    private String amharicName;
    private String contactPerson;

    private String remark;
    private String phoneNo;
    private String email;
    private String website;
    private String addressLine;
    private String city;
    private double serviceChargeRate;
    private byte regionId;
    private BranchRegion region;
    private Timestamp registeredOn;
    private User registeredBy;
    private String registeredByName;
    private String status;

    private ArrayList<String> validationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmharicName() {
        return amharicName;
    }

    public void setAmharicName(String amharicName) {
        this.amharicName = amharicName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BranchRegion getRegion() {
    	if(region == null) {
    		region = CommonStorage.getRepository().getRegion(regionId);
    	}
    	return region;
        
    }

    public double getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(double serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public void setRegion(BranchRegion region) {
        this.region = region;
    }

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegisteredByName() {
        return registeredByName;
    }

    public byte getRegionId() {
		return regionId;
	}

	public void setRegionId(byte regionId) {
		this.regionId = regionId;
	}

	public void setRegisteredByName(String registeredByName) {
        this.registeredByName = registeredByName;
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
        if (CommonStorage.getRepository().clientExists(getName())) {
            returnValue = false;
            validationMessage.add("Client already registered");
        }
        if (getRegion() == null) {
            returnValue = false;
            validationMessage.add("Region is required");
        }
        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        if (CommonStorage.getRepository().clientExists(getName(), getId())) {
            returnValue = false;
            validationMessage.add("Client already registered");
        }
        if (getRegion() == null) {
            returnValue = false;
            validationMessage.add("Region is required");
        }

        return returnValue;
    }
}
