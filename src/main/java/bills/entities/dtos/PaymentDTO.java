package bills.entities.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDTO {
    private Integer billId;
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!")
    @Column(precision = 10, scale = 2)
    private BigDecimal amountPayment;
    @NotNull
    @PastOrPresent(message = "Date cannot be in the future or older than 30 days!")
    private LocalDateTime createdAt;
    @NotNull(message = "Cancellation information is mandatory")
    private Boolean isCancelled;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer billId, BigDecimal amountPayment, LocalDateTime createdAt, Boolean isCancelled) {
        this.billId = billId;
        this.amountPayment = amountPayment;
        this.createdAt = createdAt;
        this.isCancelled = isCancelled;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public @NotNull @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!") BigDecimal getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(@NotNull @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!") BigDecimal amountPayment) {
        this.amountPayment = amountPayment;
    }

    public @NotNull @PastOrPresent(message = "Date cannot be in the future or older than 30 days!") LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull @PastOrPresent(message = "Date cannot be in the future or older than 30 days!") LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(message = "Cancellation information is mandatory") Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(@NotNull(message = "Cancellation information is mandatory") Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}