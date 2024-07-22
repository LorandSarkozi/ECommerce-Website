// message.service.ts
import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private stompClient: Stomp.Client;
  public msg: string[] = [];

  constructor() {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection(): void {
    const serverUrl = 'http://localhost:8080/socket';
    this.stompClient = Stomp.over(new WebSocket(serverUrl));
    this.stompClient.connect({}, (frame: Stomp.Frame) => {
      this.stompClient.subscribe('/app/socket', (message: Stomp.Message) => {
        if (message.body) {
          this.msg.push(message.body);
        }
      });
    });
  }
  
  sendMessage(message: string): void {
  

    const serverUrl = 'http://localhost:8080/socket';
    this.stompClient = Stomp.over(new WebSocket(serverUrl));
    this.stompClient.connect({}, (frame: Stomp.Frame) => {
      this.stompClient.send('/app/socket', {}, message);
   
    });
  }
}
