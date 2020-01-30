package cat.tecnocampus.omega.persistanceController;

import cat.tecnocampus.omega.domain.Category;
import cat.tecnocampus.omega.domain.post.Tutorial;
import cat.tecnocampus.omega.persistance.TutorialDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TutorialController")
public class TutorialController {

    private final TutorialDAO tutorialDAO;

    public TutorialController(TutorialDAO tutorialDAO){
        this.tutorialDAO = tutorialDAO;
    }

    @Transactional
    public int addTutorial(Tutorial tutorial, String category){
        return  tutorialDAO.insertTutorial(tutorial,category);
    }

    public List<Tutorial> getAll(){
        return tutorialDAO.findAll();
    }
    public Tutorial getById(String id){
        return tutorialDAO.findById(id);
    }
    public  List<Tutorial> getByCategory(String category){ return tutorialDAO.findByCategory(category);}
    public List<Category> getCategories(){return tutorialDAO.findCategories();}
    @Transactional
    public int addCategory(Category category) { return tutorialDAO.insertCategory(category); }

}
