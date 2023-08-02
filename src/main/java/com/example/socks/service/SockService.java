package com.example.socks.service;

import com.example.socks.dto.SockDto;

public interface SockService {

    boolean registerSocksIncome(SockDto sockDto);
    boolean registerSocksOutcome(SockDto sockDto);
    Integer getSocksCount(String color, String operation, Byte cottonPart);
}
