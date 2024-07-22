// dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  products: any[] = [];
  searchProductForm!: FormGroup;
  title = 'websocket-frontend';
  input: string = '';

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    public messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]]
    });
  }
  
  sendMessage(): void {
    if (this.input.trim() !== '') {
      this.messageService.sendMessage(this.input);
      this.input = '';
    }
  }

  getAllProducts(): void {
    this.products = [];
    this.customerService.getAllProducts().subscribe(res => {
      this.products = res.map(element => ({
        ...element,
        processedImg: 'data:image/jpeg;base64,' + element.byteImg
      }));
      console.log(this.products);
    });
  }

  submitForm(): void {
    this.products = [];
    const title = this.searchProductForm.get('title')!.value;
    this.customerService.getAllProductsByName(title).subscribe(res => {
      this.products = res.map(element => ({
        ...element,
        processedImg: 'data:image/jpeg;base64,' + element.byteImg
      }));
      console.log(this.products);
    });
  }

  addToCart(id: any): void {
    this.customerService.addToCart(id).subscribe(res => {
      this.snackBar.open("Product Added to Cart Successfully!", "Close", { duration: 5000 });
    });
  }
}
