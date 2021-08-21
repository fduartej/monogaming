package com.acme.demoapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;
import com.acme.demoapi.model.*;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer>{
    
}
