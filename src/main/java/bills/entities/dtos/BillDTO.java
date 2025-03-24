package bills.entities.dtos;

import java.time.LocalDate;

public class BillDTO {
    private Integer id;
    private String name;
    private Double amount;
    private LocalDate dateOfBill;
    private String description;

    public BillDTO() {
    }

    public BillDTO(Integer id, String name, Double amount, LocalDate dateOfBill, String description) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
