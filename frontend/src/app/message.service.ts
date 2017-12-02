import { Injectable } from '@angular/core';

@Injectable()
export class MessageService {
  private _messages: string[] = [];

  constructor() { }


  get messages(): string[] {
    return this._messages;
  }

  add(message: string) {
    this._messages.push(message);
  }

  clear() {
    this._messages.splice(0, this._messages.length);
  }

}
