import requests
from datetime import date
from dateutil.relativedelta import *


def getChartInfo(token, ticker, monthsPrior=6):
    payload = {'token': token}
    payload['startDate'] = date.today() - relativedelta(months=monthsPrior)
    payload['resampleFreq'] = '12hour'
    payload['columns'] = 'open,high,low,close,volume'
    url = 'https://api.tiingo.com/iex/' + ticker + '/prices'
    r = requests.get(url, payload)
    return r.json()
