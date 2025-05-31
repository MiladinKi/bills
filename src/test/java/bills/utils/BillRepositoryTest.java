package bills.utils;

import bills.entities.BillEntity;
import bills.repositories.BillRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BillRepositoryTest  implements BillRepository {


    @Override
    public Page<BillEntity> findByName(String name, Pageable pageable) {
        // filtriraj listu
        List<BillEntity> filtered = billStorage.values().stream()
                .filter(bill -> name.equals(bill.getName()))
                .collect(Collectors.toList());

        // paginacija - izlistaj deo liste na osnovu pageable-a
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<BillEntity> list;

        if (filtered.size() < startItem) {
            list = List.of(); // prazna lista ako je start veći od ukupnih elemenata
        } else {
            int toIndex = Math.min(startItem + pageSize, filtered.size());
            list = filtered.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, pageable, filtered.size());
    }



        @Override
    public void flush() {

    }

    @Override
    public <S extends BillEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends BillEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<BillEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public BillEntity getOne(Integer integer) {
        return null;
    }

    @Override
    public BillEntity getById(Integer integer) {
        return null;
    }

    @Override
    public BillEntity getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends BillEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BillEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends BillEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends BillEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends BillEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends BillEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends BillEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    private int idCounter = 0;
    private Map<Integer, BillEntity> billStorage = new HashMap<>();

    @Override
    public <S extends BillEntity> S save(S entity) {
        entity.setId(++idCounter);
        billStorage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends BillEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<BillEntity> findById(Integer Id) {
        return Optional.ofNullable(billStorage.get(Id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<BillEntity> findAll() {
        return List.of();
    }

    @Override
    public List<BillEntity> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteById(BillEntity entity) {
        billStorage.remove(entity.getId());
    }

    @Override
    public void delete(BillEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends BillEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<BillEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<BillEntity> findAll(Pageable pageable) {
        return null;
    }

}
