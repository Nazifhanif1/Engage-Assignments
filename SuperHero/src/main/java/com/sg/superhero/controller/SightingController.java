package com.sg.superhero.controller;

import com.sg.superhero.dao.LocationDao;
import com.sg.superhero.dao.SightingDao;
import com.sg.superhero.dao.SuperheroDao;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;
import com.sg.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class SightingController {

    @Autowired
    SuperheroDao superheroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superhero> superheroes = superheroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("createSighting")
    public String createSighting(HttpServletRequest request) {
        Sighting sighting = new Sighting();

        String superheroId = request.getParameter("hero");
        String locationId = request.getParameter("location");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getParameter("date"), formatter);

        sighting.setHero(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        sighting.setLoc(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setSightingDate(date);
        sightingDao.createSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> superheroes = superheroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        String superheroId = request.getParameter("hero");
        String locationId = request.getParameter("location");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(request.getParameter("date"), formatter);

        sighting.setHero(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        sighting.setLoc(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setSightingDate(date);
        sightingDao.updateSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetailDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

}