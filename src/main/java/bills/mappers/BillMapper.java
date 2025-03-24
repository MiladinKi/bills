package bills.mappers;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;

public class BillMapper {

    public static BillEntity toEntity(BillDTO billDTO){
        BillEntity bill = new BillEntity();

        bill.setId(billDTO.getId());
        bill.setName(billDTO.getName());
        bill.setAmount(billDTO.getAmount());
        bill.setDateOfBill(billDTO.getDateOfBill());
        bill.setDescription(billDTO.getDescription());
        return bill;
    }

    public static BillDTO toDTO (BillEntity billEntity){
        return new BillDTO(
                billEntity.getId(),
                billEntity.getName(),
                billEntity.getAmount(),
                billEntity.getDateOfBill(),
                billEntity.getDescription()
        );
    }
}
