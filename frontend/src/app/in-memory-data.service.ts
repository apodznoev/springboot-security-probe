import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Hero } from './hero';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const heroes = [
      new Hero(11, 'Diego', false),
      new Hero(12, 'Milton', false),
      new Hero(13, 'Gorn', false),
      new Hero(14, 'Raven', true),
      new Hero(15, 'Lexter', false),
      new Hero(16, 'Vatras', false),
      new Hero(17, 'Jack', true),
      new Hero(18, 'Li', false),
      new Hero(19, 'Pirokar', true),
      new Hero(20, 'Harald', true),
    ];
    return {heroes};
  }
}
