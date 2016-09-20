package model;

import java.util.ArrayList;
/**
 * Classe d'objet metier EFFET
 * @author etienne
 * 
 */
public class Effect {
	/**
	 * Identifiant de l'effet
	 */
	private int id;
	/**
	 * Grade de l'effet
	 */
	private int grade;
	/**
	 * Description de l'effet
	 */
	private String description;
	/**
	 * Liste statique de tous les effets
	 */
	public static ArrayList<Effect> allTheEffects = new ArrayList<Effect>();
	
	/**
	 * Constructeur de la classe Effet
	 * @param id identifiant du nouvel effet
	 * @param name nom du nouvel effet
	 */
	public Effect(int id, int grade, String description) {
		super();
		this.id = id;
		this.grade = grade;
		this.description = description;
		allTheEffects.add(this);
	}
	
	/**
	 * Accesseur en lecture sur l'identifiant de la forme
	 * @return l'identifiant de la forme
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Accesseur en lecture sur l'identifiant de la forme
	 * @return l'identifiant de la forme
	 */
	public int getGrade(){
		return grade;
	}
	
	/**
	 * Accesseur en lecture sur le nom de la forme
	 * @return le nom de la forme
	 */
	public String getName() {
		return description;
	}
	/**
	 * Méthode permettant de rechercher parmi toutes les formes
	 * celle ayant un identifiant correspondant à celui passé en paramètre
	 * @param id l'identifiant à rechercher
	 * @return la Forme correspondante
	 */
	public static Effect getEffectById(int id){
		Effect found = null;
		for(Effect f : Effect.allTheEffects){
			if(f.getId()==id)
				found=f;
		}
		return found;
	}
	/**
	 * Méthode permettant de rechercher parmi toutes les formes
	 * celle ayant un nom correspondant à celui passé en paramètre
	 * @param name le nom à rechercher
	 * @return la Forme correspondante
	 */
	public static Effect getEffectByName(String name) {
		Effect found = null;
		for(Effect f : Effect.allTheEffects){
			if(f.getName().equals(name))
				found=f;
		}
		return found;
	}

}
