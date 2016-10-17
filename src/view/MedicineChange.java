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
 * Classe définissant la vue de modification d'un médicament
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
	private String[][] effects;
	private String nomMedic;
	/**
	 * Méthode statique permettant d'obtenir le contenu du champ texte nom
	 * @return le contenu du champ texte nom
	 */
	public static String getTxtName(){
		return txtNom.getText();
	}
	/**
	 * Méthode statique permettant d'obtenir la sélection de la liste déroulante forme
	 * @return la selection de la liste déroulante forme
	 */
	public static String getTxtForm(){
		return (String) cbxFormes.getSelectedItem();
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
	 * Create the dialog.
	 * @param forms les formes à intégrer dans la liste déroulante
	 * @param medicine le détail du médicament à modifier
	 * @throws SQLException 
	 */
	public MedicineChange(String[] forms, String[] medicine, String[][] effects) throws SQLException {
		this.effects = effects;
		nomMedic = medicine[0];
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
		table.setBounds(10, 157, 422, 201);
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

	public void setJTable()
	{
		ArrayList<Effect> tousLesEffets = new ArrayList<Effect>(); 
		tousLesEffets = Effect.allTheEffects;
		Object[] col = {"Description","Grade",""};
		Object[][] test = new Object[tousLesEffets.size()][3];
		try {
			for(int i = 1; i<tousLesEffets.size();i++)
			{
				test[i-1][0] = tousLesEffets.get(i).getName();
				test[i-1][1] = String.valueOf(tousLesEffets.get(i).getGrade());
				test[i-1][2] = compareEffects(tousLesEffets.get(i).getId(),Persistence.getIdFromMedic(nomMedic));
				System.out.println(i);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultTableModel tableModel = new DefaultTableModel(test, col);
		table = new JTable(tableModel){

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };;
		
		
		
		
	}
	
	private boolean compareEffects(int idEffect, int idMedic)
	{
		boolean find = false;
		for(int i =0; i<this.effects.length;i++)
		{
			if(idMedic == Integer.parseInt(this.effects[i][0]))
			{
				for(int j = 0;j<this.effects[i].length;j++)
				{
					if(idEffect == Integer.parseInt(this.effects[i][j]))
					{
						find = true;
					}
				}
			}
		}
		return find;
	}
	
	public static int[] getMedicEffects()
	{
		int[] listeDesEffets = new int[2];
		try {
			int medicId = Persistence.getIdFromMedic(medicament.getName());
			listeDesEffets[0] = medicId;
			listeDesEffets[1] = 2;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeDesEffets;
		
		
		
		
		
	}
	
	@Override
	public void assignListener(Ctrl ctrl) {
		this.btnValider.setActionCommand("MedicineChange_valider");
		this.btnValider.addActionListener(ctrl);
	}
}