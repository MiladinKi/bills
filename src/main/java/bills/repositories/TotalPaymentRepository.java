package bills.repositories;

import bills.entities.TotalPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TotalPaymentRepository extends JpaRepository<TotalPaymentEntity, Integer> {
    List<TotalPaymentEntity> findAllByPeriodBetween(Integer from, Integer to);
}
