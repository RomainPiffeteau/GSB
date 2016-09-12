package view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Ctrl;

public class MedicineEffect extends JFrame implements MyView{
	
	private JPanel fenetreEffet;
	private static JTextField txtNomEffet;
	
public MedicineEffect()
{
	setTitle("MedocLab - Accueil");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	fenetreEffet = new JPanel();
	fenetreEffet.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(fenetreEffet);
	fenetreEffet.setLayout(null);
	
	JLabel lblNom = new JLabel("Nom :");
	lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
	lblNom.setBounds(83, 45, 50, 14);
	fenetreEffet.add(lblNom);
	
	txtNomEffet = new JTextField();
	txtNomEffet.setBounds(140, 42, 192, 20);
	fenetreEffet.add(txtNomEffet);
	txtNomEffet.setColumns(10);
	
	


	
}

@Override
public void assignListener(Ctrl ctrl) {
	// TODO Auto-generated method stub
	
}

}
