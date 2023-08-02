package com.example.socks.repository;

import com.example.socks.entity.Sock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {
}
