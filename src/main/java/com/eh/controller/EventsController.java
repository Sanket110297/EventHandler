package com.eh.controller;

import com.eh.domain.Event;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventsController {

    @PostMapping(value = "v1/event")
    public ResponseEntity<Event> postEvent(@RequestBody Event event){
        //call producer
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
