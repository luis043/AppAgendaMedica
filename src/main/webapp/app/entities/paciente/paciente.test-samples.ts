import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 5885,
  rut: 'outside if drowse',
};

export const sampleWithPartialData: IPaciente = {
  id: 12316,
  nombre: 'thorough abscond',
  rut: 'soliloquy',
  email: 'Carla.AlonsoArchuleta29@gmail.com',
};

export const sampleWithFullData: IPaciente = {
  id: 20675,
  nombre: 'granular brr',
  apellidos: 'majestically',
  rut: 'pastel beyond separately',
  telefono: 'obesity by',
  email: 'MariaLuisa88@yahoo.com',
};

export const sampleWithNewData: NewPaciente = {
  rut: 'pale',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
