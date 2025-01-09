import {Component, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {Equipment} from '../data/Entities';
import {ColoredSquareComponent} from '../utils/colored-square/colored-square.component';

@Component({
  selector: 'app-equipment-table',
  imports: [
    NgForOf,
    ColoredSquareComponent
  ],
  templateUrl: './equipment-table.component.html'
})
export class EquipmentTableComponent {
  @Input() equipmentList: Equipment[] = [];
}
