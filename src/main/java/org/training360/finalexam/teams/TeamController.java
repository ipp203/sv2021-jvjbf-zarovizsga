package org.training360.finalexam.teams;

import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @PostMapping
    public TeamDTO createTeam(@Valid @RequestBody CreateTeamCommand command) {
        return service.createTeam(command);
    }

    @GetMapping
    public List<TeamDTO> listTeams() {
        return service.listTeams();
    }

    @PostMapping("/{id}/players")
    public TeamDTO addPlayer(@PathVariable("id") long id, @Valid @RequestBody CreatePlayerCommand command) {
        return service.addPlayer(id, command);
    }

    @PutMapping("/{id}/players")
    public TeamDTO certificatePlayer(@PathVariable("id") long id, @RequestBody UpdateWithExistingPlayerCommand command) {
        return service.addExistingPlayer(id, command);
    }
}