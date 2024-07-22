import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-track-order',
  templateUrl: './track-order.component.html',
  styleUrls: ['./track-order.component.scss']
})
export class TrackOrderComponent {

  searchOrderForm!: FormGroup;

  order: any;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
  ){}

  ngOnInit(){
    this.searchOrderForm=this.fb.group({
      trackingId: [null, [Validators.required]]
    })
  }

  submitForm(){
    this.authService.getOrderByTrackingId(this.searchOrderForm.get('trackingId').value).subscribe( res =>{
      this.order = res;
      console.log(res);
    })
  }

  downloadOrderDetails(trackingId: number) {
    // Call the service method to download order details
    this.authService.exportOrderDetails(trackingId).subscribe(data => {
      // Convert the Blob to an ArrayBuffer
      const fileReader = new FileReader();
      fileReader.onload = (event) => {
        const arrayBuffer = event.target.result as ArrayBuffer;
        const xmlString = new TextDecoder().decode(arrayBuffer);
  
        // Log the received XML string
        console.log('Received XML:', xmlString);
  
        // Parse the XML string into an XML document
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlString, 'application/xml');
  
        // Check for parsing errors
        if (xmlDoc.getElementsByTagName('parsererror').length) {
          console.error('Error parsing XML:', xmlDoc.getElementsByTagName('parsererror')[0].textContent);
          return;
        }
  
        // Create a new element with the message
        const messageElement = xmlDoc.createElement('message');
        const messageText = xmlDoc.createTextNode('This is your order details.');
        messageElement.appendChild(messageText);
  
        // Append the message element to the root element (orderDto)
        const orderElement = xmlDoc.getElementsByTagName('orderDto')[0];
        if (!orderElement) {
          console.error('Order element not found in XML.');
          return;
        }
        orderElement.appendChild(messageElement);
  
        // Serialize the modified XML document back into a string
        const serializer = new XMLSerializer();
        const modifiedXmlString = serializer.serializeToString(xmlDoc);
  
        // Create a blob from the modified XML string
        const blob = new Blob([modifiedXmlString], { type: 'application/xml' });
  
        // Create a temporary URL for the blob
        const url = window.URL.createObjectURL(blob);
  
        // Create a link element
        const link = document.createElement('a');
        link.href = url;
        link.download = 'order_details.xml'; // Set the filename for the download
        document.body.appendChild(link);
  
        // Simulate a click on the link to trigger the download
        link.click();
  
        // Cleanup: remove the link and revoke the URL
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
      };
  
      // Read the Blob as an ArrayBuffer
      fileReader.readAsArrayBuffer(data);
    });
  }
  
  
}
