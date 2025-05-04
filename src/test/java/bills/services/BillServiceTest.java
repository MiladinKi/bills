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
        dto.setDescription("description");
        dto.setName("name");
        dto.setBillInterval(1);
        var retDto = billService.createBill(dto);
      //  var retDto = billService.createBill(null);
        assertEquals(dto.getName(), retDto.getName());
        assertTrue(retDto.getId() > 0);
        assertEquals(dto.getDescription(), retDto.getDescription());
        assertEquals(1, retDto.getBillInterval());
    }

    @Test
    void deleteBillById() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);

        BillDTO dto = new BillDTO();

        dto.setName("Delete test");
        dto.setDescription("Description delete test");
        dto.setBillInterval(1);

        var created = service.createBill(dto);
        assertNotNull(created);
        assertTrue(created.getId() > 0);

        assertTrue(repo.findById(created.getId()).isPresent());
        assertTrue(created.getBillInterval() > 0);

        service.deleteBillById(created.getId());

    }

    @Test
    void modifyById() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);

        BillDTO original = new BillDTO();
        original.setName("Stari naziv");
        original.setDescription("Originalni opis");
        original.setBillInterval(1);

        var saved = service.createBill(original);
        assertNotNull(saved);

        BillDTO updated = new BillDTO();
        updated.setName("Novi naziv");
        updated.setDescription("Novi opis");
        updated.setBillInterval(3);

        var result = service.modifyById(saved.getId(), updated);

        assertNotNull(result);
        assertEquals(updated.getName(), result.getName());
        assertEquals(updated.getDescription(), result.getDescription());
        assertEquals(updated.getBillInterval(), result.getBillInterval());

        var inRepo = repo.findById(saved.getId()).orElseThrow();
        assertEquals(updated.getName(), inRepo.getName());
        assertEquals(updated.getDescription(), inRepo.getDescription());
        assertEquals(updated.getBillInterval(), inRepo.getBillInterval());
    }

    @Test
    void getById() {
        BillRepositoryTest repo = new BillRepositoryTest();
        BillService service = new BillService(repo);
        BillDTO dto = new BillDTO();
        dto.setName("test");
        dto.setDescription("test description");
        dto.setBillInterval(2);

        var saved = service.createBill(dto);

        BillDTO found = service.getById(saved.getId());
        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(dto.getName(), found.getName());
        assertEquals(dto.getDescription(), found.getDescription());
        assertEquals(dto.getBillInterval(), found.getBillInterval());

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
        bill1.setBillInterval(2);
        bill1.setDescription("racun1");

        BillDTO bill2 = new BillDTO();
        bill2.setName("struja");
        bill2.setBillInterval(1);
        bill2.setDescription("racun2");

        BillDTO bill3 = new BillDTO();
        bill3.setName("grejanje");
        bill3.setBillInterval(1);
        bill3.setDescription("racun3");

        service.createBill(bill1);
        service.createBill(bill2);
        service.createBill(bill3);

        List<BillDTO> bills = service.getAllByName("struja");
        assertEquals(2, bills.size());
        assertTrue(bills.stream().allMatch(b -> b.getName().equals("struja")));
    }

}