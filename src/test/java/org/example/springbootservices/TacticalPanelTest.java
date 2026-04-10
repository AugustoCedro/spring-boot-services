package org.example.springbootservices;

import org.example.springbootservices.model.operacoes.TacticalPanel;
import org.example.springbootservices.service.TacticalPanelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class TacticalPanelTest {

    @Autowired
    private TacticalPanelService service;

    @Test
    void shouldReturnTop10RecentMissions(){

        List<TacticalPanel> result = service.listTopMissionsLast15Days();
        assertEquals(10,result.size());
    }
}
