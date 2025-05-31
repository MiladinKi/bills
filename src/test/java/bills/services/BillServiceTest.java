package bills.services;

import bills.entities.dtos.BillDTO;
import bills.repositories.BillRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BillServiceTest {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    @BeforeEach
    void clearDatabase(){
        billRepository.deleteAll();
    }

    @Test
    void createBill() {
        BillDTO dto = new BillDTO();
        dto.setDescription("description");
        dto.setName("name");
        dto.setBillInterval(1);

        BillDTO retDto = billService.createBill(dto);

        assertEquals(dto.getName(), retDto.getName());
        assertNotNull(retDto.getId());
        assertEquals(dto.getDescription(), retDto.getDescription());
        assertEquals(1, retDto.getBillInterval());
    }

    @Test
    void modifyById() {
        BillDTO original = new BillDTO();
        original.setName("Stari naziv");
        original.setDescription("Originalni opis");
        original.setBillInterval(1);

        BillDTO saved = billService.createBill(original);
        assertNotNull(saved);

        BillDTO updated = new BillDTO();
        updated.setName("Novi naziv");
        updated.setDescription("Novi opis");
        updated.setBillInterval(3);

        BillDTO result = billService.modifyById(saved.getId(), updated);

        assertNotNull(result);
        assertEquals(updated.getName(), result.getName());
        assertEquals(updated.getDescription(), result.getDescription());
        assertEquals(updated.getBillInterval(), result.getBillInterval());

        var inRepo = billRepository.findById(saved.getId()).orElseThrow();
        assertEquals(updated.getName(), inRepo.getName());
        assertEquals(updated.getDescription(), inRepo.getDescription());
        assertEquals(updated.getBillInterval(), inRepo.getBillInterval());
    }

    @Test
    void getById() {
        BillDTO dto = new BillDTO();
        dto.setName("test");
        dto.setDescription("test description");
        dto.setBillInterval(2);

        BillDTO saved = billService.createBill(dto);

        BillDTO found = billService.getById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals(dto.getName(), found.getName());
        assertEquals(dto.getDescription(), found.getDescription());
        assertEquals(dto.getBillInterval(), found.getBillInterval());
    }

    @Test
    void getBillByIdNonExisting(){
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> billService.getById(1224567));

        assertEquals("Bill not found!", exception.getMessage());
    }

    @Test
    void getAllBillsWithName() {
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

        billService.createBill(bill1);
        billService.createBill(bill2);
        billService.createBill(bill3);

//        List<BillDTO> bills = billService.getAllByName("struja");
//        assertEquals(2, bills.size());
//        assertTrue(bills.stream().allMatch(b -> b.getName().equals("struja")));
        Pageable pageable = (Pageable) (Pageable) PageRequest.of(0, 5, Sort.by("name").descending());
        List<BillDTO> bills = billService.getAllByName("struja", pageable).getContent();

        assertEquals(2, bills.size());
        assertTrue(bills.stream().allMatch(b -> b.getName().equals("struja")));
    }
}
