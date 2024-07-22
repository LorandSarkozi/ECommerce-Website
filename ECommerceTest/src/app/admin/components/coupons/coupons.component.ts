import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-coupons',
  templateUrl: './coupons.component.html',
  styleUrls: ['./coupons.component.scss']
})
export class CouponsComponent {

  coupons: any;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService,
  ){}

  ngOnInit(){
    this.getCoupons();
  }

  getCoupons(){
    this.adminService.getCoupons().subscribe(res =>{
      this.coupons = res;
    })
  }

}
