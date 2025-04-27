package bills.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class TotalPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer billId;
    private BigDecimal amountTotalPayment;
    private Integer period;

    @ManyToOne (cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bill_id")
    private BillEntity bill;

    public TotalPaymentEntity() {
    }

    public TotalPaymentEntity(Integer billId, BigDecimal amountTotalPayment, Integer period, BillEntity bill) {
        this.billId = billId;
        this.amountTotalPayment = amountTotalPayment;
        this.period = period;
        this.bill = bill;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public BigDecimal getAmountTotalPayment() {
        return amountTotalPayment;
    }

    public void setAmountTotalPayment(BigDecimal amountTotalPayment) {
        this.amountTotalPayment = amountTotalPayment;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }
}
