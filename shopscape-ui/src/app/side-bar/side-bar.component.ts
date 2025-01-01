import {Component, EventEmitter, HostListener, OnInit, Output} from '@angular/core';
import $ from 'jquery';
import {NgClass, NgForOf} from '@angular/common';
import {StoreSummary} from '../data/Entities';
import {ShopService} from '../services/shop.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-side-bar',
  imports: [
    NgClass,
    RouterLink,
    NgForOf
  ],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent implements OnInit{
  opened:boolean = true
  standardSidebar:string[] = ["navbar-nav", "bg-gradient-primary", "sidebar", "sidebar-dark accordion"]
  sidebarClasses:string[] = this.standardSidebar
  @Output() changeStatus = new EventEmitter<boolean>();

  storeSummaries:StoreSummary[] = []

  constructor(private storeService:ShopService) {
  }

  ngOnInit(){
    this.resizeSidebar()
    this.storeSummaries = this.storeService.storeSummaries
    this.storeService.getShops()
  }


  pressToggleButton(){
    this.opened = !this.opened;
    this.changeStatus.emit(this.opened)
    if(!this.opened){
      this.sidebarClasses = this.standardSidebar
      $('.sidebar .collapse').hide();
    }

    else{
      this.sidebarClasses = this.standardSidebar.concat(["toggled"])
    }
  }



  @HostListener('window:resize', ['$event'])
  resizeSidebar(){
    const width:number = window.innerWidth
    if(width < 768){
      if(width < 480 && this.opened){
        this.sidebarClasses = this.standardSidebar.concat(["toggled"])
      }
      $('.sidebar .collapse').hide();
    }
  }


}
