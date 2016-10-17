 package library;
  import java.net.InetAddress;
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
  	
	private static String hostName;
	
	public Persistence(){
		String hostName = getComputerFullName();
	}
	
	public static String getHostName(){
		return hostName;
 	}
 	
 	/**
 	   * Return the computer full name. <br>
 	 * @return 
     * @return the name or <b>null</b> if the name cannot be found
   	*/
 	public static String getComputerFullName() {
 	    String hostName = null;
 	    try {
 	      final InetAddress addr = InetAddress.getLocalHost();
 	      hostName = new String(addr.getHostName());
 	    } catch(final Exception e) {
 	    }//end try
 	    return hostName;
 }//end getComputerFullName
 	/**
  	 * Méthode d'INSERT d'un nouveau médicament
  	 * @param name le nom du nouveau médicament
 	 * @param idForm l'identifiant de la forme du nouveau médicament
 	 * @param patentDate la date d'obtention du brevet du nouveau médicament
 	 * @throws SQLException l'exception SQL levée
 	 */
 	public static void insertMedicine(String name, int idForm, GregorianCalendar patentDate, int idMolecule) throws SQLException{
 		Connection cn = Persistence.connection();
 		Statement stmt;
 		try{
 			 stmt = cn.createStatement();
 			 if(patentDate!=null)
 				 stmt.executeUpdate("INSERT INTO medicament (nom,idForme,dateBrevet,idMolecule,idEffet) VALUES ('"+name+"','"+idForm+"','"+DatesConverter.dateToStringFR(patentDate)+"','"+idMolecule+"',1)");
 			 else
 				 stmt.executeUpdate("INSERT INTO medicament (nom,idForme,dateBrevet) VALUES ('"+name+"',"+idForm+idMolecule+",null)");
 		}catch (SQLException e){
 			throw e;
 		}
 		finally{
 			Persistence.closeConnection(cn);
 		}
 	}
 	/**
 	 * Méthode d'INSERT d'une nouvelle forme
 	 * @param name le nom de la nouvelle forme
 	 * @throws SQLException l'exception SQL levée
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
 	 * Methode d'INSERT d'une nouvelle molécule
 	 * @param name le nom de la molécule
 	 * @throws SQLException l'excpetion SQL levée
 	 */
 	public static void insertMolecule(String name) throws SQLException{
 		Connection cn = Persistence.connection();
 		Statement stmt;
 		try {
 			stmt = cn.createStatement();
 			stmt.executeUpdate("INSERT INTO molecule (libelle) VALUES ('"+name+"')");
 		}catch (SQLException e) {
 			throw e;
 		}
 		finally{
 			Persistence.closeConnection(cn);
 		}
 	}
 
 	/**
 	 * Méthode de SELECT des tables
 	 * @param table le nom de la table SQL à sélectionner
 	 * @return un tableau à deux dimensions contenant tous les tuples de la table
 	 * @throws SQLException l'exception SQL levée
 	 */
 	public static String[][] load(String table) throws SQLException{	
 		//Déclaration des variables
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
 	    	//Définition de la requete pour construire le jeu d'enregistrement
 	    	rs = stmt.executeQuery("SELECT count(*) FROM "+table);
 			//Récupération du nombre de lignes du jeu d'enregistrement
 	    	rs.next();
 			rowCount=rs.getInt(1);
 	    	//Définition de la requete pour construire le jeu d'enregistrement
 	    	rs = stmt.executeQuery("SELECT * FROM "+table);
 			metadata = rs.getMetaData();
 			//Récupération du nombre de colonnes du jeu d'enregistrement
 			columnCount = metadata.getColumnCount();
 			//Déclaration du tableau qui contiendra toutes les informations
 			result = new String[rowCount][columnCount];
 			//PArcours du jeu d'enregistrement
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
 	 * Méthode de connexion à la BD
 	 * @return une connexion active sur la BD
 	 * @throws SQLException l'exception SQL levée
 	 */
 	private static Connection connection() throws SQLException{
 /*		String host = "192.168.222.72";
    	String host = "127.0.0.1:3306";
 		String host = "localhost";
 		String base = "bdMedocLab";
 		String user = "antoineZ";
 		String passwd = "zouzou";*/
 		Connection conn = null;
 		try
  		{
  			//String connectionString ="jdbc:sqlserver://"+host+";database="+base+";user="+user+";password="+passwd;
  //			String connectionString ="jdbc:mysql://"+host+"/"+base+"?user="+user+"&password="+passwd;
 			String connectionString ="jdbc:sqlserver://"+getComputerFullName()+"\\MSSQLSERVER;databaseName=bdmedoclab;integratedsecurity=true;";
// 			String connectionString ="jdbc:sqlserver://MAXIME-PC\\MSSQLSERVER;databaseName=bdmedoclab;integratedsecurity=true;";
  			conn = DriverManager.getConnection(connectionString);
  		}
  		catch (SQLException e) 
 		{
 			throw e;
 		}
 		return conn; 
 	}
 	
 	/**
 	 * Méthode de clôture de connexion
 	 * @param conn la connexion à fermer
 	 * @throws SQLException l'exception SQL levée
 	 */
 	private static void closeConnection(Connection conn) throws SQLException{
 		try {
 			conn.close();
 		} catch (SQLException e) {
 			throw e;
 		}
 	}
 
 	/**
 	 * Méthode d'UPDATE d'un médicament
 	 * @param name le nom du médicament à modifier
 	 * @param idForm la nouvelle forme du médicament à modifier
 	 * @param patentDate la nouvelle date d'obtention du brevet du médicament à modifier
 	 * @throws SQLException l'exception SQL levée
 	 */
 	public static void updateMedicine(String name, int idForm, GregorianCalendar patentDate) throws SQLException {
 		Connection cn = Persistence.connection();
 		Statement stmt;
 		try{
 			 stmt = cn.createStatement();
 			 stmt.executeUpdate("UPDATE medicament SET idForme="+idForm+" WHERE nom='"+name+"'");
 			 if(patentDate!=null)
 				 stmt.executeUpdate("UPDATE medicament SET dateBrevet='"+DatesConverter.dateToStringUS(patentDate)+"' WHERE nom='"+name+"'");
 		}catch (SQLException e){
 			throw e;
 		}
 		finally{
 			Persistence.closeConnection(cn);
 		}
 	}
 	/**
 	 * Methode d'UPDATE d'une molécule
 	 * @param nom le libelle de la molécule à modifier
 	 * @throws SQLException l'exception SQL levée
 	 */
 	public static void updateMolecule(String name, String oldName) throws SQLException{
 		Connection cn = Persistence.connection();
 		Statement stmt;
 		try{
 			stmt = cn.createStatement();
 			stmt.executeUpdate("UPDATE molecule SET libelle = '"+name+"'WHERE libelle like '"+oldName+"'");
 		}catch(SQLException e){
 			throw e;
 		}
 		finally{
 			Persistence.closeConnection(cn);
 		}
 	}
 	/**
 	 * Méthode qui permet de récupérer le nom d'un médicament via un ID médicament
 	 * @return nom
 	 * @throws SQLException l'exception SQL levée
 	 */
 	public static String searchIdMedicine(int id) throws SQLException{
 		Connection cn = Persistence.connection();
 		Statement stmt;
 		String nom =null;
 		try{
 			stmt = cn.createStatement();
 			nom = stmt.executeQuery("Select nom FROM medicament where identifiant ="+ id ).toString();
 		}catch(SQLException e){
 			throw e;
 		}
 		finally{
 			Persistence.closeConnection(cn);
 		}
 		return nom;
 	}
 	
 	/**
 	 * Méthode qui permet de récupérer le nom d'une molécule via un ID
 	 * @throws SQLException l'exception SQL levée
 	 * @return int ID molécule
 	 */
 	public static String searchMoleculeById(int id) throws SQLException{
 		Connection cn= Persistence.connection();
 		Statement stmt;
 		String libelle = null;
 		try{
 			stmt = cn.createStatement();
 			libelle = stmt.executeQuery("SELECT LIBELLE FROM MOLECULE WHERE IDENTIFIANT="+ id).toString();
 		}catch(SQLException e){
 			throw e;
 		}
 		finally{
 			Persistence.closeConnection(cn);
 		}
 		return libelle;
 	}

	public static int searchIdByName(String name) throws SQLException {
		// TODO Auto-generated method stub
		Connection cn = Persistence.connection();
		Statement stmt;
		int id;
		try{
			stmt = cn.createStatement();
			id = Integer.parseInt(stmt.executeQuery("SELECT ID FROM MOLECULE WHERE LIBELLE like '"+name+"'").toString());
		}catch(SQLException e){
			throw e;
		}
		finally{
			Persistence.closeConnection(cn);
		}
		return id;
	}
 	
 	
 	
 	
 }