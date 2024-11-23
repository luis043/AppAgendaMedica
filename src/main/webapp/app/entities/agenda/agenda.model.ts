import dayjs from 'dayjs/esm';
import { IMedico } from 'app/entities/medico/medico.model';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';

export interface IAgenda {
  id: number;
  fecha?: dayjs.Dayjs | null;
  hora?: dayjs.Dayjs | null;
  medico?: string | null;
  centro_medico?: string | null;
  idmedico?: IMedico | null;
  idpaciente?: IPaciente | null;
  idcentrosalud?: ICentroSalud | null;
}

export type NewAgenda = Omit<IAgenda, 'id'> & { id: null };
