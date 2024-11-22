import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 24316,
  login: 'j@tESYj\\luk\\2ap\\#F8ZEy',
};

export const sampleWithPartialData: IUser = {
  id: 17821,
  login: 'rwTL-4',
};

export const sampleWithFullData: IUser = {
  id: 14904,
  login: '7',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
