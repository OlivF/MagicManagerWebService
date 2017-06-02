package fr.free.francois.olivier.magicmanagerws.main;

import java.util.List;
import java.util.Set;

import fr.free.francois.olivier.magicmanagerws.model.Type;
import fr.free.francois.olivier.magicmanagerws.services.TypeServiceImpl;

public class ConsoleTypes {
	
	public static void main(String[] args) {
		
		TypeServiceImpl typeService = new TypeServiceImpl();
		
		
		/* Afficher tous les types */
		Set<Type> types = typeService.findAll();
		System.out.println( "Tous les types :" );
		for (Type type : types) {
			System.out.println( type );
		}
		System.out.println( "------------------------------" );
		
		/* Afficher le type 3 */
		System.out.println( "Type id = 3 :" );
		Type type = typeService.findById(3);
		System.out.println( type );
		System.out.println( "------------------------------" );
		
		type.setNameFr("nameAfterUpdateFr");
		type.setNameEn("NameEnAfterUpdate");
		typeService.updateType(type);
		
		/* Afficher le type "rit" */
		System.out.println( "Type name = rit :" );
		List<Type> types4 = typeService.findByName("%rit%");
		for (Type type2 : types4) {
			System.out.println(type2);
		}
		System.out.println( "------------------------------" );
		
		/* Ajouter un type */
		Type t = new Type( "TestType2", "TypeTest2");
		typeService.saveType( t );
		
		/* Afficher tous les types */
		Set<Type> types2 = typeService.findAll();
		System.out.println( "Tous les types apr�s insertion :" );
		for (Type typ : types2) {
			System.out.println( typ );
		}
		System.out.println( "------------------------------" );
		
		typeService.deleteType(t);
		/* Afficher tous les types */
		Set<Type> types3 = typeService.findAll();
		System.out.println( "Tous les types apr�s suppression :" );
		for (Type typ : types3) {
			System.out.println( typ );
		}
		System.out.println( "------------------------------" );
		
	}
}
