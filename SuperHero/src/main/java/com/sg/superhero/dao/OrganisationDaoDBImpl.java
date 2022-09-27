package com.sg.superhero.dao;

import com.sg.superhero.dto.Organisation;
import com.sg.superhero.dto.Superhero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganisationDaoDBImpl implements OrganisationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organisation getOrgById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM Organisation WHERE organisationId = ?";
            Organisation org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrgMapper(), id);
            org.setMembers(getHeroesForOrg(id));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organisation> getAllOrgs() {
        final String SELECT_ALL_ORGS = "SELECT * FROM Organisation";
        List<Organisation> orgs = jdbc.query(SELECT_ALL_ORGS, new OrgMapper());
        for (Organisation org : orgs) {
            org.setMembers(getHeroesForOrg(org.getOrganisationId()));
        }
        return orgs;
    }

    private List<Superhero> getHeroesForOrg(int id) {
        final String SELECT_HEROES_BY_ORG = "SELECT s.* FROM superhero s "
                + "JOIN super_Organisation so ON so.heroId = s.superheroId WHERE so.orgId = ?";
        List<Superhero> heroes = jdbc.query(SELECT_HEROES_BY_ORG, new SuperheroDaoDBImpl.HeroMapper(), id);
        return heroes;
    }

    @Override
    @Transactional
    public Organisation createOrg(Organisation Organisation) {
        final String INSERT_ORG = "INSERT INTO Organisation(organisationName, organisationDescription, contactInfo) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORG,
                Organisation.getOrganisationName(),
                Organisation.getDescription(),
                Organisation.getContactInfo());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        Organisation.setOrganisationId(newId);
        insertSuperOrg(Organisation);
        return Organisation;
    }

    private void insertSuperOrg(Organisation Organisation) {
        final String INSERT_SUPER_ORG = "INSERT INTO super_Organisation(orgId, heroId) VALUES(?,?)";
        if (Organisation.getMembers() != null) {
            for (Superhero hero : Organisation.getMembers()) {
                jdbc.update(INSERT_SUPER_ORG,
                        Organisation.getOrganisationId(),
                        hero.getId());
            }
        }

    }

    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_SUPER_ORG = "DELETE FROM super_Organisation WHERE orgId = ?";
        jdbc.update(DELETE_SUPER_ORG, id);

        final String DELETE_ORG = "DELETE FROM Organisation WHERE organisationId = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    @Transactional
    public void updateOrg(Organisation Organisation) {
        final String UPDATE_ORG = "UPDATE Organisation SET organisationName = ?, organisationDescription = ?, contactInfo= ?"
                + "WHERE organisationId = ?";
        jdbc.update(UPDATE_ORG,
                Organisation.getOrganisationName(),
                Organisation.getDescription(),
                Organisation.getContactInfo(),
                Organisation.getOrganisationId());

        // DELETE old references and reinsert
        final String DELETE_SUPER_ORG = "DELETE FROM super_Organisation WHERE orgId = ?";
        jdbc.update(DELETE_SUPER_ORG, Organisation.getOrganisationId());
        insertSuperOrg(Organisation);
    }

    @Override
    public List<Organisation> getOrgsForSuperhero(Superhero superhero) {
        final String SELECT_ORGS_FOR_HERO = "SELECT o.* FROM Organisation o JOIN "
                + "super_Organisation so ON so.orgId = o.organisationId WHERE so.heroId = ?";
        List<Organisation> orgs = jdbc.query(SELECT_ORGS_FOR_HERO,
                new OrgMapper(), superhero.getId());
        for (Organisation org : orgs) {
            org.setMembers(getHeroesForOrg(org.getOrganisationId()));
        }
        return orgs;
    }

    public static final class OrgMapper implements RowMapper<Organisation> {

        @Override
        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            Organisation org = new Organisation();
            org.setOrganisationId(rs.getInt("organisationId"));
            org.setOrganisationName(rs.getString("organisationName"));
            org.setDescription(rs.getString("organisationDescription"));
            org.setContactInfo(rs.getString("contactInfo"));
            return org;
        }
    }
}