package com.atreyee.playground.controller;

import com.atreyee.playground.model.Kid;
import com.atreyee.playground.service.PlayGroundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@ControllerAdvice
@RequestMapping("/playground")
public class PlayGroundController {
    private static final String SUCCESS_MESSAGE = "Kid is added to %s queue";
    private static final String FAILURE_MESSAGE = "Error while adding kid to  %s queue";

    private PlayGroundServiceImpl playGroundService;

    @Autowired
    public PlayGroundController(@Qualifier("playgroundService") PlayGroundServiceImpl playGroundService) {
        this.playGroundService = playGroundService;
    }

    @PostMapping("/addKid/{playsite}")
    public ResponseEntity<String> addKidToPLaySite(@RequestBody Kid kid, @PathVariable("playsite") String playSite) {
        int response = playGroundService.addKid(kid, playSite);

        if (response != -1) {
            return new ResponseEntity<>(String.format(SUCCESS_MESSAGE, playSite), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format(FAILURE_MESSAGE, playSite), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/utilization")
    public ResponseEntity<Map> getUtilizationReport() {
        return new ResponseEntity<>(playGroundService.getUtilizationReport(), HttpStatus.OK);

    }

    @GetMapping("/history")
    public ResponseEntity<Map> getHistoryReport() {
        return new ResponseEntity<>(playGroundService.getHistoryReport(), HttpStatus.OK);

    }

    @GetMapping("/visitorCount")
    public ResponseEntity<Integer> getVisitorCount() {
        return new ResponseEntity<>(playGroundService.getTotalVisitorCount(),HttpStatus.OK);
    }
}
