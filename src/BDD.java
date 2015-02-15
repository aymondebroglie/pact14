import java.sql.*;




public class BDD implements BDDInterface
{
	Connection con ;
    Statement st ;
    ResultSet rs ;
    
	public BDD(String dbname, String user, String mdp)
	{
		 try {
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
	            con = DriverManager.getConnection("jdbc:mysql://localhost/"+dbname+"?" +
                        "user="+user+"&password="+mdp);
	            st = con.createStatement();
               System.out.println("Connection établie");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	@Override
	public boolean ajouterConsommation(int bluetoothID, int rFID, int volume) 
	{
		int cPK=0;
		int codeBarre=0;
		try
		{
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE Associe.BluetoothID="+bluetoothID);
			while(rs.next())
			{
			codeBarre=rs.getInt(1);
			}
			rs=st.executeQuery("SELECT CPK FROM Barman WHERE Barman.CPK="+rFID);
			while(rs.next())
			{
			cPK=rs.getInt(1);
			}
			if((codeBarre!=0)&&(cPK!=0))
			{
				st.executeUpdate("INSERT INTO Composition ("+codeBarre+","+volume+","+cPK+")");
				//maque de méthode pour vérifier si bien Update.
				System.out.println("Bien ajouter une consommation.");
			}
			//on met a jour le stock à la fin de la commande.
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean finDeCommande(int rFID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ajouterGoulot(int bluetooth) 
	{
		try {
			st.executeUpdate("INSERT INTO Goulots (BluetoothID, EnCharge, NiveauDeCharge )" +
					"VALUES ('"+bluetooth+"','0','1.0')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
		
	}
	
	public boolean modifierGoulot(int bluetooth,int enCharge, float niveauDeCharge)
	{   
		String updateSql = "UPDATE Goulots SET EnCharge="+enCharge+",NiveauDeCharge="
				+ niveauDeCharge+ "WHERE BluetoothID ="+bluetooth;
		try {
			int updateResultat=st.executeUpdate(updateSql);
			System.out.println("UPDATE:" + updateResultat);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}
	
	public boolean supprimerGoulot(int bluetooth)
	{
		String sql = "DELETE FROM Goulots WHERE BluetoothID = "+bluetooth;
		try
		{
		long deleteRes = st.executeUpdate(sql);
		 System.out.print("DELETE:" + deleteRes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}

	@Override
	public boolean ajouterBarman(String rFID, String nom, String prenom,
			int age, String dateEmbauche, int cPK) 
	{
		String sql = "INSERT INTO Barman (RFID, Nom, Prenom,Age, DateEmbauche,CPK)" +
				"VALUES ('"+rFID+"','"+nom+"','"+prenom+"','"+age+"','"+dateEmbauche+"','"+cPK+"')";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
	}

	@Override
	public boolean modifierInformationBarman(String rFID, String nom,
			String prenom, int age, String dateEmbauche, int cPK) 
	{
		String updateSql = "UPDATE Barman SET Nom='"
				+ nom+"', Prenom='"+prenom+"',Age= '"+age+"',DateEmbauche='" +dateEmbauche+"',CPK='"+cPK+"' WHERE RFID='"+rFID+"'";
		try {
		int updateResultat=st.executeUpdate(updateSql);
		System.out.println("UPDATE:" + updateResultat);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
}return true;

	}

	@Override
	public boolean modifierCommandeDeBarman(String rFID, int cPK) {
		String updateSql = "UPDATE Barman SET CPK="+cPK+" WHERE RFID="+rFID;
		try {
		int updateResultat=st.executeUpdate(updateSql);
		System.out.println("UPDATE:" + updateResultat);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
}return true;
	}

	@Override
	public boolean supprimerBarman(String rFID) {
		String sql = "DELETE FROM Barman WHERE RFID = "+rFID;
		try
		{
		long deleteRes = st.executeUpdate(sql);
		 System.out.print("DELETE:" + deleteRes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}

	@Override
	public boolean ajouterBoisson(int codeBarre, String nom, String marque,
			int degre) 
	{
		String sql = "INSERT INTO Boisson (CodeBarre,Nom,Marque,Degre)" +
				"VALUES ('"+codeBarre+"','"+nom+"','"+marque+"','"+degre+"')";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
	}

}
