package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Ctrl;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Classe définissant la vue de recherche d'une molécule
 * @author maxime
 *
 */
public class MoleculeSearch extends JDialog implements MyView{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JTable tableMolecule;
	
	/**
	 * Méthode permettant de mettre à jour le contenu de la table
	 * @param dataTable le contenu de la table
	 * @param columnsTable le nom des colonnes de la table
	 */
	public static void setTable(String[][] dataTable, String[] columnsTable){
		DefaultTableModel model = new DefaultTableModel(dataTable, columnsTable);
		tableMolecule.setModel(model);
	}

	/**
	 * Create the dialog.
	 * @param dataTable le contenu de la table
	 * @param columnsTable le nom des colonnes de la table
	 */
	public MoleculeSearch(String[][] dataTable, String[] columnsTable) {
		setTitle("M\u00E9dicament - Rechercher");
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 434, 228);
			contentPanel.add(scrollPane);
			{
				tableMolecule = new JTable();
				tableMolecule.setEnabled(false);
				setTable(dataTable, columnsTable);
				scrollPane.setViewportView(tableMolecule);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
		MoleculeSearch.tableMolecule.addMouseListener(ctrl);	
	}
}
