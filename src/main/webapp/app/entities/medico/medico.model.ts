export interface IMedico {
  id: number;
  nombre?: string | null;
  especialidad?: string | null;
  telefono?: string | null;
  email?: string | null;
}

export type NewMedico = Omit<IMedico, 'id'> & { id: null };
