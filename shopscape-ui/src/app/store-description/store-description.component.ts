import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-store-description',
  imports: [],
  templateUrl: './store-description.component.html',
  styleUrl: './store-description.component.css'
})
export class StoreDescriptionComponent {
  @Input() imageSrc: string = '';
  @Input() name: string = '';
  @Input() description: string = '';
  @Input() address: string = '';
}
