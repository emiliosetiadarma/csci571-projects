// packages
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
require('dotenv').config();

// individual modules
const stock = require('./stock');

// constants
const PORT = process.env.PORT || 8080;

const app = express();
app.use(cors());
app.use(bodyParser.json());

app.get('/', function (req, res) {
	res.send('Hello. This is the root of API!');
});

// search functionality
app.get('/search/:query', stock.getSuggestions);

// stock details functionality
app.get('/stock/description/:ticker', stock.getDescription);
app.get('/stock/latestprice/:ticker', stock.getLatestPrice);
app.get('/stock/news/:query', stock.getNews);
app.get('/stock/historicaldata/:ticker/:months', stock.getHistoricalData);
app.get('/stock/chartdata/:ticker/:date', stock.getDailyChartData);

app.get('/android/search/:query', stock.getSuggestions);
app.get('/android/stock/description/:ticker', stock.getDescription);
app.get('/android/stock/latestprice/:ticker', stock.getLatestPrice);
app.get('/android/stock/news/:query', stock.getNews);
app.get(
	'/android/stock/historicaldata/:ticker/:months',
	stock.getHistoricalData
);
app.get('/android/stock/chartdata/:ticker/:date', stock.getDailyChartData);

app.listen(PORT, function () {
	console.log(`Server started on port ${PORT}.`);
});
