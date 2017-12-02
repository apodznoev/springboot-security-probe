export class Hero {
  private _id: number;
  private _name: string;
  private _isKillable: boolean;

  constructor(id: number, name: string, killable: boolean){
    this._id = id;
    this._name = name;
    this._isKillable = killable;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  set name(name: string) {
    this._name = name;
  }

  get name(): string {
    return this._name;
  }


  get isKillable(): boolean {
    return this._isKillable;
  }

  set isKillable(value: boolean) {
    this._isKillable = value;
  }
}
