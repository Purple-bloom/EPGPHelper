package custom.cyd.EpgpHelperBackend.RelationService;

import custom.cyd.EpgpHelperBackend.Entity.Boss;
import custom.cyd.EpgpHelperBackend.Entity.Raidrun;
import custom.cyd.EpgpHelperBackend.RelationEntity.RelationRaidrunBoss;
import custom.cyd.EpgpHelperBackend.RelationRepository.RelationRaidrunBossRepository;
import custom.cyd.EpgpHelperBackend.Repository.BossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RelationRaidrunBossService {
    Logger logger = Logger.getLogger(RelationRaidrunBossService.class.getName());

    @Autowired
    private RelationRaidrunBossRepository relationRaidrunBossRepository;
    @Autowired
    private BossRepository bossRepository;

    public List<Boss> getAliveBossesForRaidrun(Raidrun raidrun){
        List<RelationRaidrunBoss> listAliveBossRelations = relationRaidrunBossRepository.findByRaidrunAndDefeated(raidrun, false);
        List<Boss> aliveBosses = new ArrayList<>();
        for (RelationRaidrunBoss element : listAliveBossRelations){
            aliveBosses.add(element.getBoss());
        }
        return aliveBosses;
    }

    public List<Boss> getDefeatedBossesForRaidrun(Raidrun raidrun){
        List<RelationRaidrunBoss> listDeadBossRelations = relationRaidrunBossRepository.findByRaidrunAndDefeated(raidrun, true);
        List<Boss> aliveBosses = new ArrayList<>();
        for (RelationRaidrunBoss element : listDeadBossRelations){
            aliveBosses.add(element.getBoss());
        }
        return aliveBosses;
    }
}
