package com.sg.superhero.controller;

import com.sg.superhero.dao.SuperheroDao;
import com.sg.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperheroController {

    @Autowired
    SuperheroDao superheroDao;

    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        List<Superhero> allHeroes = superheroDao.getAllHeroes();
        model.addAttribute("superheroes", allHeroes);
        return "superheroes";
    }

    @PostMapping("createHero")
    public String createHero(HttpServletRequest request) {
        Superhero superhero = new Superhero();
        superhero.setName(request.getParameter("superheroName"));
        superhero.setDescription(request.getParameter("heroDescription"));
        superhero.setSuperpower(request.getParameter("superpower"));
        superheroDao.createHero(superhero);

        return "redirect:/superheroes";

    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(HttpServletRequest request) {
        superheroDao.deleteHeroById(Integer.parseInt(request.getParameter("id")));
        return "redirect:/superheroes";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getHeroById(id);
        model.addAttribute("superhero", superhero);
        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performEditHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getHeroById(id);
        superhero.setName(request.getParameter("superheroName"));
        superhero.setDescription(request.getParameter("heroDescription"));
        superhero.setSuperpower(request.getParameter("superpower"));

        superheroDao.updateHero(superhero);

        return "redirect:/superheroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) {
        Superhero superhero = superheroDao.getHeroById(id);
        model.addAttribute("hero", superhero);
        return "heroDetail";
    }

}
