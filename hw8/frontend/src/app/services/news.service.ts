import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Source {
  id: string;
  name: string;
}
export interface News {
  source: Source;
  author: string;
  title: string;
  description: string;
  url: string;
  urlToImage: string;
  publishedAt: string;
  content: string;
}

@Injectable({
  providedIn: 'root',
})
export class NewsService {
  constructor(private http: HttpClient) {}
  getNews(ticker: string): Observable<News[]> {
    const url = `https://node-stocksearch-backend-es-v5.wl.r.appspot.com/stock/news/${ticker}`;
    return this.http.get<News[]>(url);
  }
}
