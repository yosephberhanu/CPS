package et.artisan.cn.cps.dto;

import et.artisan.cn.cps.entity.Payment;
import java.sql.Date;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class PaymentDetailReport extends Payment {

    private double paidAmount;
    private Date orderDate;
    private String RefNo;

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRefNo() {
        return RefNo;
    }

    public void setRefNo(String RefNo) {
        this.RefNo = RefNo;
    }

    public double getUnPaidAmount() {
        return getAmount() - paidAmount;
    }

}
