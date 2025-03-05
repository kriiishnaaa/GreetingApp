package com.GreetingApp.GreetingApp.Controller;

import com.GreetingApp.GreetingApp.Exception.ResourceNotFoundException;
import com.GreetingApp.GreetingApp.Model.Greeting;
import com.GreetingApp.GreetingApp.Repository.GreetingRepository;
import com.GreetingApp.GreetingApp.Service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    @Autowired
    GreetingRepository greetingRepo;
    @Autowired(required = true)
    GreetingService greetingService;
    @GetMapping
    public List<Greeting> getGreetings(){
        return greetingRepo.findAll();
    }
    @PostMapping
    public Greeting createGreeting(@RequestBody Greeting greet){
        return greetingRepo.save(greet);
    }
    @PutMapping("/id")
    public Greeting updateGreeting(@PathVariable Long ID, @RequestBody Greeting greet){;
        Greeting greeting=greetingRepo.findById(ID).orElseThrow(()-> new ResourceNotFoundException("Greeting not found with ID: "+ID));
        greeting.setMessage(greet.getMessage());
        return greetingRepo.save(greet);
    }
    @DeleteMapping("/{id}")
    public void deleteGreeting(@PathVariable Long ID){
        greetingRepo.deleteById(ID);
    }

}