import { IMedico, NewMedico } from './medico.model';

export const sampleWithRequiredData: IMedico = {
  id: 26339,
  especialidad: 'innovate defiantly remark',
};

export const sampleWithPartialData: IMedico = {
  id: 14890,
  especialidad: 'ick',
  telefono: 'supposing',
  email: 'Ramona1@gmail.com',
};

export const sampleWithFullData: IMedico = {
  id: 19114,
  nombre: 'yawningly',
  especialidad: 'worth concerning',
  telefono: 'both',
  email: 'Veronica_HuertaFarias@yahoo.com',
};

export const sampleWithNewData: NewMedico = {
  especialidad: 'zebra valley',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
