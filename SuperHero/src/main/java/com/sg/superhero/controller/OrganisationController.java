package com.sg.superhero.controller;

import com.sg.superhero.dao.OrganisationDao;
import com.sg.superhero.dao.SuperheroDao;
import com.sg.superhero.dto.Organisation;
import com.sg.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganisationController {

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    SuperheroDao superheroDao;

    @GetMapping("organisations")
    public String displayOrganisations(Model model) {
        List<Organisation> organisations = organisationDao.getAllOrgs();
        model.addAttribute("organisations", organisations);
        return "organisations";
    }

    @PostMapping("createOrganisation")
    public String addOrganisation(HttpServletRequest request) {
        Organisation org = new Organisation();

        org.setOrganisationName(request.getParameter("name"));
        org.setDescription(request.getParameter("description"));
        org.setContactInfo(request.getParameter("contactInfo"));

        String[] superheroIds = request.getParameterValues("superId");

        List<Superhero> superheroes = new ArrayList<>();
        for (String superheroId : superheroIds) {
            superheroes.add(superheroDao.getHeroById(Integer.parseInt(superheroId)));
        }

        org.setMembers(superheroes);
        organisationDao.createOrg(org);

        return "redirect:/organisations";

    }

    @GetMapping("organisationDetail")
    public String organisationDetail(Integer id, Model model) {
        Organisation organisation = organisationDao.getOrgById(id);
        model.addAttribute("organisation", organisation);
        return "organisationDetail";
    }

    @GetMapping("deleteOrganisation")
    public String deleteOrganisation(Integer id) {
        organisationDao.deleteOrgById(id);
        return "redirect:/organisations";
    }

    @GetMapping("editOrganisation")
    public String editOrganisation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organisation organisation = organisationDao.getOrgById(id);
        model.addAttribute("organisation", organisation);
        return "editOrganisation";
    }

    @PostMapping("editOrganisation")
    public String performEditOrganisation(HttpServletRequest request) {
        Organisation org = new Organisation();

        org.setOrganisationName(request.getParameter("name"));
        org.setDescription(request.getParameter("description"));
        org.setContactInfo(request.getParameter("contactInfo"));

        organisationDao.updateOrg(org);
        return "redirect:/organisations";
    }

}