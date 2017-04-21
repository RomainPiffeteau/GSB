package view;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Ctrl;
import javax.swing.JRadioButtonMenuItem;

public class MedicineEffect extends JFrame implements MyView{
	
	private JPanel fenetreEffet;
	private static JTextField txtNomEffet;
	private static JRadioButton un;
	private static JRadioButton deux;
	private static JRadioButton trois;
	private static JRadioButton quatre;
	private static JButton buttonCreation;
	
	/**
	 * Méthode statique permettant de réinitialiser les champs
	 */
	public static void init(){
		txtNomEffet.setText("");
		un.setSelected(true);
	}
	
	/**
	 * 	Instancie la fenêtre MedicineEffect
	 */
	public MedicineEffect()
	{
		setTitle("MedocLab - Ajout effet");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		fenetreEffet = new JPanel();
		fenetreEffet.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(fenetreEffet);
		fenetreEffet.setLayout(null);
		
		JLabel lblNom = new JLabel("Description :");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNom.setBounds(57, 45, 73, 14);
		fenetreEffet.add(lblNom);
		
		txtNomEffet = new JTextField();
		txtNomEffet.setBounds(140, 42, 192, 20);
		fenetreEffet.add(txtNomEffet);
		txtNomEffet.setColumns(10);
		
		JLabel lblNiveau = new JLabel("Niveau :");
		lblNiveau.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNiveau.setBounds(83, 95, 50, 14);
		fenetreEffet.add(lblNiveau);
		
		un = new JRadioButton("1");
		un.setSelected(true);
		un.setBounds(199, 91, 109, 23);
		fenetreEffet.add(un);
		
		deux = new JRadioButton("2");
		deux.setBounds(199, 117, 109, 23);
		fenetreEffet.add(deux);
		
		trois = new JRadioButton("3");
		trois.setBounds(199, 143, 109, 23);
		fenetreEffet.add(trois);
		
		quatre = new JRadioButton("4");
		quatre.setBounds(199, 169, 109, 23);
		fenetreEffet.add(quatre);
		
		buttonCreation = new JButton("Cr\u00E9er");
		buttonCreation.setBounds(140, 228, 89, 23);
		buttonCreation = new JButton("Cr\u00E9er");
		buttonCreation.setBounds(239, 228, 89, 23);
		fenetreEffet.add(buttonCreation);
		
		JButton buttonFermer = new JButton("Fermer");
		buttonFermer.setBounds(338, 228, 89, 23);
		fenetreEffet.add(buttonFermer);
		
		JButton buttonAnnuler = new JButton("Annuler");
		buttonAnnuler.setBounds(140, 228, 89, 23);
		fenetreEffet.add(buttonAnnuler);
		
		JRadioButtonMenuItem rbGroup = new JRadioButtonMenuItem("");
		rbGroup.setBounds(252, 86, 125, 125);
		fenetreEffet.add(rbGroup);
		ButtonGroup bg = new ButtonGroup();
		bg.add(un);
		bg.add(deux);
		bg.add(quatre);
		bg.add(trois);
		
		buttonCreation.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e) {
			}
		});
		
		buttonFermer.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		buttonAnnuler.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e) {
				init();
			}
		});
	}
	
	/**
	 * Méthode statique permettant d'obtenir le nom de l'effet renseigné dans le champ txtNomEffet
	 * @return String contenant le nom de l'effet
	 */
	public static String getTxtNomEffet(){
		 if(txtNomEffet.getText().equals(""))
		 	return null;
		 	return txtNomEffet.getText();
		}
	
	/**
	 * Méthode statique permettant de connaître l'id du grade en fonction du radio button sélectionné
	 * @return int correspondant au grade séléctionné 
	 */
	public static int getGrade(){
		 int choice = 0;
		 if(un.isSelected())
		 	choice = 1;
		 if(deux.isSelected())
		 	choice = 2;
		 if(trois.isSelected())
		 	choice = 3;
		 if(quatre.isSelected())
		 	choice = 4;
		 return choice;
		 }
	
	@Override
	public void assignListener(Ctrl ctrl) {
		MedicineEffect.buttonCreation.setActionCommand("MedicineEffect_valider");
		MedicineEffect.buttonCreation.addActionListener(ctrl);
	}
}
