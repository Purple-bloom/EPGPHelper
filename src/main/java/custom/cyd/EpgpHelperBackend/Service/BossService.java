package custom.cyd.EpgpHelperBackend.Service;

import custom.cyd.EpgpHelperBackend.Entity.Boss;
import custom.cyd.EpgpHelperBackend.Entity.Raid;
import custom.cyd.EpgpHelperBackend.Repository.BossRepository;
import custom.cyd.EpgpHelperBackend.Repository.RaidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BossService {
    Logger logger = Logger.getLogger(BossService.class.getName());

    @Autowired
    private BossRepository bossRepository;
    @Autowired
    private RaidRepository raidRepository;

    public List<Boss> getAllBosses(){
        return bossRepository.findAll();
    }

    public List<Boss> getAllBossesForRaid(Long raidId){
        Optional<Raid> optionalRaid = raidRepository.findById(raidId);
        if(optionalRaid.isEmpty()) {
            logger.info(String.format("Did Not find a raid for input: %d", raidId));
        }
        return optionalRaid.map(raid -> bossRepository.findByRaid(raid)).orElse(null);
    }

    public Optional<Boss> getBoss(Long id){
        return bossRepository.findById(id);
    }

    public Boss createBoss(Boss boss){
        return bossRepository.save(boss);
    }
}
