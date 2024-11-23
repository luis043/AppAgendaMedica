package com.agendamedica.app.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * A Agenda.
 */
@Entity
@Table(name = "agenda")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private ZonedDateTime hora;

    @Column(name = "medico")
    private String medico;

    @Column(name = "centro_medico")
    private String centro_medico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico idmedico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente idpaciente;

    @ManyToOne(fetch = FetchType.LAZY)
    private CentroSalud idcentrosalud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agenda id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Agenda fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getHora() {
        return this.hora;
    }

    public Agenda hora(ZonedDateTime hora) {
        this.setHora(hora);
        return this;
    }

    public void setHora(ZonedDateTime hora) {
        this.hora = hora;
    }

    public String getMedico() {
        return this.medico;
    }

    public Agenda medico(String medico) {
        this.setMedico(medico);
        return this;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getCentro_medico() {
        return this.centro_medico;
    }

    public Agenda centro_medico(String centro_medico) {
        this.setCentro_medico(centro_medico);
        return this;
    }

    public void setCentro_medico(String centro_medico) {
        this.centro_medico = centro_medico;
    }

    public Medico getIdmedico() {
        return this.idmedico;
    }

    public void setIdmedico(Medico medico) {
        this.idmedico = medico;
    }

    public Agenda idmedico(Medico medico) {
        this.setIdmedico(medico);
        return this;
    }

    public Paciente getIdpaciente() {
        return this.idpaciente;
    }

    public void setIdpaciente(Paciente paciente) {
        this.idpaciente = paciente;
    }

    public Agenda idpaciente(Paciente paciente) {
        this.setIdpaciente(paciente);
        return this;
    }

    public CentroSalud getIdcentrosalud() {
        return this.idcentrosalud;
    }

    public void setIdcentrosalud(CentroSalud centroSalud) {
        this.idcentrosalud = centroSalud;
    }

    public Agenda idcentrosalud(CentroSalud centroSalud) {
        this.setIdcentrosalud(centroSalud);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agenda)) {
            return false;
        }
        return getId() != null && getId().equals(((Agenda) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agenda{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", hora='" + getHora() + "'" +
            ", medico='" + getMedico() + "'" +
            ", centro_medico='" + getCentro_medico() + "'" +
            "}";
    }
}
