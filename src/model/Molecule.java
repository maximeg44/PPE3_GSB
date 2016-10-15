package model;

import java.sql.SQLException;
import java.util.ArrayList;

import library.Persistence;

public class Molecule {
	private String libelle;	
	public static ArrayList<Molecule> allTheMolecules = new ArrayList<Molecule>();
	
	public Molecule(){}
	/**
	 * Constructeur de la classe Molecule
	 * @param id identifiant de la nouvelle molecule
	 * @param libelle nom de la nouvelle molecule
	 */
	public Molecule(String libelle){
		super();
		this.libelle=libelle;
		allTheMolecules.add(this);
	}
	
	public String getLibelle(){
		return libelle;
	}
	
	public static Molecule getMoleculeById(int id) throws SQLException{
		Molecule found = getMoleculesByLibelle(Persistence.searchMoleculeById(id));
		return found;
	}
	
	public static Molecule getMoleculesByLibelle(String libelle){
		Molecule found = null;
		for (Molecule m : Molecule.allTheMolecules){
			if(m.getLibelle()==libelle)
				found=m;
		}
		return found;
	}
	
	public int getIdByName(String name) throws SQLException{
		int id = Persistence.searchIdByName(name);
		return id;
	}
	
}













