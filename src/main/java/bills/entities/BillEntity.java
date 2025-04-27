package bills.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Integer interval;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.REFRESH, orphanRemoval = true)
    private List<PaymentEntity> payments = new ArrayList<>();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.REFRESH, orphanRemoval = true)
    private List<TotalPaymentEntity> totalPayments = new ArrayList<>();

    public BillEntity() {
    }

    public BillEntity(Integer id, String name, String description, Integer interval, List<PaymentEntity> payments, List<TotalPaymentEntity> totalPayments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.payments = payments;
        this.totalPayments = totalPayments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public List<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentEntity> payments) {
        this.payments = payments;
    }

    public List<TotalPaymentEntity> getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(List<TotalPaymentEntity> totalPayments) {
        this.totalPayments = totalPayments;
    }
}
