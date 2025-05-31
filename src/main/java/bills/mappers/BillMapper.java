package bills.mappers;

import bills.entities.BillEntity;
import bills.entities.PaymentEntity;
import bills.entities.TotalPaymentEntity;
import bills.entities.dtos.BillDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillMapper {

    public static BillEntity toEntity(BillDTO billDTO){
        BillEntity bill = new BillEntity();
        bill.setId(billDTO.getId());
        bill.setName(billDTO.getName());
        bill.setDescription(billDTO.getDescription());
        bill.setBillInterval(billDTO.getBillInterval());
        return bill;
    }

    public BillDTO toDTO(BillEntity billEntity){
        BillDTO dto = new BillDTO();
        dto.setId(billEntity.getId());
        dto.setName(billEntity.getName());
        dto.setDescription(billEntity.getDescription());
        dto.setBillInterval(billEntity.getBillInterval());

        dto.setPaymentIds(billEntity.getPayments().stream()
                .map(PaymentEntity:: getId)
                .collect(Collectors.toList()));

        dto.setTotalPaymentIds(billEntity.getTotalPayments().stream()
                .map(TotalPaymentEntity::getId).collect(Collectors.toList()));

        return dto;
    }
}
