package bills.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer billId;
    private BigDecimal amountPayment;
    private LocalDate createdAt;
    private Boolean isCancelled;

    @ManyToOne (cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bill_id")
    private BillEntity bill;

    public PaymentEntity() {
    }

    public PaymentEntity(Integer billId, BigDecimal amountPayment, LocalDate createdAt, Boolean isCancelled, BillEntity bill) {
        this.billId = billId;
        this.amountPayment = amountPayment;
        this.createdAt = createdAt;
        this.isCancelled = isCancelled;
        this.bill = bill;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public BigDecimal getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(BigDecimal amountPayment) {
        this.amountPayment = amountPayment;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }
}
