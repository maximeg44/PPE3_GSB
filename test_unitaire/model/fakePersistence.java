package model;

import library.Persistence;

public abstract class fakePersistence extends Persistence{
	public static String searchIdMedicine(int id){
		return "medoc";
	}
	//Voir pour rajouter la fonctionnalit� de renvoi d'une fake mol�cule pour le test test_getMoleculeById de classe mol


}
