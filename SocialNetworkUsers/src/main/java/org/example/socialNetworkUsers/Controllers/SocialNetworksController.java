package org.example.socialNetworkUsers.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.socialNetworkUsers.Dto.SocialNetworkDto;
import org.example.socialNetworkUsers.Entity.SocialNetwork;
import org.example.socialNetworkUsers.Services.SocialNetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socialNetwork")
@RequiredArgsConstructor
public class SocialNetworksController {
    private final SocialNetworkService socialNetworkService;

    @GetMapping
    public ResponseEntity getAllSocialNetworks() {
        return socialNetworkService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getSocialNetworkById(@PathVariable int id) {
        return socialNetworkService.getById(id);
    }

    @PostMapping
    public ResponseEntity createSocialNetwork(@RequestBody SocialNetworkDto socialNetworkDto) {
        return socialNetworkService.create(socialNetworkDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateSocialNetwork(@PathVariable int id, @RequestBody SocialNetworkDto socialNetworkDto) {
        return socialNetworkService.update(id, socialNetworkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSocialNetworkById(@PathVariable int id) {
        return socialNetworkService.deleteById(id);
    }

    @DeleteMapping
    public ResponseEntity deleteAllSocialNetworks() {
        return socialNetworkService.clear();
    }
}
