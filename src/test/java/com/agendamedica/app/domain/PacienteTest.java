package com.agendamedica.app.domain;

import static com.agendamedica.app.domain.PacienteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.agendamedica.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PacienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = getPacienteSample1();
        Paciente paciente2 = new Paciente();
        assertThat(paciente1).isNotEqualTo(paciente2);

        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);

        paciente2 = getPacienteSample2();
        assertThat(paciente1).isNotEqualTo(paciente2);
    }
}
