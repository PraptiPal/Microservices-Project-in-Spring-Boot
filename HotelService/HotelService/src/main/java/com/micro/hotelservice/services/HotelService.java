package com.micro.hotelservice.services;

import com.micro.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {
    Hotel create(Hotel hotel);

    //get all
    List<Hotel> getAll();

    //get single
    Hotel get(String id);
}
