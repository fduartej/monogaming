package com.acme.demoapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import com.acme.demoapi.model.*;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Integer>{
    
}
