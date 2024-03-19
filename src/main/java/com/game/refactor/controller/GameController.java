package com.game.refactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.game.refactor.model.Story;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {
    // Create the story
    private Story story = new Story();

    @Autowired
    public GameController(Story story) {
        this.story = story;
    }

    @GetMapping("/start")
    public String index(Model model) {
        // Initializing the game and pass initial data to Thymeleaf
        story.selectPosition("intro"); //Start the story
        model.addAttribute("mainTextArea", story.getMainText());
        model.addAttribute("choices", story.getCurrentChoices());
        model.addAttribute("secretButton", story.getSecretButton());
        return "game";
    }

    @PostMapping("/choice")
    public String handleChoice(@RequestParam("choice") String choice, Model model) {
        // Handle user choice and update the story
        String nextPage = story.selectPosition(choice);
        if (nextPage.equals("redirect:/")) {
            return nextPage; // Redirect to start page (index.html)
        }
        // Update UI data based on story progression
        model.addAttribute("mainTextArea", story.getMainText());
        model.addAttribute("choices", story.getCurrentChoices());
        return "game"; // Display the game page
    }
}
