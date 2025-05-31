package bills.repositories;

import bills.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    Page<PaymentEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<PaymentEntity> findALlByIsCancelledTrue(Pageable pageable);
    Page<PaymentEntity> findByIsCancelledTrueAndCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
