package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Ctrl;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

/**
 * Classe définissant la vue d'ajout d'un médicament
 * @author xavier
 *
 */
public class MedicineAdd extends JDialog implements MyView{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnValider;
	private JButton btnAnnuler;
	private static JTextField txtNom;
	private static JComboBox<String> cbxFormes;
	private static JTextField txtBrevet;
	private static JComboBox<String> cbxPrincipesActifs;
	private static JComboBox<String> cbxExcipiant;

	/**
	 * Méthode statique permettant de réinitialiser les champs
	 */
	public static void init(){
		txtBrevet.setText("");
		txtNom.setText("");
	}
	
	/**
	 * Méthode statique permettant d'obtenir le contenu du champ texte nom
	 * @return le contenu du champ texte nom
	 */
	public static String getTxtName(){
		return txtNom.getText();
	}
	
	/**
	 * Méthode statique permettant d'obtenir la sélection de la liste déroulante formes
	 * @return la selection de la liste déroulante formes
	 */
	public static String getTxtForm(){
		return (String) cbxFormes.getSelectedItem();
	}
	/**
	 * Méthode statique permettant d'obtenir la sélection de la liste déroulante PA
	 * @return la selection de la liste déroulante Principe Actifs
	 */
	public static String getTxtPrincipeActif(){
		return (String) cbxPrincipesActifs.getSelectedItem();
	}
	/**
	 * Méthode statique permettant d'obtenir la sélection de la liste déroulante Excipiant
	 * @return la selection de la liste déroulante Excipiant
	 */
	public static String getTxtExcipiant(){
		return (String) cbxExcipiant.getSelectedItem();
	}
	/**
	 * Méthode statique permettant d'obtenir le contenu du champ texte date brevet
	 * @return le contenu du champ texte date brevet
	 */
	public static String getTxtPatentDate(){
		if(txtBrevet.getText().equals(""))
			return null;
		return txtBrevet.getText();
	}
	/**
	 * Méthode statique permettant de placer le curseur dans le champ texte nom
	 */
	public static void focusTxtName(){
		MedicineAdd.txtNom.requestFocus();
	}
	
	/**
	 * Create the dialog.
	 * @param forms les formes à intégrer dans la liste déroulante
	 */
	public MedicineAdd(String[] forms, String [] molecules) {
		setTitle("M\u00E9dicament - Ajouter");
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setBounds(83, 45, 50, 14);
		contentPanel.add(lblNom);
		
		txtNom = new JTextField();
		txtNom.setBounds(140, 42, 192, 20);
		contentPanel.add(txtNom);
		txtNom.setColumns(10);
		
		JLabel lblForme = new JLabel("Forme :");
		lblForme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForme.setBounds(63, 128, 70, 14);
		contentPanel.add(lblForme);
		
		cbxFormes = new JComboBox<String>(forms); //forms à remettre dans les paranthèses
		cbxFormes.setBounds(140, 125, 192, 20);
		contentPanel.add(cbxFormes);
		
		JLabel lblDateBrevet = new JLabel("Date brevet :");
		lblDateBrevet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateBrevet.setBounds(53, 87, 80, 14);
		contentPanel.add(lblDateBrevet);
		
		txtBrevet = new JTextField();
		txtBrevet.setBounds(140, 84, 192, 20);
		contentPanel.add(txtBrevet);
		txtBrevet.setColumns(10);
		
		cbxPrincipesActifs = new JComboBox<String>(molecules); //molecules à remettre dans les paranthèses
		cbxPrincipesActifs.setBounds(140, 156, 192, 20);
		contentPanel.add(cbxPrincipesActifs);
		
		JLabel lblPrincipeActif = new JLabel("Principe actif :");
		lblPrincipeActif.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrincipeActif.setBounds(63, 159, 70, 14);
		contentPanel.add(lblPrincipeActif);
		
		cbxExcipiant = new JComboBox<String>(molecules); //molecules à remettre dans les paranthèses
		cbxExcipiant.setBounds(140, 187, 192, 20);
		contentPanel.add(cbxExcipiant);
		
		JLabel lblExcipiant = new JLabel("Excipiant :");
		lblExcipiant.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExcipiant.setBounds(63, 190, 70, 14);
		contentPanel.add(lblExcipiant);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnValider = new JButton("Valider");
				buttonPane.add(btnValider);
				getRootPane().setDefaultButton(btnValider);
			}
			{
				btnAnnuler = new JButton("Annuler");
				buttonPane.add(btnAnnuler);
			}
			{
				JButton btnFermer = new JButton("Fermer");
				btnFermer.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnFermer);
			}
		}
	}


	@Override
	public void assignListener(Ctrl ctrl) {
		this.btnValider.setActionCommand("MedicineAdd_valider");
		this.btnValider.addActionListener(ctrl);
		this.btnAnnuler.setActionCommand("MedicineAdd_annuler");
		this.btnAnnuler.addActionListener(ctrl);
	}
}
