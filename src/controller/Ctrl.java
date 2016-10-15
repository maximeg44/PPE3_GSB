package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import library.DatesConverter;
import library.Persistence;
import model.Form;
import model.Medicine;
import model.Molecule;
import view.MedicineAdd;
import view.MedicineAddMolecule;
import view.MedicineChange;
import view.MedicineHome;
import view.MedicineSearch;
import view.MoleculeAdd;
import view.MoleculeSearch;
/**
 * Classe CONTROLEUR
 * @author Maxime
 *
 */
public class Ctrl implements ActionListener, MouseListener{
	
	/**
	 * Constructeur de la classe Ctrl
	 * Ce constructeur permet, en plus de cr�er une instance de Ctrl, de cr�er tous les objets de l'application � partir de la base de donn�es
	 * @throws SQLException exception sql
	 * @throws NumberFormatException 
	 */
	public Ctrl() throws NumberFormatException, SQLException{
		//Cr�ation des objets Forme
		String[][] dataForm = null;
		try {
			dataForm = Persistence.load("forme");
		} catch (SQLException e) {
			String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataForm.length;i++){
			new Form(Integer.parseInt(dataForm[i][0]),dataForm[i][1]);
		}
		//Cr�ation des objets Molecules
		String[][] dataMolecules = null;
		try{
			dataMolecules = Persistence.load("molecule");
		} catch (SQLException e) {
			String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataMolecules.length;i++) {
			new Molecule(dataMolecules[i][1]);
		}
		//Cr�ation des objets Medicine
				String[][] dataMed = null;
				try {
					dataMed = Persistence.load("Medicament");
				} catch (SQLException e) {
					String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
					JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
				}
				for(int i=0;i<dataMed.length;i++){
					new Medicine(dataMed[i][1],Form.getFormById(Integer.parseInt(dataMed[i][5])),DatesConverter.USStringToDate(dataMed[i][2]),Molecule.getMoleculeById(Integer.parseInt(dataMed[i][6])));
				}				
		//Cr�ation des association Mol�cules/M�dicaments
		/*String[][] dataExcipiant = null;
		try{
			dataExcipiant = Persistence.load("excipiant");
		} catch (SQLException e) {
			String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for (int i=0;i<dataExcipiant.length;i++) {
			Medicine.getMedicineById(Integer.parseInt(dataExcipiant[i][1])).addMyExcipiants(Molecule.getMoleculeById(Integer.parseInt(dataExcipiant[i][0])));
			}*/
	}

