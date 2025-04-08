package custom.cyd.GuildHelper.Service;

import custom.cyd.GuildHelper.Entity.RaidReward;
import custom.cyd.GuildHelper.Repository.RaidRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RaidRewardsService {
    Logger logger = Logger.getLogger(RaidRewardsService.class.getName());

    @Autowired
    private RaidRewardRepository raidRewardRepository;

    public List<RaidReward> getAllRaidRewards(){
        return raidRewardRepository.findAll();
    }

    public Optional<RaidReward> getRaidReward(Long id){
        return raidRewardRepository.findById(id);
    }

    public RaidReward createRaidReward(RaidReward raidReward){
        return raidRewardRepository.save(raidReward);
    }

    public ResponseEntity<String> deleteRaidReward(RaidReward raidReward){
        RaidReward toBeDeleted = raidRewardRepository.findById(raidReward.getId()).orElse(null);
        if(toBeDeleted == null){
            String message = "Deletion attempted of non-existent RaidReward. Function was called for " + raidReward + ".";
            logger.severe(message);
            return ResponseEntity.badRequest().body(message);
        }
        if(!toBeDeleted.getRewardType().equalsIgnoreCase(raidReward.getRewardType())
            || !Objects.equals(toBeDeleted.getRaid().getId(), raidReward.getRaid().getId())
            || !Objects.equals(toBeDeleted.getRewardValue(), raidReward.getRewardValue())){
            String message = "Deletion attempted of RaidReward, but values differ. Function was called for " + raidReward + " and " + toBeDeleted + " was found.";
            logger.severe(message);
            return ResponseEntity.badRequest().body(message);
        }
        raidRewardRepository.delete(raidReward);
        return ResponseEntity.ok().body("Success!");
    }

    public RaidReward updateRaidReward(RaidReward raidReward){
        RaidReward old = raidRewardRepository.findById(raidReward.getId()).orElse(null);
        if(old == null){
            logger.severe("Unable to update raidreward " + raidReward + " because ID was not found.");
            return null;
        } else if (!Objects.equals(raidReward.getRaid().getId(), old.getRaid().getId())){
            logger.severe("Unable to update raidreward " + raidReward + " because it has a different raid than the original reward for that ID.");
            return null;
        }
        old.setRewardValue(raidReward.getRewardValue());
        old.setRewardType(raidReward.getRewardType());
        return raidRewardRepository.save(old);
    }
}
