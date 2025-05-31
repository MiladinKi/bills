package bills.entities.dtos;

import bills.entities.ETotalPayment;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TotalPaymentDTO {
    private Integer billId;
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!")
    @Column(precision = 10, scale = 2)
    private BigDecimal amountTotalPayment;
    @NotNull(message = "Period is mandatory.")
    @Min(value = 1, message = "The minimum period must be 1.")
    @Max(value = 36, message = "The maximum period can be bigger then 36!")
    private Integer period;
    private ETotalPayment payment;

    public TotalPaymentDTO() {
    }

    public TotalPaymentDTO(Integer billId, BigDecimal amountTotalPayment, Integer period, ETotalPayment payment) {
        this.billId = billId;
        this.amountTotalPayment = amountTotalPayment;
        this.period = period;
        this.payment = payment;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public @NotNull @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!") BigDecimal getAmountTotalPayment() {
        return amountTotalPayment;
    }

    public void setAmountTotalPayment(@NotNull @DecimalMin(value = "0.01", message = "Amount must be bigger than 0!") BigDecimal amountTotalPayment) {
        this.amountTotalPayment = amountTotalPayment;
    }

    public @NotNull(message = "Period is mandatory.") @Min(value = 1, message = "The minimum period must be 1.") @Max(value = 36, message = "The maximum period can be bigger then 36!") Integer getPeriod() {
        return period;
    }

    public void setPeriod(@NotNull(message = "Period is mandatory.") @Min(value = 1, message = "The minimum period must be 1.") @Max(value = 36, message = "The maximum period can be bigger then 36!") Integer period) {
        this.period = period;
    }

    public ETotalPayment getPayment() {
        return payment;
    }

    public void setPayment(ETotalPayment payment) {
        this.payment = payment;
    }
}
