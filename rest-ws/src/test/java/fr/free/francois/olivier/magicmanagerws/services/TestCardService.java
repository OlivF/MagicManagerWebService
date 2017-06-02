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
import fr.free.francois.olivier.magicmanagerws.model.Card;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ContextConfig.class})
public class TestCardService {

	@Resource
	private CardService cardService;
	
	@Test
	public void testFindAllCards() {
		Set<Card> cards = cardService.findAll();
		Assert.assertNotNull(cards);
        Assert.assertTrue(cards.size() > 0);
	}
	
	@Test
	public void testFindCardById() {
		Card card = cardService.findById(3);
		Assert.assertNotNull(card);
	}

	@Test
	public void testFindCardByName() {
		List<Card> cards = cardService.findByName("%luie%");
		Assert.assertNotNull(cards);
        Assert.assertTrue(cards.size() > 0);
	}
	
}
