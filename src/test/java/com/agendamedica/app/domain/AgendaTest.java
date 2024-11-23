package com.agendamedica.app.domain;

import static com.agendamedica.app.domain.AgendaTestSamples.*;
import static com.agendamedica.app.domain.CentroSaludTestSamples.*;
import static com.agendamedica.app.domain.MedicoTestSamples.*;
import static com.agendamedica.app.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.agendamedica.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agenda.class);
        Agenda agenda1 = getAgendaSample1();
        Agenda agenda2 = new Agenda();
        assertThat(agenda1).isNotEqualTo(agenda2);

        agenda2.setId(agenda1.getId());
        assertThat(agenda1).isEqualTo(agenda2);

        agenda2 = getAgendaSample2();
        assertThat(agenda1).isNotEqualTo(agenda2);
    }

    /**
     *
     */
    @Test
    void idmedicoTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        Medico medicoBack = getMedicoRandomSampleGenerator();

        agenda.medico(medicoBack);
        assertThat(agenda.getMedico()).isEqualTo(medicoBack);

        agenda.medico(medicoBack);
        assertThat(agenda.getMedico()).isNull();
    }

    @Test
    void testName() {}

    @Test
    void idpacienteTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        agenda.setPaciente(pacienteBack);
        assertThat(agenda.getPaciente()).isEqualTo(pacienteBack);

        agenda.setPaciente(null);
        assertThat(agenda.getPaciente()).isNull();
    }

    @Test
    void idcentrosaludTest() {
        Agenda agenda = getAgendaRandomSampleGenerator();
        CentroSalud centroSaludBack = getCentroSaludRandomSampleGenerator();

        agenda.centroSalud(centroSaludBack);
        assertThat(agenda.getCentroSalud()).isEqualTo(centroSaludBack);

        agenda.idcentrosalud(null);
        assertThat(agenda.getCentroSalud()).isNull();
    }
}
