package org.training360.finalexam.players;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.finalexam.EntityNotFoundException;


import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository repository;
    private final ModelMapper modelMapper;

    public PlayerService(PlayerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public PlayerDTO createPlayer(CreatePlayerCommand command) {
        Player player = new Player(command.getName(), command.getBirthDate(), command.getPosition());
        repository.save(player);
        return modelMapper.map(player, PlayerDTO.class);
    }

    public List<PlayerDTO> listPlayers() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, PlayerDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePlayer(long id) {
        Player player = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                URI.create("players/player-not-found"),
                "Player not found",
                String.format("Player with %d id not found", id)
        ));

        repository.delete(player);
    }
}
