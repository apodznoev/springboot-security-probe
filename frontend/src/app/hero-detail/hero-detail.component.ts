import { Component, OnInit } from '@angular/core';
import { Hero } from '../hero';
import { HeroService } from '../hero.service';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../message.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.css']
})

export class HeroDetailComponent implements OnInit {
  private _hero: Hero;

  constructor(private _heroService: HeroService,
              private _route: ActivatedRoute,
              private _location: Location,
              private _messageService: MessageService) {
  }

  ngOnInit() {
    this.retrieveHero();
  }

  get hero(): Hero {
    return this._hero;
  }

  goBack() {
    this._location.back();
  }

  save() {
    this._heroService.save(this._hero)
      .subscribe(updated => this._hero = updated);
  }

  reset() {
    this.retrieveHero();
  }

  killHero() {
    this._messageService.add(`Killing hero: ${this._hero.id}`);
    this._heroService.deleteHero(this._hero.id)
      .subscribe({
        complete: () => {
          this._messageService.add(`Hero: ${this._hero.name} was killed`);
          this._hero = null;
          this._location.back();
        },
        error: (msg) => {
          this._messageService.add(`Hero: ${this._hero.name} cannot be killed due to ${msg}`);
        }
      });
  }

  private retrieveHero() {
    const currentHeroId = +this._route.snapshot.paramMap.get('id');
    if (isNaN(currentHeroId)) {
      this._messageService.add(`Provided hero id is not a number: ${this._route.snapshot.paramMap.get('id')}`);
      return;
    }

    this._heroService.getHero(currentHeroId).subscribe({
      next: (hero) => {
        this._hero = hero;
      },
      error: (msg) => {
        this._hero = null;
        this._messageService.add(`Error getting hero: ${msg}`);
      }
    });
  }
}
