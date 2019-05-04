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
public class Branch implements Entity {

    private long id;
    private String name;
    private String contactPerson;

    private int type;
    private String typeName;
    private String typeLocalName;
    private int region;
    private String regionName;
    private String regionLocalName;

    private String remark;
    private String primaryPhoneNo;
    private String secondaryPhoneNo;
    private String email;
    private String addressLine;
    private String city;
    private Timestamp registeredOn;
    private long registeredBy;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeLocalName() {
        return typeLocalName;
    }

    public void setTypeLocalName(String typeLocalName) {
        this.typeLocalName = typeLocalName;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionLocalName() {
        return regionLocalName;
    }

    public void setRegionLocalName(String regionLocalName) {
        this.regionLocalName = regionLocalName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrimaryPhoneNo() {
        return primaryPhoneNo;
    }

    public void setPrimaryPhoneNo(String primaryPhoneNo) {
        this.primaryPhoneNo = primaryPhoneNo;
    }

    public String getSecondaryPhoneNo() {
        return secondaryPhoneNo;
    }

    public void setSecondaryPhoneNo(String secondaryPhoneNo) {
        this.secondaryPhoneNo = secondaryPhoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public long getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(long registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getRegisteredByName() {
        return registeredByName;
    }

    public void setRegisteredByName(String registeredByName) {
        this.registeredByName = registeredByName;
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
        if (CommonStorage.getRepository().branchNameExists(getName(), getType())) {
            returnValue = false;
            validationMessage.add("Branch Name already taken");
        }if (getRegion()<1) {
            returnValue = false;
            validationMessage.add("Region is required");
        }
        return returnValue;
    }

    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        if (CommonStorage.getRepository().branchNameExists(getName(), getType(), getId())) {
            returnValue = false;
            validationMessage.add("Branch Name already taken");
        }
        if (getRegion()<1) {
            returnValue = false;
            validationMessage.add("Region is required");
        }
        return returnValue;
    }
}
