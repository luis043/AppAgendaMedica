import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAgenda, NewAgenda } from '../agenda.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgenda for edit and NewAgendaFormGroupInput for create.
 */
type AgendaFormGroupInput = IAgenda | PartialWithRequiredKeyOf<NewAgenda>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAgenda | NewAgenda> = Omit<T, 'hora'> & {
  hora?: string | null;
};

type AgendaFormRawValue = FormValueOf<IAgenda>;

type NewAgendaFormRawValue = FormValueOf<NewAgenda>;

type AgendaFormDefaults = Pick<NewAgenda, 'id' | 'hora'>;

type AgendaFormGroupContent = {
  id: FormControl<AgendaFormRawValue['id'] | NewAgenda['id']>;
  fecha: FormControl<AgendaFormRawValue['fecha']>;
  hora: FormControl<AgendaFormRawValue['hora']>;
  medico: FormControl<AgendaFormRawValue['medico']>;
  centro_medico: FormControl<AgendaFormRawValue['centro_medico']>;
  idmedico: FormControl<AgendaFormRawValue['idmedico']>;
  idpaciente: FormControl<AgendaFormRawValue['idpaciente']>;
  idcentrosalud: FormControl<AgendaFormRawValue['idcentrosalud']>;
};

export type AgendaFormGroup = FormGroup<AgendaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgendaFormService {
  createAgendaFormGroup(agenda: AgendaFormGroupInput = { id: null }): AgendaFormGroup {
    const agendaRawValue = this.convertAgendaToAgendaRawValue({
      ...this.getFormDefaults(),
      ...agenda,
    });
    return new FormGroup<AgendaFormGroupContent>({
      id: new FormControl(
        { value: agendaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fecha: new FormControl(agendaRawValue.fecha),
      hora: new FormControl(agendaRawValue.hora),
      medico: new FormControl(agendaRawValue.medico),
      centro_medico: new FormControl(agendaRawValue.centro_medico),
      idmedico: new FormControl(agendaRawValue.idmedico),
      idpaciente: new FormControl(agendaRawValue.idpaciente),
      idcentrosalud: new FormControl(agendaRawValue.idcentrosalud),
    });
  }

  getAgenda(form: AgendaFormGroup): IAgenda | NewAgenda {
    return this.convertAgendaRawValueToAgenda(form.getRawValue() as AgendaFormRawValue | NewAgendaFormRawValue);
  }

  resetForm(form: AgendaFormGroup, agenda: AgendaFormGroupInput): void {
    const agendaRawValue = this.convertAgendaToAgendaRawValue({ ...this.getFormDefaults(), ...agenda });
    form.reset(
      {
        ...agendaRawValue,
        id: { value: agendaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgendaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      hora: currentTime,
    };
  }

  private convertAgendaRawValueToAgenda(rawAgenda: AgendaFormRawValue | NewAgendaFormRawValue): IAgenda | NewAgenda {
    return {
      ...rawAgenda,
      hora: dayjs(rawAgenda.hora, DATE_TIME_FORMAT),
    };
  }

  private convertAgendaToAgendaRawValue(
    agenda: IAgenda | (Partial<NewAgenda> & AgendaFormDefaults),
  ): AgendaFormRawValue | PartialWithRequiredKeyOf<NewAgendaFormRawValue> {
    return {
      ...agenda,
      hora: agenda.hora ? agenda.hora.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
