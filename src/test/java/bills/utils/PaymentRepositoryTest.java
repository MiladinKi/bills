package bills.utils;

import bills.entities.PaymentEntity;
import bills.repositories.PaymentRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class PaymentRepositoryTest implements PaymentRepository {

    private final List<PaymentEntity> payments = new ArrayList<>();



    @Override
    public void deleteInBatch(Iterable<PaymentEntity> entities) {
        PaymentRepository.super.deleteInBatch(entities);
    }

    @Override
    public PaymentEntity getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends PaymentEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends PaymentEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<PaymentEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public PaymentEntity getOne(Integer integer) {
        return null;
    }

    @Override
    public PaymentEntity getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends PaymentEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends PaymentEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    public PaymentRepositoryTest() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public <S extends PaymentEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<PaymentEntity> findAll() {
        return new ArrayList<>(paymentStorage.values());
    }

    @Override
    public List<PaymentEntity> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    private  int idCounter = 0;
    private Map<Integer, PaymentEntity> paymentStorage = new HashMap<>();

    @Override
    public <S extends PaymentEntity> S save(@org.jetbrains.annotations.NotNull S entity) {
        if (entity.getId() == null) {
            entity.setId(idCounter++);
        }
       paymentStorage.put(entity.getId(), entity);
       return  entity;
    }

    @Override
    public Optional<PaymentEntity> findById(Integer id) {
        return Optional.ofNullable(paymentStorage.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(PaymentEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends PaymentEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<PaymentEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<PaymentEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PaymentEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends PaymentEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends PaymentEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends PaymentEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends PaymentEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Page<PaymentEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        List<PaymentEntity> filteredPayments = paymentStorage.values().stream()
                .filter(p -> p.getCreatedAt() != null &&
                        !p.getCreatedAt().isBefore(start) &&
                        !p.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());

        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) Math.min(startIndex + pageable.getPageSize(), filteredPayments.size());
        List<PaymentEntity> pageContent = filteredPayments.subList(startIndex, endIndex);

        return new PageImpl<>(pageContent, pageable, filteredPayments.size());
    }

    @Override
    public Page<PaymentEntity> findALlByIsCancelledTrue(Pageable pageable) {
        List<PaymentEntity> filteredPayments = paymentStorage.values().stream().filter(PaymentEntity::getIsCancelled)
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredPayments.size());
        List<PaymentEntity> pagedList = filteredPayments.subList(start, end);
        return new PageImpl<>(pagedList, pageable, filteredPayments.size());
    }

    @Override
    public Page<PaymentEntity> findByIsCancelledTrueAndCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        List<PaymentEntity> filteredPayments = paymentStorage.values().stream()
                .filter(p -> Boolean.TRUE.equals(p.getIsCancelled()))
                .filter(p -> !p.getCreatedAt().isBefore(start) && !p.getCreatedAt().isAfter(end))
                .collect(Collectors.toList());
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min((startIndex + pageable.getPageSize()), filteredPayments.size());
        List<PaymentEntity> pagedList = filteredPayments.subList(startIndex, endIndex);
        return new PageImpl<>(pagedList, pageable, filteredPayments.size());
    }
}
