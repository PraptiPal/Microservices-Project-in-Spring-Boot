package com.micro.userservice.services.impl;

import com.micro.userservice.entities.Hotel;
import com.micro.userservice.entities.Rating;
import com.micro.userservice.entities.User;
import com.micro.userservice.exceptions.ResourceNotFoundException;
import com.micro.userservice.external.services.HotelService;
import com.micro.userservice.repositories.UserRepository;
import com.micro.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired private RestTemplate restTemplate;

    @Autowired private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User createUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

//    @Override
//    public User updateUser(User user, Integer userID) {
//        return null;
//    }

    @Override
    public User getUserById(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with given id"+userId+ "not found"));
        Rating[] ratingsOfUser = restTemplate.
                getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);

        logger.info("{}",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
           // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            // Hotel hotel = forEntity.getBody();

            Hotel hotel = hotelService.getHotel(rating.getHotelId());
           // logger.info("response status code: {} ",forEntity.getStatusCode());
            rating.setHotel(hotel);
            return rating;
        }).toList();
        user.setRatingList(ratingList);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @Override
//    public void deleteUser(Integer userId) {
//
//    }
}
