package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
/**
 * Classe d'objet metier MEDICAMENT
 * @author xavier
 *
 */
public class Medicine {
	/**
	 * L'id du m�dicament
	 */
	private int id;
	/**
	 * Le nom du m�dicament
	 */
	private String name;
	/**
	 * Forme pharmaceutique du m�dicament
	 */
	private Form itsForm;
	/**
	 * Date d'obtention du brevet pharmaceutique
	 */
	private GregorianCalendar patentDate;
	/**
	 * Mol�cule pharmaceutique du m�dicament
	 */
	private Molecules itsMolecule;
	/**
	 * Liste statique de tous les m�dicaments
	 */
	public static ArrayList<Medicine> allTheMedicines = new ArrayList<Medicine>();
	
	private ArrayList<Molecules> mesExcipiants;
	/**
	 * Construcuteur de la classe Medicament
	 * @param name nom du nouveau m�dicament
	 * @param itsForm forme pharmaceutique du nouveau m�dicament
	 * @param patentDate date d'obtention du brevet du nouveau m�dicament
	 */
	public Medicine(int id, String name, Form itsForm, GregorianCalendar patentDate, Molecules itsMolecule) {
		super();
		mesExcipiants = new ArrayList<Molecules>();
		this.id = id;
		this.name = name;
		this.itsForm = itsForm;
		this.patentDate = patentDate;
		this.itsMolecule = itsMolecule;
		allTheMedicines.add(this);
	}
	public void addMyExcipiants(Molecules mol){
		this.mesExcipiants.add(mol);
	}
	/**
	 * Accesseur en lecture sur le nom du m�dicament
	 * @return le nom du m�dicament
	 */
	public String getName() {
		return name;
	}
	/**
	 * Accesseur en lecture sur l'id du m�dicament
	 * @return l'id du m�dicament
	 */
	public int getId(){
		return id;
	}

	/**
	 * Accesseur en lecture sur la forme du m�dicament
	 * @return la forme du m�dicament
	 */
	public Form getItsForm() {
		return itsForm;
	}
	/**
	 * Accesseur en lecture sur le PA du m�dicament
	 */
	public Molecules getItsMolecule(){
		return itsMolecule;
	}
	/**
	 * Accesseur en lecture sur la date d'obtention du brevet du m�dicament
	 * @return la date d'obtention du brevet du m�dicament
	 */
	public GregorianCalendar getPatentDate() {
		return patentDate;
	}
	
	/**
	 * M�thode permettant de rechercher parmi tous les m�dicaments
	 * celui ayant un nom correspondant � celui pass� en param�tre
	 * @param name le nom � rechercher
	 * @return le Medicament correspondant
	 */
	public static Medicine getMedicineByName(String name) {
		Medicine found = null;
		for(Medicine m : Medicine.allTheMedicines){
			if(m.getName().equals(name))
				found=m;
		}
		return found;
	}
	public static Medicine getMedicineById(int id){
		Medicine found = null;
		for(Medicine m : Medicine.allTheMedicines){
			if(m.getId() == id)
				found=m;
		}
		return found;
	}

	/**
	 * Accesseur en �criture sur la forme du m�dicament
	 * @param itsForm la nouvelle forme du m�dicament
	 */
	public void setItsForm(Form itsForm) {
		this.itsForm = itsForm;
	}

	/**
	 * Accesseur en �criture sur la date d'obtention du brevet du m�dicament
	 * @param patentDate la nouvelle date d'obtention du brevet du m�dicament
	 */
	public void setPatentDate(GregorianCalendar patentDate) {
		this.patentDate = patentDate;
	}

	
}
