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
	
	JRadioButton deux = new JRadioButton("2");
	deux.setBounds(158, 117, 109, 23);
	fenetreEffet.add(deux);
	
	JRadioButton trois = new JRadioButton("3");
	trois.setBounds(158, 143, 109, 23);
	fenetreEffet.add(trois);
	
	JRadioButton quatre = new JRadioButton("4");
	quatre.setBounds(158, 169, 109, 23);
	fenetreEffet.add(quatre);
	
	JButton buttonCreation = new JButton("Cr\u00E9er");
	buttonCreation.setBounds(302, 213, 89, 23);
	fenetreEffet.add(buttonCreation);
	
	JRadioButton un = new JRadioButton("1");
	un.setBounds(158, 91, 109, 23);
	fenetreEffet.add(un);
	
	JButton buttonAnnuler = new JButton("Annuler");
	buttonAnnuler.setBounds(178, 213, 89, 23);
	fenetreEffet.add(buttonAnnuler);
	ButtonGroup bg = new ButtonGroup();
	
	buttonCreation.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e) {
		dispose();
			}
			});
	
	buttonAnnuler.addActionListener(new ActionListener()
	{public void actionPerformed(ActionEvent e) {
dispose();
	}
	});

	
}

@Override
public void assignListener(Ctrl ctrl) {
	// TODO Auto-generated method stub
	
}
}