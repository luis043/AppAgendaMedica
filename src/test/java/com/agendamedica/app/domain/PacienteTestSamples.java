package com.agendamedica.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PacienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Paciente getPacienteSample1() {
        return new Paciente().id(1L).nombre("nombre1").apellidos("apellidos1").rut("rut1").telefono("telefono1").email("email1");
    }

    public static Paciente getPacienteSample2() {
        return new Paciente().id(2L).nombre("nombre2").apellidos("apellidos2").rut("rut2").telefono("telefono2").email("email2");
    }

    public static Paciente getPacienteRandomSampleGenerator() {
        return new Paciente()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .apellidos(UUID.randomUUID().toString())
            .rut(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
