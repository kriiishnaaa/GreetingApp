package com.GreetingApp.GreetingApp.Repository;

import com.GreetingApp.GreetingApp.Model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {

}