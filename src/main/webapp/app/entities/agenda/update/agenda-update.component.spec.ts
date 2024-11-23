import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IMedico } from 'app/entities/medico/medico.model';
import { MedicoService } from 'app/entities/medico/service/medico.service';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { PacienteService } from 'app/entities/paciente/service/paciente.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { IAgenda } from '../agenda.model';
import { AgendaService } from '../service/agenda.service';
import { AgendaFormService } from './agenda-form.service';

import { AgendaUpdateComponent } from './agenda-update.component';

describe('Agenda Management Update Component', () => {
  let comp: AgendaUpdateComponent;
  let fixture: ComponentFixture<AgendaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let agendaFormService: AgendaFormService;
  let agendaService: AgendaService;
  let medicoService: MedicoService;
  let pacienteService: PacienteService;
  let centroSaludService: CentroSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AgendaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AgendaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AgendaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    agendaFormService = TestBed.inject(AgendaFormService);
    agendaService = TestBed.inject(AgendaService);
    medicoService = TestBed.inject(MedicoService);
    pacienteService = TestBed.inject(PacienteService);
    centroSaludService = TestBed.inject(CentroSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Medico query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const idmedico: IMedico = { id: 13845 };
      agenda.idmedico = idmedico;

      const medicoCollection: IMedico[] = [{ id: 31476 }];
      jest.spyOn(medicoService, 'query').mockReturnValue(of(new HttpResponse({ body: medicoCollection })));
      const additionalMedicos = [idmedico];
      const expectedCollection: IMedico[] = [...additionalMedicos, ...medicoCollection];
      jest.spyOn(medicoService, 'addMedicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(medicoService.query).toHaveBeenCalled();
      expect(medicoService.addMedicoToCollectionIfMissing).toHaveBeenCalledWith(
        medicoCollection,
        ...additionalMedicos.map(expect.objectContaining),
      );
      expect(comp.medicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Paciente query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const idpaciente: IPaciente = { id: 32130 };
      agenda.idpaciente = idpaciente;

      const pacienteCollection: IPaciente[] = [{ id: 13644 }];
      jest.spyOn(pacienteService, 'query').mockReturnValue(of(new HttpResponse({ body: pacienteCollection })));
      const additionalPacientes = [idpaciente];
      const expectedCollection: IPaciente[] = [...additionalPacientes, ...pacienteCollection];
      jest.spyOn(pacienteService, 'addPacienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(pacienteService.query).toHaveBeenCalled();
      expect(pacienteService.addPacienteToCollectionIfMissing).toHaveBeenCalledWith(
        pacienteCollection,
        ...additionalPacientes.map(expect.objectContaining),
      );
      expect(comp.pacientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CentroSalud query and add missing value', () => {
      const agenda: IAgenda = { id: 456 };
      const idcentrosalud: ICentroSalud = { id: 18491 };
      agenda.idcentrosalud = idcentrosalud;

      const centroSaludCollection: ICentroSalud[] = [{ id: 4030 }];
      jest.spyOn(centroSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: centroSaludCollection })));
      const additionalCentroSaluds = [idcentrosalud];
      const expectedCollection: ICentroSalud[] = [...additionalCentroSaluds, ...centroSaludCollection];
      jest.spyOn(centroSaludService, 'addCentroSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(centroSaludService.query).toHaveBeenCalled();
      expect(centroSaludService.addCentroSaludToCollectionIfMissing).toHaveBeenCalledWith(
        centroSaludCollection,
        ...additionalCentroSaluds.map(expect.objectContaining),
      );
      expect(comp.centroSaludsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const agenda: IAgenda = { id: 456 };
      const idmedico: IMedico = { id: 17802 };
      agenda.idmedico = idmedico;
      const idpaciente: IPaciente = { id: 28847 };
      agenda.idpaciente = idpaciente;
      const idcentrosalud: ICentroSalud = { id: 27551 };
      agenda.idcentrosalud = idcentrosalud;

      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      expect(comp.medicosSharedCollection).toContain(idmedico);
      expect(comp.pacientesSharedCollection).toContain(idpaciente);
      expect(comp.centroSaludsSharedCollection).toContain(idcentrosalud);
      expect(comp.agenda).toEqual(agenda);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenda>>();
      const agenda = { id: 123 };
      jest.spyOn(agendaFormService, 'getAgenda').mockReturnValue(agenda);
      jest.spyOn(agendaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenda }));
      saveSubject.complete();

      // THEN
      expect(agendaFormService.getAgenda).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(agendaService.update).toHaveBeenCalledWith(expect.objectContaining(agenda));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenda>>();
      const agenda = { id: 123 };
      jest.spyOn(agendaFormService, 'getAgenda').mockReturnValue({ id: null });
      jest.spyOn(agendaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenda: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: agenda }));
      saveSubject.complete();

      // THEN
      expect(agendaFormService.getAgenda).toHaveBeenCalled();
      expect(agendaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAgenda>>();
      const agenda = { id: 123 };
      jest.spyOn(agendaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ agenda });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(agendaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMedico', () => {
      it('Should forward to medicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(medicoService, 'compareMedico');
        comp.compareMedico(entity, entity2);
        expect(medicoService.compareMedico).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePaciente', () => {
      it('Should forward to pacienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pacienteService, 'comparePaciente');
        comp.comparePaciente(entity, entity2);
        expect(pacienteService.comparePaciente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCentroSalud', () => {
      it('Should forward to centroSaludService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centroSaludService, 'compareCentroSalud');
        comp.compareCentroSalud(entity, entity2);
        expect(centroSaludService.compareCentroSalud).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