	/**
	 * M�thode d�clanch�e lors de clics sur les boutons de l'application
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		//R�cup�ration de l'action
		String action = evt.getActionCommand();
		//D�coupage et analyse de celle-ci
		String details[] = action.split("_");
		//who : QUI ? Quelle vue a effectu� l'action ?
		String who = details[0];
		//what : QUOI ? Qu'est-ce-que cette vue souhaite faire ?
		String what = details[1];
		//switch-case de traitement des diff�rents cas
		switch(who){
		case "MainView":
			switch(what){
			case "export":
				break;
			case "manuel":
				//Cr�ation de la vue d'accueil des m�dicaments
				MedicineHome frame = new MedicineHome();
				//Assignation d'un observateur sur cette vue
				frame.assignListener(this);
				//Affichage de la vue
				frame.setVisible(true);
				break;
			}
			break;
		case "MedicineHome":
			switch(what){
			case "ajout":
				//Cr�ation de la vue d'ajout d'un m�dicament
				MedicineAdd frame = new MedicineAdd(this.formsBox(),this.moleculesBox());
				//Assignation d'un observateur sur cette vue
				frame.assignListener(this);
				//Affichage de la vue
				frame.setVisible(true);
				break;
			case "rechercherModifier":				
				String[][] dataTable = this.medicinesTable();
				String[] dataColumns = {"Nom","Forme","Brevet"};
				//Cr�ation de la vue de recherche d'un m�dicament
				MedicineSearch frame1 = new MedicineSearch(dataTable,dataColumns);
				//Assignation d'un observateur sur cette vue
				frame1.assignListener(this);
				//Affichage de la vue
				frame1.setVisible(true);
				break;
			case "ajoutMolecule":
				//Cr�ation de la vue d'ajout des mol�cules
				MedicineAddMolecule frame2 = new MedicineAddMolecule();
				//Assigation d'un observateur sur cette vue
				frame2.assignListener(this);
				//affichage de la vue
				frame2.setVisible(true);
				break;
			}
			break;
		case "MedicineAdd":
			switch(what){
			case "valider":
				//R�cup�ration des informations saisies par l'utilisateur
				String nom = MedicineAdd.getTxtName();
				String actif = MedicineAdd.getTxtPrincipeActif();
				String excipiant = MedicineAdd.getTxtExcipiant();
				if(nom.equals("") && actif.equals(excipiant) ){
					JOptionPane.showMessageDialog(null,"Le nom du m�dicament � �t� omis ou alors le principes actif est �galement excipiant. Veillez � le saisir correctement.","Erreur Saisie",JOptionPane.WARNING_MESSAGE);
					MedicineAdd.focusTxtName();
				}
				else{
					String nomF = MedicineAdd.getTxtForm();
					Form forme = Form.getFormByName(nomF);
					String dateB = MedicineAdd.getTxtPatentDate();
					Molecule molecule = Molecule.getMoleculesByLibelle(actif);
					//Cr�ation du nouvel objet Medicine
					Medicine med = new Medicine(nom,forme,DatesConverter.FRStringToDate(dateB),molecule);
					//INSERT dans la BD
					try {
						Persistence.insertMedicine(med.getName(),med.getItsForm().getId(),med.getPatentDate(),med.getItsMolecule().getIdByName(med.getName()));
						//Message de confirmation pour l'utilisateur
						JOptionPane.showMessageDialog(null,"Le m�dicament a bien �t� ajout�","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
						//R�initialisation des champs
						MedicineAdd.init();
					} catch (SQLException e) {
						String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
						JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "annuler":
				//R�initialisation des champs
				MedicineAdd.init();
				break;
			}
			break;
		case "MedicineAddMolecule":
			switch(what){
			case "Ajouter":
				//cr�ation de la vu ajout d'une mol�cule
				MoleculeAdd frame = new MoleculeAdd();
				frame.assignListener(this);
				frame.setVisible(true);
				break;
			case "Modifier":
				//cr�ation de la vu ajout d'une mol�cule
				String[][] dataTable = this.moleculeTable();
				String[] dataColumns = {"Nom"};
				//Cr�ation de la vue de recherche d'un m�dicament
				MoleculeSearch frame1 = new MoleculeSearch(dataTable,dataColumns);
				//Assignation d'un observateur sur cette vue
				frame1.assignListener(this);
				//Affichage de la vue
				frame1.setVisible(true);
				break;
			}
		case "MoleculeAdd":
			switch(what){
			case "Valider":
				//traitement de cr�ation d'une nouvelle mol�cule
				String name = MoleculeAdd.getMoleculeName();
				if(name.equals(""))
					JOptionPane.showMessageDialog(null, "Le nom de la mol�cule a �t� ommis veuillez un nom");
				if (Molecule.getMoleculesByLibelle(name) == null){
					Molecule newMol = new Molecule(name);
					try {
						Persistence.insertMolecule(newMol.getLibelle());
						JOptionPane.showMessageDialog(null,"La mol�cule a bien �t� enregistr�e dans la base","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
					}catch (SQLException e){
						String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
						JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null,"Le nom de cette mol�cule existe d�j�, enregistrement en base impossible","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
			}
			break;	
		case "MedicineSearch":
				break;
		case "MedicineChange":
			switch(what){
			case "PDF":
				String name = MedicineChange.getTxtName();
				Medicine medicament=null;
				for (Medicine med : Medicine.allTheMedicines) {
					if(med.getName().equals(name)){
						medicament = med;
					}
				}
				String nameMolecule = medicament.getItsMolecule().getLibelle();
				String form = medicament.getItsForm().getName();
				ArrayList<Molecule> excipiants = new ArrayList<Molecule>();
				excipiants = medicament.getExcipiants();

				
				this.createPdf(name, nameMolecule, excipiants, form);
				break;
			case "valider":
				//R�cup�ration des informations saisies par l'utilisateur
				String nom = MedicineChange.getTxtName();
				String nomF = MedicineChange.getTxtForm();
				Form forme = Form.getFormByName(nomF);
				String dateB = MedicineChange.getTxtPatentDate();
				//R�cup�ration de l'objet Medicine � modifier
				Medicine med = Medicine.getMedicineByName(nom);
				//Modification de celui-ci � travers les setteurs
				med.setItsForm(forme);
				med.setPatentDate(DatesConverter.FRStringToDate(dateB));
				//UPDATE dans la BD
				try {
					Persistence.updateMedicine(med.getName(),med.getItsForm().getId(),med.getPatentDate());
					//Mise � jour de la jtable
					String[][] dataTable2 = this.medicinesTable();
					String[] dataColumns2 = {"Nom","Forme","Brevet"};
					MedicineSearch.setTable(dataTable2, dataColumns2);
					//Modification du bouton (annuler devient fermer)
					MedicineChange.btnAnnuler.setText("Fermer");
					//Message de confirmation pour l'utilisateur
					JOptionPane.showMessageDialog(null,"Le m�dicament a bien �t� modifi�","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);					
				} catch (SQLException e) {
					String message = "Erreur lors de l'echange avec la base de donn�es. L'application a rencontr�e l'erreur : "+e.getMessage();
					JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			break;
		}	
	}

	/**
	 * M�thode permettant d'interroger le mod�le afin de construire un tableau contenant tous les m�dicaments
	 * @return un tableau � deux dimensions contenant tous les m�dicaments (nom,idForme,dateBrevet)
	 */
	private String[][] medicinesTable() {
		int i=0;
		String[][] liste=new String[Medicine.allTheMedicines.size()][3];
		for(Medicine m : Medicine.allTheMedicines){
			liste[i][0]=m.getName();
			liste[i][1]=m.getItsForm().getName();
			liste[i][2]=DatesConverter.dateToStringFR(m.getPatentDate());
			i++;
		}
		return liste;
	}
	private String[][] moleculeTable(){
		int i=0;
		String[][] liste = new String[Molecule.allTheMolecules.size()][1];
		for (Molecule mol : Molecule.allTheMolecules) {
			liste[i][0]=mol.getLibelle();
			i++;
		}
		return liste;
	}
	private void createPdf(String name, String actif, ArrayList<Molecule> excipiant, String form){
		Document document = new Document();
		try{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Notice "+name+" .pdf"));
			document.open();
			document.addAuthor(Persistence.getComputerFullName());
			document.addTitle(name);
			Paragraph chapterTitle = new Paragraph("Notice du m�dicament : "+name);
			Chapter chapter1 = new Chapter(chapterTitle, 1);
		    chapter1.setNumberDepth(0);
		    Paragraph Composition = new Paragraph("Composition du m�dicament : ");
		    Chapter chapter2 = new Chapter(Composition,2);
		    chapter2.setNumberDepth(1);
			document.add(new Paragraph("Ce m�dicament � pour principe actif : "+ actif+ "."));
			document.add(new Paragraph ("Ce m�dicament a pour excipient : "));
			for (Molecule mol : excipiant) {
				document.add(new Paragraph (mol.getLibelle()));
			}
			document.close();
			writer.close();
		} catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	}

