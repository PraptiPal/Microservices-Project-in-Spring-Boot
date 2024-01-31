package com.micro.userservice.controllers;

import com.micro.userservice.entities.User;
import com.micro.userservice.services.UserService;
import com.micro.userservice.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }
    int retryCount = 1;
    @GetMapping("/{userId}")
  //  @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
   // @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        logger.info("Retry count: {}", retryCount);
        retryCount++;
        logger.info("Get Single User Handler: UserController");
        return ResponseEntity.ok(userService.getUserById(userId));
    }


    // rating hotel fallback method for circuitbreaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        //logger.info("Fallback is executed because service is down: ",ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created because service is down")
                .userId("1234").build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    //GET - ALL
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return  ResponseEntity.ok(userService.getAllUsers());
    }
}
