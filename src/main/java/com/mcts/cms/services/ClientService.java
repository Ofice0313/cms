package com.mcts.cms.services;

import com.mcts.cms.controllers.ClientController;
import com.mcts.cms.dto.ClientDTO;
import com.mcts.cms.entities.Client;
import com.mcts.cms.mapper.ObjectMapper;
import com.mcts.cms.repositories.ClientRepository;
import com.mcts.cms.services.exceptions.DatabaseException;
import com.mcts.cms.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(Pageable pageable) {
        Page<Client> list = repository.findAll(pageable);
        return list.map(x -> new ClientDTO(x));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> obj = repository.findById(id);
        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
        var client = new ClientDTO(entity);

        var dto = ObjectMapper.parseObject(client, ClientDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public ClientDTO insert(ClientDTO  dto) {
        Client client = new Client();
        copyDtoToEntity(dto, client);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional
    public ClientDTO patch(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);

            if (dto.getFirstName() != null) {
                entity.setFirstName(dto.getFirstName());
            }
            if (dto.getLastName() != null) {
                entity.setLastName(dto.getLastName());
            }
            if (dto.getEmail() != null) {
                entity.setEmail(dto.getEmail());
            }
            if (dto.getPhone() != null) {
                entity.setPhone(dto.getPhone());
            }

            entity = repository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found!");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }
//
    private void copyDtoToEntity(ClientDTO dto, Client client) {
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
    }

    private void addHateoasLinks(ClientDTO dto) {
//        dto.add(linkTo(methodOn(ClientController.class).findAllPage()).withRel("findAllPaged").withType("GET"));
        dto.add(linkTo(methodOn(ClientController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ClientController.class).insert(dto)).withRel("insert").withType("POST"));
        dto.add(linkTo(methodOn(ClientController.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(ClientController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
