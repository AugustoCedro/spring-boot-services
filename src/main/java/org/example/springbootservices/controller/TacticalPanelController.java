package org.example.springbootservices.controller;

import lombok.AllArgsConstructor;
import org.example.springbootservices.model.operacoes.TacticalPanel;
import org.example.springbootservices.service.TacticalPanelService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/painel")
@AllArgsConstructor
@Validated
public class TacticalPanelController {

    private TacticalPanelService service;

    @GetMapping
    public ResponseEntity<List<TacticalPanel>> listTopMissionsLast15Days(){
        List<TacticalPanel> latestMissions = service.listTopMissionsLast15Days();

        return ResponseEntity.ok().body(latestMissions);
    }

}
