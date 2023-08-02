package com.example.socks.repository;

import com.example.socks.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {

    Optional<Sock> findByColorAndCottonPart(String color, Byte cottonPart);

    @Query("SELECT SUM(s.quantity) FROM socks s WHERE (:color IS NULL OR s.color = : color) " +
            "AND (:operation IS NULL OR " +
            "(:operation = 'moreThan' AND s.cottonPart > :cottonPart) OR " +
            "(:operation = 'lessThen' AND s.cottonPart > :cottonPart) OR " +
            "(:operation = 'equal' AND s.cottonPart = :cottonPart))")
    Integer getSocksCount(String color, String operation, Byte cottonPart);
}
