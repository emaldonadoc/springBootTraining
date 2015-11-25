package com.rlx.command.center.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rlx.command.center.model.Person;
import com.rlx.command.center.model.Greeting;
import com.rlx.command.center.model.repositories.PersonRepository;
import java.util.*;

@RestController
public class JsonController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/people")
    public Map getAllPeople(){
      Map<String,List<Person>> people = new HashMap<String,List<Person>>();
      people.put("people", personRepository.getAll());
      return people;
    }
}
