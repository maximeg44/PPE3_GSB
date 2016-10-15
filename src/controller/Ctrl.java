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
	 * Ce constructeur permet, en plus de créer une instance de Ctrl, de créer tous les objets de l'application à partir de la base de données
	 * @throws SQLException exception sql
	 * @throws NumberFormatException 
	 */
	public Ctrl() throws NumberFormatException, SQLException{
		//Création des objets Forme
		String[][] dataForm = null;
		try {
			dataForm = Persistence.load("forme");
		} catch (SQLException e) {
			String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataForm.length;i++){
			new Form(Integer.parseInt(dataForm[i][0]),dataForm[i][1]);
		}
		//Création des objets Molecules
		String[][] dataMolecules = null;
		try{
			dataMolecules = Persistence.load("molecule");
		} catch (SQLException e) {
			String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataMolecules.length;i++) {
			new Molecule(dataMolecules[i][1]);
		}
		//Création des objets Medicine
				String[][] dataMed = null;
				try {
					dataMed = Persistence.load("Medicament");
				} catch (SQLException e) {
					String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
					JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
				}
				for(int i=0;i<dataMed.length;i++){
					new Medicine(dataMed[i][1],Form.getFormById(Integer.parseInt(dataMed[i][5])),DatesConverter.USStringToDate(dataMed[i][2]),Molecule.getMoleculeById(Integer.parseInt(dataMed[i][6])));
				}				
		//Création des association Molécules/Médicaments
		/*String[][] dataExcipiant = null;
		try{
			dataExcipiant = Persistence.load("excipiant");
		} catch (SQLException e) {
			String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for (int i=0;i<dataExcipiant.length;i++) {
			Medicine.getMedicineById(Integer.parseInt(dataExcipiant[i][1])).addMyExcipiants(Molecule.getMoleculeById(Integer.parseInt(dataExcipiant[i][0])));
			}*/
	}

	/**
	 * Méthode déclanchée lors de clics sur les boutons de l'application
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		//Récupération de l'action
		String action = evt.getActionCommand();
		//Découpage et analyse de celle-ci
		String details[] = action.split("_");
		//who : QUI ? Quelle vue a effectué l'action ?
		String who = details[0];
		//what : QUOI ? Qu'est-ce-que cette vue souhaite faire ?
		String what = details[1];
		//switch-case de traitement des différents cas
		switch(who){
		case "MainView":
			switch(what){
			case "export":
				break;
			case "manuel":
				//Création de la vue d'accueil des médicaments
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
				//Création de la vue d'ajout d'un médicament
				MedicineAdd frame = new MedicineAdd(this.formsBox(),this.moleculesBox());
				//Assignation d'un observateur sur cette vue
				frame.assignListener(this);
				//Affichage de la vue
				frame.setVisible(true);
				break;
			case "rechercherModifier":				
				String[][] dataTable = this.medicinesTable();
				String[] dataColumns = {"Nom","Forme","Brevet"};
				//Création de la vue de recherche d'un médicament
				MedicineSearch frame1 = new MedicineSearch(dataTable,dataColumns);
				//Assignation d'un observateur sur cette vue
				frame1.assignListener(this);
				//Affichage de la vue
				frame1.setVisible(true);
				break;
			case "ajoutMolecule":
				//Création de la vue d'ajout des molécules
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
				//Récupération des informations saisies par l'utilisateur
				String nom = MedicineAdd.getTxtName();
				String actif = MedicineAdd.getTxtPrincipeActif();
				String excipiant = MedicineAdd.getTxtExcipiant();
				if(nom.equals("") && actif.equals(excipiant) ){
					JOptionPane.showMessageDialog(null,"Le nom du médicament à été omis ou alors le principes actif est également excipiant. Veillez à le saisir correctement.","Erreur Saisie",JOptionPane.WARNING_MESSAGE);
					MedicineAdd.focusTxtName();
				}
				else{
					String nomF = MedicineAdd.getTxtForm();
					Form forme = Form.getFormByName(nomF);
					String dateB = MedicineAdd.getTxtPatentDate();
					Molecule molecule = Molecule.getMoleculesByLibelle(actif);
					//Création du nouvel objet Medicine
					Medicine med = new Medicine(nom,forme,DatesConverter.FRStringToDate(dateB),molecule);
					//INSERT dans la BD
					try {
						Persistence.insertMedicine(med.getName(),med.getItsForm().getId(),med.getPatentDate(),med.getItsMolecule().getIdByName(med.getName()));
						//Message de confirmation pour l'utilisateur
						JOptionPane.showMessageDialog(null,"Le médicament a bien été ajouté","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
						//Réinitialisation des champs
						MedicineAdd.init();
					} catch (SQLException e) {
						String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
						JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "annuler":
				//Réinitialisation des champs
				MedicineAdd.init();
				break;
			}
			break;
		case "MedicineAddMolecule":
			switch(what){
			case "Ajouter":
				//création de la vu ajout d'une molécule
				MoleculeAdd frame = new MoleculeAdd();
				frame.assignListener(this);
				frame.setVisible(true);
				break;
			case "Modifier":
				//création de la vu ajout d'une molécule
				String[][] dataTable = this.moleculeTable();
				String[] dataColumns = {"Nom"};
				//Création de la vue de recherche d'un médicament
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
				//traitement de création d'une nouvelle molécule
				String name = MoleculeAdd.getMoleculeName();
				if(name.equals(""))
					JOptionPane.showMessageDialog(null, "Le nom de la molécule a été ommis veuillez un nom");
				if (Molecule.getMoleculesByLibelle(name) == null){
					Molecule newMol = new Molecule(name);
					try {
						Persistence.insertMolecule(newMol.getLibelle());
						JOptionPane.showMessageDialog(null,"La molécule a bien été enregistrée dans la base","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
					}catch (SQLException e){
						String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
						JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
					JOptionPane.showMessageDialog(null,"Le nom de cette molécule existe déjà, enregistrement en base impossible","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
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
				//Récupération des informations saisies par l'utilisateur
				String nom = MedicineChange.getTxtName();
				String nomF = MedicineChange.getTxtForm();
				Form forme = Form.getFormByName(nomF);
				String dateB = MedicineChange.getTxtPatentDate();
				//Récupération de l'objet Medicine à modifier
				Medicine med = Medicine.getMedicineByName(nom);
				//Modification de celui-ci à travers les setteurs
				med.setItsForm(forme);
				med.setPatentDate(DatesConverter.FRStringToDate(dateB));
				//UPDATE dans la BD
				try {
					Persistence.updateMedicine(med.getName(),med.getItsForm().getId(),med.getPatentDate());
					//Mise à jour de la jtable
					String[][] dataTable2 = this.medicinesTable();
					String[] dataColumns2 = {"Nom","Forme","Brevet"};
					MedicineSearch.setTable(dataTable2, dataColumns2);
					//Modification du bouton (annuler devient fermer)
					MedicineChange.btnAnnuler.setText("Fermer");
					//Message de confirmation pour l'utilisateur
					JOptionPane.showMessageDialog(null,"Le médicament a bien été modifié","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);					
				} catch (SQLException e) {
					String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
					JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			break;
		}	
	}

	/**
	 * Méthode permettant d'interroger le modèle afin de construire un tableau contenant tous les médicaments
	 * @return un tableau à deux dimensions contenant tous les médicaments (nom,idForme,dateBrevet)
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
			Paragraph chapterTitle = new Paragraph("Notice du médicament : "+name);
			Chapter chapter1 = new Chapter(chapterTitle, 1);
		    chapter1.setNumberDepth(0);
		    Paragraph Composition = new Paragraph("Composition du médicament : ");
		    Chapter chapter2 = new Chapter(Composition,2);
		    chapter2.setNumberDepth(1);
			document.add(new Paragraph("Ce médicament à pour principe actif : "+ actif+ "."));
			document.add(new Paragraph ("Ce médicament a pour excipient : "));
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
	 * Méthode permettant d'interroger le modèle afin de construire un tableau contenant toutes les formes
	 * @return un tableau à une dimension contenant toutes les formes (nom)
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
	 * Méthode déclanchée lors de clics souris sur l'application
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {
			//S'il s'agit d'un double-clic
			if(evt.getClickCount()==2){
				//Récupération de la jtable dans laquelle l'utilisateur a double-cliqué
				JTable laTable = (JTable)evt.getComponent();
				//Récupération du numéro de la ligne de cette jtable sur laquelle il a double-cliqué
				int row=laTable.rowAtPoint(evt.getPoint());
				//Récupération du médicament à partir de ces informations
				Medicine med = Medicine.getMedicineByName(laTable.getValueAt(row,0).toString());
				//Création d'un tableau contenant le détail du médicament
				String[] data = new String[3];
				data[0]=med.getName();
				data[1]=med.getItsForm().getName();
				data[2]=DatesConverter.dateToStringFR(med.getPatentDate());
				//Création de la vue de modification du médicament sélectionné dans la jtable
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
