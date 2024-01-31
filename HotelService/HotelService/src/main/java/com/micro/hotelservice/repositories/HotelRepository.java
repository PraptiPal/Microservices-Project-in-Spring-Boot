package com.micro.hotelservice.repositories;

import com.micro.hotelservice.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
