package bills.controllers;

import bills.controllers.utils.RESTError;
import bills.entities.dtos.BillDTO;
import bills.entities.dtos.BillTotalityDTO;
import bills.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/bills/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @RequestMapping(method = RequestMethod.POST, path = "/createBill")
    public ResponseEntity<?> createBill(@RequestBody BillDTO billDTO){
        try {
            BillDTO bill = billService.createBill(billDTO);
            return new ResponseEntity<>(bill, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RESTError(1, "Error occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteById/{billId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer billId){
        try {
            billService.deleteBillById(billId);
            return ResponseEntity.ok("Bill successfully delete.");
        } catch (Exception e) {
            return new ResponseEntity<>(new RESTError(1, "Error occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

    @RequestMapping(method = RequestMethod.PUT, path = "/modifyById/{billId}")
    public ResponseEntity<?> modifyById(@PathVariable Integer billId, @RequestBody BillDTO billDTO){
        try {
            BillDTO updatedBill = billService.modifyById(billId, billDTO);
            return new ResponseEntity<>(updatedBill, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new RESTError(1, "Error occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
}

    @RequestMapping(method = RequestMethod.GET, path = "/getById/{billId}")
    public ResponseEntity<?> getById(@PathVariable Integer billId){
        try {
            BillDTO bill = billService.getById(billId);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RESTError(1, "Exception occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allBills")
    public ResponseEntity<?> getAll(){
        try {
            List<BillDTO> bills = billService.getAll();
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RESTError(1, "Exception occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByName/{name}")
    public ResponseEntity<?> getAllByName(@PathVariable String name){
        try {
            List<BillDTO> bills = billService.getAllByName(name);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RESTError(1, "Exception occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByDate")
    public ResponseEntity<?> findByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        try{
            BillTotalityDTO totality = billService.findByDate(start, end);
            return new ResponseEntity<>(totality, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new RESTError(1, "Exception occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/byNameAndDate")
    public ResponseEntity<?> getByNameAndDate(@RequestParam String name,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        try {
            BillTotalityDTO totality = billService.findByNameAndDate(name, start, end);
            return new ResponseEntity<>(totality, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RESTError(1, "Exception occur: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
