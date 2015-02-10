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

	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	@Override
	public boolean ajouterConsommation(int bluetoothID, int rFID, int volume) 
	{
		int cPK;
		int codeBarre;
		try
		{
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE Associe.BluetoothID="+bluetoothID);
			rs.next();
			codeBarre=rs.getInt(1);
			rs=st.executeQuery("SELECT CPK FROM Barman WHERE Barman.CPK="+rFID);
			rs.next();
			cPK=rs.getInt(1);
			st.execute("INSERT INTO Composition ("+codeBarre+","+volume+","+cPK+")");
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
			st.execute("INSERT INTO Goulots (BluetoothID, EnCharge, NiveauDeCharge )" +
					"VALUES ('"+bluetooth+"','0','1.0')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
