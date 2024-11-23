package com.agendamedica.app.service;

import com.agendamedica.app.domain.Agenda;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.agendamedica.app.domain.Agenda}.
 */
public interface AgendaService {
    /**
     * Save a agenda.
     *
     * @param agenda the entity to save.
     * @return the persisted entity.
     */
    Agenda save(Agenda agenda);

    /**
     * Updates a agenda.
     *
     * @param agenda the entity to update.
     * @return the persisted entity.
     */
    Agenda update(Agenda agenda);

    /**
     * Partially updates a agenda.
     *
     * @param agenda the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Agenda> partialUpdate(Agenda agenda);

    /**
     * Get all the agenda.
     *
     * @return the list of entities.
     */
    List<Agenda> findAll();

    /**
     * Get all the agenda with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Agenda> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" agenda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agenda> findOne(Long id);

    /**
     * Delete the "id" agenda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
