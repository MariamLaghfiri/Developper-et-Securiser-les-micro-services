package com.example.amis.controller;

import com.example.amis.dto.AmiDto;
import com.example.amis.entity.Ami;
import com.example.amis.service.IAmiService;
import com.javatechie.entity.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/amis")
public class AmiController {
    private final IAmiService iAmiService;

    @Autowired
    public AmiController(IAmiService iAmiService) {
        this.iAmiService = iAmiService;
    }
    @PostMapping("/save")
    public ResponseEntity<AmiDto> saveAmi(@RequestBody AmiDto amiDto ,@RequestHeader(name = "id") long id ){
        amiDto.setIdEmetteur(id);
        AmiDto savedAmi = iAmiService.saveAmi(amiDto);
        return new ResponseEntity<>(savedAmi, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<AmiDto>> getAllAmis(){
        List<AmiDto> amis =iAmiService.getAllAmi();
        return ResponseEntity.ok(amis);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AmiDto> getAmiById(@PathVariable Long id){
        Optional<AmiDto> amiOptional = iAmiService.getAmiById(id);
        return amiOptional.map(amiDto -> new ResponseEntity<>(amiDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmi(@PathVariable Long id){
        iAmiService.deleteAmi(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/emetteur/{id}")
    public ResponseEntity<AmiDto>getEmetteurById(@PathVariable Long id){
        Optional<AmiDto> optionalAmiDto =iAmiService.getEmetteur(id);
        return optionalAmiDto.map(amiDto -> new ResponseEntity<>(amiDto,HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/accepter/{idAmi}")
    public ResponseEntity<Ami> accepterDemandeAmi(@PathVariable Long idAmi) {
        Ami ami = iAmiService.accepterDemandeAmi(idAmi);
        if (ami != null) {
            return new ResponseEntity<>(ami, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/ajouter")
    public ResponseEntity<AmiDto> ajouterAmi(@RequestBody AmiDto amiDto) {
        AmiDto amiAjoute = iAmiService.ajouterAmi(amiDto.getIdRecepteur(), amiDto.getIdEmetteur());
        return new ResponseEntity<>(amiAjoute, HttpStatus.CREATED);
    }

    @GetMapping("/amis-acceptes/{id}")
    public ResponseEntity<List<UserCredential>> getAllAcceptedAmis(@PathVariable Long id) {
        List<UserCredential> amisAcceptes = iAmiService.getAllAcceptedAmis(id);
        return new ResponseEntity<>(amisAcceptes, HttpStatus.OK);
    }
}


