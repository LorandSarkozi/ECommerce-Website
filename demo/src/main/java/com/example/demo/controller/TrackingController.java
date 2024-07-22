package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TrackingController {

    private final CartService cartService;

    @GetMapping("/order/{trackingId}")
    public ResponseEntity<OrderDto> searchOrderByTrackingId(@PathVariable UUID trackingId) {
        OrderDto orderDto = cartService.searchOrderByTrackingId(trackingId);
        if(orderDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/order/{trackingId}/export")
    public ResponseEntity<String> exportOrderDetails(@PathVariable UUID trackingId, @RequestParam String fileType) {
        String exportedData = cartService.exportOrderDetails(trackingId, fileType);
        System.out.println(exportedData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + trackingId + ".xml")
                .contentType(MediaType.APPLICATION_XML)
                .body(exportedData);
    }
}
