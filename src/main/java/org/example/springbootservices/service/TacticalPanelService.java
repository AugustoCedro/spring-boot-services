package org.example.springbootservices.service;

import lombok.AllArgsConstructor;
import org.example.springbootservices.model.operacoes.TacticalPanel;
import org.example.springbootservices.repository.TacticalPanelRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TacticalPanelService {

    //@Cacheable -> guarda o resultado pra não repetir consulta
    //@CacheEvict -> limpa o cache quando os dados mudam pra não ficar desatualizado
    //@Scheduled -> executa um método automaticamente em intervalos ou horários definidos, foi utilizado um intervalo de 1 minuto

    private TacticalPanelRepository repository;

    @Cacheable("topMissions")
    public List<TacticalPanel> listTopMissionsLast15Days() {
        LocalDateTime date = LocalDate.now()
                .minusDays(15)
                .atStartOfDay();
        return repository.findTop10Recent(date);
    }

    @Scheduled(fixedRate = 60000)
    @CacheEvict(value = "topMissions", allEntries = true)
    public void evictCache() {

    }

}
