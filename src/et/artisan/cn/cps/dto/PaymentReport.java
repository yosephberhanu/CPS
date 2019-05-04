package et.artisan.cn.cps.dto;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class PaymentReport {
    private long branchNumber;
    private String branchName;
    private String branchAmharicName;
    private String projectName;
    private String projectAmharicName;
    private int noOfPayment;
    private double clientAmount;
    private double branchServiceCharge;
    private double paidAmount;
    private double cnServiceCharge;
    private double cnVAT;
    private double total;
    private String referenceNo;
    private long regionCode;
    private String region;

    public long getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(long branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getNoOfPayment() {
        return noOfPayment;
    }

    public void setNoOfPayment(int noOfPayment) {
        this.noOfPayment = noOfPayment;
    }

    public double getClientAmount() {
        return clientAmount;
    }

    public void setClientAmount(double clientAmount) {
        this.clientAmount = clientAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getCnServiceCharge() {
        return cnServiceCharge;
    }

    public void setCnServiceCharge(double cnServiceCharge) {
        this.cnServiceCharge = cnServiceCharge;
    }

    public double getCnVAT() {
        return cnVAT;
    }

    public void setCnVAT(double cnVAT) {
        this.cnVAT = cnVAT;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public long getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(long regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getBranchServiceCharge() {
        return branchServiceCharge;
    }

    public void setBranchServiceCharge(double branchServiceCharge) {
        this.branchServiceCharge = branchServiceCharge;
    }

    public String getBranchAmharicName() {
        return branchAmharicName;
    }

    public void setBranchAmharicName(String branchAmharicName) {
        this.branchAmharicName = branchAmharicName;
    }

    public String getProjectAmharicName() {
        return projectAmharicName;
    }

    public void setProjectAmharicName(String projectAmharicName) {
        this.projectAmharicName = projectAmharicName;
    }
    
}
