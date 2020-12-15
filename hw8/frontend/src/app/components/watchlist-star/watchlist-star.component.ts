import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-watchlist-star',
  templateUrl: './watchlist-star.component.html',
  styleUrls: ['./watchlist-star.component.css'],
})
export class WatchlistStarComponent implements OnInit {
  @Input() fill: boolean = true;
  constructor() {}

  ngOnInit(): void {}
}
