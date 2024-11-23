package com.agendamedica.app.repository;

import com.agendamedica.app.domain.CentroSalud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentroSalud entity.
 */
@SuppressWarnings("Unused")
@Repository
public interface CentroSaludRepository extends JpaRepository<CentroSalud, Long> {}
