package et.artisan.cn.cps.dto;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class PaidUnpaidReport {
    private String branchName;
    private String clientRefNo;
    private String cnRefo;
    private double amount;
    private double paidAmount;
    private double unpaidAmount;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getClientRefNo() {
        return clientRefNo;
    }

    public void setClientRefNo(String clientRefNo) {
        this.clientRefNo = clientRefNo;
    }

    public String getCnRefo() {
        return cnRefo;
    }

    public void setCnRefo(String cnRefo) {
        this.cnRefo = cnRefo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }
    
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(double unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }
    
    
    
}
