package dev.peertosir.difftoolui.controller;

import dev.peertosir.difftoolui.model.Diff;
import dev.peertosir.difftoolui.service.DiffService;
import dev.peertosir.difftoolui.service.RestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class DiffController {

    private final RestService restService;
    private final DiffService diffService;

    public DiffController(RestService restService, DiffService diffService) {
        this.diffService = diffService;
        this.restService = restService;
    }

    @GetMapping("/start-diff")
    public String diffForm(Model model) {
        model.addAttribute("diff", new Diff());
        return "diff-form";
    }

    @PostMapping("/start-diff")
    public String submitDiff(@ModelAttribute Diff diff, Model model) {
        String json1 = this.restService.MakeApiRequest(diff.getHost1());
        String json2 = this.restService.MakeApiRequest(diff.getHost2());
        String ignoreFields = diff.getExcludeRegExps();

        Optional<String> result = this.diffService.MakeDiff(json1, json2, ignoreFields);
        String response = "NODIFF";
        if (result.isPresent()) {
            response = result.get();
        }
        response = response.replace(";", "<br>");
        model.addAttribute("name", diff.getService());
        model.addAttribute("response", response);
        return "sent-diff";
    }
}
