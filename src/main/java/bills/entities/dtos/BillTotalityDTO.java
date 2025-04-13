package bills.entities.dtos;

import bills.entities.BillEntity;

import java.math.BigDecimal;
import java.util.List;

public class BillTotalityDTO {
    private List<BillDTO> bills;
    private BigDecimal totalAmount;

    public BillTotalityDTO() {
    }

    public BillTotalityDTO(List<BillDTO> bills, BigDecimal totalAmount) {
        this.bills = bills;
        this.totalAmount = totalAmount;
    }

    public List<BillDTO> getBills() {
        return bills;
    }

    public void setBills(List<BillDTO> bills) {
        this.bills = bills;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
