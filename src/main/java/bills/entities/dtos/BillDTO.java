package bills.entities.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillDTO {
    private Integer id;
    @NotBlank(message = "Name must be provided!")
    private String name;
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!")
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
    @NotNull
    @PastOrPresent(message = "Date cannot be in the future or older than 30 days!")
    private LocalDate dateOfBill;
    @Max(value = 300, message = "Maximum characters in description is 300!")
    private String description;

    public BillDTO() {
    }

    public BillDTO(Integer id, String name, BigDecimal amount, LocalDate dateOfBill, String description) {
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
