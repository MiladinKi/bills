package bills.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal amount;
    private LocalDate dateOfBill;
    private String description;

    public BillEntity() {
    }

    public BillEntity(Integer id, String name, BigDecimal amount, LocalDate dateOfBill, String description) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.dateOfBill = dateOfBill;
        this.description = description;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDateOfBill() {
        return dateOfBill;
    }

    public void setDateOfBill(LocalDate dateOfBill) {
        this.dateOfBill = dateOfBill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
