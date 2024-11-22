import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '6eb6b575-865e-4964-89a4-aa93c33c87a4',
};

export const sampleWithPartialData: IAuthority = {
  name: '2de324ae-2c9e-4d1a-8675-7904ed808ef0',
};

export const sampleWithFullData: IAuthority = {
  name: 'e5bb1aa4-3b29-4dc5-9282-ff76da4c5e86',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
