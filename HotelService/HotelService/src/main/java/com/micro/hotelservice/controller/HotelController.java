package com.micro.hotelservice.controller;

import com.micro.hotelservice.entities.Hotel;
import com.micro.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
    @RestController
    @RequestMapping("/hotels")
    public class HotelController {

        @Autowired
        private HotelService hotelService;

        //create

        @PreAuthorize("hasAAuthority('Admin')")
        @PostMapping
        public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
            return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
        }


        //get single
        @PreAuthorize("hasAuthority('SCOPE_internal')")
        @GetMapping("/{hotelId}")
        public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
            return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
        }


        //get all
        @PreAuthorize("hasAuthority('SCOPE_internal') || hasAAuthority('Admin')")
        @GetMapping
        public ResponseEntity<List<Hotel>> getAll(){
            return ResponseEntity.ok(hotelService.getAll());
        }
}
