import { Component, OnInit } from '@angular/core';
import { HeroService } from '../hero.service';
import { Hero } from '../hero';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private _heroes: Hero[] = [];

  constructor(private _heroService: HeroService) { }

  ngOnInit() {
    this.retrieveHeroes();
  }

  retrieveHeroes(): void {
    this._heroService.getHeroes()
      .subscribe(heroes => {
        if(this._heroes.length == 0)
          this._heroes = heroes.slice(1, 5)
      });
  }


  get heroes(): Hero[] {
    return this._heroes;
  }
}
