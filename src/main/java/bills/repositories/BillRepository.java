package bills.repositories;

import bills.entities.BillEntity;
import bills.entities.dtos.BillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    Page<BillEntity> findByName(String name, Pageable pageable);

    void deleteById(BillEntity entity);
}

