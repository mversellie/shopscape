import {Component, ViewChild} from '@angular/core';
import {FooterComponent} from '../footer/footer.component';
import {NavbarComponent} from '../navbar/navbar.component';
import {RouterOutlet} from '@angular/router';
import {SideBarComponent} from '../side-bar/side-bar.component';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-sidebar-navbar-page-component',
  imports: [
    FooterComponent,
    NavbarComponent,
    RouterOutlet,
    SideBarComponent,
    NgClass
  ],
  templateUrl: './sidebar-navbar-page-component.component.html'
})
export class SidebarNavbarPageComponentComponent {

  @ViewChild('sidebarComp')// @ts-ignore
  sideBar: SideBarComponent
  isSideBarOpen = false;

  changeStatus(openStatus:boolean){
    this.isSideBarOpen = openStatus
  }

  protected readonly window = window;
}
