package fr.free.francois.olivier.magicmanagerws.services;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.free.francois.olivier.magicmanagerws.config.ContextConfig;
import fr.free.francois.olivier.magicmanagerws.model.Edition;
import fr.free.francois.olivier.magicmanagerws.services.EditionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ContextConfig.class})
public class TestEditionService {

	@Resource
	private EditionService editionService;
	
	@Test
	public void testFindAllEditions() {
		Set<Edition> editions = editionService.findAll();
		Assert.assertNotNull(editions);
        Assert.assertTrue(editions.size() > 0);
	}
	
	@Test
	public void testFindEditionById() {
		Edition edition = editionService.findById(1);
		Assert.assertNotNull(edition);
	}

	@Test
	public void testFindEditionByName() {
		List<Edition> editions = editionService.findByName("%odyss%");
		Assert.assertNotNull(editions);
        Assert.assertTrue(editions.size() > 0);
	}
	
	@Test
	public void testAddUpdateAndDeleteEdition() {
		Edition e = new Edition("TestEditionNameFr", "TestEditionNameEn", "");
		editionService.saveEdition(e);
		e.setNameFr("newNameFr");
		e.setNameEn("newNameEn");
		editionService.updateEdition(e);
		editionService.deleteEdition(e);
	}
}
