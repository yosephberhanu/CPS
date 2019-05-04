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
public class User implements Entity {

    private long id;
    private String username;
    private String password;
    private String firstName;
    private String fathersName;
    private String grandfathersName;
    private String sex;
    private String email;
    private String primaryPhoneNo;
    private String secondaryPhoneNo;

    private byte[] signature;
    private String signatureFormat;
    private byte[] photo;
    private String photoFormat;
    private Timestamp registeredOn;
    private long registeredBy;
    private String status;

    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String website;
    private ArrayList<String> validationMessage;

    private ArrayList<Role> roles = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getGrandfathersName() {
        return grandfathersName;
    }

    public void setGrandfathersName(String grandfathersName) {
        this.grandfathersName = grandfathersName;
    }

    public String getSex() {
        return sex.trim();
    }

    public String getSexText() {
        return "f".equalsIgnoreCase(getSex())?"Female":"Male";
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email==null?"":email;
    }

    public String getEmailHidden() {
        return email.substring(0, 2) + "..." + email.substring(email.indexOf('@'));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryPhoneNo() {
        return primaryPhoneNo==null?"":primaryPhoneNo;
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

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public void addRole(String role) {
        int rId = Integer.parseInt(role);
        addRole(Role.create(rId));
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return getFirstName() + " " + getFathersName();
    }

    public boolean hasRole(String role) {
        boolean returnValue = false;
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getTextValue().equalsIgnoreCase(role)) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getSignatureFormat() {
        return signatureFormat;
    }

    public void setSignatureFormat(String signatureFormat) {
        this.signatureFormat = signatureFormat;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        String returnValue = "";

        if (addressLine != null && !addressLine.trim().isEmpty()) {
            returnValue += addressLine + ",";
        }
        if (city != null && !city.trim().isEmpty()) {
            returnValue += city + ",";
        }
        if (state != null && !state.trim().isEmpty()) {
            returnValue += state + ",";
        }
        if (country != null && !country.trim().isEmpty()) {
            returnValue += country;
        }
        return returnValue;

    }

    public long getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(long registeredBy) {
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
        if (CommonStorage.getRepository().userNameExists(getUsername())) {
            returnValue = false;
            validationMessage.add("Username already taken");
        }

        return returnValue;
    }
    
    @Override
    public boolean valideForUpdate() {
        boolean returnValue = true;
        validationMessage = new ArrayList<>();
        if (CommonStorage.getRepository().userNameExists(getUsername(), getId())) {
            returnValue = false;
            validationMessage.add("Username already taken");
        }

        return returnValue;
    }
}
