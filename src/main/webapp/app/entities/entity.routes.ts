import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'appagendaMedicaApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'paciente',
    data: { pageTitle: 'appagendaMedicaApp.paciente.home.title' },
    loadChildren: () => import('./paciente/paciente.routes'),
  },
  {
    path: 'medico',
    data: { pageTitle: 'appagendaMedicaApp.medico.home.title' },
    loadChildren: () => import('./medico/medico.routes'),
  },
  {
    path: 'centro-salud',
    data: { pageTitle: 'appagendaMedicaApp.centroSalud.home.title' },
    loadChildren: () => import('./centro-salud/centro-salud.routes'),
  },
  {
    path: 'agenda',
    data: { pageTitle: 'appagendaMedicaApp.agenda.home.title' },
    loadChildren: () => import('./agenda/agenda.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
