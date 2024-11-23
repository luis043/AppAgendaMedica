import dayjs from 'dayjs/esm';

import { IAgenda, NewAgenda } from './agenda.model';

export const sampleWithRequiredData: IAgenda = {
  id: 3158,
};

export const sampleWithPartialData: IAgenda = {
  id: 29187,
  fecha: dayjs('2024-11-21'),
  hora: dayjs('2024-11-21T08:21'),
  centro_medico: 'wherever fluffy',
};

export const sampleWithFullData: IAgenda = {
  id: 3193,
  fecha: dayjs('2024-11-21'),
  hora: dayjs('2024-11-21T19:37'),
  medico: 'official kindheartedly mount',
  centro_medico: 'with pertinent',
};

export const sampleWithNewData: NewAgenda = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
