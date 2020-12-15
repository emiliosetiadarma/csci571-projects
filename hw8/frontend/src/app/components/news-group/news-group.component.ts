import { Component, Input, OnInit } from '@angular/core';
import { News } from './../../services/news.service';
@Component({
  selector: 'app-news-group',
  templateUrl: './news-group.component.html',
  styleUrls: ['./news-group.component.css'],
})
export class NewsGroupComponent implements OnInit {
  @Input() newsData: News[];
  constructor() {}

  ngOnInit(): void {}
}
