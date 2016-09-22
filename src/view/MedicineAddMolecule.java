package view;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.Ctrl;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MedicineAddMolecule extends JFrame implements MyView{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	public MedicineAddMolecule() {
		setTitle("Mol\u00E9cule - Accueuil");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Gestions des mol\u00E9cules");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(133, 41, 146, 29);
		contentPane.add(lblNewLabel);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAjouter.setBounds(133, 81, 132, 23);
		contentPane.add(btnAjouter);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setBounds(133, 115, 131, 23);
		contentPane.add(btnSupprimer);
		
		JButton btnFermer = new JButton("Fermer");
		btnFermer.setBounds(335, 228, 89, 23);
		contentPane.add(btnFermer);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.setBounds(133, 149, 132, 23);
		contentPane.add(btnModifier);
		
		}

	@Override
	public void assignListener(Ctrl ctrl) {
		
		
	}
}
