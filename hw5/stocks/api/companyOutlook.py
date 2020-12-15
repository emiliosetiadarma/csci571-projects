import requests


def getCompanyOutlook(token, ticker):
    payload = {'token': token}
    url = 'https://api.tiingo.com/tiingo/daily/' + ticker
    r = requests.get(url, payload)
    return r.json()
