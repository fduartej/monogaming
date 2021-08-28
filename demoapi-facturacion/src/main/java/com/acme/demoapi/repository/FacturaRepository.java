package com.acme.demoapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import com.acme.demoapi.model.*;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer>{
    @Query(value = "SELECT o FROM Factura o WHERE o.numeroFactura=?1")
    Optional<Factura> findByNumero(String numeroFactura);
}
