//package com.romco.bracketeer.controller;
//
//import com.romco.bracketeer.service.MainService;
//import com.romco.bracketeer.util.Mappings;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@RestController
//public class RestTournamentController {
//
//    // == fields
//    private final MainService service;
//
//    // == constructors
//    @Autowired
//    public RestTournamentController(MainService service) {
//        this.service = service;
//    }
//
////    @GetMapping("/home")
////    public void getHome(HttpServletResponse response) throws IOException {
////        log.info("in /home");
////        response.sendRedirect(Mappings.HOME);
////    }
//
////    @GetMapping(Mappings.Tournament.NEW)
////    public void tournamentNew() {
////        log.info("In {}", Mappings.Tournament.NEW);
////        service.createNewTournament();
////    }
//
//    @PostMapping(Mappings.Tournament.ADD)
//    public void addPlayer(String player, HttpServletResponse response) throws IOException {
//        log.info("Adding player {}", player);
//        service.addPlayer(player);
////        response.sendRedirect();
//    }
//
//}
