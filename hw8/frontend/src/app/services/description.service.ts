import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Description {
  description: string;
  endDate: string;
  exchangeCode: string;
  startDate: string;
  name: string;
  ticker: string;
}

@Injectable({
  providedIn: 'root',
})
export class DescriptionService {
  constructor(private http: HttpClient) {}
  getDescription(ticker: string): Observable<Description> {
    const url = `https://node-stocksearch-backend-es-v5.wl.r.appspot.com/stock/description/${ticker}`;
    return this.http.get<Description>(url);
  }
  getEmptyDescription(): Description {
    const r: Description = {
      description: '',
      endDate: '',
      exchangeCode: '',
      startDate: '',
      name: '',
      ticker: '',
    };
    return r;
  }
}
