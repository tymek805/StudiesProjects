import { format, parse, isValid, compareAsc } from 'date-fns';
import def from './default';

const date = Object.assign({}, def);

date.isRight = true;

/**
 * Compare the two dates and return 1 if the first date is after the second, -1 if the first date is before the second or 0 if dates are equal.
 * @param {*} x Date 1
 * @param {*} y Date 2
 * @param {Object} column Additional parameters (e.g. dateInputFormat, dateOutputFormat)
 * @returns 
 */
date.compare = function (x, y, column) {
  function cook(d) {
    if (column && column.dateInputFormat) {
      return parse(`${d}`, `${column.dateInputFormat}`, new Date());
    } else if (typeof d === 'string') {
      try {
        return Date.parse(d);
      } catch(err) {
        return d;
      }
    }
    return d;
  }
  x = cook(x);
  y = cook(y);
  if (!isValid(x)) {
    return -1;
  }
  if (!isValid(y)) {
    return 1;
  }
  return compareAsc(x, y);
};

date.format = function (v, column) {
  if (v === undefined || v === null) return '';
  // convert to date
  const date = parse(v, column.dateInputFormat, new Date());
  if (isValid(date)) {
    return format(date, column.dateOutputFormat);
  }
  console.error(`Not a valid date: "${v}"`);
  return null;
};

export default date;
