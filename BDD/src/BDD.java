import java.sql.*;




public class BDD implements BDDInterface
{
	Connection con ;
    Statement st ;
    ResultSet rs ;
	public BDD()
	{
		 try {
			 String url = "jdbc:mysql://localhost:3306/optibar"; // a changer
		        String user = "admin"; // a changer 
		        String password = "pact14"; //a changer
	            con = DriverManager.getConnection(url, user, password);
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
			st.executeQuery("INSERT INTO Composition ("+codeBarre+","+volume+","+cPK+")");
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
			st.executeQuery("INSERT INTO Goulots ("+bluetooth+",0,100)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
