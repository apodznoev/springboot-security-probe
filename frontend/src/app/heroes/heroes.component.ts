import { Component, Injectable, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './heroes.component.html',
  styleUrls: ['./heroes.component.css']
})

@Injectable()
export class HeroesComponent implements OnInit {
  private _heroes: Hero[] = [];

  constructor(private _heroService: HeroService) {
  }

  ngOnInit() {
    this.retrieveHeroes();
  }

  get heroes(): Hero[] {
    return this._heroes;
  }

  add(name: string) {
    name = name.trim();
    if (!name)
      return;

    this._heroService.create(name)
      .subscribe(newHero => this.heroes.push(newHero));
  }

  retrieveHeroes(): void {
    this._heroService.getHeroes().subscribe(heroes => {
      for (const newHero of heroes)
        this._heroes.push(newHero);
    });
  }

  // heroKilled(heroId: number) {
  //   console.log('Received kill event for hero:' + heroId);
  //   this._selectedHero = null;
  //   const index = this._heroes.findIndex(hero => hero.id == heroId);
  //   this._heroes.splice(index, 1);
  // }
}
