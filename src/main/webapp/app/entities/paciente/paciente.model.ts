export interface IPaciente {
  id: number;
  nombre?: string | null;
  apellidos?: string | null;
  rut?: string | null;
  telefono?: string | null;
  email?: string | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
