package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import controller.Ctrl;
import library.Persistence;
import model.Effect;
import model.Medicine;
import model.MyTableModel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTable;
/**
 * Classe d�finissant la vue de modification d'un m�dicament
 * @author xavier
 *
 */
public class MedicineChange extends JDialog implements MyView{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnValider;
	public static JButton btnAnnuler;
	private static JTextField txtNom;
	private static JComboBox<String> cbxFormes;
	private static JTextField txtBrevet;
	private JTable table;
	private static Medicine medicament;
	/**
	 * M�thode statique permettant d'obtenir le contenu du champ texte nom
	 * @return le contenu du champ texte nom
	 */
	public static String getTxtName(){
		return txtNom.getText();
	}
	/**
	 * M�thode statique permettant d'obtenir la s�lection de la liste d�roulante forme
	 * @return la selection de la liste d�roulante forme
	 */
	public static String getTxtForm(){
		return (String) cbxFormes.getSelectedItem();
	}
	/**
	 * M�thode statique permettant d'obtenir le contenu du champ texte date brevet
	 * @return le contenu du champ texte date brevet
	 */
	public static String getTxtPatentDate(){
		if(txtBrevet.getText().equals(""))
			return null;
		return txtBrevet.getText();
	}
	
	/**
	 * Create the dialog.
	 * @param forms les formes � int�grer dans la liste d�roulante
	 * @param medicine le d�tail du m�dicament � modifier
	 * @throws SQLException 
	 */
	public MedicineChange(String[] forms, String[] medicine, String[][] effects) throws SQLException {
		setTitle("M\u00E9dicament - Modifier");
		setModal(true);
		setBounds(100, 100, 450, 429);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNom = new JLabel("Nom :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setBounds(83, 45, 50, 14);
		contentPanel.add(lblNom);
		
		txtNom = new JTextField();
		txtNom.setEnabled(false);
		txtNom.setBounds(140, 42, 192, 20);
		contentPanel.add(txtNom);
		txtNom.setColumns(10);
		txtNom.setText(medicine[0]);
		
		JLabel lblForme = new JLabel("Forme :");
		lblForme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForme.setBounds(63, 107, 70, 14);
		contentPanel.add(lblForme);
		
		cbxFormes = new JComboBox<String>(/*forms*/);
		cbxFormes.setBounds(140, 104, 192, 20);
		contentPanel.add(cbxFormes);
		cbxFormes.setSelectedItem(medicine[1]);
		
		JLabel lblDateBrevet = new JLabel("Date brevet :");
		lblDateBrevet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDateBrevet.setBounds(53, 76, 80, 14);
		contentPanel.add(lblDateBrevet);
		
		txtBrevet = new JTextField();
		txtBrevet.setBounds(140, 73, 192, 20);
		contentPanel.add(txtBrevet);
		txtBrevet.setColumns(10);
		txtBrevet.setText(medicine[2]);
		
		table = new JTable();
		this.setJTable();
		table.setBounds(10, 157, 414, 201);
		contentPanel.add(table);
		
		/*TableModel myData = new MyTableModel();
		table.setModel(myData);*/
		
		JLabel lblEffets = new JLabel("Effet(s) :");
		lblEffets.setHorizontalAlignment(SwingConstants.CENTER);
		lblEffets.setBounds(10, 135, 422, 14);
		contentPanel.add(lblEffets);
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
				btnAnnuler.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				buttonPane.add(btnAnnuler);
			}
		}
	}

	/**
	 * M�thode permettant de cr�er le tableau des effets et des cases � cocher
	 */
	public void setJTable()
	{
		ArrayList<Effect> tousLesEffets = new ArrayList<Effect>(); 
		tousLesEffets = Effect.allTheEffects;
		String col[] = {"Description","Grade",""};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		tableModel.addRow(col);
		for(int i = 0; i<tousLesEffets.size();i++)
		{
			JCheckBox CB = new JCheckBox();
			Object[] test;
			test = new Object[]{tousLesEffets.get(i).getName(),String.valueOf(tousLesEffets.get(i).getGrade()),CB};
			tableModel.addRow(test);
		}
		
		table = new JTable(tableModel);
	}
	
	/**
	 * M�thode retournant l'id du m�dicament en premi�re position et les id de chaque effet coch�s par case suivante
	 * @return tableau de int
	 */
	public static int[] getMedicEffects()
	{
		int[] listeDesEffets;
		if(getNumberCheckedEffects()==0){
			listeDesEffets = new int[2];
			listeDesEffets[1] = 1;
		} else
			listeDesEffets = new int[getNumberCheckedEffects()+1];
		try {
			int medicId = Persistence.getIdFromMedic(medicament.getName());
			listeDesEffets[0] = medicId;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(getNumberCheckedEffects()>0){
			for(int i=1;i<listeDesEffets.length;i++){
				//Mettre dans le tableau les id des cases coch�es
			}
		}
		return listeDesEffets;
	}
	
	/**
	 * M�thode retournant le nombre d'effets coch�s
	 * @return int nombre d'effets coch�s
	 */
	private static int getNumberCheckedEffects(){
		//r�cup�rer le nombre de cases coch�es
		return 0;
	}
	
	@Override
	public void assignListener(Ctrl ctrl) {
		this.btnValider.setActionCommand("MedicineChange_valider");
		this.btnValider.addActionListener(ctrl);
	}
}