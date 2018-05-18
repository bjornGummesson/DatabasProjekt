package dbtLab3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database is an interface to the college application database, it uses JDBC to
 * connect to a SQLite3 file.
 */
public class Database {

	/**
	 * The database connection.
	 */
	private Connection conn;
	private int count = 5;

	/**
	 * Creates the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;
		
	}

	/**
	 * Opens a connection to the database, using the specified filename (if we'd
	 * used a traditional DBMS, such as PostgreSQL or MariaDB, we would have
	 * specified username and password instead).
	 */
	public boolean openConnection(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + filename);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		count = getNbrOfPallets()+1;
		return true;
	}

	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}

	/* ================================== */
	/* --- This is our code --- */
	/* ===============================*== */

	public ArrayList<String> getSearchData(String type, String searchEntry) {

		ArrayList<String> result = new ArrayList<>();
		switch (type) {
		case "Pallet ID":
			result = searchPalletInfoFromPalletID(searchEntry);
			break;
		case "Customer name":
			result = searchPalletIDfromCustomer(searchEntry);
			break;
		case "Order ID":
			result = searchPalletIDfromOrderID(searchEntry);
			break;
		case "Blocked":
			result = searchPalletIDfromBlocked(searchEntry);
			break;
		case "Date Search":
			result = searchPalletIDfromDate(searchEntry);
			break;
		default:
			result.add("Invalid Search");

			return result;
		}

		return result;
	}
	
	private ArrayList<String> searchPalletInfoFromPalletID(String searchEntry) {
		ArrayList<String> palletInfo = new ArrayList<String>();

		String query = "SELECT   *\n" + "FROM    pallets\n" + "WHERE label=?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, searchEntry);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				palletInfo.add("Label: " + rs.getString("label"));
				palletInfo.add("Cookie name: " + rs.getString("cookie_name"));
				palletInfo.add("Order ID: " + rs.getString("order_id"));
				palletInfo.add("Timestamp: " + rs.getString("timestamp"));
				palletInfo.add("Blocked status: " + rs.getString("blocked_status"));
				palletInfo.add("Arrival date: " + rs.getString("arrival_date"));
				palletInfo.add("--------------------------");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletInfo;
	}
	private String[] getDateFromString(String s) {
		
		// "2018-05-15:2018-06-16"
		
		String from = s.substring(0, 9);
		
		String to = s.substring(s.length()-10, s.length());
		String[] a = {from,to};
		return a;
	}
	
	private ArrayList<String> searchPalletIDfromDate(String searchEntry) {
		ArrayList<String> palletInfo = new ArrayList<String>();
		String[] dates = getDateFromString(searchEntry);

		String query = "SELECT   label\n" + "FROM    pallets\n" + "WHERE timestamp BETWEEN ? AND ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, dates[0]);
			ps.setString(2, dates[1]);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				palletInfo.add("Pallet ID: " + rs.getString("label"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletInfo;
	}

	private ArrayList<String> searchPalletIDfromCustomer(String searchEntry) {
		ArrayList<String> palletInfo = new ArrayList<String>();

		String query = "SELECT   label\n" + "FROM    pallets\n" + "join orders\n" + "using (order_id)\n"
				+ "join customers\n" + "using (customer_name)\n" + "WHERE customer_name = ? AND arrival_date IS NOT NULL";
		//   
		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, searchEntry);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				palletInfo.add("Pallet ID: " + rs.getString("label"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletInfo;
	}

	private ArrayList<String> searchPalletIDfromOrderID(String searchEntry) {
		ArrayList<String> palletInfo = new ArrayList<String>();

		String query = "SELECT   label\n" + "FROM    pallets\n" + "join orders\n" + "using (order_id)\n"
				+ "WHERE order_id= ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, searchEntry);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				palletInfo.add("Pallet ID: " + rs.getString("label"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletInfo;
	}

	private ArrayList<String> searchPalletIDfromBlocked(String searchEntry) {
		ArrayList<String> palletInfo = new ArrayList<String>();

		String query = "SELECT   label\n" + "FROM    pallets\n" + "WHERE blocked_status= 1";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				palletInfo.add("Pallet ID: " + rs.getString("label"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletInfo;
	}

	public int createPallet(String productName) {

		Date d = new Date(System.currentTimeMillis());
		boolean check = updateRaw(productName);
		if(!check) {
			return -1;
		}
		else {

			String query = "INSERT INTO pallets (label, cookie_name, order_id, timestamp, blocked_status, arrival_date)\n"
					+ "VALUES (" + count + ",?, null, '" + d.toString() + "', 0, null);";
			count++;

			try (PreparedStatement ps = conn.prepareStatement(query)) {

				ps.setString(1, productName);
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count-1;
	}

	private boolean updateRaw(String productName) {

		ArrayList<RawEntry> recept = getRecipie(productName);
		
		for(RawEntry x: recept) {
			int currAmt = getMaterialAmt(x.getRawMaterial());
			int newAmt = currAmt - x.getAmt();
			if(newAmt <0) {
				return false;
			}
			
			else {
				String query = "UPDATE raw_materials \n" + "SET rm_amount = ? \n" + "WHERE rm_name= ?";

				try (PreparedStatement ps = conn.prepareStatement(query)) {

					ps.setString(1, Integer.toString(newAmt) );
					ps.setString(2, x.getRawMaterial());
					

					 ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		return true;

	}

	private int getMaterialAmt(String materialName) {
		int i = 0;
		String query = "SELECT   rm_amount\n" + "FROM    raw_materials\n" + "WHERE rm_name= ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, materialName);

			ResultSet rs = ps.executeQuery();
			i = Integer.parseInt(rs.getString("rm_amount"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;

	}

	private ArrayList<RawEntry> getRecipie(String productName) {

		ArrayList<RawEntry> list = new ArrayList<>();
		String query = "SELECT   rm_name, raw_cookie_amount\n" + "FROM    raw_cookie_material\n" + "WHERE cookie_name= ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, productName);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String material = rs.getString("rm_name");
				String amount = rs.getString("raw_cookie_amount");

				list.add(new RawEntry(material, Integer.parseInt(amount)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void blockPallets(String startDate, String endDate, String productName) {

		
		String query = "UPDATE pallets \n"
				+ "SET blocked_status = 1 \n"
				+ "WHERE (cookie_name = ?) AND (timestamp BETWEEN ? AND ?)";
		count++;

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, productName);
			ps.setString(2, startDate);
			ps.setString(3, endDate);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Blocket pallets called with startDate: " + startDate + " Enddate: " + endDate
				+ " Productname: " + productName);

	}

	private int getNbrOfPallets() {
		int i=0;
		String query = 
				"SELECT MAX(label) as cnt \n"+
				"from pallets";
		
		try (PreparedStatement ps = conn.prepareStatement(query)) {


			ResultSet rs = ps.executeQuery();
			i = Integer.parseInt(rs.getString("cnt"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;

		
		
	}
	
	private class RawEntry {
		private String rawMaterial;
		private int amt;

		public RawEntry(String rawMaterial, int amt) {
			this.rawMaterial = rawMaterial;
			this.amt = amt;
		}

		public String getRawMaterial() {
			return rawMaterial;
		}

		public int getAmt() {
			return amt;
		}

	}

}
