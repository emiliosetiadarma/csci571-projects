import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Lookup {
  name: string;
  ticker: string;
  permaTicker?: string;
  openFIGIComposite?: string;
  assetType?: string;
  isActive?: boolean;
  countryCode?: string;
}

@Injectable({
  providedIn: 'root',
})
export class LookupService {
  constructor(private http: HttpClient) {}
  getSuggestions(query: string): Observable<Lookup[]> {
    const url = `https://node-stocksearch-backend-es-v5.wl.r.appspot.com/search/${query}`;
    return this.http.get<Lookup[]>(url);
  }
}
