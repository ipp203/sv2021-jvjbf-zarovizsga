package org.training360.finalexam.teams;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.finalexam.EntityNotFoundException;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.Player;
import org.training360.finalexam.players.PlayerRepository;
import org.training360.finalexam.players.PositionType;


import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private static final int MAX_PLAYERS_ON_SAME_POSITION = 2;

    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(ModelMapper modelMapper, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public TeamDTO createTeam(CreateTeamCommand command) {
        Team team = new Team(command.getName());
        teamRepository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    public List<TeamDTO> listTeams() {
        return teamRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TeamDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public TeamDTO addPlayer(long id, CreatePlayerCommand command) {
        Team team = findTeam(id);
        Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
        team.addPlayer(player);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addExistingPlayer(long id, UpdateWithExistingPlayerCommand command) {
        Team team = findTeam(id);
        Player player = findPlayer(command.getId());

        if (player.getTeam() == null && getNumberOfPlayersOnSamePosition(team, player.getPosition()) < MAX_PLAYERS_ON_SAME_POSITION) {
            player.setTeam(team);
            team.addPlayer(player);
        }
        return modelMapper.map(team, TeamDTO.class);
    }

    private long getNumberOfPlayersOnSamePosition(Team team, PositionType position) {
        return team.getPlayers().stream()
                .filter(pl -> pl.getPosition() == position)
                .count();
    }

    private Team findTeam(long id) {
        return teamRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                URI.create("teams/not-found"),
                "Team not found",
                String.format("Team with %d id not found", id)
        ));
    }

    private Player findPlayer(long id) {
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                URI.create("players/not-found"),
                "Player not found",
                String.format("Player with %d id not found", id)
        ));
    }
}
