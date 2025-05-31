package bills.services;

import bills.entities.BillEntity;
import bills.entities.PaymentEntity;
import bills.entities.dtos.PaymentDTO;
import bills.entities.dtos.PaymentsTotalityDTO;
import bills.mappers.PaymentMapper;
import bills.repositories.BillRepository;
import bills.repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private BillRepository billRepository;



    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDTO createPayment(Integer billId, PaymentDTO paymentDTO) {
        BillEntity bill = billRepository.findById(billId).orElseThrow(()-> new RuntimeException("Bill not found"));
        PaymentEntity payment = PaymentMapper.toEntity(paymentDTO, bill);
        payment.setBill(bill);
        PaymentEntity savedPayment = paymentRepository.save(payment);
        return  PaymentMapper.toDTO(savedPayment);
    }

    public PaymentDTO modifyPayment(Integer id, PaymentDTO paymentDTO){
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setAmountPayment(paymentDTO.getAmountPayment());
        payment.setCreatedAt(paymentDTO.getCreatedAt());
        payment.setIsCancelled(paymentDTO.getIsCancelled());
        paymentRepository.save(payment);

        return PaymentMapper.toDTO(payment);

        }

        public PaymentDTO cancelledPaymentById(Integer id){
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(()-> new RuntimeException("Payment not found"));

        payment.setIsCancelled(true);
        paymentRepository.save(payment);
        return PaymentMapper.toDTO(payment);
        }

        public PaymentDTO getById(Integer id){
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Payment with id" + id + " not found"));
        return PaymentMapper.toDTO(payment);
        }

        public List<PaymentDTO> getAllPayments(){
        List<PaymentEntity> allPayments = paymentRepository.findAll();
        return allPayments.stream().map(payment -> PaymentMapper.toDTO(payment)).collect(Collectors.toList());
        }

        public PaymentsTotalityDTO getAllPaymentsBetween(LocalDateTime start, LocalDateTime end, Pageable pageable){
        Page<PaymentEntity> payments = paymentRepository.findByCreatedAtBetween(start, end, pageable);
        Page<PaymentDTO> paymentsDTO = payments.map(PaymentMapper::toDTO);
        BigDecimal totalAmount = payments.stream().map(PaymentEntity :: getAmountPayment).
        reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

        return new PaymentsTotalityDTO(paymentsDTO.getContent(), totalAmount);
    }

    public Page<PaymentDTO> getAllCancelledPayments(Pageable pageable){
        Page<PaymentEntity> payments = paymentRepository.findALlByIsCancelledTrue(pageable);
        return payments.map(PaymentMapper::toDTO);
    }

    public Page<PaymentDTO> getCancelledBetween(LocalDateTime start, LocalDateTime end, Pageable pageable){
        Page<PaymentEntity> payments = paymentRepository.findByIsCancelledTrueAndCreatedAtBetween(start, end, pageable);
        return payments.map(PaymentMapper::toDTO);
    }
    }
