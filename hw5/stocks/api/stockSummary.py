import requests


def getStockSummary(token, ticker):
    payload = {'token': token}
    url = 'https://api.tiingo.com/iex/' + ticker
    r = requests.get(url, payload)
    return r.json()
