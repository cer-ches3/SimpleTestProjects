package org.example.socialNetworkUsers.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socialNetworkUsers.Dto.Message;
import org.example.socialNetworkUsers.Dto.SocialNetworkDto;
import org.example.socialNetworkUsers.Entity.SocialNetwork;
import org.example.socialNetworkUsers.Repositories.SocialNetworkRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialNetworkService implements CRUDService<SocialNetworkDto> {
    private final SocialNetworkRepository socialNetworkRepository;
    private final Message socialNetworkNotFound = new Message("Социальная сеть с таким id не найден!");

    @Override
    public ResponseEntity getAll() {
        log.info("Get all social networks");
        Collection<SocialNetwork> socialNetworkCollection = socialNetworkRepository.findAll();
        if (socialNetworkCollection.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(socialNetworkCollection, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity getById(Integer id) {
        log.info("Get social network by id: {}", id);
        if (!socialNetworkRepository.existsById(id)) {
            return new ResponseEntity<>(socialNetworkNotFound, HttpStatus.NOT_FOUND);
        } else {
            SocialNetwork socialNetwork = socialNetworkRepository.findById(id).orElseThrow();
            return new ResponseEntity<>(socialNetwork, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity create(SocialNetworkDto socialNetworkDto) {
        log.info("Create social network");
        SocialNetwork socialNetwork = mapToEntity(socialNetworkDto);
        socialNetworkRepository.save(socialNetwork);
        return new ResponseEntity<>(socialNetwork, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity update(Integer id, SocialNetworkDto socialNetworkDto) {
        log.info("Update social network");
        if (!socialNetworkRepository.existsById(id)) {
            return new ResponseEntity<>(socialNetworkNotFound, HttpStatus.NOT_FOUND);
        } else {
            SocialNetwork socialNetwork = mapToEntity(socialNetworkDto);
            socialNetwork.setId(id);
            socialNetworkRepository.save(socialNetwork);
            return new ResponseEntity<>(socialNetwork, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity deleteById(int id) {
        log.info("Delete social network by id: {}", id);
        if (!socialNetworkRepository.existsById(id)) {
            return new ResponseEntity<>(socialNetworkNotFound, HttpStatus.NOT_FOUND);
        } else {
            socialNetworkRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity clear() {
        log.info("Delete all social network ");
        socialNetworkRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    public static SocialNetworkDto mapToDto(SocialNetwork socialNetwork) {
        SocialNetworkDto socialNetworkDto = new SocialNetworkDto();
        socialNetworkDto.setId(socialNetwork.getId());
        socialNetworkDto.setName(socialNetwork.getName());
        socialNetworkDto.setUserList(socialNetwork.getUsersList()
                .stream()
                .map(UserService :: mapToDto)
                .toList());
        return socialNetworkDto;
    }

    public static SocialNetwork mapToEntity(SocialNetworkDto socialNetworkDto) {
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.setId(socialNetworkDto.getId());
        socialNetwork.setName(socialNetworkDto.getName());
        socialNetwork.setUsersList(socialNetworkDto.getUserList()
                .stream()
                .map(UserService :: mapToEntity)
                .toList());
        return socialNetwork;
    }
}
