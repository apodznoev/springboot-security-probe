import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'randomChange'})
export class RandomChangePipe implements PipeTransform {
  transform(value: string): string {
    let result = '';
    for (let counter = 0; counter < value.length; counter++) {
      if (Math.random() > 0.5) {
        result += value.charAt(counter).toUpperCase();
      } else {
        result += value.charAt(counter).toLowerCase();
      }
    }
    return result;
  }
}
