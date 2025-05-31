package bills.controllers;

import bills.entities.TotalPaymentEntity;
import bills.entities.dtos.TotalPaymentDTO;
import bills.entities.dtos.TotalPaymentSummaryDTO;
import bills.repositories.TotalPaymentRepository;
import bills.services.TotalPaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping (path = "bills/totalPayment")
public class TotalPaymentController {

    private TotalPaymentService totalPaymentService;

    public TotalPaymentController(TotalPaymentService totalPaymentService) {
        this.totalPaymentService = totalPaymentService;
    }

    @Autowired
    private TotalPaymentRepository totalPaymentRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/createTP/{billId}")
    public TotalPaymentDTO createTotalPayment(@RequestBody TotalPaymentDTO totalPaymentDTO, @PathVariable Integer billId){
        try {
            return totalPaymentService.createTotalPayment(totalPaymentDTO, billId);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occur: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/updateTP/{id}")
    public ResponseEntity<TotalPaymentDTO> modifyTotalPaymentById(@RequestBody TotalPaymentDTO totalPaymentDTO, @PathVariable Integer id){
        try {
            TotalPaymentDTO updatedPayment = totalPaymentService.modifyTotalPaymentById(id, totalPaymentDTO);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteById/{id}")
    public ResponseEntity<String> deleteTotalPaymentById(@PathVariable Integer id){
        totalPaymentService.deleteTotalPaymentById(id);
        return ResponseEntity.ok("Payment is successfully deleted!");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getById/{id}")
    public ResponseEntity<TotalPaymentDTO> getById(@PathVariable Integer id){
        try{
        TotalPaymentDTO totalPayment = totalPaymentService.getById(id);
        return ResponseEntity.ok(totalPayment);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allTotalPayments")
    public ResponseEntity<List<TotalPaymentDTO>> getAllTotalPayments(){
        try {
            List<TotalPaymentDTO> totalPayments = totalPaymentService.getAllTotalPayments();
            return ResponseEntity.ok(totalPayments);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allPaymentsBetween")
    public ResponseEntity<Page<TotalPaymentSummaryDTO>> getAllPaymentsBetween(Integer from, Integer to, Pageable pageable){
        try {
            Page<TotalPaymentSummaryDTO> payments = totalPaymentService.findAllPaymentsBetweenPeriod(from, to, pageable);
            return ResponseEntity.ok(payments);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }
}
