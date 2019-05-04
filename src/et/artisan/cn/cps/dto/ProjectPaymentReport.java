package et.artisan.cn.cps.dto;

import java.util.*;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class ProjectPaymentReport {
    private String project;
    private ArrayList<PaymentReport> details;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public ArrayList<PaymentReport> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<PaymentReport> details) {
        this.details = details;
    }
    
}
