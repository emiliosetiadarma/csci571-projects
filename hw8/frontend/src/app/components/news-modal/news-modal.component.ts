import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { News } from './../../services/news.service';
@Component({
  selector: 'app-news-modal',
  templateUrl: './news-modal.component.html',
  styleUrls: ['./news-modal.component.css'],
})
export class NewsModalComponent implements OnInit {
  @Input() news: News;
  date: string;
  twitterUrl: string;
  fbUrl: string;
  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
    var month = new Array();
    month[0] = 'January';
    month[1] = 'February';
    month[2] = 'March';
    month[3] = 'April';
    month[4] = 'May';
    month[5] = 'June';
    month[6] = 'July';
    month[7] = 'August';
    month[8] = 'September';
    month[9] = 'October';
    month[10] = 'November';
    month[11] = 'December';
    const date = new Date(this.news.publishedAt);
    this.date =
      month[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
    this.twitterUrl = `https://twitter.com/intent/tweet?text=${this.news.title}%20%20${this.news.url}`;
    this.fbUrl = `https://www.facebook.com/sharer/sharer.php?u=${this.news.url}%2F&amp;src=sdkpreparse`;
  }
}
