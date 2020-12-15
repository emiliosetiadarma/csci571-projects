import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { of, Subscription } from 'rxjs';
import { tap, debounceTime, switchMap, finalize } from 'rxjs/operators';

import { Lookup, LookupService } from './../../services/lookup.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit, OnDestroy {
  control = new FormControl();
  isLoading: boolean = false;
  options: Lookup[] = [];
  searchForm: FormGroup;
  sub: Subscription;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private lookupService: LookupService
  ) {}
  ngOnInit() {
    this.searchForm = this.fb.group({
      searchInput: null,
    });

    this.sub = this.searchForm
      .get('searchInput')
      .valueChanges.pipe(
        debounceTime(300),
        tap(() => {
          this.isLoading = true;
          this.options = [];
        }),
        switchMap((value) =>
          value !== ''
            ? this.lookupService.getSuggestions(value).pipe(
                finalize(() => {
                  this.isLoading = false;
                })
              )
            : of([]).pipe(
                finalize(() => {
                  this.isLoading = false;
                })
              )
        )
      )
      .subscribe(
        (results) => {
          this.options = results;
        },
        (error) => {
          console.log(error);
          this.options = [];
        },
        () => {
          console.log('options successfully fetched');
        }
      );
  }

  ngOnDestroy() {
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  displayFn(lookup: Lookup): string {
    return lookup && lookup.ticker ? lookup.ticker : '';
  }

  onSubmit(searchForm) {
    // validating the form
    const ticker =
      searchForm &&
      searchForm.searchInput &&
      typeof searchForm.searchInput === 'string'
        ? searchForm.searchInput
        : searchForm.searchInput.ticker
        ? searchForm.searchInput.ticker
        : '';

    // reset the form
    this.searchForm.reset();

    // routing to details page
    if (ticker !== '') {
      this.router.navigate(['/details', ticker.toUpperCase()]);
    }
  }
}
