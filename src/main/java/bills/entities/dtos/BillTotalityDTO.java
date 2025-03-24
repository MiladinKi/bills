package bills.entities.dtos;

import bills.entities.BillEntity;

import java.util.List;

public class BillTotalityDTO {
    private List<BillDTO> bills;
    private Double totalAmount;

    public BillTotalityDTO() {
    }

    public BillTotalityDTO(List<BillDTO> bills, Double totalAmount) {
        this.bills = bills;
        this.totalAmount = totalAmount;
    }

    public List<BillDTO> getBills() {
        return bills;
    }

    public void setBills(List<BillDTO> bills) {
        this.bills = bills;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
