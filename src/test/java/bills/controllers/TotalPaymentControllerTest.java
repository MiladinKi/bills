package bills.controllers;

import bills.entities.BillEntity;
import bills.entities.ETotalPayment;
import bills.entities.TotalPaymentEntity;
import bills.entities.dtos.TotalPaymentSummaryDTO;
import bills.repositories.BillRepository;
import bills.repositories.TotalPaymentRepository;
import bills.services.TotalPaymentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class TotalPaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TotalPaymentService totalPaymentService;

    @Autowired
    private TotalPaymentRepository totalPaymentRepository;

    @Autowired
    private BillRepository billRepository;

    @BeforeEach
    void  setup(){
        totalPaymentRepository.deleteAll();
    }

    @Test
    void createTotalPayment() throws Exception{
        Integer id = 1;

        String json = """
                {
                "billId" : 1,
                "amountTotalPayment" : 150.00,
                "period" : 2,
                "payment": 1
                }""";

        mockMvc.perform(post("/bills/totalPayment/createTP/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountTotalPayment").value(150.00))
                .andExpect(jsonPath("$.period").value(2))
                .andExpect(jsonPath("$.payment").value(1));
    }

    @Test
    void modifyTotalPaymentById() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setBill(bill);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(3);
        totalPayment.setPayment(ETotalPayment.fromCode(2));
        totalPaymentRepository.save(totalPayment);

        String updatedTotalPayment = """
                {
                "billId":1,
                "amountTotalPayment":122.22,
                "period":4,
                "payment":1
                }
                """;

        mockMvc.perform(put("/bills/totalPayment/updateTP/{id}", totalPayment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTotalPayment))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountTotalPayment").value(122.22))
                .andExpect(jsonPath("$.payment").value(1))
                .andExpect(jsonPath("$.period").value(4));

    }

    @Test
    void deleteTotalPaymentById() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setBill(bill);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(3);
        totalPayment.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment);

        mockMvc.perform(delete("/bills/totalPayment/deleteById/{id}", totalPayment.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment is successfully deleted!"));
    }

    @Test
    void getById() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setBill(bill);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(3);
        totalPayment.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment);

        TotalPaymentEntity totalPayment2 = new TotalPaymentEntity();
        totalPayment2.setBill(bill);
        totalPayment2.setAmountTotalPayment(BigDecimal.ONE);
        totalPayment2.setPeriod(4);
        totalPayment2.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment2);

        mockMvc.perform(get("/bills/totalPayment/getById/{id}", totalPayment2.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountTotalPayment").value(1))
                .andExpect(jsonPath("$.period").value(4));
    }

    @Test
    void getAllTotalPayments() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setBill(bill);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(3);
        totalPayment.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment);

        TotalPaymentEntity totalPayment2 = new TotalPaymentEntity();
        totalPayment2.setBill(bill);
        totalPayment2.setAmountTotalPayment(BigDecimal.ONE);
        totalPayment2.setPeriod(4);
        totalPayment2.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment2);

        mockMvc.perform(get("/bills/totalPayment/allTotalPayments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].amountTotalPayment").value(1))
                .andExpect(jsonPath("$[1].payment").value(1))
                .andExpect(jsonPath("$[0].period").value(3));
    }

    @Test
    void getAllPaymentsBetween() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        TotalPaymentEntity totalPayment = new TotalPaymentEntity();
        totalPayment.setBill(bill);
        totalPayment.setAmountTotalPayment(BigDecimal.TEN);
        totalPayment.setPeriod(3);
        totalPayment.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment);

        TotalPaymentEntity totalPayment2 = new TotalPaymentEntity();
        totalPayment2.setBill(bill);
        totalPayment2.setAmountTotalPayment(BigDecimal.ONE);
        totalPayment2.setPeriod(6);
        totalPayment2.setPayment(ETotalPayment.fromCode(1));
        totalPaymentRepository.save(totalPayment2);

        mockMvc.perform(get("/bills/totalPayment/allPaymentsBetween")
                .param("from", "1")
                .param("to", "5")
                        .param("page","0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}