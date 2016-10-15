package view;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import controller.Ctrl;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe définissant la vue d'ajout d'une molécule dans la BD
 * @author Maxime
 *
 */
public class MoleculeAdd extends JDialog implements MyView {
	private static final long serialVersionUID = 1L;
	private static JTextField moleculeName;
	private JPanel contentPane;
	private JButton btnValider;
	
	/**
	 * Methode statique permettant de retourner la saisie du nom de la molécule
	 * @return la saisi utilisateur du nom d'une nouvelle molécule
	 */
	public static String getMoleculeName(){
		return moleculeName.getText();
	}
	
	public MoleculeAdd() {
		setTitle("Ajout d'une mol\u00E9cule");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		moleculeName = new JTextField();
		moleculeName.setBounds(198, 81, 161, 20);
		getContentPane().add(moleculeName);
		moleculeName.setColumns(10);
		
		JLabel lblNomDeLa = new JLabel("Nom de la mol\u00E9cule : ");
		lblNomDeLa.setBounds(45, 84, 143, 14);
		getContentPane().add(lblNomDeLa);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAnnuler.setBounds(236, 228, 89, 23);
		getContentPane().add(btnAnnuler);
		
		btnValider = new JButton("Valider");
		btnValider.setBounds(335, 228, 89, 23);
		getContentPane().add(btnValider);
	}

	
	
	@Override
	public void assignListener(Ctrl ctrl) {
		// TODO Auto-generated method stub
		btnValider.setActionCommand("MoleculeAdd_Valider");
		btnValider.addActionListener(ctrl);
	}
}
