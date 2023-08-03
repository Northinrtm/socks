package com.example.socks.service.impl;

import com.example.socks.dto.SockDto;
import com.example.socks.entity.Sock;
import com.example.socks.exception.NotEnoughSocksException;
import com.example.socks.mapper.SockMapper;
import com.example.socks.repository.SockRepository;
import com.example.socks.service.SockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {

    private final SockMapper sockMapper;
    private final SockRepository sockRepository;

    /**
     * Регистрирует приход носков на склад.
     *
     * @param sockDto Объект {@link SockDto} с информацией о носках, которые поступают на склад.
     * @return true, если приход носков успешно зарегистрирован, иначе false.
     */
    @Override
    public boolean registerSocksIncome(SockDto sockDto) {
        if (sockDto.getColor() == null) {
            return false;
        }
        if (sockDto.getCottonPart() < 0 || sockDto.getCottonPart() > 100) {
            return false;
        }
        if (sockDto.getQuantity() <= 0) {
            return false;
        }
        Optional<Sock> optionalSock = sockRepository.findByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart());
        if (optionalSock.isEmpty()) {
            Sock sock = sockMapper.toSock(sockDto);
            sockRepository.save(sock);
            return true;
        }
        Sock sock = optionalSock.get();
        sock.setQuantity(sock.getQuantity() + sockDto.getQuantity());
        sockRepository.save(sock);
        return true;
    }

    /**
     * Регистрирует отпуск носков со склада.
     *
     * @param sockDto Объект {@link SockDto} с информацией о носках, которые уходят со склада.
     * @return true, если отпуск носков успешно зарегистрирован, иначе false.
     * @throws NotEnoughSocksException Если на складе не хватает носков указанного цвета и процентного содержания хлопка.
     */
    @Override
    public boolean registerSocksOutcome(SockDto sockDto) {
        if (sockDto.getColor() == null) {
            return false;
        }
        if (sockDto.getCottonPart() < 0 || sockDto.getCottonPart() > 100) {
            return false;
        }
        if (sockDto.getQuantity() <= 0) {
            return false;
        }
        Optional<Sock> optionalSock = sockRepository.findByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart());
        Sock sock = optionalSock.orElseThrow(() -> new NotEnoughSocksException("Not enough socks."));
        if (sock.getQuantity() == sockDto.getQuantity()) {
            sockRepository.delete(sock);
            return true;
        }
        sock.setQuantity(sock.getQuantity() - sockDto.getQuantity());
        if (sock.getQuantity() < 0) {
            throw new NotEnoughSocksException("Not enough socks.");
        }
        sockRepository.save(sock);
        return true;
    }

    /**
     * Получает общее количество носков на складе, соответствующих переданным критериям запроса.
     *
     * @param color      Цвет носков, по которому нужно выполнить фильтрацию.
     * @param operation  Оператор сравнения значения количества хлопка в составе носков.
     *                   Возможные значения: "moreThan", "lessThan", "equal".
     * @param cottonPart Значение процента хлопка в составе носков для сравнения.
     * @return Общее количество носков на складе, удовлетворяющих критериям запроса.
     */
    @Override
    public Integer getSocksCount(String color, String operation, Byte cottonPart) {
        return sockRepository.getSocksCount(color, operation, cottonPart);
    }
}
