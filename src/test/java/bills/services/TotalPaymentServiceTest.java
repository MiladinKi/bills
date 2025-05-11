package bills.services;

import bills.entities.BillEntity;
import bills.entities.TotalPaymentEntity;
import bills.entities.dtos.TotalPaymentDTO;
import bills.entities.dtos.TotalPaymentSummaryDTO;
import bills.repositories.BillRepository;
import bills.repositories.TotalPaymentRepository;
import bills.utils.BillRepositoryTest;
import bills.utils.TotalPaymentRepositoryTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TotalPaymentServiceTest {

    @Autowired
    private TotalPaymentService totalPaymentService;

    @Autowired
    private TotalPaymentRepository totalPaymentRepository;

    @Autowired
    private BillRepository billRepository;

    @Test
    void createTotalPayment() {
        TotalPaymentRepositoryTest totalPaymentRepository = new TotalPaymentRepositoryTest();
        BillRepositoryTest billRepository = new BillRepositoryTest();
        TotalPaymentService totalPaymentService = new TotalPaymentService(totalPaymentRepository, billRepository);

        BillEntity entity = new BillEntity();
        entity.setName("struja");
        entity.setDescription("test");
        entity.setBillInterval(4);
        billRepository.save(entity);

        TotalPaymentDTO totalPaymentDTO = new TotalPaymentDTO();
        totalPaymentDTO.setAmountTotalPayment(BigDecimal.TEN);
        totalPaymentDTO.setPeriod(1);

        TotalPaymentDTO createdTotalPayment = totalPaymentService.createTotalPayment(totalPaymentDTO, entity.getId());

        assertNotNull(entity);
        assertEquals(entity.getId(), createdTotalPayment.getBillId());
        assertEquals(createdTotalPayment.getAmountTotalPayment(), createdTotalPayment.getAmountTotalPayment());
        assertEquals(totalPaymentDTO.getPeriod(), createdTotalPayment.getPeriod());


    }

    @Test
    void modifyTotalPaymentById() {
        TotalPaymentRepositoryTest totalPaymentRepositoryTest = new TotalPaymentRepositoryTest();
        BillRepositoryTest billRepositoryTest = new BillRepositoryTest();
        TotalPaymentService totalPaymentService = new TotalPaymentService(totalPaymentRepositoryTest, billRepositoryTest);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        TotalPaymentEntity original = new TotalPaymentEntity();
        original.setId(1);
        original.setAmountTotalPayment(BigDecimal.TEN);
        original.setPeriod(3);
        original.setBill(bill);
        totalPaymentRepositoryTest.save(original);

        TotalPaymentDTO updateTotalPaymentDTO = new TotalPaymentDTO();
        updateTotalPaymentDTO.setAmountTotalPayment(BigDecimal.valueOf(22));
        updateTotalPaymentDTO.setPeriod(6);

        TotalPaymentDTO modifyDTO = totalPaymentService.modifyTotalPaymentById(1, updateTotalPaymentDTO);

        // Uporedi BigDecimal koristeći compareTo za sigurnost
        assertEquals(0, modifyDTO.getAmountTotalPayment().compareTo(BigDecimal.valueOf(22)));
        assertEquals(6, modifyDTO.getPeriod());
    }

    @Test
    void deleteTotalPaymentById() {
        TotalPaymentRepositoryTest totalPaymentRepository = new TotalPaymentRepositoryTest();
        BillRepositoryTest billRepository = new BillRepositoryTest();
        TotalPaymentService totalPaymentService = new TotalPaymentService(totalPaymentRepository, billRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setId(1);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(2);
        totalPayment.setBill(bill);
        totalPaymentRepository.save(totalPayment);

        totalPaymentService.deleteTotalPaymentById(1);

        Optional<TotalPaymentEntity> maybeDeleted = totalPaymentRepository.findById(1);
        assertTrue(maybeDeleted.isEmpty());
    }

    @Test
    void getById() {
        TotalPaymentRepositoryTest totalPaymentRepository = new TotalPaymentRepositoryTest();
        BillRepositoryTest billRepository = new BillRepositoryTest();
        TotalPaymentService totalPaymentService = new TotalPaymentService(totalPaymentRepository, billRepository);

        BillEntity bill = new BillEntity();
        bill.setId(1);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setId(1);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(4);
        totalPayment.setBill(bill);
        totalPaymentRepository.save(totalPayment);

        TotalPaymentDTO result = totalPaymentService.getById(1);

        assertNotNull(result);
        assertEquals(BigDecimal.TEN, result.getAmountTotalPayment());
    }

    @Test
    void getAllTotalPayments() {
        TotalPaymentRepositoryTest totalPaymentRepository = new TotalPaymentRepositoryTest();
        BillRepositoryTest billRepository = new BillRepositoryTest();
        TotalPaymentService totalPaymentService = new TotalPaymentService(totalPaymentRepository, billRepository);

        BillEntity bill1 = new BillEntity();
        bill1.setId(1);

        BillEntity bill2 = new BillEntity();
        bill2.setId(2);

        TotalPaymentEntity totalPayment1 = new TotalPaymentEntity();
        totalPayment1.setId(1);
        totalPayment1.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment1.setPeriod(4);
        totalPayment1.setBill(bill1);
        totalPaymentRepository.save(totalPayment1);

        TotalPaymentEntity totalPayment2 = new TotalPaymentEntity();
        totalPayment2.setId(2);
        totalPayment2.setAmountTotalPayment(BigDecimal.ONE);
        totalPayment2.setPeriod(14);
        totalPayment2.setBill(bill2);
        totalPaymentRepository.save(totalPayment2);

        List<TotalPaymentDTO> totalPaymentDTOS = totalPaymentService.getAllTotalPayments();

        assertEquals(2, totalPaymentDTOS.size());

    }

    @Test
    void findAllPaymentsBetweenPeriod() {

        TotalPaymentRepositoryTest totalPaymentRepository = new TotalPaymentRepositoryTest();
        BillRepositoryTest billRepository = new BillRepositoryTest();
        TotalPaymentService totalPaymentService = new TotalPaymentService(totalPaymentRepository, billRepository);

        BillEntity bill1 = new BillEntity();
        bill1.setId(1);

        BillEntity bill2 = new BillEntity();
        bill2.setId(2);

        TotalPaymentEntity totalPayment1 = new TotalPaymentEntity();
        totalPayment1.setId(1);
        totalPayment1.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment1.setPeriod(4);
        totalPayment1.setBill(bill1);
        totalPaymentRepository.save(totalPayment1);

        TotalPaymentEntity totalPayment2 = new TotalPaymentEntity();
        totalPayment2.setId(2);
        totalPayment2.setAmountTotalPayment(BigDecimal.ONE);
        totalPayment2.setPeriod(14);
        totalPayment2.setBill(bill2);
        totalPaymentRepository.save(totalPayment2);

        List<TotalPaymentSummaryDTO> totals = totalPaymentService.findAllPaymentsBetweenPeriod(1, 10);

        assertEquals(1, totals.size());
    }
}