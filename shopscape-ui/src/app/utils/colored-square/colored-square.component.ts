import {Component, Input, OnInit} from '@angular/core';
import {NgClass, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-colored-square',
  imports: [
    NgIf,
    NgClass,
    RouterLink
  ],
  templateUrl: './colored-square.component.html'
})
export class ColoredSquareComponent implements OnInit {
  @Input() status: string = '';
  @Input() quantity: number = 0;
  @Input() link: string | null = null;
  standardClasses: string = "btn-sm";
  classes: string = this.standardClasses;


  ngOnInit(): void {
    if (this.status === 'danger') {
      this.classes = this.standardClasses + " btn-danger"
    }

    if (this.status === 'warning') {
      this.classes = this.standardClasses + " btn-warning"
    }
  }
}
