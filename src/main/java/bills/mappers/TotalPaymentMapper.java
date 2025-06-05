package bills.mappers;

import bills.entities.BillEntity;
import bills.entities.ETotalPayment;
import bills.entities.TotalPaymentEntity;
import bills.entities.dtos.TotalPaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class TotalPaymentMapper {

    public static TotalPaymentEntity toEntity(TotalPaymentDTO totalPaymentDTO, BillEntity bill){
        TotalPaymentEntity entity = new TotalPaymentEntity();
        entity.setBill(bill);
        entity.setAmountTotalPayment(totalPaymentDTO.getAmountTotalPayment());
        entity.setPeriod(totalPaymentDTO.getPeriod());
        entity.setPayment(totalPaymentDTO.getPayment());

        return entity;
    }

    public static TotalPaymentDTO toDTO(TotalPaymentEntity totalPayment){
        TotalPaymentDTO dto = new TotalPaymentDTO();
        dto.setBillId(totalPayment.getBill().getId());
        dto.setAmountTotalPayment(totalPayment.getAmountTotalPayment());
        dto.setPeriod(totalPayment.getPeriod());
        dto.setPayment(totalPayment.getPayment());
        return dto;
    }
}