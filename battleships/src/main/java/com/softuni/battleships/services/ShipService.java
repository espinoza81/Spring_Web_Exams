package com.softuni.battleships.services;

import com.softuni.battleships.models.Category;
import com.softuni.battleships.models.Ship;
import com.softuni.battleships.models.ShipType;
import com.softuni.battleships.models.User;
import com.softuni.battleships.models.dtos.CreateShipDTO;
import com.softuni.battleships.models.dtos.ShipDTO;
import com.softuni.battleships.repositories.CategoryRepository;
import com.softuni.battleships.repositories.ShipRepository;
import com.softuni.battleships.repositories.UserRepository;
import com.softuni.battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final LoggedUser userSession;
    private final UserRepository userRepository;

    public ShipService(ShipRepository shipRepository,
                       CategoryRepository categoryRepository,
                       LoggedUser userSession,
                       UserRepository userRepository){

        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.userSession = userSession;
        this.userRepository = userRepository;
    }

    public boolean created(CreateShipDTO createShipDTO){

        Optional<Ship> byName = this.shipRepository.findByName(createShipDTO.getName());

        if(byName.isPresent()){
            return false;
        }

        ShipType shipType = switch (createShipDTO.getCategory()){
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.PATROL;
        };

        Category category = this.categoryRepository.findByName(shipType);
        Optional<User> owner = this.userRepository.findById(this.userSession.getId());

        Ship ship = new Ship()
                .setName(createShipDTO.getName())
                .setHealth(createShipDTO.getHealth())
                .setPower(createShipDTO.getPower())
                .setCategory(category)
                .setOwner(owner.get())
                .setCreated(createShipDTO.getCreated());

        this.shipRepository.save(ship);

        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(long ownerId) {
        return this.shipRepository.findByOwnerId(ownerId)
                .stream()
                .map(ShipDTO::new)
                .collect(Collectors.toList());
    }


    public List<ShipDTO> getShipsNotOwnedBy(long ownerId) {
        return this.shipRepository.findByOwnerIdNot(ownerId)
                .stream()
                .map(ShipDTO::new)
                .collect(Collectors.toList());
    }


    public List<ShipDTO> getAllSorted() {
        return this.shipRepository.findByOrderByHealthAscNameDescPowerAsc()
                .stream()
                .map(ShipDTO::new)
                .collect(Collectors.toList());
    }
}
