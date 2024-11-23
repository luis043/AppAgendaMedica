import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 31254,
  direccion: 'beyond jovially newsprint',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 23444,
  direccion: 'among',
  email: 'Carolina62@hotmail.com',
};

export const sampleWithFullData: ICentroSalud = {
  id: 24927,
  nombre: 'meh unruly',
  direccion: 'snowplow sweetly shrilly',
  telefono: 'spectacles beneath',
  email: 'Alejandro.AponteRincon11@hotmail.com',
};

export const sampleWithNewData: NewCentroSalud = {
  direccion: 'boo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
