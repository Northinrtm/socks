package com.example.socks.mapper;

import com.example.socks.dto.SockDto;
import com.example.socks.entity.Sock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface SockMapper {

    @Mapping(target = "id",ignore = true)
    Sock toSock(SockDto sockDto);
}
