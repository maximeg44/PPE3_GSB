package model;

import library.Persistence;

public abstract class fakePersistence extends Persistence{
	public static String searchIdMedicine(int id){
		return "medoc";
	}
	//Voir pour rajouter la fonctionnalité de renvoi d'une fake molécule pour le test test_getMoleculeById de classe mol


}
