package bills.services;

import bills.entities.dtos.BillDTO;
import bills.entities.dtos.BillTotalityDTO;
import bills.repositories.BillRepository;
import bills.utils.BillRepositoryTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BillServiceTest {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private  BillService billService;

    @BeforeEach
    void clearDatabase(){
        billRepository.deleteAll();
    }

    @Test
    void createBill() {
        BillService billService = new BillService(new BillRepositoryTest());
        BillDTO dto = new BillDTO();
        dto.setAmount(BigDecimal.TEN);
        dto.setDateOfBill(LocalDate.now());
        dto.setDescription("description");
        dto.setName("name");
        var retDto = billService.createBill(dto);
      //  var retDto = billService.createBill(null);
        assertEquals(dto.getName(), retDto.getName());
        assertTrue(retDto.getId() > 0);
        assertEquals(dto.getDescription(), retDto.getDescription());
        assertEquals(0, dto.getAmount().compareTo(retDto.getAmount()) );
        assertEquals(dto.getDateOfBill(), retDto.getDateOfBill());
    }

    @Test
    void deleteBillById() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);

        BillDTO dto = new BillDTO();
        dto.setAmount(BigDecimal.TEN);
        dto.setDateOfBill(LocalDate.now());
        dto.setName("Delete test");
        dto.setDescription("Description delete test");

        var created = service.createBill(dto);
        assertNotNull(created);
        assertTrue(created.getId() > 0);

        assertTrue(repo.findById(created.getId()).isPresent());

        service.deleteBillById(created.getId());

    }

    @Test
    void modifyById() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);

        BillDTO original = new BillDTO();
        original.setName("Stari naziv");
        original.setAmount(BigDecimal.TEN);
        original.setDateOfBill(LocalDate.now());
        original.setDescription("Originalni opis");

        var saved = service.createBill(original);
        assertNotNull(saved);

        BillDTO updated = new BillDTO();
        updated.setName("Novi naziv");
        updated.setAmount(BigDecimal.valueOf(200));
        updated.setDateOfBill(LocalDate.of(2025, 02, 28));
        updated.setDescription("Novi opis");

        var result = service.modifyById(saved.getId(), updated);

        assertNotNull(result);
        assertEquals(updated.getName(), result.getName());
        assertEquals(updated.getAmount(), result.getAmount());
        assertEquals(updated.getDateOfBill(), result.getDateOfBill());
        assertEquals(updated.getDescription(), result.getDescription());

        var inRepo = repo.findById(saved.getId()).orElseThrow();
        assertEquals(updated.getName(), inRepo.getName());
        assertEquals(updated.getAmount(), inRepo.getAmount());
        assertEquals(updated.getDateOfBill(), inRepo.getDateOfBill());
        assertEquals(updated.getDescription(), inRepo.getDescription());
    }

    @Test
    void getById() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);
        BillDTO dto = new BillDTO();
        dto.setName("test");
        dto.setAmount(BigDecimal.TEN);
        dto.setDateOfBill(LocalDate.now());
        dto.setDescription("test description");

        var saved = service.createBill(dto);

        BillDTO found = service.getById(saved.getId());
        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(dto.getName(), found.getName());
        assertEquals(0, dto.getAmount().compareTo(found.getAmount()));
        assertEquals(dto.getDateOfBill(), found.getDateOfBill());
        assertEquals(dto.getDescription(), found.getDescription());

    }

    @Test
    void getBillByIdNonExisting(){
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);
        Exception exception = assertThrows(RuntimeException.class,
                ()-> {service.getById(1224567);});

        assertEquals("Bill not found!", exception.getMessage());
    }

    @Test
    void getAllBillsWithName() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);

        BillDTO bill1 = new BillDTO();
        bill1.setName("struja");
        bill1.setAmount(BigDecimal.TEN);
        bill1.setDateOfBill(LocalDate.of(2025,04, 01));
        bill1.setDescription("racun1");

        BillDTO bill2 = new BillDTO();
        bill2.setName("struja");
        bill2.setAmount(BigDecimal.valueOf(200));
        bill2.setDateOfBill(LocalDate.now());
        bill2.setDescription("racun2");

        BillDTO bill3 = new BillDTO();
        bill3.setName("grejanje");
        bill3.setAmount(BigDecimal.TEN);
        bill3.setDateOfBill(LocalDate.now());
        bill3.setDescription("racun3");

        service.createBill(bill1);
        service.createBill(bill2);
        service.createBill(bill3);

        List<BillDTO> bills = service.getAllByName("struja");
        assertEquals(2, bills.size());
        assertTrue(bills.stream().allMatch(b -> b.getName().equals("struja")));
    }

    @Test
    void findByDate() {

        LocalDate start = LocalDate.of(2025, 01, 01);
        LocalDate end = LocalDate.now();

        BillDTO bill1 = new BillDTO();
        bill1.setName("struja");
        bill1.setAmount(BigDecimal.TEN);
        bill1.setDateOfBill(LocalDate.of(2025,04, 01));
        bill1.setDescription("racun1");

        BillDTO bill2 = new BillDTO();
        bill2.setName("struja");
        bill2.setAmount(BigDecimal.valueOf(200));
        bill2.setDateOfBill(LocalDate.of(2025, 02, 02));
        bill2.setDescription("racun2");


        billService.createBill(bill1);
        billService.createBill(bill2);

        BillTotalityDTO result = billService.findByDate(start, end);
        assertEquals(2, result.getBills().size());
        assertEquals(new BigDecimal("210.00").setScale(2), result.getTotalAmount());


    }

    @Test
    void findByNameAndDate() {

        LocalDate start = LocalDate.of(2025, 01, 01);
        LocalDate end = LocalDate.now();

        BillDTO bill1 = new BillDTO();
        bill1.setName("struja");
        bill1.setAmount(BigDecimal.TEN);
        bill1.setDateOfBill(LocalDate.of(2025,04, 01));
        bill1.setDescription("racun1");

        BillDTO bill2 = new BillDTO();
        bill2.setName("struja");
        bill2.setAmount(BigDecimal.valueOf(200));
        bill2.setDateOfBill(LocalDate.of(2025, 02, 02));
        bill2.setDescription("racun2");

        BillDTO bill3 = new BillDTO();
        bill3.setName("grejanje");
        bill3.setAmount(BigDecimal.TEN);
        bill3.setDateOfBill(LocalDate.now());
        bill3.setDescription("racun3");

        billService.createBill(bill1);
        billService.createBill(bill2);
        billService.createBill(bill3);

        BillTotalityDTO result = billService.findByNameAndDate("struja",start, end);
        assertEquals(2, result.getBills().size());
        assertEquals(new BigDecimal("210.00").setScale(2), result.getTotalAmount());


    }
}