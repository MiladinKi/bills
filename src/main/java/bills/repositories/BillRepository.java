package bills.repositories;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    List<BillEntity> findByName(String name);
    List<BillEntity> findByDateOfBillBetween(LocalDate start, LocalDate end);
    List<BillEntity> findByNameAndDateOfBillBetween(String name, LocalDate start,
                                                      LocalDate end);
}

