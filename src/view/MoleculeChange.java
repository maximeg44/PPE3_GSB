package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Ctrl;

/**
 * Classe définissant la vue de modification d'une molécule
 * @author Maxime
 *
 */
public class MoleculeChange extends JDialog implements MyView {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnValider;
	public static JButton btnAnnuler;
	private static JTextField txtNom;
	
	/**
	 * Methode statique permettant d'obtenir le contenu du champ texte Libelle
	 * @return le contenu du champ texte libelle
	 */
	public static String getTxtName(){
		return txtNom.getText();
	}
	
	public MoleculeChange(String[] molecule) {
		setTitle("M\u00E9dicament - Modifier");
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
		txtNom.setEnabled(true);
		txtNom.setBounds(140, 42, 192, 20);
		contentPanel.add(txtNom);
		txtNom.setColumns(10);
		txtNom.setText(molecule[0]);
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
			btnAnnuler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			buttonPane.add(btnAnnuler);
		}
	}

	@Override
	public void assignListener(Ctrl ctrl) {
		// TODO Auto-generated method stub
		this.btnValider.setActionCommand("MoleculeChange_valider");
		this.btnValider.addActionListener(ctrl);
	}
	

}
