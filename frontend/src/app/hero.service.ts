import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Hero } from './hero';
import { MessageService } from './message.service';
import { Subject } from 'rxjs/Subject';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class HeroService {
  private _apiUrl= 'api/v1/heroes';

  constructor(private _messageService: MessageService, private _http: HttpClient) {
  }

  log(msg: string) {
    this._messageService.add(msg);
  }

  getHeroes(): Observable<Hero[]> {
    this.log('Fetching heroes');
    return this._http.get<any[]>(this._apiUrl).pipe(
      tap(heroes => this.log(`fetched heroes ${heroes.length}`)),
      map(heroesRaw => heroesRaw.map(raw => new Hero(raw._id, raw._name, raw._isKillable))),
      catchError(this.handleError('getHeroes', []))
    );
  }

  deleteHero(hero: Hero | number): Observable<void> {
    const heroId = typeof hero === 'number' ? hero : hero.id;
    this.log(`Deleting hero: ${heroId}`);
    return this._http.delete<void>(`${this._apiUrl}/${heroId}`, httpOptions)
      .pipe(
        tap(() => this.log(`Hero deleted: ${heroId}`)),
        catchError(this.handleError<void>(`deleteHero id=${heroId}`))
      );
  }

  getHero(heroId: number): Observable<Hero> {
    this.log('Fetching hero by id:' + heroId);

    return this._http.get<any>(`${this._apiUrl}/${heroId}}`)
      .pipe(
        tap(raw => this.log(`Hero got: ${raw._name}`)),
        map(raw => new Hero(raw._id, raw._name, raw._isKillable)),
        catchError(this.handleError<Hero>(`getHero id=${heroId}`))
      );
  }

  save(_hero: Hero): Observable<Hero> {
    return this._http.put<Hero>(this._apiUrl, _hero, httpOptions)
      .pipe(
        tap(_ => this.log(`Hero was persisted: ${_hero.id}`)),
        catchError(this.handleError<any>(`updateHero: ${_hero.id}`))
      );
  }

  create(_name: string): Observable<Hero> {
    return this._http.post<any>(this._apiUrl, new Hero(-1, _name, true), httpOptions)
      .pipe(
        tap((created) => this.log(`Hero created with id: ${created._id}, name: ${created._name}`)),
        map(raw => new Hero(raw._id, raw._name, raw._isKillable)),
        catchError(this.handleError<any>(`createHero: ${_name}`))
      );
  }

  private handleError<T>(operation: string, result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }
}
