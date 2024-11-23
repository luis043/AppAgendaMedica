package com.agendamedica.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Medico getMedicoSample1() {
        return new Medico().id(1L).nombre("nombre1").especialidad("especialidad1").telefono("telefono1").email("email1");
    }

    public static Medico getMedicoSample2() {
        return new Medico().id(2L).nombre("nombre2").especialidad("especialidad2").telefono("telefono2").email("email2");
    }

    public static Medico getMedicoRandomSampleGenerator() {
        return new Medico()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .especialidad(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
