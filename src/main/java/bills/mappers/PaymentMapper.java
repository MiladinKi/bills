package bills.mappers;

import bills.entities.BillEntity;
import bills.entities.PaymentEntity;
import bills.entities.dtos.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public static PaymentEntity toEntity (PaymentDTO paymentDTO, BillEntity bill){
        PaymentEntity payment = new PaymentEntity();
        payment.setBill(bill);
        payment.setAmountPayment(paymentDTO.getAmountPayment());
        payment.setCreatedAt(paymentDTO.getCreatedAt());
        payment.setCancelled(paymentDTO.getCancelled());
        return payment;
    }

    public  static PaymentDTO toDTO(PaymentEntity paymentEntity){
        PaymentDTO toDTO = new PaymentDTO();
        toDTO.setBillId(paymentEntity.getBill().getId());
        toDTO.setAmountPayment(paymentEntity.getAmountPayment());
        toDTO.setCreatedAt(paymentEntity.getCreatedAt());
        toDTO.setCancelled(paymentEntity.getCancelled());

        return toDTO;
    }
}
