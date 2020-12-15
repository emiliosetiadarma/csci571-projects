const axios = require('axios');
const formatter = require('./formatters');

// constants
const TIINGO_API_TOKEN = process.env.TIINGO_API_TOKEN;
const NEWS_API_TOKEN = process.env.NEWS_API_TOKEN;

/**
 * Returns array of json objects for possible search results
 * sample URL: https://api.tiingo.com/tiingo/utilities/search?query=<query>&token=<APIKeyTiingo>
 * @param {*} req request object, containing param <query>
 * @param {*} res response object, array of json objects for possible suggestions
 */
exports.getSuggestions = function (req, res) {
	const query = req.params.query;
	const url = 'https://api.tiingo.com/tiingo/utilities/search';
	const params = {
		params: {
			query: query,
			token: TIINGO_API_TOKEN
		}
	};
	axios
		.get(url, params)
		.then(function (response) {
			res.send(response.data);
		})
		.catch(function (error) {
			console.log(error);
			res.send([]);
		});
};

/**
 * Returns json object containing for company description
 * sample URL: https://api.tiingo.com/tiingo/daily/<ticker>?token=<APIKeyTiingo>
 * @param {*} req request object, containing params <ticker>
 * @param {*} res response object, json object for company description
 */
exports.getDescription = function (req, res) {
	const ticker = req.params.ticker;
	const url = `https://api.tiingo.com/tiingo/daily/${ticker}`;
	const params = {
		params: {
			token: TIINGO_API_TOKEN
		}
	};

	axios
		.get(url, params)
		.then(function (response) {
			res.send(response.data);
		})
		.catch(function (error) {
			console.log(error);
			res.send({});
		});
};

/**
 * Returns json object returning latest price of the stock
 * sample URL: https://api.tiingo.com/iex/?tickers=<ticker>&token=<APIKeyTiingo>
 * @param {*} req request object, containing params <ticker>
 * @param {*} res response object, latest price of stock
 */
exports.getLatestPrice = function (req, res) {
	const ticker = req.params.ticker;
	const url = 'https://api.tiingo.com/iex';
	const params = {
		params: {
			tickers: ticker,
			token: TIINGO_API_TOKEN
		}
	};

	axios
		.get(url, params)
		.then(function (response) {
			res.send(response.data);
		})
		.catch(function (error) {
			console.log(error);
			res.send({});
		});
};

/**
 * returns array of json objects for latest news
 * sample URL: https://newsapi.org/v2/everything?apiKey=<APIkey>&q=<Query>
 * @param {*} req request object, containing params <query>
 * @param {*} res response object, array of json objects for latest news
 */
exports.getNews = function (req, res) {
	const query = req.params.query;
	const url = 'https://newsapi.org/v2/everything';
	const params = {
		params: {
			apiKey: NEWS_API_TOKEN,
			q: query
		}
	};
	axios
		.get(url, params)
		.then(function (response) {
			res.send(response.data.articles);
		})
		.catch(function (error) {
			console.log(error);
			res.send([]);
		});
};

/**
 * Returns array of json objects returning latest price of the stock
 * sample URL: https://api.tiingo.com/tiingo/daily/<ticker>/prices?startDate=<startDate>&resampleFreq=<resampleFreq>&token=<APIKeyTiingo>
 * https://api.tiingo.com/tiingo/daily/AAPL/prices?startDate=2019-09-10&resampleFreq=4min&token=12PrIvA32tEmYtEmpToKeN23
 * date: YYYY-MM-DD
 * @param {*} req request object, containing params <ticker>
 * @param {*} res response object, array of json object for latest 6 month price of stock
 */
exports.getHistoricalData = function (req, res) {
	const ticker = req.params.ticker;
	const months = req.params.months;
	const url = `https://api.tiingo.com/tiingo/daily/${ticker}/prices`;
	var pastDate = new Date();
	pastDate.setMonth(pastDate.getMonth() - months);
	pastDate = formatter.normalizeUTCDate(pastDate);
	const test = 'ladsjflakjdslfklajdslfkajds';

	const params = {
		params: {
			startDate: formatter.formatDate(pastDate),
			resampleFreq: 'daily',
			token: TIINGO_API_TOKEN
		}
	};

	axios
		.get(url, params)
		.then(function (response) {
			res.send(response.data);
		})
		.catch(function (error) {
			console.log(error);
			res.send([]);
		});
};

/**
 * returns array of json objects returning price of the stock for today
 * sample URL: https://api.tiingo.com/iex/<ticker>/prices?startDate=<startDate>&resampleFreq=<resampleFreq>&token=<APIKeyTiingo>
 * https://api.tiingo.com/iex/AAPL/prices?startDate=2019-09-10&resampleFreq=4min&token=12PrIvA32tEmYtEmpToKeN23
 * @param {*} req request object, containing params <ticker> and <date> days representing the string for the daily chart data
 * @param {*} res response object, array of json objects, returning price of stock daily
 */
exports.getDailyChartData = function (req, res) {
	const ticker = req.params.ticker;
	const date = req.params.date;
	const url = `https://api.tiingo.com/iex/${ticker}/prices`;
	const params = {
		params: {
			startDate: date,
			resampleFreq: '4min',
			token: TIINGO_API_TOKEN,
			columns: 'open,high,low,close,volume'
		}
	};
	axios
		.get(url, params)
		.then(function (response) {
			res.send(response.data);
		})
		.catch(function (error) {
			console.log(error);
			res.send([]);
		});
};
