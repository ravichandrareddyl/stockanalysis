package com.ravi.repository;

import java.util.List;

import com.ravi.domain.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    //Optional<Stock> findById(int id);

   // Optional<User> findByUsernameOrEmail(String username, String email);

    //@Query("SELECT v, h.marketPrice FROM Stock v, StockHistory h where v.id = h.stockId")
    @Query("SELECT v FROM Stock v LEFT JOIN StockHistory h ON v.id = h.stockId ")
    List<Stock> findAll();

    // Optional<User> findByUsername(String username);

    // Boolean existsByUsername(String username);

    // Boolean existsByEmail(String email);
}
