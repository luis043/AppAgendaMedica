import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { AgendaService } from '../service/agenda.service';
import { IAgenda } from '../agenda.model';
import { AgendaFormGroup, AgendaFormService } from './agenda-form.service';

@Component({
  standalone: true,
  selector: 'jhi-agenda-update',
  templateUrl: './agenda-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AgendaUpdateComponent implements OnInit {
  isSaving = false;
  agenda: IAgenda | null = null;

  medicosSharedCollection: IMedico[] = [];
  pacientesSharedCollection: IPaciente[] = [];
  centroSaludsSharedCollection: ICentroSalud[] = [];

  protected agendaService = inject(AgendaService);
  protected agendaFormService = inject(AgendaFormService);
  protected medicoService = inject(MedicoService);
  protected pacienteService = inject(PacienteService);
  protected centroSaludService = inject(CentroSaludService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AgendaFormGroup = this.agendaFormService.createAgendaFormGroup();

  compareMedico = (o1: IMedico | null, o2: IMedico | null): boolean => this.medicoService.compareMedico(o1, o2);

  comparePaciente = (o1: IPaciente | null, o2: IPaciente | null): boolean => this.pacienteService.comparePaciente(o1, o2);

  compareCentroSalud = (o1: ICentroSalud | null, o2: ICentroSalud | null): boolean => this.centroSaludService.compareCentroSalud(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agenda }) => {
      this.agenda = agenda;
      if (agenda) {
        this.updateForm(agenda);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agenda = this.agendaFormService.getAgenda(this.editForm);
    if (agenda.id !== null) {
      this.subscribeToSaveResponse(this.agendaService.update(agenda));
    } else {
      this.subscribeToSaveResponse(this.agendaService.create(agenda));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgenda>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(agenda: IAgenda): void {
    this.agenda = agenda;
    this.agendaFormService.resetForm(this.editForm, agenda);

    this.medicosSharedCollection = this.medicoService.addMedicoToCollectionIfMissing<IMedico>(
      this.medicosSharedCollection,
      agenda.idmedico,
    );
    this.pacientesSharedCollection = this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(
      this.pacientesSharedCollection,
      agenda.idpaciente,
    );
    this.centroSaludsSharedCollection = this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(
      this.centroSaludsSharedCollection,
      agenda.idcentrosalud,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.medicoService
      .query()
      .pipe(map((res: HttpResponse<IMedico[]>) => res.body ?? []))
      .pipe(map((medicos: IMedico[]) => this.medicoService.addMedicoToCollectionIfMissing<IMedico>(medicos, this.agenda?.idmedico)))
      .subscribe((medicos: IMedico[]) => (this.medicosSharedCollection = medicos));

    this.pacienteService
      .query()
      .pipe(map((res: HttpResponse<IPaciente[]>) => res.body ?? []))
      .pipe(
        map((pacientes: IPaciente[]) =>
          this.pacienteService.addPacienteToCollectionIfMissing<IPaciente>(pacientes, this.agenda?.idpaciente),
        ),
      )
      .subscribe((pacientes: IPaciente[]) => (this.pacientesSharedCollection = pacientes));

    this.centroSaludService
      .query()
      .pipe(map((res: HttpResponse<ICentroSalud[]>) => res.body ?? []))
      .pipe(
        map((centroSaluds: ICentroSalud[]) =>
          this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(centroSaluds, this.agenda?.idcentrosalud),
        ),
      )
      .subscribe((centroSaluds: ICentroSalud[]) => (this.centroSaludsSharedCollection = centroSaluds));
  }
}
