//package com.trading.projet.Controller;
//import com.trading.projet.Entities.PriceUpdate;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "http://localhost:4200")
//
//@RestController //
//@RequestMapping("/ws")
//public class WebSocketController {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//
//    @Scheduled(fixedRate = 1000)
//    @MessageMapping("/price-updates")
//    public void sendPriceUpdates() {
//
//        double updatedPriceAAPL = getUpdatedPrice("AAPL");
//        messagingTemplate.convertAndSend("/topic/price-updates", new PriceUpdate("AAPL", updatedPriceAAPL));
//
//
//        double updatedPriceGOOGL = getUpdatedPrice("GOOGL");
//        messagingTemplate.convertAndSend("/topic/price-updates", new PriceUpdate("GOOGL", updatedPriceGOOGL));
//    }
//    @GetMapping("/info")
//    public double getUpdatedPrice(@RequestParam String symbol) {
//        if (symbol == null) {
//            throw new IllegalArgumentException("Le symbole ne peut pas être nul");
//        }
//
//        // Logique pour récupérer ou simuler les données de prix en fonction du symbole
//        return Math.random() * 100 + (symbol.equals("GOOGL") ? 50 : 0); // Juste pour différencier les prix
//    }
//
//
//
//}
//
