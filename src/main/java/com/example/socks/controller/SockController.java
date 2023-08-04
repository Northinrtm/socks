package com.example.socks.controller;

import com.example.socks.dto.SockDto;
import com.example.socks.service.impl.SockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.oas.annotations.EnableOpenApi;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/socks")
public class SockController {

    private final SockServiceImpl sockService;

    /**
     * Регистрирует приход носков на склад.
     *
     * @param sockDto Объект {@link SockDto} с информацией о носках, которые поступают на склад.
     * @return ResponseEntity с текстовым сообщением об успешной регистрации или ошибке.
     */
    @PostMapping("/income")
    public ResponseEntity<String> registerSocksIncome(@RequestBody SockDto sockDto) {
        if (sockService.registerSocksIncome(sockDto)) {
            return ResponseEntity.ok("Income successfully registered.");
        }
        return ResponseEntity.badRequest().body("invalid data");
    }

    /**
     * Регистрирует отпуск носков со склада.
     *
     * @param sockDto Объект {@link SockDto} с информацией о носках, которые уходят со склада.
     * @return ResponseEntity с текстовым сообщением об успешной регистрации или ошибке.
     */
    @PostMapping("/outcome")
    public ResponseEntity<String> registerSocksOutcome(@RequestBody SockDto sockDto) {
        if (sockService.registerSocksOutcome(sockDto)) {
            return ResponseEntity.ok("Income successfully registered.");
        }
        return ResponseEntity.badRequest().body("invalid data");
    }

    /**
     * Получает общее количество носков на складе, соответствующих переданным критериям запроса.
     *
     * @param color       Цвет носков, по которому нужно выполнить фильтрацию.
     * @param operation   Оператор сравнения значения количества хлопка в составе носков.
     *                    Возможные значения: "moreThan", "lessThan", "equal".
     * @param cottonPart  Значение процента хлопка в составе носков для сравнения.
     * @return ResponseEntity с общим количеством носков на складе, удовлетворяющих критериям запроса.
     */
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
