package com.ademlo.reto_kairos_tienda.prices.infra.db;

import com.ademlo.reto_kairos_tienda.prices.infra.db.entities.PriceDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceJPARepository extends JpaRepository<PriceDB, Long> {

    @Query("SELECT p FROM PriceDB p WHERE p.brandId = ?1 AND p.productId = ?2")
    List<PriceDB> findByBrandIdAndProductId(Long brandId, Long productId);
}
