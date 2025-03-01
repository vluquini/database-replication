package br.rmi.sd.controller;

import br.rmi.sd.model.MessageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.rmi.sd.service.LeaderService;

@RestController
public class LeaderController {
    private final LeaderService leaderService;

    public LeaderController(LeaderService leaderService) {
        this.leaderService = leaderService;
    }

    // Endpoint para salvar uma mensagem simples
    @PostMapping("/message")
    public ResponseEntity<String> saveMessage(@RequestBody MessageEntity messageEntity) {
        leaderService.saveMessage(messageEntity.getMessage());
        return ResponseEntity.ok("Mensagem simples salva e replicada.");
    }

    // Endpoint para executar um comando SQL
    @PostMapping("/sql")
    public ResponseEntity<String> executeSql(@RequestBody MessageEntity messageEntity) {
        leaderService.executeSqlCommand(messageEntity.getMessage());
        return ResponseEntity.ok("Comando SQL executado e replicado.");
    }

    // Endpoint para registrar um membro via RMI
    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestParam String rmiUrl) {
        leaderService.registerMember(rmiUrl);
        return ResponseEntity.ok("Membro registrado.");
    }
}
