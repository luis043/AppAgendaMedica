import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MedicoService } from '../service/medico.service';
import { IMedico } from '../medico.model';
import { MedicoFormService } from './medico-form.service';

import { MedicoUpdateComponent } from './medico-update.component';

describe('Medico Management Update Component', () => {
  let comp: MedicoUpdateComponent;
  let fixture: ComponentFixture<MedicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let medicoFormService: MedicoFormService;
  let medicoService: MedicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MedicoUpdateComponent],
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
      .overrideTemplate(MedicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MedicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    medicoFormService = TestBed.inject(MedicoFormService);
    medicoService = TestBed.inject(MedicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const medico: IMedico = { id: 456 };

      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      expect(comp.medico).toEqual(medico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedico>>();
      const medico = { id: 123 };
      jest.spyOn(medicoFormService, 'getMedico').mockReturnValue(medico);
      jest.spyOn(medicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medico }));
      saveSubject.complete();

      // THEN
      expect(medicoFormService.getMedico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(medicoService.update).toHaveBeenCalledWith(expect.objectContaining(medico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedico>>();
      const medico = { id: 123 };
      jest.spyOn(medicoFormService, 'getMedico').mockReturnValue({ id: null });
      jest.spyOn(medicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: medico }));
      saveSubject.complete();

      // THEN
      expect(medicoFormService.getMedico).toHaveBeenCalled();
      expect(medicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMedico>>();
      const medico = { id: 123 };
      jest.spyOn(medicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ medico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(medicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
