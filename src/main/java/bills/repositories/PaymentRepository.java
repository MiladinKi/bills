package bills.repositories;

import bills.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    List<PaymentEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<PaymentEntity> findALlByIsCancelledTrue();
    List<PaymentEntity> findByIsCancelledTrueAndCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
