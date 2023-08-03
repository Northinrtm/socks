package com.example.socks.controller;

import com.example.socks.dto.SockDto;
import com.example.socks.service.impl.SockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/socks")
public class SockController {

    private final SockServiceImpl sockService;

    @PostMapping("/income")
    public ResponseEntity<String> registerSocksIncome(@RequestBody SockDto sockDto) {
        if (sockService.registerSocksIncome(sockDto)) {
            return ResponseEntity.ok("Income successfully registered.");
        }
        return ResponseEntity.badRequest().body("invalid data");
    }

    @PostMapping("/outcome")
    public ResponseEntity<String> registerSocksOutcome(@RequestBody SockDto sockDto) {
        if (sockService.registerSocksOutcome(sockDto)) {
            return ResponseEntity.ok("Income successfully registered.");
        }
        return ResponseEntity.badRequest().body("invalid data");
    }

    @GetMapping
    public ResponseEntity<Integer> getSocksCount(@RequestParam String color,
                                                 @RequestParam String operation,
                                                 @RequestParam Byte cottonPart) {
        if (!(operation.equals("moreThan") || operation.equals("lessThan") || operation.equals("equal"))) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sockService.getSocksCount(color, operation, cottonPart));
    }
}
