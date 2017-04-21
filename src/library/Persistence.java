package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
/**
 * Classe de persistance des objets dans une base SQL
 * @author xavier
 *
 */
public abstract class Persistence {

	/**
	 * M�thode de connexion � la BD
	 * @return une connexion active sur la BD
	 * @throws SQLException l'exception SQL lev�e
	 */
	private static Connection connection() throws SQLException{
		String host = "192.168.222.72"; //Notre serveur du lyc�e
//		String host = "127.0.0.1:3306"; //Serveur Local du lyc�e
//		String host = "localhost:1434";
		String base = "gsbjm";
		String user = "JeanMedicament";
		String passwd = "zouzou";
		Connection con = null;
		try{
			con = DriverManager.getConnection("jdbc:sqlserver://"+host+";database="+base+";user="+user+";password="+passwd);
		} catch (SQLException e){
			System.out.println("Erreur connection (chaine sql: "+"jdbc:sqlserver://"+host+";database="+base+";user="+user+";password="+passwd+"): "+e.getMessage());
			throw e;
		}
		return con;
	}
	
	/**
	 * M�thode d'INSERT d'un nouveau m�dicament
	 * @param name le nom du nouveau m�dicament
	 * @param idForm l'identifiant de la forme du nouveau m�dicament
	 * @param patentDate la date d'obtention du brevet du nouveau m�dicament
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static void insertMedicine(String name, int idForm, GregorianCalendar patentDate) throws SQLException{
		Connection cn = Persistence.connection();
		Statement stmt;
		try{
			 stmt = cn.createStatement();
			 if(patentDate!=null)
				 stmt.executeUpdate("INSERT INTO medicament (nom,idForme,dateBrevet) VALUES ('"+name+"',"+idForm+",'"+DatesConverter.dateToStringUS(patentDate)+"')");
			 else
				 stmt.executeUpdate("INSERT INTO medicament (nom,idForme,dateBrevet) VALUES ('"+name+"',"+idForm+",null)");
		}catch (SQLException e){
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
	}
	/**
	 * M�thode d'INSERT d'une nouvelle forme
	 * @param name le nom de la nouvelle forme
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static void insertForm(String name) throws SQLException{
		Connection cn = Persistence.connection();
		Statement stmt;
		
		try {
			 stmt = cn.createStatement();
			stmt.executeUpdate("INSERT INTO forme (nom) VALUES ('"+name+"')");
		} catch (SQLException e) {
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
	}
	/**
	 * M�thode d'INSERT d'un nouvel effet
	 * @param name le nom du nouvel effet
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static void insertEffect(int grade, String description) throws SQLException{
		Connection cn = Persistence.connection();
		Statement stmt;
		
		try {
			 stmt = cn.createStatement();
			stmt.executeUpdate("INSERT INTO effet (grade, description) VALUES ("+grade+" , '"+description+"')");
		} catch (SQLException e) {
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
	}
	
	/***
	 * M�thode d'insertion de lien mediceffet
	 * @param table le nom de la table SQL � s�lectionner
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static void insertMedicEffect(int idMedic, int idEffet) throws SQLException{
		Connection cn = Persistence.connection();
		Statement stmt;
		
		try {
			 stmt = cn.createStatement();
			stmt.executeUpdate("INSERT INTO mediceffet (idMedic, idEffet) VALUES ("+idMedic+" , '"+idEffet+"')");
		} catch (SQLException e) {
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
	}

	/**
	 * M�thode de SELECT des tables
	 * @param table le nom de la table SQL � s�lectionner
	 * @return un tableau � deux dimensions contenant tous les tuples de la table
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static String[][] load(String table) throws SQLException{	
		//D�claration des variables
		Connection cn = Persistence.connection();
		Statement stmt; 
		ResultSet rs = null;
		ResultSetMetaData metadata;
		int rowCount,columnCount,rowNum;
		String columnName;
		String[][] result = null;
		
	    try 
	    {
	    	stmt= cn.createStatement();
	    	//D�finition de la requete pour construire le jeu d'enregistrement
	    	rs = stmt.executeQuery("SELECT count(*) FROM "+table);
			//R�cup�ration du nombre de lignes du jeu d'enregistrement
	    	rs.next();
			rowCount=rs.getInt(1);
	    	//D�finition de la requete pour construire le jeu d'enregistrement
	    	rs = stmt.executeQuery("SELECT * FROM "+table);
			metadata = rs.getMetaData();
			//R�cup�ration du nombre de colonnes du jeu d'enregistrement
			columnCount = metadata.getColumnCount();
			//D�claration du tableau qui contiendra toutes les informations
			result = new String[rowCount][columnCount];
			//Parcours du jeu d'enregistrement
			rowNum = 0;
	        while (rs.next()) 
	        {
	        	for (int numCol=0; numCol<columnCount; numCol++)
	        	{
	        		//Insertion de la valeur dans une case du tableau
	        		columnName = metadata.getColumnName(numCol+1);
		        	result[rowNum][numCol] = rs.getString(columnName);
	        	}
	        	rowNum++;
	        }
	        
		} catch (SQLException e) 
		{
			throw e;
		}
	    finally{
	    	Persistence.closeConnection(cn);
	    }
	return result;
	}

	/**
	 * M�thode de cl�ture de connexion
	 * @param conn la connexion � fermer
	 * @throws SQLException l'exception SQL lev�e
	 */
	private static void closeConnection(Connection conn) throws SQLException{
		try {
			conn.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * M�thode d'UPDATE d'un m�dicament
	 * @param name le nom du m�dicament � modifier
	 * @param idForm la nouvelle forme du m�dicament � modifier
	 * @param patentDate la nouvelle date d'obtention du brevet du m�dicament � modifier
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static void updateMedicine(String name, int idForm, GregorianCalendar patentDate) throws SQLException {
		Connection cn = Persistence.connection();
		Statement stmt;
		try{
			 stmt = cn.createStatement();
			 stmt.executeUpdate("UPDATE medicament SET idForme="+idForm+" WHERE nom='"+name+"'");
			 if(patentDate!=null)
			 {		String req = "UPDATE medicament SET dateBrevet='"+DatesConverter.dateToStringFR(patentDate)+"' WHERE nom='"+name+"'";
			 System.out.println(req);
				 stmt.executeUpdate(req);
			 }
		}catch (SQLException e){
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
	}
	
	/**
	 * M�thode d'UPDATE des effets pour un m�dicament
	 * @param effets tableau qui contient l'id du m�dicament puis la liste des effets
	 * @throws SQLException l'exception SQL lev�e
	 */
	public static void updateMedicEffects(int[] effects) throws SQLException{
		Connection cn = Persistence.connection();
		Statement stmt;
		try{
			 stmt = cn.createStatement();
			 stmt.executeUpdate("DELETE FROM mediceffet WHERE idMedic="+effects[0]);
			for(int i = 1; i<effects.length; i++){
				 if(effects[i]!=0)
				 { stmt.executeUpdate("INSERT INTO mediceffet (idMedic,idEffet) VALUES ("+effects[0]+","+effects[i]+")"); }
			 }
		}catch (SQLException e){
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
	}
	
	/**
	 * Charger les effets d'un m�dicament pr�cis
	 * @param idMedic l'identifiant du m�dicament
	 * @return String[] liste des effets correspondants
	 * @throws SQLException 
	 */
	public static int[] loadEffectsFromMedic(int idMedic) throws SQLException{
		//D�claration des variables
		Connection cn = Persistence.connection();
		Statement stmt; 
		ResultSet rs = null;
		int loop;
		int[] effects = null;
		
	    try 
	    {
	    	stmt= cn.createStatement();
	    	//D�finition de la requete pour construire le jeu d'enregistrement
	    	rs = stmt.executeQuery("SELECT count(*) FROM "+idMedic);
			//R�cup�ration du nombre de lignes du jeu d'enregistrement
	    	rs.next();
			effects = new int[rs.getInt(1)];
	    	//D�finition de la requete pour construire le jeu d'enregistrement
	    	rs = stmt.executeQuery("SELECT * FROM mediceffet WHERE idMedic="+idMedic);
			//Parcours du jeu d'enregistrement
			loop = 0;
	        while (rs.next()) 
	        {
	        	effects[loop] = rs.getInt(1);
	        	loop++;
	        }
	        
		} catch (SQLException e) 
		{
			throw e;
		}
	    finally{
	    	Persistence.closeConnection(cn);
	    }
	    return effects;
	}
	
	/**
	 * Donne l'identifiant d'un m�dicament en fonction d'un nom donn� en param�tre
	 * @param medicName sp�cifiant le nom du m�dicament dont on d�sire obtenir l'indentifiant
	 * @return int correspondant � l'id d'un m�dicament donn� en param�tre
	 * @throws SQLException
	 */
	
	public static int getIdFromMedic(String medicName) throws SQLException{
		//D�claration des variables
		Connection cn = Persistence.connection();
		Statement stmt; 
		ResultSet rs = null;
		int medicId;
		
	    try 
	    {
	    	stmt= cn.createStatement();
	    	//D�finition de la requete pour construire le jeu d'enregistrement
	    	rs = stmt.executeQuery("SELECT identifiant FROM medicament WHERE nom like '"+medicName+"'");
			//Parcours du jeu d'enregistrement
	    	rs.next();
	        medicId = rs.getInt(1);
	        
		} catch (SQLException e) 
		{
			throw e;
		}
	    finally{
	    	Persistence.closeConnection(cn);
	    }
		return medicId;
	}
}