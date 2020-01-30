package cat.tecnocampus.omega.persistanceController;

import cat.tecnocampus.omega.domain.post.Tutorial;
import cat.tecnocampus.omega.persistance.ChallengeDAO;
import cat.tecnocampus.omega.domain.post.Challenge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ChallengeController")
public class ChallengeController {

    private final ChallengeDAO challengeDAO;

    public ChallengeController(ChallengeDAO challengeDAO) {
        this.challengeDAO = challengeDAO;
    }

    @Transactional
    public int addChallenge(Challenge challenge) {
        return challengeDAO.insertChallenge(challenge, "Java");
    }

    public List<Challenge> getAll() {

        return challengeDAO.findAll();
    }
    public Challenge getById(String id){
        return challengeDAO.findById(id);
    }

}
