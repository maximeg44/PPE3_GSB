package model;

import java.util.ArrayList;

public class Molecules {
	private int id;
	private String libelle;
	private ArrayList<Medicine> mesMedicaments;
	
	public static ArrayList<Molecules> allTheMolecules = new ArrayList<Molecules>();
	
	public Molecules(){}
	/**
	 * Constructeur de la classe Forme
	 * @param id identifiant de la nouvelle molecule
	 * @param libelle nom de la nouvelle molecule
	 */
	public Molecules(int id, String libelle){
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
	
	public static Molecules getMoleculeById(int id){
		Molecules found = null;
		for (Molecules m : Molecules.allTheMolecules){
			if(m.getId()==id)
				found=m;
		}
		return found;
	}
	
	public static Molecules getMoleculesByLibelle(String libelle){
		Molecules found = null;
		for (Molecules m : Molecules.allTheMolecules){
			if(m.getLibelle()==libelle)
				found=m;
		}
		return found;
	}
	
}













