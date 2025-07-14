package bills.services;

import bills.entities.BillEntity;
import bills.entities.PaymentEntity;
import bills.entities.TotalPaymentEntity;
import bills.entities.dtos.TotalPaymentDTO;
import bills.entities.dtos.TotalPaymentSummaryDTO;
import bills.mappers.TotalPaymentMapper;
import bills.repositories.BillRepository;
import bills.repositories.TotalPaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TotalPaymentService {

//    @Autowired
//    private TotalPaymentRepository totalPaymentRepository;
//
//    @Autowired
//    private BillRepository billRepository;

    private final TotalPaymentRepository totalPaymentRepository;
    private final BillRepository billRepository;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class ObjectNotFoundException extends RuntimeException{
        public ObjectNotFoundException(String message){
            super(message);
        }
    }

    public TotalPaymentService(TotalPaymentRepository totalPaymentRepository, BillRepository billRepository) {
        this.totalPaymentRepository = totalPaymentRepository;
        this.billRepository = billRepository;
    }



    public TotalPaymentDTO createTotalPayment(TotalPaymentDTO totalPaymentDTO, Integer billId){
        BillEntity bill = billRepository.findById(billId).orElseThrow(()-> new ObjectNotFoundException("Bill with id: " + billId + " not found!"));
        TotalPaymentEntity totalPayment = TotalPaymentMapper.toEntity(totalPaymentDTO, bill);
        totalPaymentRepository.save(totalPayment);
        return TotalPaymentMapper.toDTO(totalPayment);
    }

    public TotalPaymentDTO modifyTotalPaymentById(Integer id, TotalPaymentDTO totalPaymentDTO){
        System.out.println("Trying to find TotalPayment with ID: " + id);
        TotalPaymentEntity totalPayment = totalPaymentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("TotalPayment with id: " + id + " not found!"));
        System.out.println("TotalPayment found: " + totalPayment);

        // Update polja...
        totalPayment.setAmountTotalPayment(totalPaymentDTO.getAmountTotalPayment());
        totalPayment.setPeriod(totalPaymentDTO.getPeriod());
        totalPayment.setPayment(totalPaymentDTO.getPayment());

        totalPaymentRepository.save(totalPayment);

        return TotalPaymentMapper.toDTO(totalPayment);
    }


    public void deleteTotalPaymentById(Integer id){
        TotalPaymentEntity totalPayment = totalPaymentRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("TotalPayment with id: " + id + " not found!"));
        totalPaymentRepository.delete(totalPayment);
    }

    public TotalPaymentDTO getById(Integer id){
        TotalPaymentEntity paymentEntity = totalPaymentRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("TotalPayment with id: " + id + " not found!"));
        return TotalPaymentMapper.toDTO(paymentEntity);
    }

    public List<TotalPaymentDTO> getAllTotalPayments () {
        List<TotalPaymentEntity> totalPayments = totalPaymentRepository.findAll();
        return totalPayments.stream().map(tp -> TotalPaymentMapper.toDTO(tp)).collect(Collectors.toList());
    }

    public Page<TotalPaymentSummaryDTO> findAllPaymentsBetweenPeriod(Integer from, Integer to, Pageable pageable){
        Page<TotalPaymentEntity> totalPayments = totalPaymentRepository.findAllByPeriodBetween(from, to, pageable);
        return totalPayments.map(entity -> new TotalPaymentSummaryDTO(
                entity.getAmountTotalPayment(),
                entity.getPeriod()));
    }
}
