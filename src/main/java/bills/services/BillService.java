package bills.services;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import bills.entities.dtos.BillTotalityDTO;
import bills.mappers.BillMapper;
import bills.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public BillDTO createBill(BillDTO billDTO){
      BillEntity bill = BillMapper.toEntity(billDTO);
      billRepository.save(bill);
      return BillMapper.toDTO(bill);
    }

    public void deleteBillById(Integer billId){
        BillEntity bill = billRepository.findById(billId).orElseThrow(()-> new RuntimeException("Bill not found!"));
        billRepository.delete(bill);
    }

    public BillDTO modifyById(Integer billId, BillDTO billDTO){
        BillEntity bill = billRepository.findById(billId).orElseThrow(()-> new RuntimeException("Bill not found!"));

        bill.setName(billDTO.getName());
        bill.setAmount(billDTO.getAmount());
        bill.setDateOfBill(billDTO.getDateOfBill());
        bill.setDescription(bill.getDescription());

        billRepository.save(bill);
        return BillMapper.toDTO(bill);
    }

    public BillDTO getById(Integer billId){
        BillEntity bill = billRepository.findById(billId).
                orElseThrow(()-> new RuntimeException("Bill not found!"));
        return BillMapper.toDTO(bill);
    }

    public List<BillDTO> getAll(){
        List<BillEntity> bills = billRepository.findAll();
        return bills.stream().map(bill -> BillMapper.toDTO(bill)).collect(Collectors.toList());
    }

    public List<BillDTO> getAllByName(String name){
        List<BillEntity> bills = billRepository.findByName(name);
        return bills.stream().map(bill -> BillMapper.toDTO(bill)).collect(Collectors.toList());
    }

    public BillTotalityDTO findByDate(LocalDate start, LocalDate end){
        List<BillEntity> bills = billRepository.findByDateOfBillBetween(start, end);


        List<BillDTO> billDTOs = bills.stream().
                map(bill -> BillMapper.toDTO(bill)).collect(Collectors.toList());

        double totalAmount = bills.stream().mapToDouble(BillEntity :: getAmount).sum();

        return new BillTotalityDTO(billDTOs, totalAmount);
    }

    public BillTotalityDTO findByNameAndDate(String name, LocalDate start, LocalDate end){
        List<BillEntity> bills = billRepository.findByNameAndDateOfBillBetween(name, start, end);
        List<BillDTO> billDTOs = bills.stream().map(bill-> BillMapper.toDTO(bill)).collect(Collectors.toList());

        double totalAmount = bills.stream().mapToDouble(BillEntity :: getAmount).sum();

        return new BillTotalityDTO(billDTOs, totalAmount);

    }
}
