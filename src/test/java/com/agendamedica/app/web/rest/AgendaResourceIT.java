package com.agendamedica.app.web.rest;

import static com.agendamedica.app.domain.AgendaAsserts.*;
import static com.agendamedica.app.web.rest.TestUtil.createUpdateProxyForBean;
import static com.agendamedica.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.agendamedica.app.IntegrationTest;
import com.agendamedica.app.domain.Agenda;
import com.agendamedica.app.repository.AgendaRepository;
import com.agendamedica.app.service.AgendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AgendaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AgendaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_MEDICO = "BBBBBBBBBB";

    private static final String DEFAULT_CENTRO_MEDICO = "AAAAAAAAAA";
    private static final String UPDATED_CENTRO_MEDICO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agenda";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendaRepository agendaRepository;

    @Mock
    private AgendaRepository agendaRepositoryMock;

    @Mock
    private AgendaService agendaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendaMockMvc;

    private Agenda agenda;

    private Agenda insertedAgenda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agenda createEntity() {
        return new Agenda().fecha(DEFAULT_FECHA).hora(DEFAULT_HORA).medico(DEFAULT_MEDICO).centro_medico(DEFAULT_CENTRO_MEDICO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agenda createUpdatedEntity() {
        return new Agenda().fecha(UPDATED_FECHA).hora(UPDATED_HORA).medico(UPDATED_MEDICO).centro_medico(UPDATED_CENTRO_MEDICO);
    }

    @BeforeEach
    public void initTest() {
        agenda = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgenda != null) {
            agendaRepository.delete(insertedAgenda);
            insertedAgenda = null;
        }
    }

    @Test
    @Transactional
    void createAgenda() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Agenda
        var returnedAgenda = om.readValue(
            restAgendaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenda)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Agenda.class
        );

        // Validate the Agenda in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgendaUpdatableFieldsEquals(returnedAgenda, getPersistedAgenda(returnedAgenda));

        insertedAgenda = returnedAgenda;
    }

    @Test
    @Transactional
    void createAgendaWithExistingId() throws Exception {
        // Create the Agenda with an existing ID
        agenda.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenda)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(sameInstant(DEFAULT_HORA))))
            .andExpect(jsonPath("$.[*].medico").value(hasItem(DEFAULT_MEDICO)))
            .andExpect(jsonPath("$.[*].centro_medico").value(hasItem(DEFAULT_CENTRO_MEDICO)));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    void getAllAgendaWithEagerRelationshipsIsEnabled() throws Exception {
        when(agendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    void getAllAgendaWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(agendaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        // Get the agenda
        restAgendaMockMvc
            .perform(get(ENTITY_API_URL_ID, agenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agenda.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.hora").value(sameInstant(DEFAULT_HORA)))
            .andExpect(jsonPath("$.medico").value(DEFAULT_MEDICO))
            .andExpect(jsonPath("$.centro_medico").value(DEFAULT_CENTRO_MEDICO));
    }

    @Test
    @Transactional
    void getNonExistingAgenda() throws Exception {
        // Get the agenda
        restAgendaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenda
        Agenda updatedAgenda = agendaRepository.findById(agenda.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgenda are not directly saved in db
        em.detach(updatedAgenda);
        updatedAgenda.fecha(UPDATED_FECHA).hora(UPDATED_HORA).medico(UPDATED_MEDICO).centro_medico(UPDATED_CENTRO_MEDICO);

        restAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgenda.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgenda))
            )
            .andExpect(status().isOk());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendaToMatchAllProperties(updatedAgenda);
    }

    @Test
    @Transactional
    void putNonExistingAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(put(ENTITY_API_URL_ID, agenda.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenda)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agenda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agenda)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendaWithPatch() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenda using partial update
        Agenda partialUpdatedAgenda = new Agenda();
        partialUpdatedAgenda.setId(agenda.getId());

        partialUpdatedAgenda.fecha(UPDATED_FECHA).hora(UPDATED_HORA).centro_medico(UPDATED_CENTRO_MEDICO);

        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgenda))
            )
            .andExpect(status().isOk());

        // Validate the Agenda in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAgenda, agenda), getPersistedAgenda(agenda));
    }

    @Test
    @Transactional
    void fullUpdateAgendaWithPatch() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agenda using partial update
        Agenda partialUpdatedAgenda = new Agenda();
        partialUpdatedAgenda.setId(agenda.getId());

        partialUpdatedAgenda.fecha(UPDATED_FECHA).hora(UPDATED_HORA).medico(UPDATED_MEDICO).centro_medico(UPDATED_CENTRO_MEDICO);

        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgenda.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgenda))
            )
            .andExpect(status().isOk());

        // Validate the Agenda in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaUpdatableFieldsEquals(partialUpdatedAgenda, getPersistedAgenda(partialUpdatedAgenda));
    }

    @Test
    @Transactional
    void patchNonExistingAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agenda.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agenda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agenda))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgenda() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agenda.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agenda)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agenda in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgenda() throws Exception {
        // Initialize the database
        insertedAgenda = agendaRepository.saveAndFlush(agenda);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agenda
        restAgendaMockMvc
            .perform(delete(ENTITY_API_URL_ID, agenda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendaRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Agenda getPersistedAgenda(Agenda agenda) {
        return agendaRepository.findById(agenda.getId()).orElseThrow();
    }

    protected void assertPersistedAgendaToMatchAllProperties(Agenda expectedAgenda) {
        assertAgendaAllPropertiesEquals(expectedAgenda, getPersistedAgenda(expectedAgenda));
    }

    protected void assertPersistedAgendaToMatchUpdatableProperties(Agenda expectedAgenda) {
        assertAgendaAllUpdatablePropertiesEquals(expectedAgenda, getPersistedAgenda(expectedAgenda));
    }
}
