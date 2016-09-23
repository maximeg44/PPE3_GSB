package model;

import java.util.ArrayList;

public class Molecule {
	private int id;
	private String libelle;
	private ArrayList<Medicine> mesMedicaments;
	
	public static ArrayList<Molecule> allTheMolecules = new ArrayList<Molecule>();
	
	public Molecule(){}
	/**
	 * Constructeur de la classe Molecule
	 * @param id identifiant de la nouvelle molecule
	 * @param libelle nom de la nouvelle molecule
	 */
	public Molecule(int id, String libelle){
		super();
		mesMedicaments = new ArrayList<Medicine>();
		this.id=id;
		this.libelle=libelle;
		allTheMolecules.add(this);
	}
	
	public void addMyMedicament (Medicine med){
		this.mesMedicaments.add(med);
	}
	
	public int getId(){
		return id;
	}
	
	public String getLibelle(){
		return libelle;
	}
	
	public static Molecule getMoleculeById(int id){
		Molecule found = null;
		for (Molecule m : Molecule.allTheMolecules){
			if(m.getId()==id)
				found=m;
		}
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
	
}













