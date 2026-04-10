package org.example.springbootservices.service;

import lombok.AllArgsConstructor;
import org.example.springbootservices.model.operacoes.TacticalPanel;
import org.example.springbootservices.repository.TacticalPanelRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TacticalPanelService {

    private TacticalPanelRepository repository;

    @Cacheable("topMissions")
    public List<TacticalPanel> listTopMissionsLast15Days() {
        List<TacticalPanel> missionPanelList = repository.findAll();
        return missionPanelList.stream()
                .filter(m -> m.getUpdatedAt().isAfter(LocalDate.now().minusDays(15).atStartOfDay()))
                .sorted(Comparator.comparing(TacticalPanel::getReadinessIndex).reversed())
                .limit(10)
                .toList();
    }

    @Scheduled(fixedRate = 60000)
    @CacheEvict(value = "topMissions", allEntries = true)
    public void evictCache() {
        System.out.println("Cache limpo");
    }

}
