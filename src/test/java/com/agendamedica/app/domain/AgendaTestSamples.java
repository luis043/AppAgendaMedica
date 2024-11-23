package com.agendamedica.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgendaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agenda getAgendaSample1() {
        return new Agenda().id(1L).medico("medico1").centro_medico("centro_medico1");
    }

    public static Agenda getAgendaSample2() {
        return new Agenda().id(2L).medico("medico2").centro_medico("centro_medico2");
    }

    public static Agenda getAgendaRandomSampleGenerator() {
        return new Agenda()
            .id(longCount.incrementAndGet())
            .medico(UUID.randomUUID().toString())
            .centro_medico(UUID.randomUUID().toString());
    }
}
