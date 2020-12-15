import requests
import json


def getNews(token, ticker, maxNews=5):
    payload = {'apiKey': token, 'q': ticker}
    url = 'https://newsapi.org/v2/everything'
    r = requests.get(url, payload)
    return getMaxNews(r.json()['articles'], maxNews)


def getMaxNews(listOfNews, maxNews=5):
    result = []
    i = 0
    for news in listOfNews:
        if i == maxNews:
            return result
        if (isValid(news)):
            i = i + 1
            result.append(news)

    return result


def isValid(news):
    # for each news, i will check if the key exists
    # then I will check if the value is an empty string

    # check image
    if 'urlToImage' not in news:
        return False
    if not news['urlToImage']:
        return False

    # check title
    if 'title' not in news:
        return False
    if not news['title']:
        return False

    # check date
    if 'publishedAt' not in news:
        return False
    if not news['publishedAt']:
        return False

    # check link to original post
    if 'url' not in news:
        return False
    if not news['url']:
        return False

    return True
