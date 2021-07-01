package dev.peertosir.difftoolui.controller;

import dev.peertosir.difftoolui.model.Diff;
import dev.peertosir.difftoolui.model.response.TeamCityResponseBuildCreated;
import dev.peertosir.difftoolui.service.RestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DiffController {

    private final RestService restService;

    public DiffController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/start-diff")
    public String diffForm(Model model) {
        model.addAttribute("diff", new Diff());
        return "diff-form";
    }

    @PostMapping("/start-diff")
    public String submitDiff(@ModelAttribute Diff diff, Model model) {
        TeamCityResponseBuildCreated responseBody = restService.createTeamCityDiffBuild(diff);
        model.addAttribute("response", responseBody);
        return "sent-diff";
    }

}
