package com.example.socks.repository;

import com.example.socks.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {

    Optional<Sock> findByColorAndCottonPart(String color, Byte cottonPart);

    @Query("SELECT SUM(s.quantity) FROM socks s WHERE s.color = :color AND " +
            "( :operation = 'moreThan' AND s.cottonPart > :cottonPart OR " +
            "  :operation = 'lessThan' AND s.cottonPart < :cottonPart OR " +
            "  :operation = 'equal' AND s.cottonPart = :cottonPart )")
    Integer getSocksCount(@Param("color") String color,@Param("operation") String operation,@Param("cottonPart") Byte cottonPart);
}
