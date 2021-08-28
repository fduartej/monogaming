package com.acme.demoapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import com.acme.demoapi.model.*;

import java.util.*;
@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer>{
    
    @Query(value = "SELECT o FROM DetalleFactura o WHERE o.factura=?1")
    List<DetalleFactura> findItemsByFactura(Factura factura);
}
