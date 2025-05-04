package bills.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal amountPayment;
    private LocalDateTime createdAt;
    private Boolean isCancelled;

    @ManyToOne (cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bill_id")
    private BillEntity bill;

    public PaymentEntity() {
    }

    public PaymentEntity(Integer id, BigDecimal amountPayment, LocalDateTime createdAt, Boolean isCancelled, BillEntity bill) {
        this.id = id;
        this.amountPayment = amountPayment;
        this.createdAt = createdAt;
        this.isCancelled = isCancelled;
        this.bill = bill;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(BigDecimal amountPayment) {
        this.amountPayment = amountPayment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }
}
