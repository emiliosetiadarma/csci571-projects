/**
 * Turns a num to its two decimal places
 * @param {Number} num	number to turn to two decimal places
 * @return {Number} 		two decimal representation of the number
 */
function twoDecimals(num) {
	return num.toFixed(2);
}

/**
 * returns a YYYY-MM-DD formatted string for the date
 * @param {Date} date	the date object to turn into YYYY-MM-DD
 * @return {String}		the YYYY-MM-DD formatted string of the date
 */
function formatDate(date) {
	var d = new Date(date),
		month = '' + (d.getMonth() + 1),
		day = '' + d.getDate(),
		year = d.getFullYear();

	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;

	return [year, month, day].join('-');
}

/**
 * returns UTC Date (time 00:00)
 * @param {Date} date The date object to normalize
 * @return {Date} 		The date object that has been normalized
 */
function normalizeUTCDate(date) {
	const y = date.getFullYear();
	const m = date.getMonth();
	const d = date.getDate();
	const newDate = new Date(Date.UTC(y, m, d, 0, 0, 0, 0));
	return newDate;
}

module.exports = { normalizeUTCDate, formatDate, twoDecimals };
