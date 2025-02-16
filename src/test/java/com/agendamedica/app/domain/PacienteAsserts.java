package com.agendamedica.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class PacienteAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacienteAllPropertiesEquals(Paciente expected, Paciente actual) {
        assertPacienteAutoGeneratedPropertiesEquals(expected, actual);
        assertPacienteAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacienteAllUpdatablePropertiesEquals(Paciente expected, Paciente actual) {
        assertPacienteUpdatableFieldsEquals(expected, actual);
        assertPacienteUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacienteAutoGeneratedPropertiesEquals(Paciente expected, Paciente actual) {
        assertThat(expected)
            .as("Verify Paciente auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacienteUpdatableFieldsEquals(Paciente expected, Paciente actual) {
        assertThat(expected)
            .as("Verify Paciente relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getApellidos()).as("check apellidos").isEqualTo(actual.getApellidos()))
            .satisfies(e -> assertThat(e.getRut()).as("check rut").isEqualTo(actual.getRut()))
            .satisfies(e -> assertThat(e.getTelefono()).as("check telefono").isEqualTo(actual.getTelefono()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPacienteUpdatableRelationshipsEquals(Paciente expected, Paciente actual) {
        // empty method
    }
}
