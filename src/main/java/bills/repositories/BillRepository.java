package bills.repositories;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    List<BillEntity> findByName(String name);

    void deleteById(BillEntity entity);
}

