package com.example.socks.service.impl;

import com.example.socks.dto.SockDto;
import com.example.socks.entity.Sock;
import com.example.socks.exception.NotEnoughSocksException;
import com.example.socks.mapper.SockMapper;
import com.example.socks.repository.SockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SockServiceImplTest {
    @Mock
    private SockMapper sockMapper;

    @Mock
    private SockRepository sockRepository;

    @InjectMocks
    private SockServiceImpl sockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSocksIncome_InvalidInput() {

        SockDto sockDto = new SockDto();
        sockDto.setColor(null);
        sockDto.setCottonPart((byte) 101);
        sockDto.setQuantity(0);

        assertFalse(sockService.registerSocksIncome(sockDto));
    }

    @Test
    void testRegisterSocksIncome_Success() {

        SockDto sockDto = new SockDto();
        sockDto.setColor("red");
        sockDto.setCottonPart((byte) 90);
        sockDto.setQuantity(10);

        Sock sock = new Sock();
        sock.setColor("red");
        sock.setCottonPart((byte) 90);
        sock.setQuantity(20);

        when(sockRepository.findByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart())).thenReturn(Optional.empty());
        when(sockMapper.toSock(sockDto)).thenReturn(sock);

        assertTrue(sockService.registerSocksIncome(sockDto));
        verify(sockRepository).save(sock);
    }

    @Test
    public void testRegisterSocksOutcome_Success() {

        SockDto sockDto = new SockDto();
        sockDto.setColor("red");
        sockDto.setCottonPart((byte) 90);
        sockDto.setQuantity(10);

        Sock existingSock = new Sock();
        existingSock.setColor(sockDto.getColor());
        existingSock.setCottonPart(sockDto.getCottonPart());
        existingSock.setQuantity(15);

        when(sockRepository.findByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart())).thenReturn(Optional.of(existingSock));

        assertTrue(sockService.registerSocksOutcome(sockDto));
        assertEquals(5, existingSock.getQuantity());
    }

    @Test
    public void testRegisterSocksOutcome_NotEnoughSocksException() {

        SockDto sockDto = new SockDto();
        sockDto.setColor("red");
        sockDto.setCottonPart((byte) 90);
        sockDto.setQuantity(15);

        Sock existingSock = new Sock();
        existingSock.setColor(sockDto.getColor());
        existingSock.setCottonPart(sockDto.getCottonPart());
        existingSock.setQuantity(10);
        when(sockRepository.findByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart())).thenReturn(Optional.of(existingSock));

        try {
            sockService.registerSocksOutcome(sockDto);
            fail("Expected NotEnoughSocksException to be thrown, but it was not thrown.");
        } catch (NotEnoughSocksException e) {
            assertEquals("Not enough socks.", e.getMessage());
        }

        verify(sockRepository, times(1)).findByColorAndCottonPart(sockDto.getColor(), sockDto.getCottonPart());
        verify(sockRepository, never()).delete(any(Sock.class));
        verify(sockRepository, never()).save(any(Sock.class));
    }

    @Test
    public void testGetSocksCount_Success() {
        String color = "white";
        String operation = "lessThan";
        Byte cottonPart = 50;
        int expectedCount = 30;

        when(sockRepository.getSocksCount(color, operation, cottonPart)).thenReturn(expectedCount);

        int result = sockService.getSocksCount(color, operation, cottonPart);

        assertEquals(expectedCount, result);
    }
}