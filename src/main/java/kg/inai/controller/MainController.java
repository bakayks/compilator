package kg.inai.controller;

import kg.inai.entity.Reg;
import kg.inai.model.Compilator;
import kg.inai.service.RegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class MainController {
    @Autowired
    private RegService regService;

    @GetMapping("/")
    public String get(Model model){
        List<Reg> regs = regService.findAll();
        model.addAttribute("regs", regs);
        if(Compilator.isIsTrue()){
            String word = Compilator.getWordM();
            String[][] table = Compilator.run(word);
            List<String> words = new ArrayList<>();
            for(int i = 0; i < word.length(); i++){
                words.add(String.valueOf(word.charAt(i)));
            }
            model.addAttribute("table", table);
            model.addAttribute("words", words);
            model.addAttribute("esGeht", Compilator.isEsGeht());
        }
        Compilator.setIsTrue(false);
        return "index";
    }

    @PostMapping(value = "/addReg")
    public String addReg(@Valid @ModelAttribute("reg") Reg reg) {
        regService.create(reg);
        Compilator.setGrammar(regService.findAll());
        return "redirect:/";
    }

    @PostMapping(value = "/delReg/{id}")
    public String delReg(@PathVariable("id") Long id) {
        regService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping(value = "/addWord")
    public String addWord(@Valid @ModelAttribute("word") String word, Model model) {
        Compilator.setGrammar(regService.findAll());
        Compilator.setIsTrue(true);
        String[][] table = Compilator.run(word);
        return "redirect:/";
    }

    @GetMapping("/doc")
    public String getDoc(){
        return "doc";
    }

    @GetMapping("/faq")
    public String getFaq(){
        return "faq";
    }
}

