package logic.persistence.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.domain.BusinessInCountry;
import logic.domain.Country;
import logic.exceptions.NoResultFoundException;
import logic.persistence.ConnectionManager;
import logic.persistence.RoutinesIdentifier;
import logic.persistence.RoutinesManager;

public class BusinessDAO {
	
	private BusinessDAO() {
		/**/
	}

	public static List<String> selectBusinesses() throws SQLException {
		CallableStatement stmt = null;
		ResultSet res = null;
		List<String> list = null;

		try {
			Connection conn = ConnectionManager.getConnection();
        	stmt = conn.prepareCall(RoutinesIdentifier.GET_BUSINESSES, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = RoutinesManager.executeStmt(stmt);
			
			if(res.first()) {
				list = new ArrayList<>();
				do {
					list.add(res.getString("category"));
				}while(res.next());
			}
        } catch (SQLException e) {
        	throw new SQLException("An error occured while trying to retrieve list of businesses."); 
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
        
        return list;
	}

	public static List<BusinessInCountry> selectBusinessInCountry(String country, String bus) throws SQLException, NoResultFoundException {
		CallableStatement stmt = null;
		ResultSet res = null;
		List<BusinessInCountry> list = new ArrayList<>();

		try {
			Connection conn = ConnectionManager.getConnection();
        	stmt = conn.prepareCall(RoutinesIdentifier.GET_BUSINESS_IN_COUNTRY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = RoutinesManager.bindParametersAndExec(stmt, country, bus);
			
			processResearch(res, list);
			
        } catch (SQLException e) {
        	throw new SQLException("An error occured while trying to retrieve list of businesses in country."); 
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
        
        return list;
	}

	public static List<BusinessInCountry> selectBusinessByCountry(String country) throws SQLException, NoResultFoundException {
		CallableStatement stmt = null;
		ResultSet res = null;
		List<BusinessInCountry> list = new ArrayList<>();

		try {
			Connection conn = ConnectionManager.getConnection();
        	stmt = conn.prepareCall(RoutinesIdentifier.GET_BUSINESS_BY_COUNTRY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = RoutinesManager.bindParametersAndExec(stmt, country);
			
			processResearch(res, list);
			
        } catch (SQLException e) {
        	throw new SQLException("An error occured while trying to retrieve list of businesses by country."); 
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
        
        return list;
	}

	public static List<BusinessInCountry> selectBusinessByName(String business) throws SQLException, NoResultFoundException {
		CallableStatement stmt = null;
		ResultSet res = null;
		List<BusinessInCountry> list = new ArrayList<>();

		try {
			Connection conn = ConnectionManager.getConnection();
        	stmt = conn.prepareCall(RoutinesIdentifier.GET_BUSINESS_BY_NAME, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = RoutinesManager.bindParametersAndExec(stmt, business);
			
			processResearch(res, list);
			
        } catch (SQLException e) {
        	throw new SQLException("An error occured while trying to retrieve list of businesses by name."); 
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
        
        return list;
	}
	
	public static List<BusinessInCountry> selectFavourites(String id) throws SQLException {
		CallableStatement stmt = null;
		ResultSet res = null;
		List<BusinessInCountry> list = new ArrayList<>();

		try {
			Connection conn = ConnectionManager.getConnection();
        	stmt = conn.prepareCall(RoutinesIdentifier.GET_FAVOURITE_BUSINESSES, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			res = RoutinesManager.bindParametersAndExec(stmt, id);
			
			processResearch(res, list);
			
        } catch (SQLException e) {
        	throw new SQLException("An error occured while trying to retrieve favourite businesses."); 
		} catch (NoResultFoundException e) {
			/*No op*/
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
        
        return list;
	}

	private static void processResearch(ResultSet res, List<BusinessInCountry> list) throws NoResultFoundException, SQLException {
		if(!res.first()) {
			throw new NoResultFoundException();
		}

		res.first();
		do {
			BusinessInCountry business = new BusinessInCountry();
			business.setName(res.getString("name"));
			business.setCategory(res.getString("category"));
			
			Country country = new Country();
			country.setName(res.getString("country"));
			business.setCountry(country);
			
			business.setDescription(res.getString("description"));
			business.setAverageEarnings(res.getFloat("average_earnings"));
			business.setAverageManagementCost(res.getFloat("average_costs"));
			
			list.add(business);
		}while(res.next());
	}
}