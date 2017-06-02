package fr.free.francois.olivier.magicmanagerws.services;

import org.junit.Test;

import fr.free.francois.olivier.magicmanagerws.model.Type;
import fr.free.francois.olivier.magicmanagerws.services.TypeServiceImpl;

public class TestTypeService {

	private TypeServiceImpl typeService = new TypeServiceImpl();
	
	@Test
	public void testFindAllTypes() {
		typeService.findAll();
	}
	
	@Test
	public void testFindTypeById() {
		typeService.findById(3);
	}

	@Test
	public void testFindTypeByName() {
		typeService.findByName("%rit%");
	}
	
	@Test
	public void testAddUpdateAndDeleteType() {
		Type t = new Type("TestTypeNameFr", "TestTypeNameEn");
		typeService.saveType(t);
		t.setNameFr("newNameFr");
		t.setNameEn("newNameEn");
		typeService.updateType(t);
		typeService.deleteType(t);
	}
}
