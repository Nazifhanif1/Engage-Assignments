package com.sg.superhero.dao;

import com.sg.superhero.dto.Organisation;
import com.sg.superhero.dto.Superhero;

import java.util.List;

public interface OrganisationDao {

    Organisation createOrg(Organisation organisation);

    void updateOrg(Organisation organisation);

    void deleteOrgById(int id);

    Organisation getOrgById(int id);

    List<Organisation> getAllOrgs();

    List<Organisation> getOrgsForSuperhero(Superhero superhero);

}