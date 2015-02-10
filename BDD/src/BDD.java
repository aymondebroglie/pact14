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
	public boolean ajouterConsommation(int bluetoothID, int rFID, int volume) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finDeCommande(int rFID) {
		// TODO Auto-generated method stub
		return false;
	}

}
