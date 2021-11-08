import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'momentFormat'
})
export class MomentFormatPipe implements PipeTransform {

  transform(value: string, format: string = 'DD/MM/YYYY', now: boolean = false): string {
    if (value !== undefined && value !== null && value.length > 4)
      return moment(value).format(format);
    return (now) ? moment().format(format) : '-';
  }


}
