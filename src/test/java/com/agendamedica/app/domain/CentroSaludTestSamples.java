package com.agendamedica.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CentroSaludTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CentroSalud getCentroSaludSample1() {
        return new CentroSalud().id(1L).nombre("nombre1").direccion("direccion1").telefono("telefono1").email("email1");
    }

    public static CentroSalud getCentroSaludSample2() {
        return new CentroSalud().id(2L).nombre("nombre2").direccion("direccion2").telefono("telefono2").email("email2");
    }

    public static CentroSalud getCentroSaludRandomSampleGenerator() {
        return new CentroSalud()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
