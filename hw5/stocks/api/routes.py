from flask import Flask, request, Blueprint
from .companyOutlook import getCompanyOutlook
from .stockSummary import getStockSummary
from .chartInfo import getChartInfo
from .news import getNews

TIINGO_API_TOKEN = 'b92a66830102d16fe7f65ff238796dd56b4db524'
NEWS_API_TOKEN = '2aa9e4b0d49f4b37b540ef9ba62ded8f'
api = Blueprint('api', __name__)


@api.route('/stock')
def getStockInfo():
    # get url params
    ticker = request.args.get('ticker')

    # return object
    info = {}
    info['valid'] = True

    # get company outlook
    info['companyOutlook'] = getCompanyOutlook(TIINGO_API_TOKEN, ticker)
    if 'detail' in info['companyOutlook'].keys() and info['companyOutlook']['detail'] == "Not found.":
        info['valid'] = False
        return info

    # get stock summary
    info['stockSummary'] = getStockSummary(TIINGO_API_TOKEN, ticker)
    if (len(info['stockSummary']) == 0):
        info['valid'] = False
        return info

    # get info for charts
    info['charts'] = getChartInfo(TIINGO_API_TOKEN, ticker, 6)

    # get news
    info['news'] = getNews(NEWS_API_TOKEN, ticker, 5)

    return info
