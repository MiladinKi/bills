package bills.entities.dtos;

import java.math.BigDecimal;
import java.util.List;

public class PaymentsTotalityDTO {
    private List<PaymentDTO> payments;
    private BigDecimal totalAmount;

    public PaymentsTotalityDTO() {
    }

    public PaymentsTotalityDTO(List<PaymentDTO> payments, BigDecimal totalAmount) {
        this.payments = payments;
        this.totalAmount = totalAmount;
    }

    public List<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDTO> payments) {
        this.payments = payments;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
