// turns a num to its two decimal places
function twoDecimals(num) {
  return num.toFixed(2);
  // return num.toString().match(/^-?\d+(?:\.\d{0,2})?/)[0];
}

// format a date object to YYYY-MM-DD
function formatDate(date) {
  var d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2) month = '0' + month;
  if (day.length < 2) day = '0' + day;

  return [year, month, day].join('-');
}

// format date object for MM-DD-YYYY
function formatNewsDate(date) {
  var d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2) month = '0' + month;
  if (day.length < 2) day = '0' + day;

  return [month, day, year].join('/');
}

// returns UTC Date
function normalizeUTCDate(date) {
  const y = date.getFullYear();
  const m = date.getMonth();
  const d = date.getDate();
  const newDate = new Date(Date.UTC(y, m, d, 0, 0, 0, 0));
  return newDate;
}
