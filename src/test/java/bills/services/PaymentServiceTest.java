package bills.services;

import bills.entities.BillEntity;
import bills.entities.PaymentEntity;
import bills.entities.dtos.PaymentDTO;
import bills.entities.dtos.PaymentsTotalityDTO;
import bills.repositories.BillRepository;
import bills.repositories.PaymentRepository;
import bills.utils.PaymentRepositoryTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
//@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Autowired
    private BillRepository billRepository;

//    @BeforeEach
//    void setUp() {
//        paymentService = new PaymentService(paymentRepository);
//    }

    @BeforeEach
    void clearDatabase(){
        billRepository.deleteAll();
    }

    @Test
    void createPayment (){
        BillEntity entity = new BillEntity();
        entity.setName("struja");
        entity.setDescription("test");
        entity.setBillInterval(4);
        billRepository.save(entity);

        PaymentDTO dto = new PaymentDTO();
        dto.setAmountPayment(BigDecimal.ONE);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setCancelled(false);

        PaymentDTO createdPayment = paymentService.createPayment(entity.getId(), dto);

        assertNotNull(entity);
        assertEquals(entity.getId(), createdPayment.getBillId());
        assertEquals(dto.getAmountPayment(), createdPayment.getAmountPayment());
        assertEquals(dto.getCreatedAt(), createdPayment.getCreatedAt());

    }

    @Test
    void modifyPaymentById() {
        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        PaymentEntity originalPayment = new PaymentEntity();
        originalPayment.setBill(bill);
        originalPayment.setAmountPayment(BigDecimal.TEN);
        originalPayment.setCreatedAt(LocalDateTime.of(2025, 12, 24, 14, 44));
        originalPayment.setIsCancelled(false);


        PaymentEntity savedPayment = paymentRepository.save(originalPayment);
        Integer savedId = savedPayment.getId();

        PaymentDTO modifiedDTO = new PaymentDTO();
        modifiedDTO.setAmountPayment(BigDecimal.valueOf(33));
        modifiedDTO.setCreatedAt(LocalDateTime.of(2025, 1, 1, 11, 1));
        modifiedDTO.setCancelled(true);

        PaymentDTO result = paymentService.modifyPayment(savedId, modifiedDTO);

        assertEquals(BigDecimal.valueOf(33), result.getAmountPayment());
        assertEquals(LocalDateTime.of(2025, 1, 1, 11, 1), result.getCreatedAt());
        assertTrue(result.getCancelled());
    }

    @Test
    void cancalledPaymentById(){
        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        LocalDateTime createdTime = LocalDateTime.now();

        PaymentEntity payment = new PaymentEntity();
        payment.setAmountPayment(BigDecimal.TEN);
        payment.setCreatedAt(createdTime);
        payment.setIsCancelled(false);
        payment.setBill(bill);

        payment.setId(1);
        paymentRepository.save(payment);

        PaymentDTO result = paymentService.cancelledPaymentById(1);
        assertTrue(result.getCancelled(), "Payment should be cancelled!");
        assertEquals(BigDecimal.TEN, result.getAmountPayment());
        assertEquals(createdTime, result.getCreatedAt());
    }

    @Test
    void getPaymentWithId(){
        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setAmountPayment(BigDecimal.TEN);
        payment1.setCreatedAt(LocalDateTime.now());
        payment1.setIsCancelled(false);
        payment1.setBill(bill);

        payment1.setId(1);
        paymentRepository.save(payment1);

        PaymentDTO result = paymentService.getById(1);
        assertEquals(BigDecimal.TEN, result.getAmountPayment());
        assertFalse(false);
    }

    @Test
    void getAllPayments(){
        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setAmountPayment(BigDecimal.valueOf(10));
        payment1.setCreatedAt(LocalDateTime.of(2025, 01, 31, 12, 33));
        payment1.setIsCancelled(false);
        payment1.setBill(bill);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setAmountPayment(BigDecimal.valueOf(144));
        payment2.setCreatedAt(LocalDateTime.now());
        payment2.setIsCancelled(true);
        payment2.setBill(bill);
        paymentRepository.save(payment2);

        List<PaymentDTO> allPayments = paymentService.getAllPayments();

        assertEquals(2, allPayments.size());
        assertEquals(BigDecimal.TEN, allPayments.get(0).getAmountPayment());
        assertEquals(BigDecimal.valueOf(144), allPayments.get(1).getAmountPayment());
        assertFalse(allPayments.get(0).getCancelled());
        assertTrue(allPayments.get(1).getCancelled());

    }

    @Test
    void paymentsCreatedAt(){

        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setAmountPayment(BigDecimal.valueOf(10));
        payment1.setCreatedAt(LocalDateTime.of(2025, 1, 31, 12, 33));
        payment1.setIsCancelled(false);
        payment1.setBill(bill);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setAmountPayment(BigDecimal.valueOf(1));
        payment2.setCreatedAt(LocalDateTime.of(2025, 1, 12, 12, 23));
        payment2.setIsCancelled(false);
        payment2.setBill(bill);
        paymentRepository.save(payment2);

        PaymentEntity payment3 = new PaymentEntity();
        payment3.setAmountPayment(BigDecimal.valueOf(33));
        payment3.setCreatedAt(LocalDateTime.now());
        payment3.setIsCancelled(false);
        payment3.setBill(bill);
        paymentRepository.save(payment3);

      //  List<PaymentEntity> mockPayments = List.of(payment1, payment2);

        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 2, 12, 0, 0);

       // Mockito.when(paymentRepository.findByCreatedAtBetween(start, end)).thenReturn(mockPayments);
        PaymentsTotalityDTO payments = paymentService.getAllPaymentsBetween(start, end);

        assertEquals(2, payments.getPayments().size(), "Treba da budu 2 uplate u opsegu");
        assertTrue(BigDecimal.valueOf(11).compareTo(payments.getTotalAmount()) == 0, "Ukupan iznos treba biti 11");
    }

    @Test
    void allPaymentsCancelled(){
        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setAmountPayment(BigDecimal.TEN);
        payment1.setCreatedAt(LocalDateTime.of(2025, 01, 01, 12, 12));
        payment1.setIsCancelled(true);
        payment1.setBill(bill);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setAmountPayment(BigDecimal.ONE);
        payment2.setCreatedAt(LocalDateTime.of(2025, 01, 12, 12, 23));
        payment2.setIsCancelled(false);
        payment2.setBill(bill);
        paymentRepository.save(payment2);

        PaymentEntity payment3 = new PaymentEntity();
        payment3.setAmountPayment(BigDecimal.valueOf(33));
        payment3.setCreatedAt(LocalDateTime.of(2025, 03, 31, 11, 12));
        payment3.setIsCancelled(true);
        payment3.setBill(bill);
        paymentRepository.save(payment3);

        List<PaymentDTO> cancelledPayments = paymentService.gwtAllCancelledPayments();

        assertEquals(2, cancelledPayments.size(), "Two payments cancelled!");
        assertEquals(BigDecimal.valueOf(10), cancelledPayments.get(0).getAmountPayment());
        assertEquals(BigDecimal.valueOf(33), cancelledPayments.get(1).getAmountPayment());

    }

    @Test
    void allCancelledBetween(){
        PaymentRepositoryTest paymentRepository = new PaymentRepositoryTest();
        PaymentService paymentService = new PaymentService(paymentRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setAmountPayment(BigDecimal.TEN);
        payment1.setCreatedAt(LocalDateTime.of(2025, 01, 01, 12, 12));
        payment1.setIsCancelled(true);
        payment1.setBill(bill);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setAmountPayment(BigDecimal.ONE);
        payment2.setCreatedAt(LocalDateTime.of(2025, 01, 12, 12, 23));
        payment2.setIsCancelled(false);
        payment2.setBill(bill);
        paymentRepository.save(payment2);

        PaymentEntity payment3 = new PaymentEntity();
        payment3.setAmountPayment(BigDecimal.valueOf(33));
        payment3.setCreatedAt(LocalDateTime.of(2025, 03, 31, 11, 12));
        payment3.setIsCancelled(true);
        payment3.setBill(bill);
        paymentRepository.save(payment3);

        LocalDateTime start = LocalDateTime.of(2025, 01, 01, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 3, 1, 0, 0);

        List<PaymentDTO> cancelledPayments = paymentService.getCancelledBetween(start, end);

        assertEquals(1, cancelledPayments.size(), "One payment cancelled!");
        assertEquals(BigDecimal.TEN, cancelledPayments.get(0).getAmountPayment());

    }
}
