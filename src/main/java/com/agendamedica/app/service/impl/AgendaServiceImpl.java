package com.agendamedica.app.service.impl;

import com.agendamedica.app.domain.Agenda;
import com.agendamedica.app.repository.AgendaRepository;
import com.agendamedica.app.service.AgendaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.agendamedica.app.domain.Agenda}.
 */
@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

    private static final Logger LOG = LoggerFactory.getLogger(AgendaServiceImpl.class);

    private final AgendaRepository agendaRepository;

    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Agenda save(Agenda agenda) {
        LOG.debug("Request to save Agenda : {}", agenda);
        return agendaRepository.save(agenda);
    }

    @Override
    public Agenda update(Agenda agenda) {
        LOG.debug("Request to update Agenda : {}", agenda);
        return agendaRepository.save(agenda);
    }

    @Override
    public Optional<Agenda> partialUpdate(Agenda agenda) {
        LOG.debug("Request to partially update Agenda : {}", agenda);

        return agendaRepository
            .findById(agenda.getId())
            .map(existingAgenda -> {
                if (agenda.getFecha() != null) {
                    existingAgenda.setFecha(agenda.getFecha());
                }
                if (agenda.getHora() != null) {
                    existingAgenda.setHora(agenda.getHora());
                }
                if (agenda.getMedico() != null) {
                    existingAgenda.setMedico(agenda.getMedico());
                }
                if (agenda.getCentroSalud() != null) {
                    existingAgenda.setCentroSalud(agenda.getCentroSalud());
                }

                return existingAgenda;
            })
            .map(agendaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agenda> findAll() {
        LOG.debug("Request to get all Agenda");
        return agendaRepository.findAll();
    }

    public Page<Agenda> findAllWithEagerRelationships(Pageable pageable) {
        return agendaRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agenda> findOne(Long id) {
        LOG.debug("Request to get Agenda : {}", id);
        return agendaRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Agenda : {}", id);
        agendaRepository.deleteById(id);
    }
}
