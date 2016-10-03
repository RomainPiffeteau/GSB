package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import library.DatesConverter;
import library.Persistence;
import model.Effect;
import model.Form;
import model.Medicine;
import view.MedicineAdd;
import view.MedicineChange;
import view.MedicineEffect;
import view.MedicineHome;
import view.MedicineSearch;
/**
 * Classe CONTROLEUR
 * @author xavier
 *
 */
public class Ctrl implements ActionListener, MouseListener{
	
	/**
	 * Constructeur de la classe Ctrl
	 * Ce constructeur permet, en plus de créer une instance de Ctrl, de créer tous les objets de l'application à partir de la base de données
	 */
	public Ctrl(){
		//Création des objets Forme
		String[][] dataForm = null;
		try {
			dataForm = Persistence.load("forme");
		} catch (SQLException e) {
			String message = "Erreur lors de l'échange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataForm.length;i++){
			new Form(Integer.parseInt(dataForm[i][0]),dataForm[i][1]);
		}
		
		//Création des objets Effet
		String[][] dataEffect = null;
		try {
			dataEffect = Persistence.load("effet");
		} catch (SQLException e) {
			String message = "Erreur lors de l'échange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataEffect.length;i++){
			new Effect(Integer.parseInt(dataEffect[i][0]),Integer.parseInt(dataEffect[i][1]),dataEffect[i][2]);
		}
		if(Effect.getEffectById(0)==null)
			Effect.allTheEffects.add(new Effect(0, 1, "Pas d'effet"));
		
		//Création des objets Medicine
		String[][] dataMed = null;
		try {
			dataMed = Persistence.load("medicament");
		} catch (SQLException e) {
			String message = "Erreur lors de l'échange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
			JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
		}
		for(int i=0;i<dataMed.length;i++){
			if(dataMed[i][6]!=null)
				new Medicine(dataMed[i][1],Form.getFormById(Integer.parseInt(dataMed[i][5])),DatesConverter.USStringToDate(dataMed[i][2]), Effect.getEffectById(Integer.parseInt(dataMed[i][6])) );
			else
				new Medicine(dataMed[i][1],Form.getFormById(Integer.parseInt(dataMed[i][5])),DatesConverter.USStringToDate(dataMed[i][2]), Effect.getEffectById(0));
		}
	}

	/**
	 * Méthode déclenchée lors de clics sur les boutons de l'application
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		//Récupération de l'action
		String action = evt.getActionCommand();
		//Découpage et analyse de celle-ci
		String details[] = action.split("_");
		//who : QUI ? Quelle vue a effectué l'action ?
		String who = details[0];
		//what : QUOI ? Qu'est-ce-que cette vue souhaite faire ?
		String what = details[1];
		//switch-case de traitement des différents cas
		switch(who){
		case "MainView":
			switch(what){
			case "export":
				break;
			case "manuel":
				//Création de la vue d'accueil des médicaments
				MedicineHome frame = new MedicineHome();
				//Assignation d'un observateur sur cette vue
				frame.assignListener(this);
				//Affichage de la vue
				frame.setVisible(true);
				break;
			case "effets":
				//Création de la vue d'accueil des effets
				MedicineEffect frameEffects = new MedicineEffect();
				//Assignation d'un observateur sur cette vue
				frameEffects.assignListener(this);
				//affichage de la vue
				frameEffects.setVisible(true);
				break;
			}
			break;
		case "MedicineHome":
			switch(what){
			case "ajout":
				//Création de la vue d'ajout d'un médicament
				MedicineAdd frame = new MedicineAdd(this.formsBox(), this.effectsBox());
				//Assignation d'un observateur sur cette vue
				frame.assignListener(this);
				//Affichage de la vue
				frame.setVisible(true);
				break;
			case "rechercherModifier":				
				String[][] dataTable = this.medicinesTable();
				String[] dataColumns = {"Nom","Forme","Brevet"};
				//Création de la vue de recherche d'un médicament
				MedicineSearch frame1 = new MedicineSearch(dataTable,dataColumns);
				//Assignation d'un observateur sur cette vue
				frame1.assignListener(this);
				//Affichage de la vue
				frame1.setVisible(true);
				break;
			}
			break;
		case "MedicineEffect":
			switch(what){
			case "valider":
				//Récupération des informations saisies par l'utilisateur
				int grade = MedicineEffect.getGrade();
				if(grade>4 || grade<1)
					JOptionPane.showMessageDialog(null,"Le grade de sévérité est incorrect.","Erreur Selection",JOptionPane.WARNING_MESSAGE);
				else{
					Effect effect = new Effect(Effect.getNextId(), grade, MedicineEffect.getTxtNomEffet());
					//INSERT dans la BD
					try {
						Persistence.insertEffect(grade, MedicineEffect.getTxtNomEffet());
						//Message de confirmation pour l'utilisateur
						JOptionPane.showMessageDialog(null,"L'effet a bien été ajouté","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
						//Réinitialisation des champs
						MedicineEffect.init();
					} catch (SQLException e) {
						String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
						JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "annuler":
				//Réinitialisation des champs
				MedicineEffect.init();
				break;
			}
			break;
			
		case "MedicineAdd":
			switch(what){
			case "valider":
				//Récupération des informations saisies par l'utilisateur
				String nom = MedicineAdd.getTxtName();
				if(nom.equals("")){
					JOptionPane.showMessageDialog(null,"Le nom du médicament à été omis. Veillez à le saisir correctement.","Erreur Saisie",JOptionPane.WARNING_MESSAGE);
					MedicineAdd.focusTxtName();
				}
				else{
					String nomF = MedicineAdd.getTxtForm();
					Form forme = Form.getFormByName(nomF);
					String dateB = MedicineAdd.getTxtPatentDate();
					String nomEffet = MedicineAdd.getTxtEffect();
					Effect effet = Effect.getEffectByName(nomEffet);
					//Création du nouvel objet Medicine
					Medicine med = new Medicine(nom,forme,DatesConverter.FRStringToDate(dateB), effet);
					//INSERT dans la BD
					try {
						Persistence.insertMedicine(med.getName(),med.getItsForm().getId(),med.getPatentDate(),med.getItsEffect().getId());
						//Message de confirmation pour l'utilisateur
						JOptionPane.showMessageDialog(null,"Le médicament a bien été ajouté","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);
						//Réinitialisation des champs
						MedicineAdd.init();
					} catch (SQLException e) {
						String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
						JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
			case "annuler":
				//Réinitialisation des champs
				MedicineAdd.init();
				break;
			}
			break;
		case "MedicineSearch":
				break;
		case "MedicineChange":
			switch(what){
			case "valider":
				//Récupération des informations saisies par l'utilisateur
				String nom = MedicineChange.getTxtName();
				String nomF = MedicineChange.getTxtForm();
				Form forme = Form.getFormByName(nomF);
				String dateB = MedicineChange.getTxtPatentDate();
				String nomEffet = MedicineChange.getTxtEffet();
				Effect effet = Effect.getEffectByName(nomEffet);
				//Récupération de l'objet Medicine à modifier
				Medicine med = Medicine.getMedicineByName(nom);
				//Modification de celui-ci à travers les setteurs
				med.setItsForm(forme);
				med.setPatentDate(DatesConverter.FRStringToDate(dateB));
				med.setItsEffect(effet);
				//UPDATE dans la BD
				try {
					Persistence.updateMedicine(med.getName(),med.getItsForm().getId(),med.getPatentDate(), med.getItsEffect().getId());
					//Mise à jour de la jtable
					String[][] dataTable = this.medicinesTable();
					String[] dataColumns = {"Nom","Forme","Brevet"};
					MedicineSearch.setTable(dataTable, dataColumns);
					//Modification du bouton (annuler devient fermer)
					MedicineChange.btnAnnuler.setText("Fermer");
					//Message de confirmation pour l'utilisateur
					JOptionPane.showMessageDialog(null,"Le médicament a bien été modifié","Confirmation Enregistrement",JOptionPane.INFORMATION_MESSAGE);					
				} catch (SQLException e) {
					String message = "Erreur lors de l'echange avec la base de données. L'application a rencontrée l'erreur : "+e.getMessage();
					JOptionPane.showMessageDialog(null,message,"Erreur SQL",JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			break;
		}	
	}

	/**
	 * Méthode permettant d'interroger le modèle afin de construire un tableau contenant tous les médicaments
	 * @return un tableau à deux dimensions contenant tous les médicaments (nom,idForme,dateBrevet, Effet)
	 */
	private String[][] medicinesTable() {
		int i=0;
		String[][] liste=new String[Medicine.allTheMedicines.size()][4];
		for(Medicine m : Medicine.allTheMedicines){
			liste[i][0]=m.getName();
			liste[i][1]=m.getItsForm().getName();
			liste[i][2]=DatesConverter.dateToStringFR(m.getPatentDate());
			liste[i][3]=m.getItsEffect().getName();
			i++;
		}
		return liste;
	}

