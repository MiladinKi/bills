package bills.controllers;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import bills.repositories.BillRepository;
import bills.services.BillService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;


//    @BeforeEach
//    void setUp() {
//        billRepository.deleteAll(); // očistimo bazu pre svakog testa
//    }
//    @Test
//    void createBill () throws Exception {
//        String json = """
//                {
//                "name":"struja",
//                "amount": 100.00,
//                "dateOfBill":"2025-02-12",
//                "description":"create test"
//                }
//                """;
//
//        mockMvc.perform(post("/bills/bill/createBill")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("struja"))
//                .andExpect(jsonPath("$.amount").value(100.00));
//    }

//    @Test
//    void deleteById() throws  Exception{
//
//        BillDTO dto = new BillDTO();
//        dto.setName("struja");
//        dto.setAmount(BigDecimal.TEN);
//        dto.setDateOfBill(LocalDate.now());
//        dto.setDescription("test za brisanje");
//
//        BillDTO created = billService.createBill(dto);
//        Integer billId = created.getId();
//
//        mockMvc.perform(delete("/bills/bill/deleteById/" + billId))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Bill successfully delete."));
//    }

//    @Test
//    void modifyById() throws  Exception{
//        BillEntity bill = new BillEntity();
//        bill.setName("struja");
//        bill.setAmount(BigDecimal.TEN);
//        bill.setDateOfBill(LocalDate.now());
//        bill.setDescription("modify test");
//        billRepository.save(bill);
//
//        String updatedJson = """
//                {
//                "name":"struja-updated",
//                "amount":150.00,
//                "dateOfBill":"2025-03-03",
//                "description":"test updated"
//                }
//                """;
//
//        mockMvc.perform(put("/bills/bill/modifyById/" + bill.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(updatedJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("struja-updated"))
//                .andExpect((jsonPath("$.amount").value(150.00)))
//                .andExpect(jsonPath("$.dateOfBill").value("2025-03-03"));
//    }

//    @Test
//    void getById() throws  Exception{
//        BillEntity bill = new BillEntity();
//        bill.setName("struja");
//        bill.setAmount(BigDecimal.valueOf(100.00));
//        bill.setDateOfBill(LocalDate.now());
//        bill.setDescription("test");
//        billRepository.save(bill);
//
//        mockMvc.perform(get("/bills/bill/getById/" + bill.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("struja"))
//                .andExpect(jsonPath("$.amount").value(100.00))
//                .andExpect(jsonPath("$.description").value("test"));
//    }

//    @Test
//    void getAll() throws Exception{
//        BillEntity bill1 = new BillEntity();
//        bill1.setName("struja");
//        bill1.setAmount(BigDecimal.valueOf(10.00));
//        bill1.setDateOfBill(LocalDate.now());
//        bill1.setDescription("test1");
//        billRepository.save(bill1);
//
//        BillEntity bill2 = new BillEntity();
//        bill2.setName("struja");
//        bill2.setAmount(BigDecimal.valueOf(10.99));
//        bill2.setDateOfBill(LocalDate.of(2025, 03, 23));
//        bill2.setDescription("test2");
//        billRepository.save(bill2);
//
//        BillEntity bill3 = new BillEntity();
//        bill3.setName("gas");
//        bill3.setAmount(BigDecimal.valueOf(14.44));
//        bill3.setDateOfBill(LocalDate.of(2025, 01, 01));
//        bill3.setDescription("test3");
//        billRepository.save(bill3);
//
//        mockMvc.perform(get("/bills/bill/allBills"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(3))
//                .andExpect(jsonPath("$[0].name").value("struja"))
//                .andExpect(jsonPath("$[2].name").value("gas"));
//    }

//    @Test
//    void getAllByName() throws  Exception{
//        BillEntity bill1 = new BillEntity();
//        bill1.setName("struja");
//        bill1.setAmount(BigDecimal.valueOf(10.00));
//        bill1.setDateOfBill(LocalDate.now());
//        bill1.setDescription("test1");
//        billRepository.save(bill1);
//
//        BillEntity bill2 = new BillEntity();
//        bill2.setName("struja");
//        bill2.setAmount(BigDecimal.valueOf(10.99));
//        bill2.setDateOfBill(LocalDate.of(2025, 03, 23));
//        bill2.setDescription("test2");
//        billRepository.save(bill2);
//
//        BillEntity bill3 = new BillEntity();
//        bill3.setName("gas");
//        bill3.setAmount(BigDecimal.valueOf(14.44));
//        bill3.setDateOfBill(LocalDate.of(2025, 01, 01));
//        bill3.setDescription("test3");
//        billRepository.save(bill3);
//
//        mockMvc.perform(get("/bills/bill/findByName/struja"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].name").value("struja"))
//                .andExpect(jsonPath("$[1].name").value("struja"));
//    }

//    @Test
//    void findByDate() throws Exception{
//        BillEntity bill1 = new BillEntity();
//        bill1.setName("struja");
//        bill1.setAmount(BigDecimal.valueOf(10.00));
//        bill1.setDateOfBill(LocalDate.now());
//        bill1.setDescription("test1");
//        billRepository.save(bill1);
//
//        BillEntity bill2 = new BillEntity();
//        bill2.setName("struja");
//        bill2.setAmount(BigDecimal.valueOf(10.99));
//        bill2.setDateOfBill(LocalDate.of(2025, 03, 23));
//        bill2.setDescription("test2");
//        billRepository.save(bill2);
//
//        BillEntity bill3 = new BillEntity();
//        bill3.setName("gas");
//        bill3.setAmount(BigDecimal.valueOf(14.44));
//        bill3.setDateOfBill(LocalDate.of(2025, 01, 01));
//        bill3.setDescription("test3");
//        billRepository.save(bill3);
//
//        mockMvc.perform(get("/bills/bill/findByDate")
//                .param("start", "2025-01-01")
//                .param("end", "2025-03-31"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$.totalAmount").value(25.43));
//    }


//    @Test
//    void getByNameAndDate() throws Exception{
//        BillEntity bill1 = new BillEntity();
//        bill1.setName("struja");
//        bill1.setAmount(BigDecimal.valueOf(10.00));
//        bill1.setDateOfBill(LocalDate.of(2025, 04, 12));
//        bill1.setDescription("test1");
//        billRepository.save(bill1);
//
//        BillEntity bill2 = new BillEntity();
//        bill2.setName("struja");
//        bill2.setAmount(BigDecimal.valueOf(10.99));
//        bill2.setDateOfBill(LocalDate.of(2025, 03, 23));
//        bill2.setDescription("test2");
//        billRepository.save(bill2);
//
//        BillEntity bill3 = new BillEntity();
//        bill3.setName("gas");
//        bill3.setAmount(BigDecimal.valueOf(14.44));
//        bill3.setDateOfBill(LocalDate.of(2025, 01, 01));
//        bill3.setDescription("test3");
//        billRepository.save(bill3);
//
//        mockMvc.perform(get("/bills/bill/byNameAndDate")
//                .param("name","struja")
//                .param("start", "2025-01-01")
//                .param("end", "2025-03-31"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.bills.length()").value(1))
//                .andExpect(jsonPath("$.totalAmount").value(10.99));
//    }
}