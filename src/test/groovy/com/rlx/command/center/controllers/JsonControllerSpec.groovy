package com.rlx.command.center.controllers

import groovy.json.JsonSlurper
import com.rlx.command.center.model.Person
import com.rlx.command.center.repositories.PersonRepository
import com.rlx.command.center.CommandCenterApplication

import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class JsonControllerSpec extends Specification{

   @Shared
   @AutoCleanup
   ConfigurableApplicationContext context

   final String SERVER_URI ="http://localhost:9999"

   void setupSpec() {
       Future future = Executors
               .newSingleThreadExecutor().submit(
               new Callable() {
                   @Override
                   public ConfigurableApplicationContext call() throws Exception {
                       return (ConfigurableApplicationContext) SpringApplication
                               .run(CommandCenterApplication.class)
                   }
               })
       context = future.get(60, TimeUnit.SECONDS)
   }

    void "Should return all persons in DB" (){
        setup:
          JsonSlurper jsonSlurper = new JsonSlurper()
          PersonRepository repo = context.getBean(PersonRepository.class);
          Person person = new Person()
          person.setFirstName("steve")
          person.setLastName("jobs")
          repo.save(person)

        when:
          ResponseEntity entity = new RestTemplate().getForEntity(SERVER_URI + "/people", String.class)

        then:
          entity.statusCode == HttpStatus.OK
          Map map = jsonSlurper.parseText(entity.body)
          assert map.people.class == ArrayList
          assert map.people[0].firstName == "steve"
          assert map.people[0].lastName == "jobs"
    }


    void "Should to save Person on DB" (){
      setup:
        JsonSlurper jS = new JsonSlurper()
        String json = "{\"name\":\"Han\", \"lastName\":\"Solo\", \"phone\":\"000000\"}"
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json")
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);

      when:
        ResponseEntity entity = new RestTemplate().exchange(SERVER_URI+"/addPerson", HttpMethod.PUT, requestEntity, String.class)

      then:
        println "ENTITY -----> ${entity.dump()}"
        false

    }


}
