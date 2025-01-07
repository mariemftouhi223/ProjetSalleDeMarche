package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Service.ser.AIService;

import java.util.Map;
@RestController
public class AIController {

    @Autowired
    private AIService iaService;

    @GetMapping("/api/recommandations")
    public String getRecommandations() {
        return iaService.getRecommandations();
    }
}
