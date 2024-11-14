package ca.cmpt213.mazeapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @GetMapping("/api/about")
    public String getAbout() {
        return "Tran Quang Ngoc Cao";
    }

    
}
