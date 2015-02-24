import java.sql.*;
import java.util.ArrayList;





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
               System.out.println("Connection avec la Base De Données "+dbname+" établie.\n");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	@Override
	public boolean ajouterConsommation(int bluetoothID, int rFID, int volume) 
	{
		int cPK=0;
		long codeBarre=0;
		try
		{
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE Associe.BluetoothID='"+bluetoothID+"'");
			if(!rs.next())
				throw(new Exception("Pas de goulot associé à l'identifiant bluetooth "+bluetoothID));
			codeBarre=rs.getInt(1);
			
			rs=st.executeQuery("SELECT CPK FROM Barman WHERE Barman.CPK='"+rFID+"'");
			if(!rs.next())
				throw(new Exception("Pas de barman associé à l'identifiant RFID "+rFID));
			cPK=rs.getInt(1);
			if(codeBarre==0)
				throw(new Exception("Pas de boisson associée au goulot d'identifiant RFID "+rFID));
			if(cPK==0)
			{
				rs=st.executeQuery("SELECT CPK FROM Servi");
				while(rs.next())
					cPK=Math.max(rs.getInt(1),cPK);
				cPK++;
			}
			st.executeUpdate("INSERT INTO Composition ('"+codeBarre+"','"+cPK+"','"+volume+"')");
			System.out.println("La consommation a bien été ajoutée.\n");
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
			st.executeUpdate("INSERT INTO Associe (CodeBarre, BluetoothID)" + "VALUES ('0',"+bluetooth+"')");
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
			System.out.println("UPDATE:" + updateResultat+"\n");
			
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
	public boolean ajouterBarman(int rFID, String nom, String prenom,
			int age, java.util.Date dateEmbauche, int cPK) 
	{
		java.sql.Timestamp sqlTime= new java.sql.Timestamp(dateEmbauche.getTime());
		String sql = "INSERT INTO Barman (RFID, Nom, Prenom,Age, DateEmbauche,CPK)" +
				"VALUES ('"+rFID+"','"+nom+"','"+prenom+"','"+age+"','"+sqlTime+"','"+cPK+"')";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
	}

	@Override
	public boolean modifierInformationBarman(int rFID, String nom,
			String prenom, int age, java.util.Date dateEmbauche, int cPK) 
	{
		java.sql.Timestamp sqlTime= new java.sql.Timestamp(dateEmbauche.getTime());
		String updateSql = "UPDATE Barman SET Nom='"
				+ nom+"', Prenom='"+prenom+"',Age= '"+age+"',DateEmbauche='" +sqlTime+"',CPK='"+cPK+"' WHERE RFID='"+rFID+"'";
		try {
		int updateResultat=st.executeUpdate(updateSql);
		System.out.println("UPDATE:" + updateResultat+"\n");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
}return true;

	}

	@Override
	public boolean modifierCommandeDeBarman(int rFID, int cPK) {
		String updateSql = "UPDATE Barman SET CPK="+cPK+" WHERE RFID="+rFID;
		try {
		int updateResultat=st.executeUpdate(updateSql);
		System.out.println("UPDATE:" + updateResultat+"\n");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
}return true;
	}

	@Override
	public boolean supprimerBarman(int rFID) {
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
	public boolean ajouterBoisson(long codeBarre, String nom, String marque,int volume,
			int degre) 
	{
		String sql = "INSERT INTO Boisson (CodeBarre,Nom,Marque,Volume, Degre)" +
				"VALUES ('"+codeBarre+"','"+nom+"','"+marque+"','"+volume+"','"+degre+"')";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
	}

	@Override
	public boolean bouteilleFinie(int bluetoothID) 
	{
		//A COMPLETER
		String sql="SELCT Date FROM Stocks";
		return false;
	}

	@Override
	public boolean livraison(ArrayList<Livraison> livraison) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean associerGoulot(int blutoothID, long codeBarre) {
		//String sql = ""
		return false;
	}

}