	/**
	 * M�thode permettant d'interroger le mod�le afin de construire un tableau contenant toutes les formes
	 * @return un tableau � une dimension contenant toutes les formes (nom)
	 */
	private String[] formsBox(){
		int i=0;
		String[] liste=new String[Form.allTheForms.size()];
		for(Form l : Form.allTheForms){
			liste[i]=l.getName();
			i++;
		}
		return liste;
	}
	private String[] moleculesBox(){
		int i=0;
		String[] liste = new String[Molecule.allTheMolecules.size()];
		for (Molecule m : Molecule.allTheMolecules){
			liste[i]=m.getLibelle();
			i++;
		}
		return liste;
	}

	/**
	 * M�thode d�clanch�e lors de clics souris sur l'application
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {
			//S'il s'agit d'un double-clic
			if(evt.getClickCount()==2){
				//R�cup�ration de la jtable dans laquelle l'utilisateur a double-cliqu�
				JTable laTable = (JTable)evt.getComponent();
				//R�cup�ration du num�ro de la ligne de cette jtable sur laquelle il a double-cliqu�
				int row=laTable.rowAtPoint(evt.getPoint());
				//R�cup�ration du m�dicament � partir de ces informations
				Medicine med = Medicine.getMedicineByName(laTable.getValueAt(row,0).toString());
				//Cr�ation d'un tableau contenant le d�tail du m�dicament
				String[] data = new String[3];
				data[0]=med.getName();
				data[1]=med.getItsForm().getName();
				data[2]=DatesConverter.dateToStringFR(med.getPatentDate());
				//Cr�ation de la vue de modification du m�dicament s�lectionn� dans la jtable
				MedicineChange frame = new MedicineChange(this.formsBox(),data);
				//Assignation d'un observateur sur cette vue
				frame.assignListener(this);
				//Affichage de la vue
				frame.setVisible(true);
			}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
