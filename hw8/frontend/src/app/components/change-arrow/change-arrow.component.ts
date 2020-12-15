import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-change-arrow',
  templateUrl: './change-arrow.component.html',
  styleUrls: ['./change-arrow.component.css'],
})
export class ChangeArrowComponent {
  @Input() change: number;
}
