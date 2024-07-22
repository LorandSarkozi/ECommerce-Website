import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrls: ['./place-order.component.scss']
})
export class PlaceOrderComponent {


  orderForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private customerService: CustomerService,
    public dialog: MatDialog
  ){}


  ngOnInit(){
    this.orderForm = this.fb.group({
      address: [null, [Validators.required]],
      orderDescription: [null],
    })
  }

  placeOrder(){
    this.customerService.placeOrder(this.orderForm.value).subscribe(res =>{
      if(res.id != null){
        this.snackBar.open('Order Placed Successfully!', 'Close', {duration: 5000});
        this.router.navigateByUrl("/customer/my-orders");
        this.closeForm();
      }else{
        this.snackBar.open('Something went wrong.', 'Close', {duration: 5000});
      }
    })
  }

  closeForm(){
    this.dialog.closeAll();
  }
}
