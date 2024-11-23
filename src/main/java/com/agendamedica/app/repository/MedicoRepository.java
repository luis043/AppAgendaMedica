package com.agendamedica.app.repository;

import com.agendamedica.app.domain.Medico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Medico entity.
 */
@SuppressWarnings("Unused")
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {}
