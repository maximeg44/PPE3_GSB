package model;

import library.Persistence;

public abstract class fakePersistence extends Persistence{
	public static String searchIdMedicine(int id){
		return "medoc";
	}


}
