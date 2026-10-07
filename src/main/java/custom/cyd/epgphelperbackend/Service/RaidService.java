package custom.cyd.epgphelperbackend.Service;

import custom.cyd.epgphelperbackend.Entity.Raid;
import custom.cyd.epgphelperbackend.Entity.RaidReward;
import custom.cyd.epgphelperbackend.Repository.RaidRepository;
import custom.cyd.epgphelperbackend.Repository.RaidRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RaidService {

    Logger logger = Logger.getLogger(RaidService.class.getName());

    @Autowired
    private RaidRepository raidRepository;
    @Autowired
    private RaidRewardRepository raidRewardRepository;

    public List<Raid> getAllRaids(){
        return raidRepository.findAll();
    }

    public Optional<Raid> getRaid(Long id){
        return raidRepository.findById(id);
    }

    public Raid createRaid(Raid raid){
        return raidRepository.save(raid);
    }

    public void deleteRaid(Long id){
        Raid raid = raidRepository.findById(id).orElse(null);
        if (raid == null) throw new NoSuchElementException("No raid found with ID " + id);
        logger.info("Deleting raid " + raid + " and all related RaidRewards.");
        List<RaidReward> raidRewards = raidRewardRepository.findByRaid(raid);
        for (RaidReward raidReward : raidRewards){
            raidRewardRepository.delete(raidReward);
        }
        raidRepository.deleteById(id);
    }
}
