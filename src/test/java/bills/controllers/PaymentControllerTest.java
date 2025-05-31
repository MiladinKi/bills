package bills.controllers;

import bills.entities.BillEntity;
import bills.entities.PaymentEntity;
import bills.repositories.BillRepository;
import bills.repositories.PaymentRepository;
import bills.services.PaymentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional

public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillRepository billRepository;

    @BeforeEach
    void setup() {
        paymentRepository.deleteAll();
    }


    @Test
    void testCreatePayment() throws Exception{
        Integer id = 1;

        String json = """
                {
                "billId" : 1,
                "amountPayment" : 150.00,
                "createdAt" : "2025-05-08T12:00:00",
                "isCancelled" : false
                }""";
        mockMvc.perform(post("/bills/payment/createPayment/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountPayment").value(150.00))
                .andExpect(jsonPath("$.isCancelled").value(false))
                .andDo(print());
    }

    @Test
    void modifyById() throws Exception{

        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);


        PaymentEntity payment = new PaymentEntity();
        payment.setBill(bill);
        payment.setAmountPayment(BigDecimal.TEN);
        payment.setCreatedAt(LocalDateTime.of(2025, 02, 02, 12, 12));
        payment.setIsCancelled(false);

        paymentRepository.save(payment);

        String updatedPayment = """
                {
                "billId":1,
                "amountPayment": 122.22,
                "createdAt":"2025-02-12T12:00:00",
                "isCancelled":false
                }""";

        mockMvc.perform(put("/bills/payment/modifyPayment/{id}", payment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedPayment))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountPayment").value(122.22))
                .andExpect(jsonPath("$.isCancelled").value(false));

    }

    @Test
    void cancelledPayment() throws Exception{

        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        PaymentEntity payment = new PaymentEntity();
        payment.setBill(bill);
        payment.setAmountPayment(BigDecimal.TEN);
        payment.setCreatedAt(LocalDateTime.of(2025, 02, 02, 12, 12));
        payment.setIsCancelled(false);
        paymentRepository.save(payment);

        mockMvc.perform(delete("/bills/payment/cancelled/{id}", payment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCancelled").value(true));


    }

    @Test
    void getById() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        PaymentEntity payment = new PaymentEntity();
        payment.setBill(bill);
        payment.setAmountPayment(BigDecimal.TEN);
        payment.setCreatedAt(LocalDateTime.of(2025, 02, 02, 12, 12));
        payment.setIsCancelled(false);
        paymentRepository.save(payment);

        mockMvc.perform(get("/bills/payment/getById/{id}", payment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amountPayment").value(10))
                .andExpect(jsonPath("$.isCancelled").value(false));

    }

    @Test
    void getAllPayments() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        PaymentEntity payment = new PaymentEntity();
        payment.setBill(bill);
        payment.setAmountPayment(BigDecimal.TEN);
        payment.setCreatedAt(LocalDateTime.of(2025, 02, 02, 12, 12));
        payment.setIsCancelled(false);
        paymentRepository.save(payment);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setBill(bill);
        payment2.setAmountPayment(BigDecimal.ONE);
        payment2.setCreatedAt(LocalDateTime.now());
        payment2.setIsCancelled(false);
        paymentRepository.save(payment2);

        mockMvc.perform(get("/bills/payment/allPayments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].amountPayment").value(10));
    }

    @Test
    void paymentsBetween() throws Exception {
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setBill(bill);
        payment1.setAmountPayment(BigDecimal.TEN);
        payment1.setCreatedAt(LocalDateTime.of(2025, 2, 2, 12, 12));
        payment1.setIsCancelled(false);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setBill(bill);
        payment2.setAmountPayment(BigDecimal.ONE);
        payment2.setCreatedAt(LocalDateTime.of(2025, 4, 1, 12, 0)); // van opsega
        payment2.setIsCancelled(false);
        paymentRepository.save(payment2);

        mockMvc.perform(get("/bills/payment/allPaymentsBetween")
                        .param("start", "2025-01-01T00:00:00")
                        .param("end", "2025-03-01T00:00:00")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                // Proveravamo da je lista payments veličine 1
                .andExpect(jsonPath("$.payments.length()").value(1))
                // Proveravamo da je ukupna suma jednaka 10
                .andExpect(jsonPath("$.totalAmount").value(10))
                // Opcionalno: proveri da prvi payment ima amountPayment 10
                .andExpect(jsonPath("$.payments[0].amountPayment").value(10));
    }


    @Test
    void getAllCancelled() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setBill(bill);
        payment1.setAmountPayment(BigDecimal.TEN);
        payment1.setCreatedAt(LocalDateTime.of(2025, 2, 2, 12, 12));
        payment1.setIsCancelled(true);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setBill(bill);
        payment2.setAmountPayment(BigDecimal.ONE);
        payment2.setCreatedAt(LocalDateTime.of(2025, 4, 1, 12, 0)); // van opsega
        payment2.setIsCancelled(false);
        paymentRepository.save(payment2);

        mockMvc.perform(get("/bills/payment/cancelledPayments")
                        .param("page","0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].isCancelled").value(true))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.size").value(5));
    }

    @Test
    void cancelledPaymentsBetween() throws Exception{
        BillEntity bill = new BillEntity();
        bill.setId(1);
        billRepository.save(bill);

        PaymentEntity payment1 = new PaymentEntity();
        payment1.setBill(bill);
        payment1.setAmountPayment(BigDecimal.TEN);
        payment1.setCreatedAt(LocalDateTime.of(2025, 2, 2, 12, 12));
        payment1.setIsCancelled(true);
        paymentRepository.save(payment1);

        PaymentEntity payment2 = new PaymentEntity();
        payment2.setBill(bill);
        payment2.setAmountPayment(BigDecimal.ONE);
        payment2.setCreatedAt(LocalDateTime.of(2025, 4, 1, 12, 0)); // van opsega
        payment2.setIsCancelled(false);
        paymentRepository.save(payment2);

        PaymentEntity payment3 = new PaymentEntity();
        payment3.setBill(bill);
        payment3.setAmountPayment(BigDecimal.ONE);
        payment3.setCreatedAt(LocalDateTime.of(2025, 1, 1, 12, 0)); // van opsega
        payment3.setIsCancelled(false);
        paymentRepository.save(payment3);

        mockMvc.perform(get("/bills/payment/allCancelledBetween")
                .param("start", "2025-01-01T00:00:00")
                .param("end", "2025-03-03T00:00:00")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}
