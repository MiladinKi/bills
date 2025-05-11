package bills.entities.dtos;

import java.math.BigDecimal;

public class TotalPaymentSummaryDTO {
    private BigDecimal totalAmount;
    private Integer period;

    public TotalPaymentSummaryDTO() {
    }

    public TotalPaymentSummaryDTO(BigDecimal totalAmount, Integer period) {
        this.totalAmount = totalAmount;
        this.period = period;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
