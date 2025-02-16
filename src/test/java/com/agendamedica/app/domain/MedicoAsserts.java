package com.agendamedica.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoAllPropertiesEquals(Medico expected, Medico actual) {
        assertMedicoAutoGeneratedPropertiesEquals(expected, actual);
        assertMedicoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoAllUpdatablePropertiesEquals(Medico expected, Medico actual) {
        assertMedicoUpdatableFieldsEquals(expected, actual);
        assertMedicoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoAutoGeneratedPropertiesEquals(Medico expected, Medico actual) {
        assertThat(expected)
            .as("Verify Medico auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoUpdatableFieldsEquals(Medico expected, Medico actual) {
        assertThat(expected)
            .as("Verify Medico relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getEspecialidad()).as("check especialidad").isEqualTo(actual.getEspecialidad()))
            .satisfies(e -> assertThat(e.getTelefono()).as("check telefono").isEqualTo(actual.getTelefono()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMedicoUpdatableRelationshipsEquals(Medico expected, Medico actual) {
        // empty method
    }
}
