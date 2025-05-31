package bills.controllers;

import bills.entities.PaymentEntity;
import bills.entities.dtos.PaymentDTO;
import bills.entities.dtos.PaymentsTotalityDTO;
import bills.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.plaf.PanelUI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping (path = "/bills/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/createPayment/{id}")
    public PaymentDTO createPayment(@PathVariable Integer id, @RequestBody PaymentDTO dto){
        try {
            return paymentService.createPayment(id, dto);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occur: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/modifyPayment/{id}")
    public ResponseEntity<PaymentDTO> modifyById (@PathVariable Integer id, @RequestBody PaymentDTO dto){
        try {
            PaymentDTO updatedPayment = paymentService.modifyPayment(id, dto);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/cancelled/{id}")
    public ResponseEntity<PaymentDTO> cancelledPayment(@PathVariable Integer id){
        try {
            PaymentDTO cancelledPayment = paymentService.cancelledPaymentById(id);
            return ResponseEntity.ok(cancelledPayment);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getById/{id}")
    public ResponseEntity<PaymentDTO> getById(@PathVariable Integer id){
        try {
            PaymentDTO payment = paymentService.getById(id);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allPayments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments () {
        try {
            List<PaymentDTO> allPayments = paymentService.getAllPayments();
            return ResponseEntity.ok(allPayments);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allPaymentsBetween")
   //@GetMapping("/allPaymentsBetween")
    public ResponseEntity<PaymentsTotalityDTO> paymentsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        try {
            PaymentsTotalityDTO payments = paymentService.getAllPaymentsBetween(start, end, pageable);
            return ResponseEntity.ok(payments);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }


    @RequestMapping(method = RequestMethod.GET, path = "/cancelledPayments")
    public ResponseEntity<Page<PaymentDTO>> getAllCancelled (@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<PaymentDTO> payments = paymentService.getAllCancelledPayments(pageable);
            return ResponseEntity.ok(payments);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allCancelledBetween")
    public ResponseEntity<Page<PaymentDTO>> cancelledPaymentsBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                                                     @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        try {
            Page<PaymentDTO> payments = paymentService.getCancelledBetween(start, end, pageable);
            return ResponseEntity.ok(payments);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }
}
