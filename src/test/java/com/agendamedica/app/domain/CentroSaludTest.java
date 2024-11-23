package com.agendamedica.app.domain;

import static com.agendamedica.app.domain.CentroSaludTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.agendamedica.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroSaludTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroSalud.class);
        CentroSalud centroSalud1 = getCentroSaludSample1();
        CentroSalud centroSalud2 = new CentroSalud();
        assertThat(centroSalud1).isNotEqualTo(centroSalud2);

        centroSalud2.setId(centroSalud1.getId());
        assertThat(centroSalud1).isEqualTo(centroSalud2);

        centroSalud2 = getCentroSaludSample2();
        assertThat(centroSalud1).isNotEqualTo(centroSalud2);
    }
}
