package org.training360.finalexam.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping
    public PlayerDTO createPlayer(@Valid @RequestBody CreatePlayerCommand command) {
        return service.createPlayer(command);
    }

    @GetMapping
    public List<PlayerDTO> listPlayers() {
        return service.listPlayers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable("id") long id) {
        service.deletePlayer(id);
    }
}