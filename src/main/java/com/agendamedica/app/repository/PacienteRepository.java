package com.agendamedica.app.repository;

import com.agendamedica.app.domain.Paciente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Paciente entity.
 */
@SuppressWarnings("Unused")
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {}
