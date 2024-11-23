export interface ICentroSalud {
  id: number;
  nombre?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  email?: string | null;
}

export type NewCentroSalud = Omit<ICentroSalud, 'id'> & { id: null };