	/**
	 * Méthode permettant d'interroger le modèle afin de construire un tableau contenant toutes les formes
	 * @return un tableau à une dimension contenant toutes les formes (nom)
	 */
	private String[] formsBox(){
		int i=0;
		String[] liste=new String[Form.allTheForms.size()];
		for(Form l : Form.allTheForms){
			liste[i]=l.getName();
			i++;
		}
		return liste;
	}

	/**
	 * Méthode permettant d'interroger le modèle afin de construire un tableau contenant toutes les formes
	 * @return un tableau à une dimension contenant toutes les formes (nom)
	 */
	private String[] effectsBox(){
		int i=0;
		String[] liste=new String[Effect.allTheEffects.size()];
		for(Effect l : Effect.allTheEffects){
			liste[i]=l.getName();
			i++;
		}
		return liste;
	}
	
	/**
	 * Méthode permettant d'interroger la bd afin de construire un tableau contenant tous les liens mediceffet
	 * @return un tableau à deux dimensions contenant tous les liens mediceffet
	 * @throws SQLException 
	 */
	public static String[][] getMedicEffets() throws SQLException{
		return Persistence.load("mediceffet");
	}

	/**
	 * Méthode déclanchée lors de clics souris sur l'application
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {
		//S'il s'agit d'un double-clic
		if(evt.getClickCount()==2){
			//Récupération de la jtable dans laquelle l'utilisateur a double-cliqué
			JTable laTable = (JTable)evt.getComponent();
			//Récupération du numéro de la ligne de cette jtable sur laquelle il a double-cliqué
			int row=laTable.rowAtPoint(evt.getPoint());
			//Récupération du médicament à partir de ces informations
			Medicine med = Medicine.getMedicineByName(laTable.getValueAt(row,0).toString());
			//Création d'un tableau contenant le détail du médicament
			String[] data = new String[4];
			data[0]=med.getName();
			data[1]=med.getItsForm().getName();
			data[2]=DatesConverter.dateToStringFR(med.getPatentDate());
			data[3]=med.getItsEffect().getName();
			//Création de la vue de modification du médicament sélectionné dans la jtable
			MedicineChange frame = new MedicineChange(this.formsBox(), data, this.effectsBox());
			//Assignation d'un observateur sur cette vue
			frame.assignListener(this);
			//Affichage de la vue
			frame.setVisible(true);
		 } 
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}