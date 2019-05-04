package et.artisan.cn.cps.entity;

/**
 *
 * @author Yoseph Berhanu<yoseph@bayeth.com>
 * @since 1.0
 * @version 1.0
 */
public class ReportType {

    private byte id;
    private String name;

    private boolean branchBased;
    private boolean projectBased;
    private boolean clientBased;
    private boolean clientRegionBased;
    private boolean timeBased;
    private boolean documentBased;
    private boolean claimBased;
    private boolean claimCNNoBased;
    private boolean branchTypeBased;

    public ReportType() {
        this((byte) 0);
    }

    public ReportType(byte id) {
        this(id, "Report " + id);
    }

    public ReportType(byte id, String name) {
        this.id = id;
        this.name = name;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBranchBased() {
        return branchBased;
    }

    public void isBranchBased(boolean branchBased) {
        this.branchBased = branchBased;
    }

    public boolean isProjectBased() {
        return projectBased;
    }

    public void isProjectBased(boolean projectBased) {
        this.projectBased = projectBased;
    }

    public boolean isClientBased() {
        return clientBased;
    }

    public void isClientBased(boolean clientBased) {
        this.clientBased = clientBased;
    }

    public boolean isClientRegionBased() {
        return clientRegionBased;
    }

    public void isClientRegionBased(boolean clientRegionBased) {
        this.clientRegionBased = clientRegionBased;
    }

    public boolean isTimeBased() {
        return timeBased;
    }

    public void isTimeBased(boolean timeBased) {
        this.timeBased = timeBased;
    }

    public boolean isDocumentBased() {
        return documentBased;
    }

    public void isDocumentBased(boolean documentBased) {
        this.documentBased = documentBased;
    }

    public boolean isClaimBased() {
        return claimBased;
    }

    public void isClaimBased(boolean claimBased) {
        this.claimBased = claimBased;
    }

    public boolean isBranchTypeBased() {
        return branchTypeBased;
    }

    public void isBranchTypeBased(boolean branchTypeBased) {
        this.branchTypeBased = branchTypeBased;
    }

    public boolean isClaimCNNoBased() {
        return claimCNNoBased;
    }

    public void isClaimCNNoBased(boolean claimCNNoBased) {
        this.claimCNNoBased = claimCNNoBased;
    }

}
