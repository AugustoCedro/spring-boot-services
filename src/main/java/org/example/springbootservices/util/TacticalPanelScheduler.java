package org.example.springbootservices.util;


import lombok.AllArgsConstructor;
import org.example.springbootservices.service.TacticalPanelService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TacticalPanelScheduler {

    private TacticalPanelService service;

    //@Scheduled(cron = "0 0 0 * * *") // todo dia 00:00
    @Scheduled(cron = "0 * * * * *")
    @CacheEvict(value = "topMissions", allEntries = true)
    public void limparCacheAutomaticamente() {
        System.out.println("Cache limpo!");

        service.listTopMissionsLast15Days();
    }
}
