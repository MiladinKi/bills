package bills.services;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import bills.entities.dtos.BillTotalityDTO;
import bills.mappers.BillMapper;
import bills.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {


    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Autowired
    public BillService(BillRepository billRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
    }

    public BillDTO createBill(BillDTO billDTO){
      BillEntity bill = BillMapper.toEntity(billDTO);
      billRepository.save(bill);
      return billMapper.toDTO(bill);
    }

    public void deleteBillById(Integer billId){
        BillEntity bill = billRepository.findById(billId).orElseThrow(()-> new RuntimeException("Bill not found!"));
        billRepository.delete(bill);
    }

    public BillDTO modifyById(Integer billId, BillDTO billDTO){
        BillEntity bill = billRepository.findById(billId).orElseThrow(()-> new RuntimeException("Bill not found!"));

        bill.setName(billDTO.getName());
        bill.setDescription(billDTO.getDescription());
        bill.setBillInterval(billDTO.getBillInterval());

        billRepository.save(bill);
        return billMapper.toDTO(bill);
    }

    public BillDTO getById(Integer billId){
        BillEntity bill = billRepository.findById(billId).
                orElseThrow(()-> new RuntimeException("Bill not found!"));
        return billMapper.toDTO(bill);
    }

    public List<BillDTO> getAll(){
        List<BillEntity> bills = billRepository.findAll();
        return bills.stream().map(bill -> billMapper.toDTO(bill)).collect(Collectors.toList());
    }

    public Page<BillDTO> getAllByName(String name, Pageable pageable){
        Page<BillEntity> bills = billRepository.findByName(name, pageable);
        return bills.map(billMapper::toDTO);
    }


}
