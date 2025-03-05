package com.GreetingApp.GreetingApp.Service;

import com.GreetingApp.GreetingApp.Model.Greeting;
import com.GreetingApp.GreetingApp.Repository.GreetingRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {
    @Autowired
    GreetingRepository greetingRepository;
    public String getSimpleGreet(){
        return "Hello World";
    }

}