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
import fr.free.francois.olivier.magicmanagerws.model.Deck;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ContextConfig.class})
public class TestDeckService {

	@Resource
	private DeckService deckService;
	
	@Test
	public void testFindAllDecks() {
		Set<Deck> decks = deckService.findAll();
		Assert.assertNotNull(decks);
        Assert.assertTrue(decks.size() > 0);
	}
	
	@Test
	public void testFindDeckById() {
		Deck deck = deckService.findById(1);
		Assert.assertNotNull(deck);
	}

	@Test
	public void testFindDeckByName() {
		List<Deck> decks = deckService.findByName("%olie%");
		Assert.assertNotNull(decks);
        Assert.assertTrue(decks.size() > 0);
	}
	
}
