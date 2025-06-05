package bills.controllers;

import bills.controllers.utils.RESTError;
import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import bills.entities.dtos.BillTotalityDTO;
import bills.services.BillService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/bills/bill")
public class BillController {


    final private BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/createBill")
    public BillDTO createBill(@RequestBody BillDTO billDTO){
        try {
            return billService.createBill(billDTO);

        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occur: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteById/{billId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer billId){
        try {
            billService.deleteBillById(billId);
            return ResponseEntity.ok("Bill successfully delete.");

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");

        } catch (Exception e) {
            throw new  ResponseStatusException (HttpStatus.INTERNAL_SERVER_ERROR, "Error occur: " + e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.PUT, path = "/modifyById/{billId}")
    public ResponseEntity<BillDTO> modifyById(@PathVariable Integer billId, @RequestBody BillDTO billDTO){
        try {
            BillDTO updatedBill = billService.modifyById(billId, billDTO);
            return ResponseEntity.ok(updatedBill);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getById/{billId}")
    public ResponseEntity<BillDTO> getById(@PathVariable Integer billId){
        try {
            BillDTO bill = billService.getById(billId);
            return ResponseEntity.ok(bill);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allBills")
    public ResponseEntity<List<BillDTO>> getAll(){
        try {
            List<BillDTO> bills = billService.getAll();
            return ResponseEntity.ok(bills);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/findByName/{name}")
    public ResponseEntity<Page<BillDTO>> getAllByName(@PathVariable String name,
                                                      @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.DESC) Pageable pageable){
        try {
            Page<BillDTO> bills = billService.getAllByName(name, pageable);
            return ResponseEntity.ok(bills);
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred: " + e.getMessage());
        }
    }

}