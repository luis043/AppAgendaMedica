import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgenda, NewAgenda } from '../agenda.model';

export type PartialUpdateAgenda = Partial<IAgenda> & Pick<IAgenda, 'id'>;

type RestOf<T extends IAgenda | NewAgenda> = Omit<T, 'fecha' | 'hora'> & {
  fecha?: string | null;
  hora?: string | null;
};

export type RestAgenda = RestOf<IAgenda>;

export type NewRestAgenda = RestOf<NewAgenda>;

export type PartialUpdateRestAgenda = RestOf<PartialUpdateAgenda>;

export type EntityResponseType = HttpResponse<IAgenda>;
export type EntityArrayResponseType = HttpResponse<IAgenda[]>;

@Injectable({ providedIn: 'root' })
export class AgendaService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agenda');

  create(agenda: NewAgenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agenda);
    return this.http
      .post<RestAgenda>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(agenda: IAgenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agenda);
    return this.http
      .put<RestAgenda>(`${this.resourceUrl}/${this.getAgendaIdentifier(agenda)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(agenda: PartialUpdateAgenda): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agenda);
    return this.http
      .patch<RestAgenda>(`${this.resourceUrl}/${this.getAgendaIdentifier(agenda)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAgenda>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAgenda[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgendaIdentifier(agenda: Pick<IAgenda, 'id'>): number {
    return agenda.id;
  }

  compareAgenda(o1: Pick<IAgenda, 'id'> | null, o2: Pick<IAgenda, 'id'> | null): boolean {
    return o1 && o2 ? this.getAgendaIdentifier(o1) === this.getAgendaIdentifier(o2) : o1 === o2;
  }

  addAgendaToCollectionIfMissing<Type extends Pick<IAgenda, 'id'>>(
    agendaCollection: Type[],
    ...agendaToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agenda: Type[] = agendaToCheck.filter(isPresent);
    if (agenda.length > 0) {
      const agendaCollectionIdentifiers = agendaCollection.map(agendaItem => this.getAgendaIdentifier(agendaItem));
      const agendaToAdd = agenda.filter(agendaItem => {
        const agendaIdentifier = this.getAgendaIdentifier(agendaItem);
        if (agendaCollectionIdentifiers.includes(agendaIdentifier)) {
          return false;
        }
        agendaCollectionIdentifiers.push(agendaIdentifier);
        return true;
      });
      return [...agendaToAdd, ...agendaCollection];
    }
    return agendaCollection;
  }

  protected convertDateFromClient<T extends IAgenda | NewAgenda | PartialUpdateAgenda>(agenda: T): RestOf<T> {
    return {
      ...agenda,
      fecha: agenda.fecha?.format(DATE_FORMAT) ?? null,
      hora: agenda.hora?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAgenda: RestAgenda): IAgenda {
    return {
      ...restAgenda,
      fecha: restAgenda.fecha ? dayjs(restAgenda.fecha) : undefined,
      hora: restAgenda.hora ? dayjs(restAgenda.hora) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAgenda>): HttpResponse<IAgenda> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAgenda[]>): HttpResponse<IAgenda[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
