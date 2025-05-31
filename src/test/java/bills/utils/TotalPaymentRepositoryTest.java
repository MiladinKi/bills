package bills.utils;

import bills.entities.TotalPaymentEntity;
import bills.repositories.TotalPaymentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
public class TotalPaymentRepositoryTest implements TotalPaymentRepository {

    @Override
    public void deleteInBatch(Iterable<TotalPaymentEntity> entities) {
        TotalPaymentRepository.super.deleteInBatch(entities);
    }

    @Override
    public <S extends TotalPaymentEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends TotalPaymentEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TotalPaymentEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TotalPaymentEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TotalPaymentEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public Page<TotalPaymentEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<TotalPaymentEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteAll(Iterable<? extends TotalPaymentEntity> entities) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void delete(TotalPaymentEntity entity) {
        totalPaymentStorage.remove(entity.getId());
    }

    @Override
    public void deleteById(Integer id) {
        totalPaymentStorage.remove(id);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Optional<TotalPaymentEntity> findById(Integer id) {
        return Optional.ofNullable(totalPaymentStorage.get(id));
    }

    private Map<Integer, TotalPaymentEntity> totalPaymentStorage = new HashMap<>();
    private int idCounter = 0;

    @Override
    public <S extends TotalPaymentEntity> S save(S entity) {
        if(entity.getId() == null){
            entity.setId(idCounter++);
        }
        totalPaymentStorage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<TotalPaymentEntity> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public List<TotalPaymentEntity> findAll() {
        return new ArrayList<>(totalPaymentStorage.values());
    }

    @Override
    public <S extends TotalPaymentEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends TotalPaymentEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends TotalPaymentEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public TotalPaymentEntity getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public TotalPaymentEntity getById(Integer integer) {
        return null;
    }

    @Override
    public TotalPaymentEntity getOne(Integer integer) {
        return null;
    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch(Iterable<TotalPaymentEntity> entities) {

    }

    @Override
    public <S extends TotalPaymentEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends TotalPaymentEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public Page<TotalPaymentEntity> findAllByPeriodBetween(Integer from, Integer to, Pageable pageable) {
       List<TotalPaymentEntity> payments = totalPaymentStorage.values().stream()
                .filter(payment -> payment.getPeriod() >= from && payment.getPeriod() <= to)
                .collect(Collectors.toList());
       int start = (int) pageable.getOffset();
       int end = Math.min((start + pageable.getPageSize()), payments.size());
       List<TotalPaymentEntity> pagedList = payments.subList(start, end);
       return new PageImpl<>(pagedList, pageable, payments.size());
    }
}