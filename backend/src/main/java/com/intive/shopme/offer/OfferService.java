package com.intive.shopme.offer;

import com.intive.shopme.model.db.DbOffer;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
class OfferService {

    private final OfferRepository repository;

    OfferService(OfferRepository repository) {
        this.repository = repository;
    }

    Page<DbOffer> getAll(Pageable pageable, Specification<DbOffer> filter) {
        return repository.findAll(filter, pageable);
    }

    DbOffer get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DataRetrievalFailureException("Offer with id: " + id + " not found"));
    }

    DbOffer createOrUpdate(DbOffer dbOffer) {
        return repository.save(dbOffer);
    }

    void delete(UUID id) {
        repository.deleteById(id);
    }
}
