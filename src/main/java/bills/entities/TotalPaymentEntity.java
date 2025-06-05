package bills.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TotalPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal amountTotalPayment;
    private Integer period;
    private ETotalPayment payment;

    @ManyToOne (cascade = CascadeType.REFRESH)
    @JoinColumn(name = "bill_id")
    private BillEntity bill;

    public TotalPaymentEntity() {
    }

    public TotalPaymentEntity(Integer id, BigDecimal amountTotalPayment, Integer period, ETotalPayment payment, BillEntity bill) {
        this.id = id;
        this.amountTotalPayment = amountTotalPayment;
        this.period = period;
        this.payment = payment;
        this.bill = bill;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ETotalPayment getPayment() {
        return payment;
    }

    public void setPayment(ETotalPayment payment) {
        this.payment = payment;
    }

    public BillEntity getBill() {
        return bill;
    }

    public void setBill(BillEntity bill) {
        this.bill = bill;
    }
}