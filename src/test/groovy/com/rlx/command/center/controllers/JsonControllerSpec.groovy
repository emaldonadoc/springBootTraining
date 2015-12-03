package com.rlx.command.center.controllers

import groovy.json.JsonSlurper
import com.rlx.command.center.model.Person
import com.rlx.command.center.CommandCenterApplication;

import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
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

        when:
          ResponseEntity entity = new RestTemplate().getForEntity("http://localhost:9999/people", String.class)

        then:
          entity.statusCode == HttpStatus.OK
          Map map = jsonSlurper.parseText(entity.body)
          println "MAP FROM RESPONSE >>>>>>>>>> ${map.people}"
          assert map.people.class == ArrayList
    }


}
