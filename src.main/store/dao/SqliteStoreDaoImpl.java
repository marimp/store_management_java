package store.dao;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import store.model.DiscountItem;
import store.model.StoreItem;

public class SqliteStoreDaoImpl implements StoreDao{
	public static final String DB_URL = "jdbc:sqlite:"+new File("store.db").getAbsolutePath(); 
	private static Connection conn = null;

	/**
	 * @return
	 */
	private static Connection getConnection() {
		if (conn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(DB_URL);
				System.out.println("Connection succeded");
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(1);
			}
		}
		return conn;
	}

	/**
	 * @throws Throwable
	 */
	public void closeConnection() throws Throwable {
		try {
			if (conn != null) {
				try {
					conn.close();
				} catch ( Exception e ) {
					e.printStackTrace();
				}
				conn = null;
				System.out.println("Connection closed");
			}
		} finally {
			super.finalize();
		}
	}


	/* (non-Javadoc)
	 * @see store.dao.StoreDao#getAllStoreItems()
	 */
	@Override
	public HashMap<String, StoreItem> getAllStoreItems() {
		conn = SqliteStoreDaoImpl.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String, StoreItem> storeItems = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM store");
			storeItems = new HashMap<String,StoreItem>();
			while(rs.next()) {
				StoreItem storeItem = extractStoreItemFromResultSet(rs);
				storeItems.put(storeItem.getCode(),storeItem);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {  
			try {  
				rs.close();  
				stmt.close();  
			} 
			catch (Exception e) {  
				e.printStackTrace();  
			}  
		}
		return storeItems;
	}

	/* (non-Javadoc)
	 * @see store.dao.StoreDao#getAllDiscountItems()
	 */
	@Override
	public HashMap<String,DiscountItem> getAllDiscountItems() {
		conn = SqliteStoreDaoImpl.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String,DiscountItem> discountItems = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM discount");
			discountItems = new HashMap<String,DiscountItem>();
			while(rs.next()) {
				DiscountItem discountItem = extractDiscountItemFromResultSet(rs);
				discountItems.put(discountItem.getType(),discountItem);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {  
			try {  
				rs.close();  
				stmt.close();  
			} 
			catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return discountItems;
	}

	@Override
	public HashMap<String, DiscountItem> getSelectedDiscountItems(
			String[] discounts) {
		conn = SqliteStoreDaoImpl.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String,DiscountItem> discountItems = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM discount where code in ('"+String.join("','", discounts)+"')");
			discountItems = new HashMap<String,DiscountItem>();
			while(rs.next()) {
				DiscountItem discountItem = extractDiscountItemFromResultSet(rs);
				discountItems.put(discountItem.getType(),discountItem);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {  
			try {  
				rs.close();  
				stmt.close();  
			} 
			catch (Exception e) {  
				e.printStackTrace();  
			}  
		}  
		return discountItems;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private StoreItem extractStoreItemFromResultSet(ResultSet rs) throws SQLException {
		StoreItem storeItem = new StoreItem();
		storeItem.setCode(rs.getString("code"));
		storeItem.setName( rs.getString("name") );
		storeItem.setPrice( rs.getFloat("price") );
		return storeItem;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private DiscountItem extractDiscountItemFromResultSet(ResultSet rs) throws SQLException {
		DiscountItem discountItem = new DiscountItem();
		discountItem.setCode(rs.getString("code"));
		discountItem.setType(rs.getString("type") );
		discountItem.setQuantity(rs.getInt("quantity"));
		discountItem.setDiscount(rs.getFloat("discount"));
		return discountItem;
	}

}
