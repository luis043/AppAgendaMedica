package com.agendamedica.app.repository;

import com.agendamedica.app.domain.Authority;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
@SuppressWarnings("Unused")
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
