package cat.tecnocampus.omega.webControllers;

import cat.tecnocampus.omega.persistanceController.ChallengeController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChallengeWebController {

    private ChallengeController challengeController;

    public ChallengeWebController(ChallengeController challengeController) {
        this.challengeController = challengeController;
    }
    @GetMapping("challenge/all")
    public String listChallenges(Model model) {
        model.addAttribute("challengesList", challengeController.getAll());
        return "post/showChallenges";
    }

    @PostMapping("challenge/all")
    public String listChallenges(String chosen, RedirectAttributes redirectAttributes) {
        System.out.println(chosen);
        redirectAttributes.addAttribute("id", chosen);
        return "redirect:/challenge/do/{id}";
    }
    @GetMapping("challenge/do/{id}")
    public String showChallenge(Model model, @PathVariable String id) {
        model.addAttribute("challenge", challengeController.getById(id));
        return "post/showChallenge";
    }

//    @PostMapping("challenge/{id}")
//    public String showChallenge(String chosen, @PathVariable String id, RedirectAttributes redirectAttributes) {
//        if (chosen.equals("Return"))
//            return "redirect:/challenge/all";
//
//        Exercise exercise = exerciseController.getExercise(chosen);
//        redirectAttributes.addAttribute("post", id);
//        redirectAttributes.addAttribute("exercise", exercise.getExercise_ID());
//        redirectAttributes.addAttribute("type", "do");
//        if (exercise.getType().equals("Test"))
//            return "redirect:/exercise/doTest/{type}/{post}/{exercise}";
//        if (exercise.isDrag())
//            redirectAttributes.addAttribute("drag", "2");
//        else redirectAttributes.addAttribute("drag", "1");
//        return "redirect:/exercise/doFill/{type}/{post}/{exercise}/{drag}";
//    }

/*
    @GetMapping("createChallenge")
    public String createChallenge(Model model) {
        model.addAttribute(new Challenge());
        return "post/newChallenge";
    }

    @PostMapping("createChallenge")
    public String createChallenge(@Valid Challenge challenge,String description, Errors errors, Model model, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "post/newChallenge";
        }

        model.addAttribute("title", challenge.getTitle());
        challenge.setDescription(Processor.process(description));
        challengeController.addChallenge(challenge);

        redirectAttributes.addAttribute("id", challenge.getPostID());
        redirectAttributes.addAttribute("type", "Cha");
        return "redirect:/createExercise/{id}/{type}";
    }
*/
}
