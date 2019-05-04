package et.artisan.cn.cps.dao;

import et.artisan.cn.cps.dto.*;
import et.artisan.cn.cps.util.*;
import et.artisan.cn.cps.entity.*;

import java.sql.*;
import java.time.*;
import java.util.*;

/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public class PostgresMasterRepository implements MasterRepository {

	protected QueryParameter parameter;
	private final ArrayList<ReportType> reportTypes;

	public PostgresMasterRepository() {
		reportTypes = new ArrayList<>();
		ReportType type1 = new ReportType((byte) 1, "Client Region Summary For Time Period");
		type1.isClientBased(true);
		type1.isClientRegionBased(true);
		type1.isTimeBased(true);
		reportTypes.add(type1);

		ReportType type2 = new ReportType((byte) 2, "Time Based Client Summary");
		type2.isClientBased(true);
		type2.isTimeBased(true);
		reportTypes.add(type2);

		ReportType type3 = new ReportType((byte) 3, "Client Region Summary For a Claim Document");
		type3.isClaimBased(true);
		type3.isClientBased(true);
		type3.isClientRegionBased(true);
		reportTypes.add(type3);

		ReportType type4 = new ReportType((byte) 4, "Claim Document Summary");
		type4.isClaimBased(true);
		reportTypes.add(type4);

		ReportType type5 = new ReportType((byte) 5, "Paying Branch Summary");
		type5.isTimeBased(true);
		type5.isClientBased(true);
		reportTypes.add(type5);

		ReportType type6 = new ReportType((byte) 6, "Paid/Unpaid Summary");
		type6.isTimeBased(true);
		type6.isClientBased(true);
		reportTypes.add(type6);

		ReportType type7 = new ReportType((byte) 7, "Paid/Unpaid Summary By Region");
		type7.isTimeBased(true);
		type7.isClientBased(true);
		type7.isClientRegionBased(true);
		reportTypes.add(type7);

		ReportType type8 = new ReportType((byte) 8, "Paid/Unpaid Summary By Branch");
		type8.isTimeBased(true);
		type8.isClientBased(true);
		type8.isBranchBased(true);
		reportTypes.add(type8);

		ReportType type9 = new ReportType((byte) 9, "Claim Document Summary By Claim CN Number");
		type9.isClaimCNNoBased(true);
		reportTypes.add(type9);

		ReportType type10 = new ReportType((byte) 10, "Claim Document Summary By Claim CN Number For A Client Region");
		type10.isClaimCNNoBased(true);
		type10.isClientBased(true);
		type10.isClientRegionBased(true);
		reportTypes.add(type10);

		ReportType type11 = new ReportType((byte) 11, "Paying Branch Summary By Claim CN Number");
		type11.isClaimCNNoBased(true);
		type11.isBranchBased(true);
		reportTypes.add(type11);

		ReportType type12 = new ReportType((byte) 12, "Document Payments Export");
		type12.isDocumentBased(true);
		reportTypes.add(type12);

		ReportType type13 = new ReportType((byte) 13, "Claim Document Summary By Claim CN Number (Branch Order)");
		type13.isClientBased(true);
		type13.isClaimCNNoBased(true);
		reportTypes.add(type13);
	}

	@Override
	public void setQueryParamters(QueryParameter parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the list of notifications for a user
	 * @param id of the user to get notifications for
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Notification> getNotifications(long id) {
		ArrayList<Notification> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement notificationsStmnt = connection
					.prepareStatement("SELECT * FROM notification WHERE messageto=?");
			notificationsStmnt.setLong(1, id);
			ResultSet notifications = notificationsStmnt.executeQuery();
			while (notifications.next()) {
				Notification notification = new Notification();
				notification.setId(notifications.getLong("id"));
				notification.setContent(notifications.getString("content"));
				notification.setMessageTo(getUser(notifications.getLong("messageto")));
				notification.setMessageFrom(getUser(notifications.getLong("messageFrom")));
				notification.setSentOn(notifications.getTimestamp("sentOn"));
				notification.setStatus(notifications.getString("status"));
				notification.setSubject(notifications.getString("subject"));
				returnValue.add(notification);
			}
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getNotifications() ");
		}
		return returnValue;
	}

	/**
	 * @return the list of notifications for a user
	 * @param id of the user to get notifications for
	 * @since version 1.0
	 */
	@Override
	public int getUnreadNotificationsCount(long id) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement notificationsStmnt = connection
					.prepareStatement("SELECT count(*) as c FROM notification WHERE messageto=? AND status='"
							+ Constants.STATUS_ACTIVE + "'");
			notificationsStmnt.setLong(1, id);
			ResultSet notifications = notificationsStmnt.executeQuery();
			if (notifications.next()) {
				returnValue = notifications.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository::getUnreadNotificationsCount() ");
		}
		return returnValue;
	}

	/**
	 *
	 * @param userName - username provided by the client
	 * @param password - password provided by the client
	 * @param host     - address of the host the client is trying to login from
	 * @return if login is successful a CurrentUserDTO object representing the
	 *         current user else null is returned
	 */
	@Override
	public CurrentUserDTO tryLogin(String userName, String password, String host) {
		CurrentUserDTO returnValue = null;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement usersStmnt = connection.prepareStatement(
					"SELECT * FROM useraccount WHERE username=? AND password=(SELECT md5(?)) AND status='active'");
			usersStmnt.setString(1, userName);
			usersStmnt.setString(2, password);
			ResultSet users = usersStmnt.executeQuery();
			if (users.next()) {
				returnValue = new CurrentUserDTO();
				returnValue.setId(users.getLong("id"));
				returnValue.setUsername(users.getString("username"));
				returnValue.setFirstName(users.getString("firstName"));
				returnValue.setFathersName(users.getString("fathersName"));
				returnValue.setGrandfathersName(users.getString("grandfathersName"));
				returnValue.setSex(users.getString("sex"));
				returnValue.setEmail(users.getString("email"));
				returnValue.setPrimaryPhoneNo(users.getString("primaryphoneno"));
				returnValue.setSecondaryPhoneNo(users.getString("secondaryphoneno"));
				returnValue.setSignature(users.getBytes("signature"));
				returnValue.setSignatureFormat(users.getString("signatureFormat"));
				returnValue.setPhoto(users.getBytes("photo"));
				returnValue.setPhotoFormat(users.getString("photoFormat"));
				returnValue.setAddressLine(users.getString("addressLine"));
				returnValue.setCity(users.getString("city"));
				returnValue.setState(users.getString("state"));
				returnValue.setCountry(users.getString("country"));
				returnValue.setWebsite(users.getString("website"));
				returnValue.setRegisteredOn(users.getTimestamp("registeredon"));
				returnValue.setStatus(users.getString("status"));
				returnValue.setLoggedInAt(Timestamp.from(Instant.now()));
				returnValue.setLoggedInFrom(host);
				connection = CommonStorage.getConnection();
				PreparedStatement rolesStmnt = connection.prepareStatement(
						"SELECT U.username AS username, G.code as code, G.textValue as role, G.englishName as englishName, G.localName AS localName FROM userGroup G, userRole R, userAccount U WHERE G.code = R.userGroup AND R.userAccount = U.id AND U.status = 'active' AND G.status = 'active' AND U.id=?");
				rolesStmnt.setLong(1, returnValue.getId());
				ResultSet roles = rolesStmnt.executeQuery();
				while (roles.next()) {
					Role r = new Role();
					r.setCode(roles.getByte("code"));
					r.setEnglishName(roles.getString("englishName"));
					r.setLocalName(roles.getString("localName"));
					r.setTextValue(roles.getString("role"));
					returnValue.addRole(r);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:tryLogin() ");
		}
		return returnValue;
	}

	/**
	 * Logs start of sessions at login time
	 *
	 * @since version 1.0
	 * @param currentUser a CurrentUserDTO object representing the currently logged
	 *                    in user
	 */
	@Override
	public void logSessionStart(CurrentUserDTO currentUser) {
		Connection connection = CommonStorage.getConnection();
		String query = "INSERT INTO userlogin(sessionId, useraccount, loginon, loginfrom, useragent) SELECT ?, ?, ?, ?, ? WHERE NOT EXISTS ( SELECT sessionId FROM userlogin WHERE sessionId = ? )";
		try {
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, currentUser.getSessionLogId());
			stmnt.setLong(2, currentUser.getId());
			stmnt.setTimestamp(3, currentUser.getLoggedInAt());
			stmnt.setString(4, currentUser.getLoggedInFrom());
			stmnt.setString(5, currentUser.getUserAgent());
			stmnt.setString(6, currentUser.getSessionLogId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR,
						"Logging user session start returned " + result);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:logSessionStart() ");
		}
	}

	/**
	 * Logs endo of sessions at logout time or when the session expires
	 *
	 * @since version 1.0
	 * @param currentUser a CurrentUserDTO object representing the currently logged
	 *                    in user
	 */
	@Override
	public void logSessionEnd(CurrentUserDTO currentUser) {
		Connection connection = CommonStorage.getConnection();
		String query = "UPDATE userlogin SET logOutOn = ? WHERE sessionId = ? AND useraccount = ? ";

		try {
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setTimestamp(1, currentUser.getLoggedOutAt());
			stmnt.setString(2, currentUser.getSessionLogId());
			stmnt.setLong(3, currentUser.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR,
						"Logging user session end returned " + result);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:logSessionStart() ");
		}
	}

	/**
	 * @return all user accounts in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
    public ArrayList<User> getAllUsers(UsersQueryParameter param){
			
		ArrayList<User> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT U.* FROM useraccount U WHERE U.status <> '" + Constants.STATUS_DELETED + "' ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( U.username ILIKE '%" + searchString + "%' OR U.firstName ILIKE '%"
							+ searchString + "%' OR U.fathersName ILIKE '%"+searchString+"%' "
							+ " OR U.fathersName ILIKE '%"+searchString+"%' OR U.primaryphoneno ILIKE '%"+searchString+"%' " 
							+ " OR U.email ILIKE '%"+searchString+"%' ) "
					: "");
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement usersStmnt = connection.prepareStatement(query);
			ResultSet users = usersStmnt.executeQuery();
			while (users.next()) {
				CurrentUserDTO user = new CurrentUserDTO();
				user.setId(users.getLong("id"));
				user.setUsername(users.getString("username"));
				user.setFirstName(users.getString("firstName"));
				user.setFathersName(users.getString("fathersName"));
				user.setGrandfathersName(users.getString("grandfathersName"));
				user.setSex(users.getString("sex"));
				user.setEmail(users.getString("email"));
				user.setPrimaryPhoneNo(users.getString("primaryphoneno"));
				user.setSecondaryPhoneNo(users.getString("secondaryphoneno"));
				user.setSignature(users.getBytes("signature"));
				user.setSignatureFormat(users.getString("signatureFormat"));
				user.setPhoto(users.getBytes("photo"));
				user.setPhotoFormat(users.getString("photoFormat"));
				user.setRegisteredOn(users.getTimestamp("registeredon"));
				user.setStatus(users.getString("status"));
				user.setAddressLine(users.getString("addressLine"));
				user.setCity(users.getString("city"));
				user.setState(users.getString("state"));
				user.setCountry(users.getString("country"));
				user.setWebsite(users.getString("website"));
				connection = CommonStorage.getConnection();
				PreparedStatement rolesStmnt = connection.prepareStatement(
						"SELECT U.username AS username, G.code as code, G.textValue as role, G.englishName as englishName, G.localName AS localName FROM userGroup G, userRole R, userAccount U WHERE G.code = R.userGroup AND R.userAccount = U.id AND U.status = 'active' AND G.status = 'active' AND U.id=?");
				rolesStmnt.setLong(1, user.getId());
				ResultSet roles = rolesStmnt.executeQuery();
				while (roles.next()) {
					Role r = new Role();
					r.setCode(roles.getByte("code"));
					r.setEnglishName(roles.getString("englishName"));
					r.setLocalName(roles.getString("localName"));
					r.setTextValue(roles.getString("role"));
					user.addRole(r);
				}
				returnValue.add(user);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllUsers() ");
		}
		return returnValue;
	}

	/**
	 * @return all user accounts in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
    public long getUsersCount(UsersQueryParameter param){
		long returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT Count(U.*) as c FROM useraccount U WHERE U.status <> '" + Constants.STATUS_DELETED + "' ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( U.username ILIKE '%" + searchString + "%' OR U.firstName ILIKE '%"
							+ searchString + "%' OR U.fathersName ILIKE '%"+searchString+"%' "
							+ " OR U.fathersName ILIKE '%"+searchString+"%' OR U.primaryphoneno ILIKE '%"+searchString+"%' " 
							+ " OR U.email ILIKE '%"+searchString+"%' ) "
					: "");
			PreparedStatement usersStmnt = connection.prepareStatement(query);
			ResultSet users = usersStmnt.executeQuery();
			if (users.next()) {
				returnValue = users.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getUsersCount() ");
		}
		return returnValue;
	}
	
	/**
	 * @return all user accounts in the system with the approver role
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
	public ArrayList<User> getAllApproverUsers() {
		ArrayList<User> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement usersStmnt = connection.prepareStatement(
					"SELECT distinct on (U.id) * FROM useraccount U, userrole R WHERE U.status='active' AND R.useraccount=U.id AND R.usergroup=3");
			ResultSet users = usersStmnt.executeQuery();
			while (users.next()) {
				CurrentUserDTO user = new CurrentUserDTO();
				user.setId(users.getLong("id"));
				user.setUsername(users.getString("username"));
				user.setFirstName(users.getString("firstName"));
				user.setFathersName(users.getString("fathersName"));
				user.setGrandfathersName(users.getString("grandfathersName"));
				user.setSex(users.getString("sex"));
				user.setEmail(users.getString("email"));
				user.setPrimaryPhoneNo(users.getString("primaryphoneno"));
				user.setSecondaryPhoneNo(users.getString("secondaryphoneno"));
				user.setSignature(users.getBytes("signature"));
				user.setSignatureFormat(users.getString("signatureFormat"));
				user.setPhoto(users.getBytes("photo"));
				user.setPhotoFormat(users.getString("photoFormat"));
				user.setRegisteredOn(users.getTimestamp("registeredon"));
				user.setStatus(users.getString("status"));
				user.setAddressLine(users.getString("addressLine"));
				user.setCity(users.getString("city"));
				user.setState(users.getString("state"));
				user.setCountry(users.getString("country"));
				user.setWebsite(users.getString("website"));
				connection = CommonStorage.getConnection();
				PreparedStatement rolesStmnt = connection.prepareStatement(
						"SELECT U.username AS username, G.code as code, G.textValue as role, G.englishName as englishName, G.localName AS localName FROM userGroup G, userRole R, userAccount U WHERE G.code = R.userGroup AND R.userAccount = U.id AND U.status = 'active' AND G.status = 'active' AND U.id=?");
				rolesStmnt.setLong(1, user.getId());
				ResultSet roles = rolesStmnt.executeQuery();
				while (roles.next()) {
					Role r = new Role();
					r.setCode(roles.getByte("code"));
					r.setEnglishName(roles.getString("englishName"));
					r.setLocalName(roles.getString("localName"));
					r.setTextValue(roles.getString("role"));
					user.addRole(r);
				}
				returnValue.add(user);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllUsers() ");
		}
		return returnValue;
	}

	/**
	 *
	 * @param userId - id of the user
	 * @return a user object if the user found, otherwise null
	 */
	@Override
	public User getUser(long userId) {
		User returnValue = null;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement usersStmnt = connection.prepareStatement("SELECT * FROM useraccount WHERE id=?");
			usersStmnt.setLong(1, userId);
			ResultSet users = usersStmnt.executeQuery();
			if (users.next()) {
				returnValue = new CurrentUserDTO();
				returnValue.setId(users.getLong("id"));
				returnValue.setUsername(users.getString("username"));
				returnValue.setFirstName(users.getString("firstName"));
				returnValue.setFathersName(users.getString("fathersName"));
				returnValue.setGrandfathersName(users.getString("grandfathersName"));
				returnValue.setSex(users.getString("sex"));
				returnValue.setEmail(users.getString("email"));
				returnValue.setPrimaryPhoneNo(users.getString("primaryphoneno"));
				returnValue.setSecondaryPhoneNo(users.getString("secondaryphoneno"));
				returnValue.setSignature(users.getBytes("signature"));
				returnValue.setSignatureFormat(users.getString("signatureFormat"));
				returnValue.setPhoto(users.getBytes("photo"));
				returnValue.setPhotoFormat(users.getString("photoFormat"));
				returnValue.setRegisteredOn(users.getTimestamp("registeredon"));
				returnValue.setStatus(users.getString("status"));
				returnValue.setAddressLine(users.getString("addressLine"));
				returnValue.setCity(users.getString("city"));
				returnValue.setState(users.getString("state"));
				returnValue.setCountry(users.getString("country"));
				returnValue.setWebsite(users.getString("website"));
				connection = CommonStorage.getConnection();
				PreparedStatement rolesStmnt = connection.prepareStatement(
						"SELECT U.username AS username, G.code as code, G.textValue as role, G.englishName as englishName, G.localName AS localName FROM userGroup G, userRole R, userAccount U WHERE G.code = R.userGroup AND R.userAccount = U.id AND U.status = 'active' AND G.status = 'active' AND U.id=?");
				rolesStmnt.setLong(1, returnValue.getId());
				ResultSet roles = rolesStmnt.executeQuery();
				while (roles.next()) {
					Role r = new Role();
					r.setCode(roles.getByte("code"));
					r.setEnglishName(roles.getString("englishName"));
					r.setLocalName(roles.getString("localName"));
					r.setTextValue(roles.getString("role"));
					returnValue.addRole(r);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getUser() ");
		}
		return returnValue;
	}

	/**
	 * @param userId - id of the user to delete
	 * @return username of the deleted user or null if a user with the specified
	 *         user id does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteUser(long userId) {
		String returnValue = null;
		User user = getUser(userId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement usersStmnt = connection
					.prepareStatement("UPDATE useraccount SET status = 'deleted' WHERE id=?");
			usersStmnt.setLong(1, userId);
			usersStmnt.executeUpdate();
			returnValue = user.getUsername();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllUsers() ");
		}
		return returnValue;
	}

	/**
	 * Checks if a username exists in the database
	 *
	 * @param username - The username to be checked
	 * @return true if the username exists, otherwise false
	 */
	@Override
	public boolean userNameExists(String username) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement usersStmnt = connection
					.prepareStatement("SELECT * FROM useraccount WHERE username=? AND status='active'");
			usersStmnt.setString(1, username);
			ResultSet users = usersStmnt.executeQuery();
			if (users.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:userNameExists() ");
		}
		return returnValue;
	}

	/**
	 * Checks if a username exists in the database that does not have the Id
	 * specified
	 *
	 * @param username - The username to be checked
	 * @param id       - The user id to exclude form the checking
	 * @return true if the username exists, otherwise false
	 */
	@Override
	public boolean userNameExists(String username, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement usersStmnt = connection
					.prepareStatement("SELECT * FROM useraccount WHERE username=? AND status='active' AND id<>?");
			usersStmnt.setString(1, username);
			usersStmnt.setLong(2, id);
			ResultSet users = usersStmnt.executeQuery();
			if (users.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:userNameExists() ");
		}
		return returnValue;
	}

	/**
	 *
	 * @return all user role types in the system
	 */
	@Override
	public ArrayList<Role> getAllRoles() {
		ArrayList<Role> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement rolesStmnt = connection
					.prepareStatement("SELECT * FROM userGroup WHERE status = 'active'");
			ResultSet roles = rolesStmnt.executeQuery();
			while (roles.next()) {
				Role role = new Role();
				role.setCode(roles.getByte("code"));
				role.setEnglishName(roles.getString("englishName"));
				role.setLocalName(roles.getString("localName"));
				role.setTextValue(roles.getString("textValue"));
				returnValue.add(role);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllRoles() ");
		}
		return returnValue;
	}

	/**
	 *
	 * @return a specific user role type from the system
	 */
	@Override
	public Role getRole(int id) {
		Role returnValue = null;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement roleStmnt = connection
					.prepareStatement("SELECT * FROM userGroup WHERE status = 'active' AND code = ?");
			roleStmnt.setInt(1, id);
			ResultSet roles = roleStmnt.executeQuery();
			if (roles.next()) {
				returnValue = new Role();
				returnValue.setCode(roles.getByte("code"));
				returnValue.setEnglishName(roles.getString("englishName"));
				returnValue.setLocalName(roles.getString("localName"));
				returnValue.setTextValue(roles.getString("textValue"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllRoles() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a user object to the database
	 *
	 * @param user a model object representing the user data to be saved
	 * @throws java.lang.Exception
	 */
	@Override
	public void save(User user) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO userAccount( username, password, firstname,fathersname, grandfathersname,"
					+ "sex,email,primaryPhoneNo,secondaryPhoneNo, signature, photo, signatureFormat, photoFormat,"
					+ "website, addressLine, city, state, country, registeredOn, registeredBy, status) "
					+ "VALUES (?, (SELECT MD5(?)), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setString(1, user.getUsername());
			stmnt.setString(2, user.getPassword());
			stmnt.setString(3, user.getFirstName());
			stmnt.setString(4, user.getFathersName());
			stmnt.setString(5, user.getGrandfathersName());
			stmnt.setString(6, user.getSex());
			stmnt.setString(7, user.getEmail());
			stmnt.setString(8, user.getPrimaryPhoneNo());
			stmnt.setString(9, user.getSecondaryPhoneNo());
			stmnt.setBytes(10, user.getSignature());
			stmnt.setBytes(11, user.getPhoto());
			stmnt.setString(12, user.getSignatureFormat());
			stmnt.setString(13, user.getPhotoFormat());
			stmnt.setString(14, user.getWebsite());
			stmnt.setString(15, user.getAddressLine());
			stmnt.setString(16, user.getCity());
			stmnt.setString(17, user.getState());
			stmnt.setString(18, user.getCountry());
			stmnt.setTimestamp(19, user.getRegisteredOn());
			stmnt.setLong(20, user.getRegisteredBy());
			stmnt.setString(21, user.getStatus());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving user to database returend zero(0)");
			}
			try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getLong(1));
				}
			}
			query = "INSERT INTO userRole(userAccount, userGroup) VALUES (?, ?)";
			for (int i = 0; i < user.getRoles().size(); i++) {
				Role role = user.getRoles().get(i);
				stmnt = connection.prepareStatement(query);
				stmnt.setLong(1, user.getId());
				stmnt.setByte(2, role.getCode());
				result = stmnt.executeUpdate();
				if (result < 1) {
					throw new Exception("Saving user role assignment to database returend zero(0)");
				}
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 *
	 * Updates a user object in the database
	 *
	 * @param user - A User object representing the data to be updated
	 * @throws java.lang.Exception
	 */
	@Override
	public void update(User user, boolean changePassword) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE userAccount SET username = ? ,firstname = ? ,fathersname = ?,"
					+ "grandfathersname = ? , sex = ? , email = ? ,primaryPhoneNo = ? ,secondaryPhoneNo = ?,"
					+ "signature = ? ,photo = ? ,signatureFormat = ? , photoFormat = ? ,website = ?,"
					+ "addressLine = ? ,city = ? ,state = ? ,country = ? ,status = ? "
					+ (changePassword ? ",password = (SELECT MD5(?))" : "") + " WHERE id=? ";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, user.getUsername());
			stmnt.setString(2, user.getFirstName());
			stmnt.setString(3, user.getFathersName());
			stmnt.setString(4, user.getGrandfathersName());
			stmnt.setString(5, user.getSex());
			stmnt.setString(6, user.getEmail());
			stmnt.setString(7, user.getPrimaryPhoneNo());
			stmnt.setString(8, user.getSecondaryPhoneNo());
			stmnt.setBytes(9, user.getSignature());
			stmnt.setBytes(10, user.getPhoto());
			stmnt.setString(11, user.getSignatureFormat());
			stmnt.setString(12, user.getPhotoFormat());
			stmnt.setString(13, user.getWebsite());
			stmnt.setString(14, user.getAddressLine());
			stmnt.setString(15, user.getCity());
			stmnt.setString(16, user.getState());
			stmnt.setString(17, user.getCountry());
			stmnt.setString(18, user.getStatus());
			if (changePassword) {
				stmnt.setString(19, user.getPassword());
				stmnt.setLong(20, user.getId());
			} else {
				stmnt.setLong(19, user.getId());
			}
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Update user in database returend zero(0)");
			}
			query = "DELETE FROM userRole WHERE userAccount = ?";
			stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, user.getId());
			result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Removing role while updating user account in the database returend zero(0)");
			}
			query = "INSERT INTO userRole(userAccount, userGroup) VALUES (?, ?)";
			for (int i = 0; i < user.getRoles().size(); i++) {
				Role role = user.getRoles().get(i);
				stmnt = connection.prepareStatement(query);
				stmnt.setLong(1, user.getId());
				stmnt.setByte(2, role.getCode());
				result = stmnt.executeUpdate();
				if (result < 1) {
					throw new Exception("Saving user role assignment to database returend zero(0)");
				}
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @return all branch types in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<BranchType> getAllBranchTypes() {
		ArrayList<BranchType> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchTypesStmnt = connection
					.prepareStatement("SELECT * FROM branchtype WHERE status = '" + Constants.STATUS_ACTIVE + "'");
			ResultSet branchTypes = branchTypesStmnt.executeQuery();
			while (branchTypes.next()) {
				BranchType branchType = new BranchType();
				branchType.setCode(branchTypes.getByte("id"));
				branchType.setEnglishName(branchTypes.getString("englishName"));
				branchType.setLocalName(branchTypes.getString("localName"));
				branchType.setTextValue(branchTypes.getString("textValue"));
				branchType.setScRate(branchTypes.getDouble("rate"));
				branchType.setStatus(branchTypes.getString("status"));
				returnValue.add(branchType);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getBranchTypes() ");
		}
		return returnValue;
	}

	/**
	 * @return a single branch type in the system
	 * @param id - id of the branch type to return
	 * @since version 1.0
	 */
	@Override
	public BranchType getBranchType(byte id) {
		BranchType returnValue = new BranchType();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchTypesStmnt = connection.prepareStatement(
					"SELECT * FROM branchtype WHERE status = '" + Constants.STATUS_ACTIVE + "' AND id = ?");
			branchTypesStmnt.setInt(1, id);
			ResultSet branchTypes = branchTypesStmnt.executeQuery();
			if (branchTypes.next()) {
				returnValue.setCode(branchTypes.getByte("id"));
				returnValue.setEnglishName(branchTypes.getString("englishName"));
				returnValue.setLocalName(branchTypes.getString("localName"));
				returnValue.setTextValue(branchTypes.getString("textValue"));
				returnValue.setScRate(branchTypes.getDouble("rate"));
				returnValue.setStatus(branchTypes.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getBranchType(byte) ");
		}
		return returnValue;
	}

	/**
	 * @return all branch types in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<BranchRegion> getAllBranchRegions() {
		ArrayList<BranchRegion> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchTypesStmnt = connection
					.prepareStatement("SELECT * FROM BranchRegion WHERE status = '" + Constants.STATUS_ACTIVE + "'");
			ResultSet branchRegions = branchTypesStmnt.executeQuery();
			while (branchRegions.next()) {
				BranchRegion branchRegion = new BranchRegion();
				branchRegion.setCode(branchRegions.getByte("id"));
				branchRegion.setEnglishName(branchRegions.getString("englishName"));
				branchRegion.setLocalName(branchRegions.getString("localName"));
				branchRegion.setTextValue(branchRegions.getString("textValue"));
				branchRegion.setStatus(branchRegions.getString("status"));
				returnValue.add(branchRegion);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getBranchRegions() ");
		}
		return returnValue;
	}

	/**
	 * @return a region in the system
	 * @param id of the region to return
	 * @since version 1.0
	 */
	@Override
	public BranchRegion getRegion(byte id) {
		BranchRegion returnValue = new BranchRegion();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement regionStmnt = connection.prepareStatement(
					"SELECT * FROM BranchRegion WHERE status = '" + Constants.STATUS_ACTIVE + "' AND id=?");
			regionStmnt.setByte(1, id);
			ResultSet regions = regionStmnt.executeQuery();
			if (regions.next()) {
				returnValue.setCode(regions.getByte("id"));
				returnValue.setEnglishName(regions.getString("englishName"));
				returnValue.setLocalName(regions.getString("localName"));
				returnValue.setTextValue(regions.getString("textValue"));
				returnValue.setStatus(regions.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getRegions() ");
		}
		return returnValue;
	}
	
	/**
	 * @return all branches in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Branch> getAllBranches(BranchesParamModel param){
		ArrayList<Branch> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = "";
			if(param.sSearch!=null) {
				searchString = CommonTasks.escape(param.sSearch);
			}
			String query = "SELECT B.*, R.localName as regionLocalName, "
					+ "R.englishName as regionName, T.localName as typeLocalName, "
					+ "T.englishName as typeName, U.username as registeredByName  "
					+ "FROM BranchType T, BranchRegion R,Branch B, UserAccount U "
					+ "WHERE B.type=T.id AND B.region = R.id AND B.registeredBy = U.id "
					+ "AND B.status <> '" + Constants.STATUS_DELETED + "' AND T.status <> '" + Constants.STATUS_DELETED + "' "
					+ "AND U.status <> '" + Constants.STATUS_DELETED + "' AND R.status <> '" + Constants.STATUS_DELETED + "' ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( B.name ILIKE '%" + searchString + "%' OR R.englishName ILIKE '%"
							+ searchString + "%' OR T.englishName ILIKE '%"+searchString+"%') "
					: "");
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement branchesStmnt = connection.prepareStatement(query);
			ResultSet branches = branchesStmnt.executeQuery();
			while (branches.next()) {
				Branch branch = new Branch();
				branch.setAddressLine(branches.getString("addressLine"));
				branch.setCity(branches.getString("city"));
				branch.setEmail(branches.getString("email"));
				branch.setId(branches.getLong("id"));
				branch.setName(branches.getString("name"));
				branch.setContactPerson(branches.getString("contactPerson"));
				branch.setPrimaryPhoneNo(branches.getString("primaryPhoneNumber"));
				branch.setSecondaryPhoneNo(branches.getString("secondaryPhoneNumber"));
				branch.setRegion(branches.getInt("region"));
				branch.setRegionLocalName(branches.getString("regionLocalName"));
				branch.setRegionName(branches.getString("regionName"));
				branch.setRegisteredBy(branches.getLong("registeredby"));
				branch.setRegisteredByName(branches.getString("registeredByName"));
				branch.setRegisteredOn(branches.getTimestamp("registeredon"));
				branch.setRemark(branches.getString("remark"));
				branch.setStatus(branches.getString("status"));
				branch.setType(branches.getInt("type"));
				branch.setTypeLocalName(branches.getString("typeLocalName"));
				branch.setTypeName(branches.getString("typeName"));
				returnValue.add(branch);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllBranches() ");
		}
		return returnValue;
	}

	@Override
	public int getBranchesCount(BranchesParamModel param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT count(B.*) as c FROM BranchType T, BranchRegion R,Branch B, UserAccount U "
					+ "WHERE B.type=T.id AND B.region = R.id AND B.registeredBy = U.id "
					+ "AND B.status <> '" + Constants.STATUS_DELETED + "' AND T.status <> '" + Constants.STATUS_DELETED + "' "
					+ "AND U.status <> '" + Constants.STATUS_DELETED + "' AND R.status <> '" + Constants.STATUS_DELETED + "' ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( B.name ILIKE '%" + searchString + "%' OR R.englishName ILIKE '%"
							+ searchString + "%' OR T.englishName ILIKE '%"+searchString+"%') "
					: "");
			PreparedStatement branchesStmnt = connection.prepareStatement(query);
			ResultSet branches = branchesStmnt.executeQuery();
			if (branches.next()) {
				returnValue = branches.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getBranchesCount() ");
		}
		return returnValue;
	}
	/**
	 * Checks if a branchName exists in the database
	 *
	 * @param branchName - The branchName to be checked
	 * @param type       - The type of branch(Bank) to be checked
	 * @return true if the username exists, otherwise false
	 */
	@Override
	public boolean branchNameExists(String branchName, int type) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchesStmnt = connection.prepareStatement(
					"SELECT * FROM Branch WHERE name=? AND status='" + Constants.STATUS_ACTIVE + "' AND type=?");
			branchesStmnt.setString(1, branchName);
			branchesStmnt.setInt(2, type);
			ResultSet branches = branchesStmnt.executeQuery();
			if (branches.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:branchNameExists(branchName,type) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a branch exists in the database that does not have the Id specified
	 *
	 * @param branchName - The branchName to be checked
	 * @param type       - The type of branch(Bank) to be checked
	 * @param id         - The branch id to exclude form the checking
	 * @return true if the branchName exists, otherwise false
	 */
	@Override
	public boolean branchNameExists(String branchName, int type, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchesStmnt = connection
					.prepareStatement("SELECT * FROM Branch WHERE name=? AND status='" + Constants.STATUS_ACTIVE
							+ "' AND type=? AND id<>? ");
			branchesStmnt.setString(1, branchName);
			branchesStmnt.setInt(2, type);
			branchesStmnt.setLong(3, id);
			ResultSet branches = branchesStmnt.executeQuery();
			if (branches.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:branchNameExists(branchName,type, id) ");
		}
		return returnValue;
	}

	/**
	 * @return The specified branch if found, null otherwise
	 * @param id of the branch to return
	 * @since version 1.0
	 */
	@Override
	public Branch getBranch(long id) {
		Branch returnValue = new Branch();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchesStmnt = connection.prepareStatement(
					"SELECT B.*, R.localName as regionLocalName, R.englishName as regionName, T.localName as typeLocalName, T.englishName as typeName, U.username as registeredByName FROM BranchType T, BranchRegion R,Branch B, UserAccount U WHERE B.type=T.id AND B.region = R.id AND B.registeredBy = U.id AND B.status = '"
							+ Constants.STATUS_ACTIVE + "' AND B.id = ?");
			branchesStmnt.setLong(1, id);
			ResultSet branches = branchesStmnt.executeQuery();
			if (branches.next()) {
				returnValue.setAddressLine(branches.getString("addressLine"));
				returnValue.setCity(branches.getString("city"));
				returnValue.setEmail(branches.getString("email"));
				returnValue.setId(branches.getLong("id"));
				returnValue.setName(branches.getString("name"));
				returnValue.setContactPerson(branches.getString("contactPerson"));
				returnValue.setPrimaryPhoneNo(branches.getString("primaryPhoneNumber"));
				returnValue.setSecondaryPhoneNo(branches.getString("secondaryPhoneNumber"));
				returnValue.setRegion(branches.getInt("region"));
				returnValue.setRegionLocalName(branches.getString("regionLocalName"));
				returnValue.setRegionName(branches.getString("regionName"));
				returnValue.setRegisteredBy(branches.getLong("registeredby"));
				returnValue.setRegisteredByName(branches.getString("registeredByName"));
				returnValue.setRegisteredOn(branches.getTimestamp("registeredon"));
				returnValue.setRemark(branches.getString("remark"));
				returnValue.setStatus(branches.getString("status"));
				returnValue.setType(branches.getInt("type"));
				returnValue.setTypeLocalName(branches.getString("typeLocalName"));
				returnValue.setTypeName(branches.getString("typeName"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getBranch(id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a branch object to the database
	 *
	 * @param branch - a model object representing the branch data to be saved
	 * @throws java.lang.Exception
	 */
	@Override
	public void save(Branch branch) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO public.branch(name, contactperson, type, region, email, addressline, city, "
					+ "remark, primaryphonenumber, secondaryphonenumber, registeredon, registeredby, status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, branch.getName());
			stmnt.setString(2, branch.getContactPerson());
			stmnt.setInt(3, branch.getType());
			if (branch.getRegion() > 0) {
				stmnt.setInt(4, branch.getRegion());
			} else {
				stmnt.setNull(4, Types.INTEGER);
			}
			stmnt.setString(5, branch.getEmail());
			stmnt.setString(6, branch.getAddressLine());
			stmnt.setString(7, branch.getCity());
			stmnt.setString(8, branch.getRemark());
			stmnt.setString(9, branch.getPrimaryPhoneNo());
			stmnt.setString(10, branch.getSecondaryPhoneNo());
			stmnt.setTimestamp(11, branch.getRegisteredOn());
			stmnt.setLong(12, branch.getRegisteredBy());
			stmnt.setString(13, branch.getStatus());

			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving branch to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 *
	 * Updates a branch object in the database
	 *
	 * @param branch - A Branch object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Branch branch) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE public.branch SET name = ?, contactperson = ?, type = ?, region = ?, email = ?, "
					+ "addressline = ?, city = ?, remark = ?, primaryphonenumber = ?, secondaryphonenumber = ?, "
					+ "registeredon = ?, registeredby = ?, status = ? WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, branch.getName());
			stmnt.setString(2, branch.getContactPerson());
			stmnt.setInt(3, branch.getType());
			stmnt.setInt(4, branch.getRegion());
			stmnt.setString(5, branch.getEmail());
			stmnt.setString(6, branch.getAddressLine());
			stmnt.setString(7, branch.getCity());
			stmnt.setString(8, branch.getRemark());
			stmnt.setString(9, branch.getPrimaryPhoneNo());
			stmnt.setString(10, branch.getSecondaryPhoneNo());
			stmnt.setTimestamp(11, branch.getRegisteredOn());
			stmnt.setLong(12, branch.getRegisteredBy());
			stmnt.setString(13, branch.getStatus());
			stmnt.setLong(14, branch.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating branch to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @param branchId - id of the branch to delete
	 * @return name of the deleted branch or null if a branch with the specified id
	 *         does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteBranch(long branchId) {
		String returnValue = null;
		Branch branch = getBranch(branchId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement branchsStmnt = connection.prepareStatement(
					"UPDATE public.branch  SET status = '" + Constants.STATUS_DELETED + "' WHERE id=?");
			branchsStmnt.setLong(1, branchId);
			branchsStmnt.executeUpdate();
			returnValue = branch.getName();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deleteBranch() ");
		}
		return returnValue;
	}

	/**
	 * @return all clients in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Client> getAllClients() {
		return getAllClients("name");
	}

	/**
	 * @return all clients in the system
	 * @param sortKey the column to sort by
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Client> getAllClients(String sortKey) {
		return getAllClients(sortKey, true);
	}

	/**
	 * @return all clients in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Client> getAllClients(String sortKey, boolean ascending) {
		ArrayList<Client> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection.prepareStatement(
					"SELECT C.*, U.username as registeredByName FROM compensation.Client C, UserAccount U WHERE C.registeredBy = U.id AND C.status = '"
							+ Constants.STATUS_ACTIVE + "'");
			ResultSet clients = clientsStmnt.executeQuery();
			while (clients.next()) {
				Client client = new Client();
				client.setAddressLine(clients.getString("addressLine"));
				client.setCity(clients.getString("city"));
				client.setEmail(clients.getString("email"));
				client.setId(clients.getLong("id"));
				client.setName(clients.getString("name"));
				client.setServiceChargeRate(clients.getDouble("rate"));
				client.setAmharicName(clients.getString("amharicName"));
				client.setContactPerson(clients.getString("contactPerson"));
				client.setPhoneNo(clients.getString("phoneNumber"));
				client.setRegionId(clients.getByte("region"));
				client.setRegisteredBy(getUser(clients.getLong("registeredby")));
				client.setRegisteredByName(clients.getString("registeredByName"));
				client.setRegisteredOn(clients.getTimestamp("registeredon"));
				client.setRemark(clients.getString("remark"));
				client.setStatus(clients.getString("status"));
				returnValue.add(client);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllRoles() ");
		}
		return returnValue;
	}

	/**
	 * Checks if a client exists in the database
	 *
	 * @param name - Name of the client be checked
	 * @return true if the name exists, otherwise false
	 */
	@Override
	public boolean clientExists(String name) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection.prepareStatement(
					"SELECT * FROM compensation.Client WHERE name=? AND status='" + Constants.STATUS_ACTIVE + "' ");
			clientsStmnt.setString(1, name);
			ResultSet branches = clientsStmnt.executeQuery();
			if (branches.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:clientExists(name) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a client exists in the database that does not have the Id specified
	 *
	 * @param name - Name of the client be checked
	 * @param id   - The client id to exclude form the checking
	 * @return true if the name exists, otherwise false
	 */
	@Override
	public boolean clientExists(String name, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.Client WHERE name=? AND status='"
							+ Constants.STATUS_ACTIVE + "' AND id<>? ");
			clientsStmnt.setString(1, name);
			clientsStmnt.setLong(2, id);
			ResultSet branches = clientsStmnt.executeQuery();
			if (branches.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:clientExists(name, id) ");
		}
		return returnValue;
	}

	/**
	 * @return a client with the specified id
	 * @param id - of the client to return
	 * @since version 1.0
	 */
	@Override
	public Client getClient(long id) {
		Client returnValue = new Client();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection.prepareStatement(
					"SELECT C.*, U.username as registeredByName FROM compensation.Client C, UserAccount U WHERE C.registeredBy = U.id AND C.status = '"
							+ Constants.STATUS_ACTIVE + "' AND C.id=?");
			clientsStmnt.setLong(1, id);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue.setAddressLine(clients.getString("addressLine"));
				returnValue.setCity(clients.getString("city"));
				returnValue.setEmail(clients.getString("email"));
				returnValue.setId(clients.getLong("id"));
				returnValue.setName(clients.getString("name"));
				returnValue.setAmharicName(clients.getString("amharicName"));
				returnValue.setWebsite(clients.getString("website"));
				returnValue.setContactPerson(clients.getString("contactPerson"));
				returnValue.setPhoneNo(clients.getString("phoneNumber"));
				returnValue.setServiceChargeRate(clients.getDouble("rate"));
				returnValue.setRegion(getRegion(clients.getByte("region")));
				returnValue.setRegisteredBy(getUser(clients.getLong("registeredby")));
				returnValue.setRegisteredByName(clients.getString("registeredByName"));
				returnValue.setRegisteredOn(clients.getTimestamp("registeredon"));
				returnValue.setRemark(clients.getString("remark"));
				returnValue.setStatus(clients.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClient(id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a client object to the database
	 *
	 * @param client - A Client object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(Client client) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.client(name, amharicName, contactperson, "
					+ "remark, phonenumber, email, website, addressline, city, region, registeredon, "
					+ "registeredby, status) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, client.getName());
			stmnt.setString(2, client.getAmharicName());
			stmnt.setString(3, client.getContactPerson());
			stmnt.setString(4, client.getRemark());
			stmnt.setString(5, client.getPhoneNo());
			stmnt.setString(6, client.getEmail());
			stmnt.setString(7, client.getWebsite());
			stmnt.setString(8, client.getAddressLine());
			stmnt.setString(9, client.getCity());
			stmnt.setByte(10, client.getRegion().getCode());
			stmnt.setTimestamp(11, client.getRegisteredOn());
			stmnt.setLong(12, client.getRegisteredBy().getId());
			stmnt.setString(13, client.getStatus());

			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving Client to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 *
	 * Updates a client object in the database
	 *
	 * @param client - A Client object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Client client) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.client SET name = ?, amharicName = ?, contactperson = ?, remark = ?, "
					+ "phonenumber = ?, email = ?, website = ?, addressline = ?, city = ?, region = ?, "
					+ "registeredon = ?, registeredby = ?, status = ? WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, client.getName());
			stmnt.setString(2, client.getAmharicName());
			stmnt.setString(3, client.getContactPerson());
			stmnt.setString(4, client.getRemark());
			stmnt.setString(5, client.getPhoneNo());
			stmnt.setString(6, client.getEmail());
			stmnt.setString(7, client.getWebsite());
			stmnt.setString(8, client.getAddressLine());
			stmnt.setString(9, client.getCity());
			stmnt.setByte(10, client.getRegion().getCode());
			stmnt.setTimestamp(11, client.getRegisteredOn());
			stmnt.setLong(12, client.getRegisteredBy().getId());
			stmnt.setString(13, client.getStatus());
			stmnt.setLong(14, client.getId());

			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating Client to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @param clientId - id of the client to delete
	 * @return name of the deleted client or null if a client with the specified
	 *         client id does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteClients(long clientId) {
		String returnValue = null;
		Client client = getClient(clientId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection
					.prepareStatement("UPDATE compensation.client SET status = 'deleted' WHERE id=?");
			clientsStmnt.setLong(1, clientId);
			clientsStmnt.executeUpdate();
			returnValue = client.getName();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deleteClients() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a client service charge to the database
	 *
	 * @param scRate - A Client Service Charge Rate object representing the data to
	 *               be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(ClientServiceChargeRate scRate) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.clientservicechargerate(client, startdate,"
					+ " enddate,rate, registeredon, registeredby, status) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, scRate.getClient().getId());
			stmnt.setDate(2, scRate.getStartDate());
			stmnt.setDate(3, scRate.getEndDate());
			stmnt.setDouble(4, scRate.getRate());
			stmnt.setTimestamp(5, scRate.getRegisteredOn());
			stmnt.setLong(6, scRate.getRegisteredBy().getId());
			stmnt.setString(7, scRate.getStatus());

			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving Client Service Charge Rate to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @param clientRegionId - id of the client region to delete
	 * @return name of the deleted client region or null if a client region with the
	 *         specified client region id does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteClientRegions(long clientRegionId) {
		String returnValue = null;
		ClientRegion clientRegion = getClientRegion(clientRegionId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientRegionsStmnt = connection
					.prepareStatement("UPDATE compensation.clientregion SET status = 'deleted' WHERE id=?");
			clientRegionsStmnt.setLong(1, clientRegionId);
			clientRegionsStmnt.executeUpdate();
			returnValue = clientRegion.getRegionName();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deleteClientRegions() ");
		}
		return returnValue;
	}

	/**
	 * @return all client region types in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<ClientRegion> getAllClientRegions(ClientRegionsParamModel param){
		ArrayList<ClientRegion> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT CR.*, C.name as clientName "
					+ "FROM compensation.ClientRegion CR, compensation.Client C "
					+ "WHERE CR.client = C.id "
					+ "AND C.status <> '" + Constants.STATUS_DELETED + "' AND CR.status <> '" + Constants.STATUS_DELETED + "' ";
			query += param.getClientId() >0?" AND CR.client = "+ param.getClientId()+" ":"";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( CR.regionname ILIKE '%" + searchString + "%' OR CR.contactperson ILIKE '%"
							+ searchString + "%' OR CR.phonenumber ILIKE '%"+searchString+"%' "
									+ "OR CR.addressline ILIKE '%" + searchString + "%') "
					: "");
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement branchesStmnt = connection.prepareStatement(query);
			ResultSet clientRegions = branchesStmnt.executeQuery();
			while (clientRegions.next()) {
				ClientRegion clientRegion = new ClientRegion();
				clientRegion.setId(clientRegions.getLong("id"));
				clientRegion.setAddressLine(clientRegions.getString("addressLine"));
				clientRegion.setClientId(clientRegions.getLong("client"));
				clientRegion.setClientName(clientRegions.getString("clientName"));
				clientRegion.setContactPerson(clientRegions.getString("contactPerson"));
				clientRegion.setAmharicName(clientRegions.getString("amharicName"));
				clientRegion.setPhoneNumber(clientRegions.getString("phoneNumber"));
				clientRegion.setRegionName(clientRegions.getString("regionName"));
				clientRegion.setRegisteredById(clientRegions.getLong("registeredby"));
				clientRegion.setRegisteredOn(clientRegions.getTimestamp("registeredon"));
				clientRegion.setRemark(clientRegions.getString("remark"));
				clientRegion.setStatus(clientRegions.getString("status"));
				returnValue.add(clientRegion);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllBranches() ");
		}
		return returnValue;
	}

	@Override
	public long getClientRegionCount(ClientRegionsParamModel param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT count(CR.*) as c "
					+ "FROM compensation.ClientRegion CR, compensation.Client C "
					+ "WHERE CR.client = C.id "
					+ "AND C.status <> '" + Constants.STATUS_DELETED + "' AND CR.status <> '" + Constants.STATUS_DELETED + "' ";
			query += param.getClientId() >0?" AND CR.client = "+ param.getClientId()+" ":"";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( CR.regionname ILIKE '%" + searchString + "%' OR CR.contactperson ILIKE '%"
							+ searchString + "%' OR CR.phonenumber ILIKE '%"+searchString+"%' "
									+ "OR CR.addressline ILIKE '%" + searchString + "%') "
					: "");
			PreparedStatement clientRegionsStmnt = connection.prepareStatement(query);
			ResultSet clientRegions = clientRegionsStmnt.executeQuery();
			if (clientRegions.next()) {
				returnValue = clientRegions.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getBranchesCount() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a client region object to the database
	 *
	 * @param clientRegion - A ClientRegion object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(ClientRegion clientRegion) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.ClientRegion(client,regionName, amharicName, contactPerson, phoneNumber, addressLine, remark, "
					+ "registeredon, registeredby, status) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, clientRegion.getClient().getId());
			stmnt.setString(2, clientRegion.getRegionName());
			stmnt.setString(3, clientRegion.getAmharicName());
			stmnt.setString(4, clientRegion.getContactPerson());
			stmnt.setString(5, clientRegion.getPhoneNumber());
			stmnt.setString(6, clientRegion.getAddressLine());
			stmnt.setString(7, clientRegion.getRemark());
			stmnt.setTimestamp(8, clientRegion.getRegisteredOn());
			stmnt.setLong(9, clientRegion.getRegisteredBy().getId());
			stmnt.setString(10, clientRegion.getStatus());

			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving Client Region to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @return a client regions in the system with the specified id
	 * @param id - of the client region to return
	 * @since version 1.0
	 */
	@Override
	public ClientRegion getClientRegion(long id) {
		ClientRegion returnValue = new ClientRegion();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientRegionsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.ClientRegion WHERE status <> '"
							+ Constants.STATUS_DELETED + "' AND id=?");
			clientRegionsStmnt.setLong(1, id);
			ResultSet clientRegions = clientRegionsStmnt.executeQuery();
			if (clientRegions.next()) {
				returnValue.setId(clientRegions.getLong("id"));
				returnValue.setAddressLine(clientRegions.getString("addressLine"));
				returnValue.setClient(getClient(clientRegions.getLong("client")));
				returnValue.setContactPerson(clientRegions.getString("contactPerson"));
				returnValue.setPhoneNumber(clientRegions.getString("phoneNumber"));
				returnValue.setRegionName(clientRegions.getString("regionName"));
				returnValue.setAmharicName(clientRegions.getString("amharicName"));
				returnValue.setRegisteredBy(getUser(clientRegions.getLong("registeredby")));
				returnValue.setRegisteredOn(clientRegions.getTimestamp("registeredon"));
				returnValue.setRemark(clientRegions.getString("remark"));
				returnValue.setStatus(clientRegions.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClientRegion(id) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a client region exists in the database
	 *
	 * @param regionName - The project name to be checked
	 * @param client     - The client the region belongs to
	 * @return true if the regionName exists, otherwise false
	 */
	@Override
	public boolean clientRegionExists(String regionName, long client) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.ClientRegion WHERE regionName=? AND status='"
							+ Constants.STATUS_ACTIVE + "' AND client=? ");
			clientsStmnt.setString(1, regionName);
			clientsStmnt.setLong(2, client);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:clientRegionExists(regionName, client) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a client region exists in the database that does not have the Id
	 *
	 * @param regionName - The project name to be checked
	 * @param client     - The client the region belongs to
	 * @param id         - The client region id to exclude form the checking
	 * @return true if the regionName exists, otherwise false
	 */
	@Override
	public boolean clientRegionExists(String regionName, long client, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.ClientRegion WHERE regionName=? AND status='"
							+ Constants.STATUS_ACTIVE + "' AND client=? AND id<>? ");
			clientsStmnt.setString(1, regionName);
			clientsStmnt.setLong(2, client);
			clientsStmnt.setLong(3, id);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:clientRegionExists(regionName, client, id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Updates a clientRegion object in the database
	 *
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(ClientRegion clientRegion) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.ClientRegion SET client = ?, regionName = ?, amharicName = ?, contactPerson = ?, "
					+ "phoneNumber = ?, addressLine = ?, remark = ?, registeredon = ?, registeredby = ?, status = ? WHERE "
					+ "id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, clientRegion.getClient().getId());
			stmnt.setString(2, clientRegion.getRegionName());
			stmnt.setString(3, clientRegion.getAmharicName());
			stmnt.setString(4, clientRegion.getContactPerson());
			stmnt.setString(5, clientRegion.getPhoneNumber());
			stmnt.setString(6, clientRegion.getAddressLine());
			stmnt.setString(7, clientRegion.getRemark());
			stmnt.setTimestamp(8, clientRegion.getRegisteredOn());
			stmnt.setLong(9, clientRegion.getRegisteredBy().getId());
			stmnt.setString(10, clientRegion.getStatus());
			stmnt.setLong(11, clientRegion.getId());

			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating Client Region to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * Checks if a projectName exists in the database
	 *
	 * @param projectName - The project name to be checked
	 * @param client      - The client of the project to be checked
	 * @return true if the projectName exists, otherwise false
	 */
	@Override
	public boolean projectExists(String projectName, String code, long client) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.Project WHERE (name=? AND code=?) AND status='"
							+ Constants.STATUS_ACTIVE + "' AND client=? ");
			clientsStmnt.setString(1, projectName);
			clientsStmnt.setString(2, code);
			clientsStmnt.setLong(3, client);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:projectExists(projectName, client) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a project exists in the database that does not have the Id
	 *
	 * @param projectName - The project name to be checked
	 * @param client      - The client of the project to be checked
	 * @param id          - The project id to exclude form the checking
	 * @return true if the projectName exists, otherwise false
	 */
	@Override
	public boolean projectExists(String projectName, String code, long client, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.Project WHERE (name=? OR code=?) AND status='"
							+ Constants.STATUS_ACTIVE + "' AND client=? AND id<>? ");
			clientsStmnt.setString(1, projectName);
			clientsStmnt.setString(2, code);
			clientsStmnt.setLong(3, client);
			clientsStmnt.setLong(4, id);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:projectExists(projectName, client, id) ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<Project> getAllProjects(JQueryDataTableParamModel param) {
		ArrayList<Project> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String query = "SELECT P.*, C.name as clientName FROM compensation.Project P, compensation.Client C WHERE P.status = '" + Constants.STATUS_ACTIVE + "' AND C.id = P.client ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( P.name ILIKE '%" + CommonTasks.escape(param.sSearch) + "%' OR P.amharicname ILIKE '%"
							+ CommonTasks.escape(param.sSearch) + "%' OR C.name ILIKE '%"+CommonTasks.escape(param.sSearch)+"%') "
					: "");
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement projectsStmnt = connection.prepareStatement(query);
			ResultSet projects = projectsStmnt.executeQuery();
			while (projects.next()) {
				Project project = new Project();
				project.setId(projects.getLong("id"));
				project.setClientId(projects.getLong("client"));
				project.setClientName(projects.getString("clientName"));
				project.setAmharicName(projects.getString("amharicName"));
				project.setName(projects.getString("name"));
				project.setCode(projects.getString("code"));
				project.setRegisteredId(projects.getLong("registeredby"));
				project.setRegisteredOn(projects.getTimestamp("registeredon"));
				project.setRemark(projects.getString("remark"));
				project.setStatus(projects.getString("status"));
				returnValue.add(project);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllProjects() ");
		}
		return returnValue;
	}

	@Override
	public int getProjectsCount(JQueryDataTableParamModel param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String query = "SELECT count(P.*) as c FROM compensation.Project P, compensation.Client C WHERE P.status = '" + Constants.STATUS_ACTIVE + "' AND C.id = P.client ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( P.name ILIKE '%" + CommonTasks.escape(param.sSearch) + "%' OR P.amharicname ILIKE '%"
							+ CommonTasks.escape(param.sSearch) + "%' OR C.name ILIKE '%"+CommonTasks.escape(param.sSearch)+"%') "
					: "");

			PreparedStatement projectsStmnt = connection.prepareStatement(query);
			ResultSet projects = projectsStmnt.executeQuery();
			if (projects.next()) {
				returnValue = projects.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllProjects() ");
		}
		return returnValue;
	}

	/**
	 * @return all projects in the system for a single client
	 * @param clientId id of the client to filter by
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Project> getAllProjects(long clientId) {
		return getAllProjects(clientId, "name");
	}

	/**
	 * @return all projects in the system for a single client
	 * @param sortKey  the column to sort by
	 * @param clientId id of the client to filter by
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Project> getAllProjects(long clientId, String sortKey) {
		ArrayList<Project> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement projectsStmnt = connection
					.prepareStatement("SELECT P.* FROM compensation.Project P WHERE P.status <> '"
							+ Constants.STATUS_DELETED + "' AND P.client=?");
			projectsStmnt.setLong(1, clientId);
			ResultSet projectsRegions = projectsStmnt.executeQuery();
			while (projectsRegions.next()) {
				Project project = new Project();
				project.setId(projectsRegions.getLong("id"));
				project.setClientId(projectsRegions.getLong("client"));
				project.setName(projectsRegions.getString("name"));
				project.setCode(projectsRegions.getString("code"));
				project.setRegisteredId(projectsRegions.getLong("registeredby"));
				project.setRegisteredOn(projectsRegions.getTimestamp("registeredon"));
				project.setRemark(projectsRegions.getString("remark"));
				project.setStatus(projectsRegions.getString("status"));
				returnValue.add(project);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllProjects() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a project region object to the database
	 *
	 * @param project - A Project object representing the data to be saved
	 * @return
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public Project save(Project project) throws Exception {
		Connection connection = CommonStorage.getConnection();
		String query = "INSERT INTO compensation.Project(client, name, amharicName, code, remark, registeredon, registeredby, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		stmnt.setLong(1, project.getClient().getId());
		stmnt.setString(2, project.getName());
		stmnt.setString(3, project.getAmharicName());
		stmnt.setString(4, project.getCode());
		stmnt.setString(5, project.getRemark());
		stmnt.setTimestamp(6, project.getRegisteredOn());
		stmnt.setLong(7, project.getRegisteredBy().getId());
		stmnt.setString(8, project.getStatus());

		int result = stmnt.executeUpdate();
		if (result < 1) {
			throw new Exception("Saving Project to database returend zero(0)");
		}
		try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				project.setId(generatedKeys.getLong(1));
			}
		}
		return project;
		/// TODO: Check if there is a need to log to activity log table.
	}

	/**
	 * @return a project in the system
	 * @param id of the document to return
	 * @since version 1.0
	 */
	@Override
	public Project getProject(long id) {
		Project returnValue = new Project();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement projectsStmnt = connection
					.prepareStatement("SELECT P.* FROM compensation.Project P WHERE P.status = '"
							+ Constants.STATUS_ACTIVE + "' AND P.id=?");
			projectsStmnt.setLong(1, id);
			ResultSet projects = projectsStmnt.executeQuery();
			if (projects.next()) {
				returnValue.setId(projects.getLong("id"));
				returnValue.setClientId(projects.getLong("client"));
				returnValue.setCode(projects.getString("code"));
				returnValue.setName(projects.getString("name").replace("\"", ""));
				returnValue.setAmharicName(projects.getString("amharicName"));
				returnValue.setRegisteredBy(getUser(projects.getLong("registeredby")));
				returnValue.setRegisteredOn(projects.getTimestamp("registeredon"));
				returnValue.setRemark(projects.getString("remark"));
				returnValue.setStatus(projects.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getProject(id) ");
		}
		return returnValue;
	}

	@Override
	public Project getProject(String projectCode) {
		Project returnValue = null;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement projectsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.Project WHERE status <> '" + Constants.STATUS_DELETED
							+ "' AND code = ?");
			projectsStmnt.setString(1, projectCode);
			ResultSet projects = projectsStmnt.executeQuery();
			if (projects.next()) {
				returnValue = new Project();
				returnValue.setId(projects.getLong("id"));
				returnValue.setClient(getClient(projects.getLong("client")));
				returnValue.setCode(projects.getString("code"));
				returnValue.setName(projects.getString("name"));
				returnValue.setAmharicName(projects.getString("amharicName"));
				returnValue.setRegisteredBy(getUser(projects.getLong("registeredby")));
				returnValue.setRegisteredOn(projects.getTimestamp("registeredon"));
				returnValue.setRemark(projects.getString("remark"));
				returnValue.setStatus(projects.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getProject(id) ");
		}
		return returnValue;
	}

	/**
	 * @param projectId - id of the project to delete
	 * @return name of the project or null if a project with the specified projectId
	 *         does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteProject(long projectId) {
		String returnValue = null;
		Project project = getProject(projectId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement projectsStmnt = connection
					.prepareStatement("UPDATE compensation.project SET status = 'deleted' WHERE id=?");
			projectsStmnt.setLong(1, projectId);
			projectsStmnt.executeUpdate();
			returnValue = project.getName();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deleteProject(long) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Updates a project object in the database
	 *
	 * @param project - A Project object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Project project) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.Project SET client = ?, name = ?, amharicName = ?,"
					+ "code = ?, remark = ?, registeredon = ?, registeredby = ?, status = ? WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, project.getClientId());
			stmnt.setString(2, project.getName());
			stmnt.setString(3, project.getAmharicName());
			stmnt.setString(4, project.getCode());
			stmnt.setString(5, project.getRemark());
			stmnt.setTimestamp(6, project.getRegisteredOn());
			stmnt.setLong(7, project.getRegisteredBy().getId());
			stmnt.setString(8, project.getStatus());
			stmnt.setLong(9, project.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating project to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @return all documents in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Document> getAllDocuments() {
		return getAllDocuments("incomingDate");
	}

	/**
	 * @return all documents in the system
	 * @param sortKey the column to sort by
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Document> getAllDocuments(String sortKey) {
		return getAllDocuments(sortKey, true);
	}

	/**
	 * @return all documents in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Document> getAllDocuments(String sortKey, boolean ascending) {
		ArrayList<Document> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection.prepareStatement(
					"SELECT *, P.code as projectCode, P.name as projectName, CR.regionname as clientRegionName, C.name as clientName, (SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = D.id AND status <> '"
							+ Constants.STATUS_DELETED
							+ "') as registeredAmount, (SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = D.id AND status = '"
							+ Constants.STATUS_FULLY_PAID
							+ "') AS paidAmount FROM compensation.document D, compensation.project P, compensation.client C, compensation.clientRegion CR WHERE D.project = p.id AND D.clientregion = CR.id AND CR.client = C.id AND D.status <> '"
							+ Constants.STATUS_DELETED + "' ");
			ResultSet documents = documentsStmnt.executeQuery();
			while (documents.next()) {
				Document document = new Document();
				document.setId(documents.getLong("id"));
				document.setClientRegionName(documents.getString("clientRegionName"));
				document.setProjectName(documents.getString("projectName"));
				document.setProjectCode(documents.getString("projectCode"));
				document.setClientName(documents.getString("clientName"));

				document.setInComingDate(documents.getDate("incomingDate"));
				document.setDocumentYear(documents.getInt("documentYear"));
				document.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				document.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				document.setOutGoingDate(documents.getDate("outGoingDate"));
				document.setPaymentDue(documents.getDate("paymentDue"));

				document.setScannedDocument(documents.getBytes("scannedDocument"));
				document.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				document.setTotalAmount(documents.getDouble("totalAmount"));
				document.setRemark(documents.getString("remark"));

				document.setRegisteredOn(documents.getTimestamp("registeredon"));
				document.setApprovedOn(documents.getTimestamp("approvedOn"));

				document.setAssignedOn(documents.getTimestamp("assignedOn"));

				document.setClosedOn(documents.getTimestamp("closedOn"));

				document.setStatus(documents.getString("status"));
				document.setRegisteredAmount(documents.getDouble("registeredAmount"));
				document.setPaidAmount(documents.getDouble("paidAmount"));
				returnValue.add(document);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllDocuments(sortKey, ascending) ");
		}
		return returnValue;
	}

	/**
	 * @return all documents in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Document> getAllDocuments(long projectId) {
		if (projectId > 0) {
			return getAllDocuments(projectId, "id", true);
		} else {
			return getAllDocuments();
		}
	}

	/**
	 * @return all documents in the system
	 * @param sortKey   the column to sort by
	 * @param ascending if true the result will be in ascending order
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Document> getAllDocuments(long projectId, String sortKey, boolean ascending) {
		ArrayList<Document> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.document WHERE status <> '" + Constants.STATUS_DELETED
							+ "' AND project=?");
			documentsStmnt.setLong(1, projectId);
			ResultSet documents = documentsStmnt.executeQuery();
			while (documents.next()) {
				Document document = new Document();
				document.setId(documents.getLong("id"));
				document.setClientRegion(getClientRegion(documents.getLong("clientRegion")));
				document.setProject(getProject(documents.getLong("project")));
				document.setBranch(getBranch(documents.getLong("payingBranch")));

				document.setInComingDate(documents.getDate("incomingDate"));
				document.setDocumentYear(documents.getInt("documentYear"));
				document.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				document.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				document.setOutGoingDate(documents.getDate("outGoingDate"));
				document.setPaymentDue(documents.getDate("paymentDue"));

				document.setScannedDocument(documents.getBytes("scannedDocument"));
				document.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				document.setTotalAmount(documents.getDouble("totalAmount"));
				document.setRemark(documents.getString("remark"));

				document.setRegisteredBy(getUser(documents.getLong("registeredby")));
				document.setRegisteredOn(documents.getTimestamp("registeredon"));

				document.setApprovedBy(getUser(documents.getLong("approvedBy")));
				document.setApprovedOn(documents.getTimestamp("approvedOn"));

				document.setAssignedTo(getUser(documents.getLong("assignedTo")));
				document.setAssignedOn(documents.getTimestamp("assignedOn"));

				document.setClosedBy(getUser(documents.getLong("closedBy")));
				document.setClosedOn(documents.getTimestamp("closedOn"));

				document.setStatus(documents.getString("status"));
				connection = CommonStorage.getConnection();
				PreparedStatement registeredStatement = connection.prepareStatement(
						"SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = ? AND status <> '"
								+ Constants.STATUS_DELETED + "'");
				registeredStatement.setLong(1, document.getId());
				ResultSet registeredResult = registeredStatement.executeQuery();
				if (registeredResult.next()) {
					document.setRegisteredAmount(registeredResult.getDouble("registeredAmount"));
				}
				PreparedStatement paidStatement = connection.prepareStatement(
						"SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = ? AND status = '"
								+ Constants.STATUS_FULLY_PAID + "'");
				paidStatement.setLong(1, document.getId());
				ResultSet paidResult = paidStatement.executeQuery();
				if (paidResult.next()) {
					document.setPaidAmount(paidResult.getDouble("paidAmount"));
				}

				returnValue.add(document);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllDocuments(sortKey, ascending) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a document exists in the database
	 *
	 * @param project            - The project to be checked
	 * @param inComingDocumentNo - The incoming document number to be checked
	 * @param year               - The document year to be checked
	 * @return true if the username exists, otherwise false
	 */
	@Override
	public boolean documentExists(long project, String inComingDocumentNo, int year) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection.prepareStatement(
					"SELECT * FROM compensation.Document WHERE incomingdocumentno=? AND project = ? AND documentyear = ? AND status='"
							+ Constants.STATUS_ACTIVE + "' ");
			clientsStmnt.setString(1, inComingDocumentNo);
			clientsStmnt.setLong(2, project);
			clientsStmnt.setInt(3, year);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:clientRegionExists(regionName, client) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a document exists in the database
	 *
	 * @param project            - The project to be checked
	 * @param inComingDocumentNo - The incoming document number to be checked
	 * @param year               - The document year to be checked
	 * @param id                 - id of the document exclude
	 * @return true if the username exists, otherwise false
	 */
	@Override
	public boolean documentExists(long project, String inComingDocumentNo, int year, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection.prepareStatement(
					"SELECT * FROM compensation.Document WHERE incomingdocumentno=? AND project = ? AND documentyear = ? AND status='"
							+ Constants.STATUS_ACTIVE + "' AND id <> ?");
			clientsStmnt.setString(1, inComingDocumentNo);
			clientsStmnt.setLong(2, project);
			clientsStmnt.setInt(3, year);
			clientsStmnt.setLong(4, id);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:clientRegionExists(regionName, client) ");
		}
		return returnValue;
	}

	/**
	 * @return a document in the system
	 * @param id of the document to return
	 * @since version 1.0
	 */
	@Override
	public Document getDocument(long id) {
		Document returnValue = new Document();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection.prepareStatement(
					"SELECT D.*, (SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = D.id AND status <> '"
							+ Constants.STATUS_DELETED + "') AS registeredAmount, "
							+ "(SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = D.id AND status = '"
							+ Constants.STATUS_FULLY_PAID + "') AS paidAmount "
							+ "FROM compensation.document D WHERE D.status <> '" + Constants.STATUS_DELETED
							+ "' AND D.id=?");
			documentsStmnt.setLong(1, id);
			ResultSet documents = documentsStmnt.executeQuery();
			if (documents.next()) {
				returnValue.setId(documents.getLong("id"));
				returnValue.setClientRegion(getClientRegion(documents.getLong("clientRegion")));
				returnValue.setProject(getProject(documents.getLong("project")));
				returnValue.setBranch(getBranch(documents.getLong("payingBranch")));

				returnValue.setDocumentYear(documents.getInt("documentYear"));
				returnValue.setInComingDate(documents.getDate("incomingDate"));
				returnValue.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				returnValue.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				returnValue.setOutGoingDate(documents.getDate("outGoingDate"));
				returnValue.setPaymentDue(documents.getDate("paymentDue"));

				returnValue.setScannedDocument(documents.getBytes("scannedDocument"));
				returnValue.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				returnValue.setTotalAmount(documents.getDouble("totalAmount"));
				returnValue.setRemark(documents.getString("remark"));
				returnValue.setRegisteredBy(getUser(documents.getLong("registeredby")));
				returnValue.setRegisteredOn(documents.getTimestamp("registeredon"));
				returnValue.setApprovedBy(getUser(documents.getLong("approvedBy")));
				returnValue.setApprovedOn(documents.getTimestamp("approvedOn"));
				returnValue.setAssignedTo(getUser(documents.getLong("assignedTo")));
				returnValue.setAssignedOn(documents.getTimestamp("assignedOn"));
				returnValue.setClosedBy(getUser(documents.getLong("closedBy")));
				returnValue.setClosedOn(documents.getTimestamp("closedOn"));
				returnValue.setRegisteredAmount(documents.getDouble("registeredAmount"));
				returnValue.setPaidAmount(documents.getDouble("paidAmount"));
				returnValue.setStatus(documents.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getDocument(id) ");
		}
		return returnValue;
	}

	/**
	 * @return a document in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Document> findDocument(String projectCode, String documentNo, int documentYear) {
		ArrayList<Document> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection.prepareStatement(
					"SELECT D.*, (SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = D.id AND status <> '"
							+ Constants.STATUS_DELETED + "') AS registeredAmount, "
							+ "(SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = D.id AND status = '"
							+ Constants.STATUS_FULLY_PAID + "') AS paidAmount "
							+ "FROM compensation.document D, compensation.project P  WHERE P.id = D.project AND D.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND P.code=? AND D.incomingdocumentno = ? AND D.documentyear = ?");
			documentsStmnt.setString(1, projectCode);
			documentsStmnt.setString(2, documentNo);
			documentsStmnt.setInt(3, documentYear);
			ResultSet documents = documentsStmnt.executeQuery();
			while (documents.next()) {
				Document document = new Document();
				document.setId(documents.getLong("id"));
				document.setClientRegion(getClientRegion(documents.getLong("clientRegion")));
				document.setProject(getProject(documents.getLong("project")));
				document.setBranch(getBranch(documents.getLong("payingBranch")));

				document.setDocumentYear(documents.getInt("documentYear"));
				document.setInComingDate(documents.getDate("incomingDate"));
				document.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				document.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				document.setOutGoingDate(documents.getDate("outGoingDate"));
				document.setPaymentDue(documents.getDate("paymentDue"));

				document.setScannedDocument(documents.getBytes("scannedDocument"));
				document.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				document.setTotalAmount(documents.getDouble("totalAmount"));
				document.setRemark(documents.getString("remark"));
				document.setRegisteredBy(getUser(documents.getLong("registeredby")));
				document.setRegisteredOn(documents.getTimestamp("registeredon"));
				document.setApprovedBy(getUser(documents.getLong("approvedBy")));
				document.setApprovedOn(documents.getTimestamp("approvedOn"));
				document.setAssignedTo(getUser(documents.getLong("assignedTo")));
				document.setAssignedOn(documents.getTimestamp("assignedOn"));
				document.setClosedBy(getUser(documents.getLong("closedBy")));
				document.setClosedOn(documents.getTimestamp("closedOn"));
				document.setRegisteredAmount(documents.getDouble("registeredAmount"));
				document.setPaidAmount(documents.getDouble("paidAmount"));
				document.setStatus(documents.getString("status"));
				returnValue.add(document);
			}
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getDocument(id) ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<Document> findDocument(double amount) {
		ArrayList<Document> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection.prepareStatement(
					"SELECT D.*, (SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = D.id AND status <> '"
							+ Constants.STATUS_DELETED + "') AS registeredAmount, "
							+ "(SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = D.id AND status = '"
							+ Constants.STATUS_FULLY_PAID + "') AS paidAmount "
							+ "FROM compensation.document D, compensation.project P  WHERE P.id = D.project AND D.status <> '"
							+ Constants.STATUS_DELETED + "' AND D.totalamount = ?");
			documentsStmnt.setDouble(1, amount);
			ResultSet documents = documentsStmnt.executeQuery();
			while (documents.next()) {
				Document document = new Document();
				document.setId(documents.getLong("id"));
				document.setClientRegion(getClientRegion(documents.getLong("clientRegion")));
				document.setProject(getProject(documents.getLong("project")));
				document.setBranch(getBranch(documents.getLong("payingBranch")));

				document.setDocumentYear(documents.getInt("documentYear"));
				document.setInComingDate(documents.getDate("incomingDate"));
				document.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				document.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				document.setOutGoingDate(documents.getDate("outGoingDate"));
				document.setPaymentDue(documents.getDate("paymentDue"));

				document.setScannedDocument(documents.getBytes("scannedDocument"));
				document.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				document.setTotalAmount(documents.getDouble("totalAmount"));
				document.setRemark(documents.getString("remark"));
				document.setRegisteredBy(getUser(documents.getLong("registeredby")));
				document.setRegisteredOn(documents.getTimestamp("registeredon"));
				document.setApprovedBy(getUser(documents.getLong("approvedBy")));
				document.setApprovedOn(documents.getTimestamp("approvedOn"));
				document.setAssignedTo(getUser(documents.getLong("assignedTo")));
				document.setAssignedOn(documents.getTimestamp("assignedOn"));
				document.setClosedBy(getUser(documents.getLong("closedBy")));
				document.setClosedOn(documents.getTimestamp("closedOn"));
				document.setRegisteredAmount(documents.getDouble("registeredAmount"));
				document.setPaidAmount(documents.getDouble("paidAmount"));
				document.setStatus(documents.getString("status"));
				returnValue.add(document);
			}
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getDocument(id) ");
		}
		return returnValue;
	}
	
	@Override
	public ArrayList<Document> findDocument(String cnnumber) {
		ArrayList<Document> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection.prepareStatement(
					"SELECT D.*, (SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = D.id AND status <> '"
							+ Constants.STATUS_DELETED + "') AS registeredAmount, "
							+ "(SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = D.id AND status = '"
							+ Constants.STATUS_FULLY_PAID + "') AS paidAmount "
							+ "FROM compensation.document D, compensation.project P  WHERE P.id = D.project AND D.status <> '"
							+ Constants.STATUS_DELETED + "' AND D.outgoingdocumentno = ?");
			documentsStmnt.setString(1, cnnumber);
			ResultSet documents = documentsStmnt.executeQuery();
			while (documents.next()) {
				Document document = new Document();
				document.setId(documents.getLong("id"));
				document.setClientRegion(getClientRegion(documents.getLong("clientRegion")));
				document.setProject(getProject(documents.getLong("project")));
				document.setBranch(getBranch(documents.getLong("payingBranch")));

				document.setDocumentYear(documents.getInt("documentYear"));
				document.setInComingDate(documents.getDate("incomingDate"));
				document.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				document.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				document.setOutGoingDate(documents.getDate("outGoingDate"));
				document.setPaymentDue(documents.getDate("paymentDue"));
				document.setScannedDocument(documents.getBytes("scannedDocument"));
				document.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				document.setTotalAmount(documents.getDouble("totalAmount"));
				document.setRemark(documents.getString("remark"));
				document.setRegisteredBy(getUser(documents.getLong("registeredby")));
				document.setRegisteredOn(documents.getTimestamp("registeredon"));
				document.setApprovedBy(getUser(documents.getLong("approvedBy")));
				document.setApprovedOn(documents.getTimestamp("approvedOn"));
				document.setAssignedTo(getUser(documents.getLong("assignedTo")));
				document.setAssignedOn(documents.getTimestamp("assignedOn"));
				document.setClosedBy(getUser(documents.getLong("closedBy")));
				document.setClosedOn(documents.getTimestamp("closedOn"));
				document.setRegisteredAmount(documents.getDouble("registeredAmount"));
				document.setPaidAmount(documents.getDouble("paidAmount"));
				document.setStatus(documents.getString("status"));
				returnValue.add(document);
			}
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getDocument(id) ");
		}
		return returnValue;
	}

	
	@Override
	public int getDocumentCount(DocumentQueryParameter param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String query = "SELECT count(D.*) as c "
					+ "FROM compensation.document D, compensation.project P  WHERE P.id = D.project AND D.status <> '"
					+ Constants.STATUS_DELETED + "' ";
					
			query += param.getOutgoingdocumentno()!=null&&!param.getOutgoingdocumentno().trim().isEmpty() ?" AND D.outgoingdocumentno = "+ param.getOutgoingdocumentno()+" ":"";
			query += param.getProjectCode()  != null ?" AND P.project = '"+ param.getProjectCode()+"' ":"";
			query += param.getDocumentNo() != null ?" AND D.incomingdocumentno = '"+ param.getDocumentNo()+"' ":"";
			query += param.getYear()  != null ?" AND D.year = '"+ param.getYear()+"' ":"";
			
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( P.name ILIKE '%" + CommonTasks.escape(param.sSearch) + "%' OR P.amount::text ILIKE '%" 
					+ CommonTasks.escape(param.sSearch) + "%' OR P.status ILIKE '%"+ CommonTasks.escape(param.sSearch).toLowerCase() +"%' "
					+ "P.code ||'/'|| D.incomingdocumentno||'/'||D.documentyear ILIKE  '%"+ CommonTasks.escape(param.sSearch).toLowerCase() + "%') "
					: "");
			PreparedStatement documentsStmnt = connection.prepareStatement(query);
			ResultSet documents = documentsStmnt.executeQuery();
			if (documents.next()) {
				returnValue = documents.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository::getPaymentsCount() ");
		}
		return returnValue;
	}

    
	@Override
    public ArrayList<Document> getAllDocuments(DocumentQueryParameter param){
		ArrayList<Document> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String query = "SELECT D.*, (SELECT sum(amount) as registeredAmount FROM compensation.Payment WHERE document = D.id AND status <> '"
					+ Constants.STATUS_DELETED + "') AS registeredAmount, "
					+ "(SELECT sum(amount) as paidAmount FROM compensation.Payment WHERE document = D.id AND status = '"
					+ Constants.STATUS_FULLY_PAID + "') AS paidAmount, "
					+ "P.code ||'/'|| D.incomingdocumentno||'/'||D.documentyear AS documentNo, "
					+ "P.name AS projectName, CR.regionname AS clientRegionName, C.name AS clientName "
					+ "FROM compensation.document D, compensation.project P , compensation.client C , compensation.clientRegion CR "
					+ "WHERE D.clientregion = CR.id AND CR.client=C.id AND P.id = D.project AND D.status <> '" + Constants.STATUS_DELETED + "' ";
			query += param.getOutgoingdocumentno()!=null&&!param.getOutgoingdocumentno().trim().isEmpty() ?" AND D.outgoingdocumentno = "+ param.getOutgoingdocumentno()+" ":"";
			query += param.getProjectCode()  != null ?" AND P.project = '"+ param.getProjectCode()+"' ":"";
			query += param.getDocumentNo() != null ?" AND D.incomingdocumentno = '"+ param.getDocumentNo()+"' ":"";
			query += param.getYear()  != null ?" AND D.year = '"+ param.getYear()+"' ":"";
			
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( P.name ILIKE '%" + CommonTasks.escape(param.sSearch) + "%' OR D.totalamount::text ILIKE '%" 
					+ CommonTasks.escape(param.sSearch) + "%' OR P.status ILIKE '%"+ CommonTasks.escape(param.sSearch).toLowerCase() +"%' "
							+ "OR P.code ||'/'|| D.incomingdocumentno||'/'||D.documentyear ILIKE  '%"+ CommonTasks.escape(param.sSearch).toLowerCase() + "%') "
					: "");
			
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement documentsStmnt = connection.prepareStatement(query);
			ResultSet documents = documentsStmnt.executeQuery();
			while (documents.next()) {
				Document document = new Document();
				document.setId(documents.getLong("id"));
				document.setDocumentNo(documents.getString("documentNo"));
				document.setProjectName(documents.getString("projectName"));
				document.setClientName(documents.getString("clientName"));
				document.setClientRegionName(documents.getString("clientRegionName"));
				
				document.setClientRegionId(documents.getLong("clientRegion"));
				document.setProjectId(documents.getLong("project"));
				document.setBranchId(documents.getLong("payingBranch"));
				document.setDocumentYear(documents.getInt("documentYear"));
				document.setInComingDate(documents.getDate("incomingDate"));
				document.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				document.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				document.setOutGoingDate(documents.getDate("outGoingDate"));
				document.setPaymentDue(documents.getDate("paymentDue"));
				document.setScannedDocument(documents.getBytes("scannedDocument"));
				document.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				document.setTotalAmount(documents.getDouble("totalAmount"));
				document.setRemark(documents.getString("remark"));
				document.setRegisteredById(documents.getLong("registeredby"));
				document.setRegisteredOn(documents.getTimestamp("registeredon"));
				document.setApprovedById(documents.getLong("approvedBy"));
				document.setApprovedOn(documents.getTimestamp("approvedOn"));
				document.setAssignedToId(documents.getLong("assignedTo"));
				document.setAssignedOn(documents.getTimestamp("assignedOn"));
				document.setClosedById(documents.getLong("closedBy"));
				document.setClosedOn(documents.getTimestamp("closedOn"));
				document.setRegisteredAmount(documents.getDouble("registeredAmount"));
				document.setPaidAmount(documents.getDouble("paidAmount"));
				document.setStatus(documents.getString("status"));
				returnValue.add(document);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllProjects() ");
		}
		
		return returnValue;
	}
    
    
	@Override
	public Document getDocument(ClientRegion clientRegion, Branch branch, String projectCode, String documenNo,
			int documentYear) {
		Document returnValue = null;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection.prepareStatement(
					"SELECT D.* FROM compensation.document D, compensation.project P WHERE D.project = P.id AND D.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND D.documentyear=? AND D.incomingdocumentno=? AND P.code = ? AND D.clientregion= ? AND D.payingbranch = ?");
			documentsStmnt.setInt(1, documentYear);
			documentsStmnt.setString(2, documenNo);
			documentsStmnt.setString(3, projectCode);
			documentsStmnt.setLong(4, clientRegion.getId());
			documentsStmnt.setLong(5, branch.getId());
			ResultSet documents = documentsStmnt.executeQuery();
			if (documents.next()) {
				returnValue = new Document();
				returnValue.setId(documents.getLong("id"));
				returnValue.setClientRegion(getClientRegion(documents.getLong("clientRegion")));
				returnValue.setProject(getProject(documents.getLong("project")));
				returnValue.setBranch(getBranch(documents.getLong("payingBranch")));

				returnValue.setDocumentYear(documents.getInt("documentYear"));
				returnValue.setInComingDate(documents.getDate("incomingDate"));
				returnValue.setInComingDocumentNo(documents.getString("inComingDocumentNo"));
				returnValue.setOutGoingDocumentNo(documents.getString("outGoingDocumentNo"));
				returnValue.setOutGoingDate(documents.getDate("outGoingDate"));
				returnValue.setPaymentDue(documents.getDate("paymentDue"));

				returnValue.setScannedDocument(documents.getBytes("scannedDocument"));
				returnValue.setScannedDocumentFormat(documents.getString("scannedDocumentFormat"));
				returnValue.setTotalAmount(documents.getDouble("totalAmount"));
				returnValue.setRemark(documents.getString("remark"));
				returnValue.setRegisteredBy(getUser(documents.getLong("registeredby")));
				returnValue.setRegisteredOn(documents.getTimestamp("registeredon"));
				returnValue.setApprovedBy(getUser(documents.getLong("approvedBy")));
				returnValue.setApprovedOn(documents.getTimestamp("approvedOn"));
				returnValue.setAssignedTo(getUser(documents.getLong("assignedTo")));
				returnValue.setAssignedOn(documents.getTimestamp("assignedOn"));
				returnValue.setClosedBy(getUser(documents.getLong("closedBy")));
				returnValue.setClosedOn(documents.getTimestamp("closedOn"));
				returnValue.setStatus(documents.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getDocument(id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a document object to the database
	 *
	 * @param document - A Document object representing the data to be saved
	 * @return
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public Document save(Document document) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.Document( incomingDate, clientRegion, project, payingBranch, inComingDocumentNo,"
					+ "outGoingDocumentNo, outGoingDate, paymentDue ,scannedDocument, scannedDocumentFormat, totalAmount, remark, "
					+ "registeredOn, registeredBy, assignedOn, documentYear, assignedTo, approvedOn, approvedBy, closedOn, closedBy, status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setDate(1, document.getInComingDate());
			if (document.getClientRegion().getId() > 0) {
				stmnt.setLong(2, document.getClientRegion().getId());
			} else {
				stmnt.setNull(2, Types.BIGINT);
			}
			stmnt.setLong(3, document.getProject().getId());
			stmnt.setLong(4, document.getBranch().getId());
			stmnt.setString(5, document.getInComingDocumentNo());
			stmnt.setString(6, document.getOutGoingDocumentNo());
			stmnt.setDate(7, document.getOutGoingDate());
			stmnt.setDate(8, document.getPaymentDue());
			stmnt.setBytes(9, document.getScannedDocument());
			stmnt.setString(10, document.getScannedDocumentFormat());
			stmnt.setDouble(11, document.getTotalAmount());
			stmnt.setString(12, document.getRemark());
			stmnt.setTimestamp(13, document.getRegisteredOn());
			stmnt.setLong(14, document.getRegisteredBy().getId());
			stmnt.setTimestamp(15, document.getAssignedOn());
			stmnt.setInt(16, document.getDocumentYear());
			if (document.getAssignedTo() != null) {
				stmnt.setLong(17, document.getAssignedTo().getId());
			} else {
				stmnt.setNull(17, java.sql.Types.INTEGER);
			}

			stmnt.setTimestamp(18, document.getApprovedOn());

			if (document.getApprovedBy() != null) {
				stmnt.setLong(19, document.getApprovedBy().getId());
			} else {
				stmnt.setNull(19, java.sql.Types.INTEGER);
			}
			stmnt.setTimestamp(20, document.getClosedOn());

			if (document.getClosedBy() != null) {
				stmnt.setLong(21, document.getClosedBy().getId());
			} else {
				stmnt.setNull(21, java.sql.Types.INTEGER);
			}
			stmnt.setString(22, document.getStatus());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving document to database returend zero(0)");
			}
			try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					document.setId(generatedKeys.getLong(1));
				}
			}
			return document;
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @param documentId - id of the document to delete
	 * @return reference number of the deleted document or null if a document with
	 *         the specified documentId does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteDocument(long documentId) {
		String returnValue = null;
		Document document = getDocument(documentId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement documentsStmnt = connection
					.prepareStatement("UPDATE compensation.document SET status = 'deleted' WHERE id=?");
			documentsStmnt.setLong(1, documentId);
			documentsStmnt.executeUpdate();
			returnValue = document.getInComingDocumentNo();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deleteDocument() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Updates a document object to the database
	 *
	 * @param document - A Document object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Document document) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.Document SET incomingDate = ?, clientRegion = ?,"
					+ "project = ?, payingBranch = ?, inComingDocumentNo = ?, outGoingDocumentNo = ?,"
					+ "outGoingDate = ?, paymentDue  = ?,scannedDocument = ?, scannedDocumentFormat = ?,"
					+ "totalAmount = ?, remark = ?, registeredOn = ?, registeredBy = ?, assignedOn = ?,"
					+ "documentYear = ?, assignedTo = ?, approvedOn = ?, approvedBy = ?, closedOn = ?,"
					+ "closedBy = ?, status = ? WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setDate(1, document.getInComingDate());
			stmnt.setLong(2, document.getClientRegion().getId());
			stmnt.setLong(3, document.getProject().getId());
			stmnt.setLong(4, document.getBranch().getId());
			stmnt.setString(5, document.getInComingDocumentNo());
			stmnt.setString(6, document.getOutGoingDocumentNo());
			stmnt.setDate(7, document.getOutGoingDate());
			stmnt.setDate(8, document.getPaymentDue());
			stmnt.setBytes(9, document.getScannedDocument());
			stmnt.setString(10, document.getScannedDocumentFormat());
			stmnt.setDouble(11, document.getTotalAmount());
			stmnt.setString(12, document.getRemark());
			stmnt.setTimestamp(13, document.getRegisteredOn());
			stmnt.setLong(14, document.getRegisteredBy().getId());
			stmnt.setTimestamp(15, document.getAssignedOn());
			stmnt.setInt(16, document.getDocumentYear());
			if (document.getAssignedTo() != null) {
				stmnt.setLong(17, document.getAssignedTo().getId());
			} else {
				stmnt.setNull(17, java.sql.Types.INTEGER);
			}

			stmnt.setTimestamp(18, document.getApprovedOn());

			if (document.getApprovedBy() != null) {
				stmnt.setLong(19, document.getApprovedBy().getId());
			} else {
				stmnt.setNull(19, java.sql.Types.INTEGER);
			}
			stmnt.setTimestamp(20, document.getClosedOn());

			if (document.getClosedBy() != null) {
				stmnt.setLong(21, document.getClosedBy().getId());
			} else {
				stmnt.setNull(21, java.sql.Types.INTEGER);
			}
			stmnt.setString(22, document.getStatus());
			stmnt.setLong(23, document.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving document to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	
	/**
	 * @return all payments in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Payment> getAllPayments(PaymentQueryParameter param) {
		ArrayList<Payment> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String query = "SELECT P.*, (P.amount - (SELECT COALESCE(sum(amount),0) "
					+ "FROM compensation.claimdetail WHERE payment=P.id AND status "
					+ "<>'"+Constants.STATUS_DELETED+"')) AS remainingAmount, "
							+ "PJ.code ||'/'|| D.incomingdocumentno||'/'||D.documentyear AS documentId , "
							+ "(SELECT C.claimcnnumber FROM compensation.claim C, compensation.claimDetail CD WHERE CD.payment=P.id AND C.id=CD.claim AND CD.payment=P.id AND C.id=CD.claim) AS claimcnnumber "
							+ "FROM compensation.Payment P, compensation.Document D, compensation.Project PJ "
							+ "WHERE P.document = D.id AND D.project = PJ.id "
							+ "AND D.status <> '" + Constants.STATUS_DELETED+"' "
							+ "AND P.status <> '" + Constants.STATUS_DELETED+"' "
							+ "AND PJ.status <> '" + Constants.STATUS_DELETED+"' ";
			query += param.getDocumentId() >0?" AND P.document = "+ param.getDocumentId()+" ":"";
			query += param.getProjectId() >0?" AND D.project = "+ param.getProjectId()+" ":"";
			query += param.getBranchId() >0?" AND D.payingbranch = "+ param.getBranchId()+" ":"";
			query += param.getStatus().isEmpty()?"":" AND "+ param.getStatus().replace("status", "P.status")+" ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( P.name ILIKE '%" + CommonTasks.escape(param.sSearch) + "%' OR P.amount::text ILIKE '%" 
							+ CommonTasks.escape(param.sSearch) + "%' OR P.status ILIKE '%"+ CommonTasks.escape(param.sSearch).toLowerCase() +"%') "
					: "");
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement paymentsStmnt = connection.prepareStatement(query);
			ResultSet payments = paymentsStmnt.executeQuery();
			while (payments.next()) {
				Payment payment = new Payment();
				payment.setId(payments.getLong("id"));
				payment.setClaimcnnumber(payments.getString("claimcnnumber"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.setRemainingAmount(payments.getDouble("remainingAmount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setDocumentId(payments.getLong("document"));
				payment.setDocumentNo(payments.getString("documentId"));
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllProjects() ");
		}
		
		return returnValue;
	}
	
	@Override
	public int getPaymentsCount(PaymentQueryParameter param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String query = "SELECT count(P.*) as c FROM compensation.Payment P, compensation.Document D, compensation.Project PJ "
							+ "WHERE P.document = D.id AND D.project = PJ.id "
							+ "AND D.status <> '" + Constants.STATUS_DELETED+"' "
							+ "AND P.status <> '" + Constants.STATUS_DELETED+"' "
							+ "AND PJ.status <> '" + Constants.STATUS_DELETED+"' ";
			query += param.getDocumentId() >0?" AND P.document = "+ param.getDocumentId()+" ":"";
			query += param.getProjectId() >0?" AND D.project = "+ param.getProjectId()+" ":"";
			query += param.getBranchId() >0?" AND D.payingbranch = "+ param.getBranchId()+" ":"";
			
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( P.name ILIKE '%" + CommonTasks.escape(param.sSearch) + "%' OR P.amount::text ILIKE '%" 
					+ CommonTasks.escape(param.sSearch) + "%' OR P.status ILIKE '%"+ CommonTasks.escape(param.sSearch).toLowerCase() +"%') "
					: "");
			PreparedStatement paymentsStmnt = connection.prepareStatement(query);
			ResultSet payments = paymentsStmnt.executeQuery();
			if (payments.next()) {
				returnValue = payments.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository::getPaymentsCount() ");
		}
		return returnValue;
	}


	/**
	 * @return a payment in the system
	 * @param id of the document to return
	 * @since version 1.0
	 */
	@Override
	public Payment getPayment(long id) {
		Payment returnValue = new Payment();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentsStmnt = connection.prepareStatement(
					"SELECT *, (amount - (SELECT COALESCE(sum(amount),0) FROM compensation.claimdetail WHERE payment=P.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "')) AS remainingAmount FROM compensation.Payment P WHERE status <> '"
							+ Constants.STATUS_DELETED + "' AND id = ?");
			paymentsStmnt.setLong(1, id);
			ResultSet payments = paymentsStmnt.executeQuery();
			if (payments.next()) {
				returnValue.setId(payments.getLong("id"));
				returnValue.setDocument(getDocument(payments.getLong("document")));
				returnValue.setLotNo(payments.getInt("lotNo"));
				returnValue.setName(payments.getString("name"));
				returnValue.setAmount(payments.getDouble("amount"));
				returnValue.setRemainingAmount(payments.getDouble("remainingAmount"));
				returnValue.isRestricted(payments.getBoolean("restricted"));
				returnValue.setRemark(payments.getString("remark"));
				returnValue.setStatus(payments.getString("status"));
				returnValue.setRegisteredBy(getUser(payments.getLong("registeredby")));
				returnValue.setRegisteredOn(payments.getTimestamp("registeredon"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPayment(id) ");
		}
		return returnValue;
	}

	/**
	 * Checks if a payment exists in the database with the specified lot number
	 *
	 * @param lotNumber - The lot number to be checked
	 * @param document  of the payment
	 * @return true if the lot number exists, otherwise false
	 */
	@Override
	public boolean paymentExists(int lotNumber, long document) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.Payment WHERE lotNo=? AND document=? AND status<>'"
							+ Constants.STATUS_DELETED + "'");
			paymentsStmnt.setInt(1, lotNumber);
			paymentsStmnt.setLong(2, document);
			ResultSet payments = paymentsStmnt.executeQuery();
			if (payments.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:paymentExists() ");
		}
		return returnValue;
	}

	/**
	 * Checks if a payment exists in the database with the specified lot number that
	 * does not have the Id specified
	 *
	 * @param lotNumber - The lot number to be checked
	 * @param document  of the payment
	 * @param id        - The user id to exclude form the checking
	 * @return true if the lot number exists, otherwise false
	 */
	@Override
	public boolean paymentExists(int lotNumber, long document, long id) {
		boolean returnValue = false;
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentsStmnt = connection.prepareStatement(
					"SELECT * FROM compensation.Payment WHERE lotNo=? AND document=? AND id <> ? AND status<>'"
							+ Constants.STATUS_DELETED + "'");
			paymentsStmnt.setInt(1, lotNumber);
			paymentsStmnt.setLong(2, document);
			paymentsStmnt.setLong(3, id);
			ResultSet payments = paymentsStmnt.executeQuery();
			if (payments.next()) {
				returnValue = true;
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:paymentExists() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a payment object to the database
	 *
	 * @param payment - A payment object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(Payment payment) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.Payment( document, lotNo, name, amount, restricted,"
					+ "remark, registeredOn, registeredBy, status) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, payment.getDocument().getId());
			stmnt.setInt(2, payment.getLotNo());
			stmnt.setString(3, payment.getName());
			stmnt.setDouble(4, payment.getAmount());
			stmnt.setBoolean(5, payment.isRestricted());
			stmnt.setString(6, payment.getRemark());
			stmnt.setTimestamp(7, payment.getRegisteredOn());
			stmnt.setLong(8, payment.getRegisteredBy().getId());
			stmnt.setString(9, payment.getStatus());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving payment to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 *
	 * Updates a payment object to the database
	 *
	 * @param payment - A Payment object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Payment payment) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.Payment SET document = ?, lotNo = ?, name = ?, amount = ?,"
					+ "restricted = ?, remark = ?, registeredOn = ?, registeredBy = ?, status = ? WHERE id = ? ";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, payment.getDocument().getId());
			stmnt.setInt(2, payment.getLotNo());
			stmnt.setString(3, payment.getName());
			stmnt.setDouble(4, payment.getAmount());
			stmnt.setBoolean(5, payment.isRestricted());
			stmnt.setString(6, payment.getRemark());
			stmnt.setTimestamp(7, payment.getRegisteredOn());
			stmnt.setLong(8, payment.getRegisteredBy().getId());
			stmnt.setString(9, payment.getStatus());
			stmnt.setLong(10, payment.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating payment to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 * @param paymentId - id of the payment to delete
	 * @return name of the deleted payment or null if a document with the specified
	 *         documentId does not exit
	 * @since version 1.0
	 */
	@Override
	public String deletePayment(long paymentId) {
		String returnValue = null;
		Payment payment = getPayment(paymentId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentsStmnt = connection
					.prepareStatement("UPDATE compensation.payment SET status = 'deleted' WHERE id=?");
			paymentsStmnt.setLong(1, paymentId);
			paymentsStmnt.executeUpdate();
			returnValue = payment.getName();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deletePayment() ");
		}
		return returnValue;
	}

	/**
	 * @return all claims in the system
	 * @since version 1.0
	 */

	public ArrayList<Claim> getAllClaims(ClaimParamModel param){
		ArrayList<Claim> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);

			String query = "SELECT CLM.*, (SELECT sum(CD.amount) AS registeredAmount "
					+ "FROM compensation.ClaimDetail CD WHERE CD.claim = CLM.id AND status <> '"
					+ Constants.STATUS_DELETED + "') AS registeredAmount, C.name as clientName, "
							+ "B.name AS payingBranchName "
							+ "FROM compensation.Claim CLM, compensation.client C, public.branch B "
							+ "WHERE  CLM.client = C.id AND B.id = CLM.payingbranch AND CLM.status <> "
							+ "'" + Constants.STATUS_DELETED + "' ";
			if((param.sSearch != null && !param.sSearch.trim().isEmpty())){
			query +=param.exact? " AND ( CLM.claimnumber ILIKE '" + searchString + "' OR CLM.claimcnnumber ILIKE '"
					+ searchString + "' OR C.name ILIKE '" + searchString + "' OR amount::TEXT ILIKE '" 
					+ searchString + "' OR B.name ILIKE '" + searchString + "') "
					: " AND ( CLM.claimnumber ILIKE '%" + searchString + "%' OR CLM.claimcnnumber ILIKE '%"
							+ searchString + "%' OR C.name ILIKE '%" + searchString + "%' OR amount::TEXT ILIKE '%" 
							+ searchString + "%' OR B.name ILIKE '%" + searchString + "%') ";
			}
			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement claimsStmnt = connection.prepareStatement(query);
			ResultSet claims = claimsStmnt.executeQuery();
			while (claims.next()) {
				Claim claim = new Claim();
				claim.setId(claims.getLong("id"));
				claim.setAmount(claims.getDouble("amount"));
				claim.setRegisteredAmount(claims.getDouble("registeredAmount"));
				claim.setArrivalDate(claims.getDate("arrivalDate"));
				claim.setClaimDate(claims.getDate("claimDate"));
				claim.setClaimNumber(claims.getString("claimNumber"));
				claim.setClaimCNNumber(claims.getString("claimCNNumber"));
				claim.setClientId(claims.getLong("client"));
				claim.setPayingBranchId(claims.getLong("payingbranch"));
				claim.setPayingBranchName(claims.getString("payingBranchName"));
				claim.setClientName(claims.getString("clientName"));
				claim.setScannedDocument(claims.getBytes("document"));
				claim.setScannedDocumentFormat(claims.getString("documentFormat"));
				claim.setCount(claims.getInt("claimCount"));
				claim.setRemark(claims.getString("remark"));
				claim.setStatus(claims.getString("status"));
				claim.setRegisteredId(claims.getLong("registeredby"));
				claim.setRegisteredOn(claims.getTimestamp("registeredon"));
				returnValue.add(claim);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllClaims() ");
		}
		return returnValue;
	}

	@Override
	public long getClaimCount(ClaimParamModel param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT count(CLM.*) as c "
							+ "FROM compensation.Claim CLM, compensation.client C, public.branch B WHERE "
							+ "CLM.client = C.id AND CLM.status <> '" + Constants.STATUS_DELETED + "' "
							+ "AND CLM.payingbranch = B.id AND C.status <> '" + Constants.STATUS_DELETED + "'";
			
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( CLM.claimnumber ILIKE '%" + searchString + "%' OR CLM.claimcnnumber ILIKE '%"
							+ searchString + "%' OR C.name ILIKE '%" + searchString + "%' OR B.name ILIKE '%" + searchString + "%') "
					: "");
			
			PreparedStatement clientRegionsStmnt = connection.prepareStatement(query);
			ResultSet clientRegions = clientRegionsStmnt.executeQuery();
			if (clientRegions.next()) {
				returnValue = clientRegions.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClaimCount() ");
		}
		return returnValue;
	}

	/**
	 * @return all claim CN Numbers in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<String> getAllClaimCNNumbers() {
		ArrayList<String> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement claimsStmnt = connection
					.prepareStatement("SELECT distinct claimCNNumber FROM compensation.Claim WHERE status <> '"
							+ Constants.STATUS_DELETED + "'");
			ResultSet claims = claimsStmnt.executeQuery();
			while (claims.next()) {
				returnValue.add(claims.getString("claimCNNumber"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllClaimCNNumbers() ");
		}
		return returnValue;
	}

	/**
	 * @return a claim in the system
	 * @param id of the claim to return
	 * @since version 1.0
	 */
	@Override
	public Claim getClaim(long id) {
		Claim returnValue = new Claim();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement claimsStmnt = connection.prepareStatement(
					"SELECT *, (SELECT sum(amount) as registeredAmount FROM compensation.ClaimDetail WHERE claim = C.id AND status <> '"
							+ Constants.STATUS_DELETED
							+ "') AS registeredAmount FROM compensation.Claim C WHERE status = '"
							+ Constants.STATUS_ACTIVE + "' AND id = ?");
			claimsStmnt.setLong(1, id);
			ResultSet claims = claimsStmnt.executeQuery();
			if (claims.next()) {
				returnValue.setId(claims.getLong("id"));
				returnValue.setAmount(claims.getDouble("amount"));
				returnValue.setRegisteredAmount(claims.getDouble("registeredAmount"));
				returnValue.setArrivalDate(claims.getDate("arrivalDate"));
				returnValue.setClaimDate(claims.getDate("claimDate"));
				returnValue.setClaimNumber(claims.getString("claimNumber"));
				returnValue.setClaimCNNumber(claims.getString("claimCNNumber"));
				returnValue.setClient(getClient(claims.getLong("client")));
				returnValue.setScannedDocument(claims.getBytes("document"));
				returnValue.setScannedDocumentFormat(claims.getString("documentFormat"));
				returnValue.setCount(claims.getInt("claimCount"));
				returnValue.setPayingBranch(getBranch(claims.getLong("payingBranch")));
				returnValue.setRemark(claims.getString("remark"));
				returnValue.setStatus(claims.getString("status"));
				returnValue.setRegisteredBy(getUser(claims.getLong("registeredby")));
				returnValue.setRegisteredOn(claims.getTimestamp("registeredon"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClaim(id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a claim object to the database
	 *
	 * @param claim - A Claim object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(Claim claim) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.Claim( claimNumber, claimCNNumber, claimDate, arrivalDate, payingBranch, amount, "
					+ "claimCount, client, document, documentFormat, remark, status, registeredOn, registeredBy) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, claim.getClaimNumber());
			stmnt.setString(2, claim.getClaimCNNumber());
			stmnt.setDate(3, claim.getClaimDate());
			stmnt.setDate(4, claim.getArrivalDate());
			stmnt.setLong(5, claim.getPayingBranch().getId());
			stmnt.setDouble(6, claim.getAmount());
			stmnt.setInt(7, claim.getCount());
			stmnt.setLong(8, claim.getClient().getId());
			stmnt.setBytes(9, claim.getScannedDocument());
			stmnt.setString(10, claim.getScannedDocumentFormat());
			stmnt.setString(11, claim.getRemark());
			stmnt.setString(12, claim.getStatus());
			stmnt.setTimestamp(13, claim.getRegisteredOn());
			stmnt.setLong(14, claim.getRegisteredBy().getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving claim to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}

	}

	/**
	 *
	 * Updates a claim object in the database
	 *
	 * @param claim - A Claim object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Claim claim) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.Claim SET claimNumber = ?, claimCNNumber = ?, claimDate = ?, arrivalDate = ?, payingBranch = ?,"
					+ "amount = ?, claimCount = ?, client = ?, document = ?, documentFormat = ?, remark = ?, status = ?,"
					+ "registeredOn = ?, registeredBy = ? WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setString(1, claim.getClaimNumber());
			stmnt.setString(2, claim.getClaimCNNumber());
			stmnt.setDate(3, claim.getClaimDate());
			stmnt.setDate(4, claim.getArrivalDate());
			stmnt.setLong(5, claim.getPayingBranch().getId());
			stmnt.setDouble(6, claim.getAmount());
			stmnt.setInt(7, claim.getCount());
			stmnt.setLong(8, claim.getClient().getId());
			stmnt.setBytes(9, claim.getScannedDocument());
			stmnt.setString(10, claim.getScannedDocumentFormat());
			stmnt.setString(11, claim.getRemark());
			stmnt.setString(12, claim.getStatus());
			stmnt.setTimestamp(13, claim.getRegisteredOn());
			stmnt.setLong(14, claim.getRegisteredBy().getId());
			stmnt.setLong(15, claim.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating claim to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}
	}

	/**
	 * @param claimId - id of the claim to delete
	 * @return reference number of the deleted claim or null if a claim with the
	 *         specified claimId does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteClaim(long claimId) {
		String returnValue = null;
		Claim claim = getClaim(claimId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement claimsStmnt = connection
					.prepareStatement("UPDATE compensation.claim SET status = 'deleted' WHERE id=?");
			claimsStmnt.setLong(1, claimId);
			claimsStmnt.executeUpdate();
			returnValue = claim.getClaimNumber();
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:deleteClaim(long) ");
		}
		return returnValue;
	}

	public ArrayList<ClaimDetail> getAllClaimDetails(ClaimDetailParamModel param){
		ArrayList<ClaimDetail> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);

			String query = "SELECT CD.*,P.lotNo AS paymentLotNo, P.name AS paymentName, PJ.code ||'/'|| "
					+ "D.incomingdocumentno||'/'||D.documentyear AS documentId "
					+ "FROM compensation.ClaimDetail CD, compensation.Payment P, compensation.Document D, "
					+ "compensation.project PJ "
					+ "WHERE PJ.id = D.project AND P.document = D.id AND CD.payment = P.id AND "
					+ "CD.status <> ' " +Constants.STATUS_DELETED +"' AND P.status<>'"+Constants.STATUS_DELETED +"' ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( CD.amount ILIKE '%" + searchString + "%' OR P.lotno ILIKE '%" + searchString + "%' "
							+ "OR P.name ILIKE '%" + searchString + "%' OR PJ.code ILIKE '%" + searchString + "%' "
							+ "OR D.incomingdocumentno ILIKE '%" + searchString + "%' OR D.documentyear ILIKE '%" + searchString + "%') "
					: "");
			query += param.getClaimId() >0?" AND CD.claim = "+ param.getClaimId()+" ":"";

			query += (param.iDisplayLength > 0 ? " LIMIT " + param.iDisplayLength + " OFFSET " + param.iDisplayStart
					: "");
			PreparedStatement claimDetailsStmnt = connection.prepareStatement(query);
			ResultSet claimDetails = claimDetailsStmnt.executeQuery();
			while (claimDetails.next()) {
				ClaimDetail claimDetail = new ClaimDetail();
				claimDetail.setId(claimDetails.getLong("id"));
				claimDetail.setPaymentLotNo(claimDetails.getInt("paymentLotNo"));
				claimDetail.setPaymentName(claimDetails.getString("paymentName"));
				claimDetail.setDocumentId(claimDetails.getString("documentId"));
				claimDetail.setPaidBy(claimDetails.getString("paidBy"));
				claimDetail.setAmount(claimDetails.getDouble("amount"));
				claimDetail.setPaidOn(claimDetails.getDate("paidOn"));
				claimDetail.setRemark(claimDetails.getString("remark"));
				claimDetail.setStatus(claimDetails.getString("status"));
				claimDetail.setRegisteredById(claimDetails.getLong("registeredby"));
				claimDetail.setRegisteredOn(claimDetails.getTimestamp("registeredon"));

				returnValue.add(claimDetail);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllClaims() ");
		}
		return returnValue;
	}

	@Override
	public long getClaimDetailCount(ClaimDetailParamModel param) {
		int returnValue = 0;
		Connection connection = CommonStorage.getConnection();
		try {
			String searchString = CommonTasks.escape(param.sSearch);
			String query = "SELECT count(CD.*) AS c "
					+ "FROM compensation.ClaimDetail CD, compensation.Payment P, compensation.Document D, "
					+ "compensation.project PJ "
					+ "WHERE PJ.id = D.project AND P.document = D.id AND CD.payment = P.id AND "
					+ "CD.status <> ' " +Constants.STATUS_DELETED +"' AND P.status<>'"+Constants.STATUS_DELETED +"' ";
			query += (param.sSearch != null && !param.sSearch.trim().isEmpty()
					? " AND ( CD.amount ILIKE '%" + searchString + "%' OR P.lotno ILIKE '%" + searchString + "%' "
							+ "OR P.name ILIKE '%" + searchString + "%' OR PJ.code ILIKE '%" + searchString + "%' "
							+ "OR D.incomingdocumentno ILIKE '%" + searchString + "%' OR D.documentyear ILIKE '%" + searchString + "%') "
					: "");
			query += param.getClaimId() >0?" AND CD.claim = "+ param.getClaimId()+" ":"";

			PreparedStatement claimDetailsStmnt = connection.prepareStatement(query);
			ResultSet claimDetails = claimDetailsStmnt.executeQuery();
			if (claimDetails.next()) {
				returnValue = claimDetails.getInt("c");
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClaimCount() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves a claim detail object to the database
	 *
	 * @param claimDetail - A ClaimDetail object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(ClaimDetail claimDetail) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.ClaimDetail( payment, claim, paidBy, amount, paidOn, remark, status, registeredOn, registeredBy, clientRate) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT max(rate) FROM compensation.clientservicechargerate CSR WHERE CSR.client = ? AND CSR.status<>'"
					+ Constants.STATUS_DELETED
					+ "' AND ? >= CSR.startdate AND (? <= CSR.enddate OR CSR.enddate=null)))";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, claimDetail.getPayment().getId());
			stmnt.setLong(2, claimDetail.getClaim().getId());
			stmnt.setString(3, claimDetail.getPaidBy());
			stmnt.setDouble(4, claimDetail.getAmount());
			stmnt.setDate(5, claimDetail.getPaidOn());
			stmnt.setString(6, claimDetail.getRemark());
			stmnt.setString(7, claimDetail.getStatus());
			stmnt.setTimestamp(8, claimDetail.getRegisteredOn());
			stmnt.setLong(9, claimDetail.getRegisteredBy().getId());
			stmnt.setLong(10, claimDetail.getClaim().getClient().getId());
			stmnt.setDate(11, claimDetail.getPaidOn());
			stmnt.setDate(12, claimDetail.getPaidOn());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving claim detail to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}
	}

	/**
	 * @return a claim detail in the system identified by
	 * @param id
	 * @since version 1.0
	 */
	@Override
	public ClaimDetail getClaimDetail(long id) {
		ClaimDetail returnValue = new ClaimDetail();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement claimDetailsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.ClaimDetail WHERE status = '"
							+ Constants.STATUS_ACTIVE + "' AND id = ?");
			claimDetailsStmnt.setLong(1, id);
			ResultSet claimDetails = claimDetailsStmnt.executeQuery();
			if (claimDetails.next()) {
				returnValue.setId(claimDetails.getLong("id"));
				returnValue.setPayment(getPayment(claimDetails.getLong("payment")));
				returnValue.setClaim(getClaim(claimDetails.getLong("claim")));
				returnValue.setPaidBy(claimDetails.getString("paidBy"));
				returnValue.setAmount(claimDetails.getDouble("amount"));
				returnValue.setPaidOn(claimDetails.getDate("paidOn"));
				returnValue.setRemark(claimDetails.getString("remark"));
				returnValue.setStatus(claimDetails.getString("status"));

				returnValue.setRegisteredBy(getUser(claimDetails.getLong("registeredby")));
				returnValue.setRegisteredOn(claimDetails.getTimestamp("registeredon"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClaimDetail(id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Updates a claim detail object to the database
	 *
	 * @param claimDetail - A ClaimDetail object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(ClaimDetail claimDetail) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.ClaimDetail SET payment = ?, claim = ?, paidBy = ?, amount = ?, paidOn = ?,"
					+ "remark = ?, status = ?, registeredOn = ?, registeredBy = ?, clientRate = (SELECT max(rate) FROM compensation.clientservicechargerate CSR WHERE CSR.client = ? AND CSR.status<>'deleted' AND ? >= CSR.startdate AND (? <= CSR.enddate OR CSR.enddate=null)) WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, claimDetail.getPayment().getId());
			stmnt.setLong(2, claimDetail.getClaim().getId());
			stmnt.setString(3, claimDetail.getPaidBy());
			stmnt.setDouble(4, claimDetail.getAmount());
			stmnt.setDate(5, claimDetail.getPaidOn());
			stmnt.setString(6, claimDetail.getRemark());
			stmnt.setString(7, claimDetail.getStatus());
			stmnt.setTimestamp(8, claimDetail.getRegisteredOn());
			stmnt.setLong(9, claimDetail.getRegisteredBy().getId());
			stmnt.setLong(10, claimDetail.getClaim().getClient().getId());
			stmnt.setDate(11, claimDetail.getPaidOn());
			stmnt.setDate(12, claimDetail.getPaidOn());
			stmnt.setLong(13, claimDetail.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating claim detail to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}
	}

	/**
	 * @param claimDetailId - id of the claim to delete
	 * @return amount of the deleted claim or null if a claim detail with the
	 *         specified id does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteClaimDetail(long claimDetailId) {
		String returnValue = null;
		ClaimDetail claimDetail = getClaimDetail(claimDetailId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement claimDetailsStmnt = connection.prepareStatement(
					"UPDATE compensation.ClaimDetail SET status = '" + Constants.STATUS_DELETED + "' WHERE id=?");
			claimDetailsStmnt.setLong(1, claimDetailId);
			claimDetailsStmnt.executeUpdate();
			returnValue = claimDetail.getAmount() + "";
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository::deleteClaimDetail(long) ");
		}
		return returnValue;
	}

	/**
	 * @return all amendments in the system
	 * @since version 1.0
	 */
	@Override
	public ArrayList<Amendment> getAllAmendments() {
		ArrayList<Amendment> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement amendmentsStmnt = connection.prepareStatement(
					"SELECT * FROM compensation.Amendment WHERE status <> '" + Constants.STATUS_DELETED + "' ");
			ResultSet amendments = amendmentsStmnt.executeQuery();
			while (amendments.next()) {
				Amendment amendment = new Amendment();
				amendment.setId(amendments.getLong("id"));
				amendment.setIncomingDate(amendments.getDate("incomingdate"));
				amendment.setOutgoingDate(amendments.getDate("outgoingdate"));
				amendment.setIncomingDocumentNo(amendments.getString("incomingdocumentno"));
				amendment.setOutgoingDocumentNo(amendments.getString("outgoingdocumentno"));
				amendment.setPayment(getPayment(amendments.getLong("payment")));
				amendment.setAmount(amendments.getDouble("amount"));
				amendment.setRemark(amendments.getString("remark"));
				amendment.setStatus(amendments.getString("status"));
				amendment.setRegisteredBy(getUser(amendments.getLong("registeredby")));
				amendment.setRegisteredOn(amendments.getTimestamp("registeredon"));
				returnValue.add(amendment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAllAmendments() ");
		}
		return returnValue;
	}

	/**
	 *
	 * Saves an amendment object to the database
	 *
	 * @param amendment - An amendment object representing the data to be saved
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void save(Amendment amendment) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO compensation.Amendment(payment, amount, remark, status, registeredOn, registeredBy, incomingdate, outgoingdate, incomingdocumentno, outgoingdocumentno) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, amendment.getPayment().getId());
			stmnt.setDouble(2, amendment.getAmount());
			stmnt.setString(3, amendment.getRemark());
			stmnt.setString(4, amendment.getStatus());
			stmnt.setTimestamp(5, amendment.getRegisteredOn());
			stmnt.setLong(6, amendment.getRegisteredBy().getId());
			stmnt.setDate(7, amendment.getIncomingDate());
			stmnt.setDate(8, amendment.getOutgoingDate());
			stmnt.setString(9, amendment.getIncomingDocumentNo());
			stmnt.setString(10, amendment.getOutgoingDocumentNo());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving amendment to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}
	}

	/**
	 * @return an amendment in the system identified by
	 * @param id
	 * @since version 1.0
	 */
	@Override
	public Amendment getAmendment(long id) {
		Amendment returnValue = new Amendment();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement amendmentsStmnt = connection
					.prepareStatement("SELECT * FROM compensation.Amendment WHERE status <> '"
							+ Constants.STATUS_DELETED + "' AND id = ?");
			ResultSet amendments = amendmentsStmnt.executeQuery();
			if (amendments.next()) {
				returnValue.setId(amendments.getLong("id"));
				returnValue.setPayment(getPayment(amendments.getLong("payment")));
				returnValue.setAmount(amendments.getDouble("amount"));
				returnValue.setRemark(amendments.getString("remark"));
				returnValue.setStatus(amendments.getString("status"));
				returnValue.setRegisteredBy(getUser(amendments.getLong("registeredby")));
				returnValue.setRegisteredOn(amendments.getTimestamp("registeredon"));
				returnValue.setIncomingDate(amendments.getDate("incomingdate"));
				returnValue.setOutgoingDate(amendments.getDate("outgoingdate"));
				returnValue.setIncomingDocumentNo(amendments.getString("incomingdocumentno"));
				returnValue.setOutgoingDocumentNo(amendments.getString("outgoingdocumentno"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getAmendment(id) ");
		}
		return returnValue;
	}

	/**
	 *
	 * Updates an amendment object in the database
	 *
	 * @param amendment - An amendment object representing the data to be updated
	 * @throws java.lang.Exception
	 * @since version 1.0
	 */
	@Override
	public void update(Amendment amendment) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "UPDATE compensation.amendment SET payment = ?, amount = ?, "
					+ "remark = ?, status = ?, registeredOn = ?, registeredBy = ?, incomingdate=?,"
					+ "outgoingdate=?, incomingdocumentno=?, outgoingdocumentno=? WHERE id = ?";
			PreparedStatement stmnt = connection.prepareStatement(query);
			stmnt.setLong(1, amendment.getPayment().getId());
			stmnt.setDouble(2, amendment.getAmount());
			stmnt.setString(3, amendment.getRemark());
			stmnt.setString(4, amendment.getStatus());
			stmnt.setTimestamp(5, amendment.getRegisteredOn());
			stmnt.setLong(6, amendment.getRegisteredBy().getId());
			stmnt.setDate(7, amendment.getIncomingDate());
			stmnt.setDate(8, amendment.getOutgoingDate());
			stmnt.setString(9, amendment.getIncomingDocumentNo());
			stmnt.setString(10, amendment.getOutgoingDocumentNo());
			stmnt.setLong(11, amendment.getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Updating amendment to database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}
	}

	/**
	 * @param amendmentId - id of the Amendment to delete
	 * @return amount of the deleted Amendment or null if a Amendment with the
	 *         specified id does not exit
	 * @since version 1.0
	 */
	@Override
	public String deleteAmendment(long amendmentId) {
		String returnValue = null;
		Amendment amendment = getAmendment(amendmentId);
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement amendmentsStmnt = connection.prepareStatement(
					"UPDATE compensation.amendment SET status = '" + Constants.STATUS_DELETED + "' WHERE id=?");
			amendmentsStmnt.setLong(1, amendmentId);
			amendmentsStmnt.executeUpdate();
			returnValue = amendment.getAmount() + "";
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository::deleteAmendment(long) ");
		}
		return returnValue;
	}

	/**
	 * @param claimCNNumber - CN claim number of the Client to return
	 * @return Client registered with the claim number or null the specified id does
	 *         not exit
	 * @since version 1.0
	 */
	@Override
	public Client getClientFromCNClaimNo(String claimCNNumber) {
		Client returnValue = new Client();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement clientsStmnt = connection.prepareStatement(
					"SELECT Distinct ON (C.id) C.* FROM compensation.Client C, compensation.Claim CL WHERE C.status <> '"
							+ Constants.STATUS_DELETED + "' AND CL.status <> '" + Constants.STATUS_DELETED
							+ "' AND C.id = CL.client AND CL.claimcnnumber ILIKE ? LIMIT 1");
			clientsStmnt.setString(1, claimCNNumber);
			ResultSet clients = clientsStmnt.executeQuery();
			if (clients.next()) {
				returnValue.setAddressLine(clients.getString("addressLine"));
				returnValue.setCity(clients.getString("city"));
				returnValue.setEmail(clients.getString("email"));
				returnValue.setId(clients.getLong("id"));
				returnValue.setName(clients.getString("name"));
				returnValue.setAmharicName(clients.getString("amharicName"));
				returnValue.setWebsite(clients.getString("website"));
				returnValue.setContactPerson(clients.getString("contactPerson"));
				returnValue.setPhoneNo(clients.getString("phoneNumber"));
				returnValue.setServiceChargeRate(clients.getDouble("rate"));
				returnValue.setRegion(getRegion(clients.getByte("region")));
				returnValue.setRegisteredBy(getUser(clients.getLong("registeredby")));
				returnValue.setRegisteredOn(clients.getTimestamp("registeredon"));
				returnValue.setRemark(clients.getString("remark"));
				returnValue.setStatus(clients.getString("status"));
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getClient(id) ");
		}
		return returnValue;
	}

	/**
	 * returns a single report type
	 *
	 * @return a report type if there exists one with the specified
	 * @param id - of the report type to get
	 * @since version 1.0
	 */
	@Override
	public ReportType getReportType(byte id) {
		ReportType returnValue;
		if (id < 1) {
			returnValue = new ReportType();
		} else {
			returnValue = reportTypes.get(id - 1);
		}
		return returnValue;
	}

	/**
	 * returns the list of available report types
	 *
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public ArrayList<ReportType> getAllReportTypes() {
		ArrayList<ReportType> returnValue;
		returnValue = this.reportTypes;
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified data range
	 *
	 * @param clientRegion object representing client region of the client to report
	 *                     on
	 * @param from         starting date of the report
	 * @param to           ending date of the report
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(ClientRegion clientRegion, java.sql.Date from,
			java.sql.Date to) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'deleted') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR, compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=CL.id AND CR.id=? AND CD.registeredon BETWEEN ? AND ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setLong(1, clientRegion.getId());
			reportStmnt.setDate(2, from);
			reportStmnt.setDate(3, to);
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a claim document
	 *
	 * @param claim object representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Claim claim) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR , compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=CL.id AND CD.claim = ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setLong(1, claim.getId());
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a claim document
	 *
	 * @param claimCNNumber CN Number representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR , compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=CL.id AND C.claimCNNumber ILIKE ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setString(1, claimCNNumber);
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a claim document
	 *
	 * @param claimCNNumber CN Number representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber, boolean branchBased) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR , compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=CL.id AND C.claimCNNumber ILIKE ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setString(1, claimCNNumber);
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("branchID"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a claim document
	 *
	 * @param claimCNNumber CN Number representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber, ClientRegion clientRegion) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR , compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=CL.id AND C.claimCNNumber ILIKE ? AND CR.id = ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setString(1, claimCNNumber);
			reportStmnt.setLong(2, clientRegion.getId());
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a claim document for a client region
	 *
	 * @param clientRegion object representing client region of the client to report
	 *                     on
	 * @param claim        object representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Claim claim, ClientRegion clientRegion) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'deleted') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR , compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CR.client=CL.id AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CD.claim = ? AND CR.id = ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setLong(1, claim.getId());
			reportStmnt.setLong(2, clientRegion.getId());
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * @param branch        object representing paying to report on
	 * @param claimCNNumber CN Number representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber, Branch branch) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, branch B, compensation.clientregion CR , compensation.client CL, BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=CL.id AND C.claimCNNumber ILIKE ? AND C.payingbranch = ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setString(1, claimCNNumber);
			reportStmnt.setLong(2, branch.getId());
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client with the specified data range
	 *
	 * @param client object representing the client to report on
	 * @param from   starting date of the report
	 * @param to     ending date of the report
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Client client, java.sql.Date from,
			java.sql.Date to) {
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicName AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'deleted') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, compensation.client CL, branch B, compensation.clientregion CR , BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.id = D.clientRegion AND CR.client=CL.id AND CR.client=? AND CD.registeredon BETWEEN ? AND ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setLong(1, client.getId());
			reportStmnt.setDate(2, from);
			reportStmnt.setDate(3, to);
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (SQLException ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client with the specified data range
	 *
	 * @param client      object representing the client to report on
	 * @param branchBased if true the report is on the bases of paying branch
	 * @param from        starting date of the report
	 * @param to          ending date of the report
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Client client, java.sql.Date from,
			java.sql.Date to, boolean branchBased) {
		if (!branchBased) {
			return getPaymentReport(client, from, to);
		}
		HashMap<String, ArrayList<PaymentReport>> returnValue = new HashMap<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement reportStmnt = connection.prepareStatement(
					"SELECT sum(CD.amount * cd.clientrate) as serviceCharge, max(BT.rate) as branchRate, CR.id AS regionId, B.id AS branchID, PJ.code AS projectCode,  PJ.name AS projectName, PJ.amharicname AS projectAmharicName, count(CD.id) AS noPayment, (SELECT sum(amount) FROM compensation.Payment WHERE document=D.id AND status<>'deleted') AS clientAmount, sum(CD.amount) as paidAmount, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo, CR.regionname AS region, B.name AS branchName FROM compensation.ClaimDetail CD, compensation.Claim C, compensation.Document D, compensation.Payment P, compensation.Project PJ, compensation.client CL, branch B, compensation.clientregion CR , BranchType BT WHERE B.type=BT.id AND CD.claim = C.id AND P.document = D.id AND CD.payment = P.id AND PJ.id = D.project AND B.id = D.payingbranch AND CR.id = D.clientRegion AND CR.client=CL.id AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND CR.client=? AND CD.registeredon BETWEEN ? AND ? GROUP BY D.id,B.id, PJ.id, CR.id, B.id ");
			reportStmnt.setLong(1, client.getId());
			reportStmnt.setDate(2, from);
			reportStmnt.setDate(3, to);
			ResultSet reports = reportStmnt.executeQuery();
			while (reports.next()) {
				PaymentReport report = new PaymentReport();
				report.setBranchName(reports.getString("branchName"));
				report.setBranchNumber(reports.getLong("branchID"));
				report.setNoOfPayment(reports.getInt("noPayment"));
				report.setPaidAmount(reports.getDouble("paidAmount"));
				report.setReferenceNo(reports.getString("refNo"));
				report.setRegion(reports.getString("region"));
				report.setRegionCode(reports.getLong("regionId"));
				report.setProjectName(reports.getString("projectName"));
				report.setProjectAmharicName(reports.getString("projectAmharicName"));
				report.setClientAmount(reports.getDouble("clientAmount"));
				report.setBranchServiceCharge(report.getPaidAmount() * reports.getDouble("branchRate"));
				report.setCnServiceCharge(reports.getDouble("serviceCharge"));
				report.setCnVAT(report.getCnServiceCharge() * 0.15);
				report.setTotal(report.getCnServiceCharge() + report.getCnVAT());
				if (returnValue.containsKey(reports.getString("projectCode"))) {
					returnValue.get(reports.getString("projectCode")).add(report);
				} else {
					ArrayList<PaymentReport> temp = new ArrayList<>();
					temp.add(report);
					returnValue.put(reports.getString("projectCode"), temp);
				}
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReport() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified data range
	 *
	 * @param clientRegion object representing client region of the client to report
	 *                     on
	 * @param from         starting date of the report
	 * @param to           ending date of the report
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(ClientRegion clientRegion, java.sql.Date from,
			java.sql.Date to) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ , compensation.claimDetail CD, compensation.Document D WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND CD.status <> '"
							+ Constants.STATUS_DELETED
							+ "' AND D.clientregion = ? AND CD.registeredon BETWEEN ? AND ?");
			paymentStmnt.setLong(1, clientRegion.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setStatus(payments.getString("status"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified data range
	 *
	 * @param clientRegion object representing client region of the client to report
	 *                     on
	 * @param from         starting date of the report
	 * @param to           ending date of the report
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(Claim claim) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND CD.claim = ?");
			paymentStmnt.setLong(1, claim.getId());
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setRemark(payments.getString("remark"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified claim number
	 *
	 * @param claimCNNumber CN Number representing claim to report on
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(String claimCNNumber, ClientRegion clientRegion) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D, compensation.Claim C WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND C.status <> '" + Constants.STATUS_DELETED
							+ "' AND CD.claim = C.id AND C.claimCNNumber ILIKE ? AND D.clientregion = ?");
			paymentStmnt.setString(1, claimCNNumber);
			paymentStmnt.setLong(2, clientRegion.getId());
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setRemark(payments.getString("remark"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * @param branch        object representing paying to report on
	 * @param claimCNNumber CN Number representing claim to report on
	 * @return list of report types with a project based key
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(String claimCNNumber, Branch branch) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D, compensation.Claim C WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND C.status <> '" + Constants.STATUS_DELETED
							+ "' AND CD.claim = C.id AND C.claimCNNumber ILIKE ? AND C.payingbranch = ?");
			paymentStmnt.setString(1, claimCNNumber);
			paymentStmnt.setLong(2, branch.getId());
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setRemark(payments.getString("remark"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified claim number
	 *
	 * @param claimCNNumber CN Number representing claim to report on
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(String claimCNNumber) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D, compensation.Claim C WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND C.status <> '" + Constants.STATUS_DELETED
							+ "' AND CD.claim = C.id AND C.claimCNNumber ILIKE ?");
			paymentStmnt.setString(1, claimCNNumber);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setRemark(payments.getString("remark"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified data range
	 *
	 * @param from starting date of the report
	 * @param to   ending date of the report
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(Client client, java.sql.Date from, java.sql.Date to) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D, compensation.ClientRegion CR WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND D.clientregion = CR.id AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND CR.client = ? AND CD.registeredon BETWEEN ? AND ?");
			paymentStmnt.setLong(1, client.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified data range
	 *
	 * @param clientRegion object representing client region of the client to report
	 *                     on
	 * @param from         starting date of the report
	 * @param to           ending date of the report
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(Claim claim, ClientRegion clientRegion) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D WHERE PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND CD.claim = ? AND D.clientregion = ?");
			paymentStmnt.setLong(1, claim.getId());
			paymentStmnt.setLong(2, clientRegion.getId());
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	/**
	 * returns the payment for a client region with the specified data range
	 *
	 * @param client      object representing the client to report on
	 * @param branchBased if true the report is on the bases of paying branch
	 * @param from        starting date of the report
	 * @param to          ending date of the report
	 * @return list of report types
	 * @since version 1.0
	 */
	@Override
	public ArrayList<PaymentDetailReport> getPaymentReportList(Client client, java.sql.Date from, java.sql.Date to,
			boolean branchBased) {
		if (!branchBased) {
			return getPaymentReportList(client, from, to);
		}
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, CD.amount AS paidAmount, D.incomingdate AS orderDate, PJ.code||'/'||D.incomingdocumentno||'/'||D.documentyear AS refNo FROM compensation.payment P, compensation.project PJ, compensation.claimDetail CD, compensation.Document D, compensation.ClientRegion CR, public.branch B WHERE D.payingbranch = B.id AND PJ.id = D.project AND P.id = CD.payment AND D.id = P.document AND D.clientregion = CR.id AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND CR.client = ? AND CD.registeredon BETWEEN ? AND ?");
			paymentStmnt.setLong(1, client.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setRefNo(payments.getString("refNo"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	@Override
	public void updateClientServiceChargeRate(long id) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			PreparedStatement stmnt = connection.prepareStatement(
					"UPDATE compensation.claimdetail CD SET clientRate=CSR.rate FROM compensation.claim C, compensation.clientservicechargerate CSR   WHERE (CD.claim = C.id) AND CSR.client = C.client AND C.client = ? AND CSR.status<>'deleted' AND CD.paidon >= CSR.startdate AND (CD.paidon <= CSR.enddate OR CSR.enddate=null)");
			stmnt.setLong(1, id);
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Update service charge rate database returend zero(0)");
			}
			/// TODO: Check if there is a need to log to activity log table.
		}
	}

	@Override
	public ArrayList<PaidUnpaidReport> getPaidUpaidSummary(Client client, java.sql.Date from, java.sql.Date to) {
		ArrayList<PaidUnpaidReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT B.name as bank, (P.code || '/' || D.incomingdocumentno || '/' || D.documentyear || ' ' ||  to_char(D.incomingdate, 'DD/MM/YYYY') )  AS clientNo, (D.outgoingdocumentno || '  ' || to_char(D.outgoingdate, 'DD/MM/YYYY') ) AS cnNo, D.totalamount, (SELECT sum(CD.amount) FROM  compensation.claimdetail CD, compensation.payment PY WHERE CD.payment = PY.id AND PY.document=D.id AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND PY.status <>  '" + Constants.STATUS_DELETED
							+ "') as paid FROM compensation.project P, compensation.document D,public.branch B WHERE D.payingbranch = B.id AND D.project = P.id AND D.status<>'"
							+ Constants.STATUS_DELETED
							+ "' AND P.client=? AND D.registeredon BETWEEN ? AND ? ORDER BY bank, clientno, cnNo");
			paymentStmnt.setLong(1, client.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaidUnpaidReport payment = new PaidUnpaidReport();
				payment.setAmount(payments.getDouble("totalAmount"));
				payment.setBranchName(payments.getString("bank"));
				payment.setClientRefNo(payments.getString("clientno"));
				payment.setCnRefo(payments.getString("cnno"));
				payment.setPaidAmount(payments.getDouble("paid"));
				payment.setUnpaidAmount(payment.getAmount() - payment.getPaidAmount());
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaidUpaidSummary() ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<PaidUnpaidReport> getPaidUpaidSummary(ClientRegion clientRegion, java.sql.Date from,
			java.sql.Date to) {
		ArrayList<PaidUnpaidReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT B.name as bank, (P.code || '/' || D.incomingdocumentno || '/' || D.documentyear || ' ' ||  to_char(D.incomingdate, 'DD/MM/YYYY') )  AS clientNo, (D.outgoingdocumentno || '  ' || to_char(D.outgoingdate, 'DD/MM/YYYY') ) AS cnNo, D.totalamount, (SELECT sum(CD.amount) FROM  compensation.claimdetail CD, compensation.payment PY WHERE CD.payment = PY.id AND PY.document=D.id AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND PY.status <>  '" + Constants.STATUS_DELETED
							+ "') as paid FROM compensation.clientRegion CR, compensation.project P, compensation.document D,public.branch B WHERE D.payingbranch = B.id AND D.project = P.id AND CR.client = P.client AND D.status<>'"
							+ Constants.STATUS_DELETED + "' AND CR.status<>'" + Constants.STATUS_DELETED
							+ "' AND CR.id=? AND D.clientRegion=CR.id AND D.registeredon BETWEEN ? AND ? ORDER BY bank, clientno, cnNo");
			paymentStmnt.setLong(1, clientRegion.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaidUnpaidReport payment = new PaidUnpaidReport();
				payment.setAmount(payments.getDouble("totalAmount"));
				payment.setBranchName(payments.getString("bank"));
				payment.setClientRefNo(payments.getString("clientno"));
				payment.setCnRefo(payments.getString("cnno"));
				payment.setPaidAmount(payments.getDouble("paid"));
				payment.setUnpaidAmount(payment.getAmount() - payment.getPaidAmount());
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaidUpaidSummary() ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<PaidUnpaidReport> getPaidUpaidSummary(Client client, Branch branch, java.sql.Date from,
			java.sql.Date to) {
		ArrayList<PaidUnpaidReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT B.name as bank, (P.code || '/' || D.incomingdocumentno || '/' || D.documentyear || ' ' ||  to_char(D.incomingdate, 'DD/MM/YYYY') )  AS clientNo, (D.outgoingdocumentno || '  ' || to_char(D.outgoingdate, 'DD/MM/YYYY') ) AS cnNo, D.totalamount, (SELECT sum(CD.amount) FROM  compensation.claimdetail CD, compensation.payment PY WHERE CD.payment = PY.id AND PY.document=D.id AND CD.status <> '"
							+ Constants.STATUS_DELETED + "' AND PY.status <>  '" + Constants.STATUS_DELETED
							+ "') as paid FROM compensation.project P, compensation.document D,public.branch B WHERE D.payingbranch = B.id AND D.project = P.id AND D.status<>'"
							+ Constants.STATUS_DELETED
							+ "' AND P.client=? AND D.payingbranch = ? AND D.registeredon BETWEEN ? AND ? ORDER BY bank, clientno, cnNo");
			paymentStmnt.setLong(1, client.getId());
			paymentStmnt.setLong(2, branch.getId());
			paymentStmnt.setDate(3, from);
			paymentStmnt.setDate(4, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaidUnpaidReport payment = new PaidUnpaidReport();
				payment.setAmount(payments.getDouble("totalAmount"));
				payment.setBranchName(payments.getString("bank"));
				payment.setClientRefNo(payments.getString("clientno"));
				payment.setCnRefo(payments.getString("cnno"));
				payment.setPaidAmount(payments.getDouble("paid"));
				payment.setUnpaidAmount(payment.getAmount() - payment.getPaidAmount());
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaidUpaidSummary() ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<PaymentDetailReport> getPaymentDetail(Client client, java.sql.Date from, java.sql.Date to) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, D.incomingdate orderDate, (PJ.code || '/' || D.incomingdocumentno || '/' || D.documentyear)  AS clientNo, ( SELECT sum(amount) FROM compensation.claimDetail WHERE payment=P.id AND status <> '"
							+ Constants.STATUS_DELETED
							+ "' ) AS paidAmount FROM compensation.payment P, compensation.document D, compensation.project PJ WHERE P.document = D.id AND PJ.id = D.project AND D.status <> '"
							+ Constants.STATUS_DELETED + "' AND P.status <> '" + Constants.STATUS_DELETED
							+ "' AND PJ.status <> '" + Constants.STATUS_DELETED
							+ "' AND PJ.client = ? AND D.registeredon BETWEEN ? AND ?;");
			paymentStmnt.setLong(1, client.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setRefNo(payments.getString("clientNo"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<PaymentDetailReport> getPaymentDetail(ClientRegion clientRegion, java.sql.Date from,
			java.sql.Date to) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, D.incomingdate orderDate, (PJ.code || '/' || D.incomingdocumentno || '/' || D.documentyear)  AS clientNo, ( SELECT sum(amount) FROM compensation.claimDetail WHERE payment=P.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "' ) AS paidAmount FROM compensation.payment P, compensation.document D, compensation.project PJ WHERE P.document = D.id AND PJ.id = D.project AND D.status <> '"
							+ Constants.STATUS_DELETED + "' AND P.status <> '" + Constants.STATUS_DELETED
							+ "' AND PJ.status <> '" + Constants.STATUS_DELETED
							+ "' AND D.clientRegion = ? AND D.registeredon BETWEEN ? AND ?;");
			paymentStmnt.setLong(1, clientRegion.getId());
			paymentStmnt.setDate(2, from);
			paymentStmnt.setDate(3, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setRefNo(payments.getString("clientNo"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	@Override
	public ArrayList<PaymentDetailReport> getPaymentDetail(Client client, Branch branch, java.sql.Date from,
			java.sql.Date to) {
		ArrayList<PaymentDetailReport> returnValue = new ArrayList<>();
		Connection connection = CommonStorage.getConnection();
		try {
			PreparedStatement paymentStmnt = connection.prepareStatement(
					"SELECT P.*, D.incomingdate orderDate, (PJ.code || '/' || D.incomingdocumentno || '/' || D.documentyear)  AS clientNo, ( SELECT sum(amount) FROM compensation.claimDetail WHERE payment=P.id AND status<>'"
							+ Constants.STATUS_DELETED
							+ "' ) AS paidAmount FROM compensation.payment P, compensation.document D, compensation.project PJ WHERE P.document = D.id AND PJ.id = D.project AND D.status <> '"
							+ Constants.STATUS_DELETED + "' AND P.status <> '" + Constants.STATUS_DELETED
							+ "' AND PJ.status <> '" + Constants.STATUS_DELETED
							+ "' AND PJ.client = ? AND D.payingbranch = ? AND D.registeredon BETWEEN ? AND ?");
			paymentStmnt.setLong(1, client.getId());
			paymentStmnt.setLong(2, branch.getId());
			paymentStmnt.setDate(3, from);
			paymentStmnt.setDate(4, to);
			ResultSet payments = paymentStmnt.executeQuery();
			while (payments.next()) {
				PaymentDetailReport payment = new PaymentDetailReport();
				payment.setId(payments.getLong("id"));
				payment.setLotNo(payments.getInt("lotNo"));
				payment.setName(payments.getString("name"));
				payment.setAmount(payments.getDouble("amount"));
				payment.isRestricted(payments.getBoolean("restricted"));
				payment.setRemark(payments.getString("remark"));
				payment.setRefNo(payments.getString("clientNo"));
				payment.setStatus(payments.getString("status"));
				payment.setRegisteredBy(getUser(payments.getLong("registeredby")));
				payment.setRegisteredOn(payments.getTimestamp("registeredon"));
				payment.setOrderDate(payments.getDate("orderDate"));
				payment.setPaidAmount(payments.getDouble("paidAmount"));
				if (payment.getPaidAmount() == 0) {
					payment.setRemark("Unpaid");
				} else if (payment.getPaidAmount() < payment.getAmount()) {
					payment.setRemark("Paid Partially");
				}
				returnValue.add(payment);
			}
			connection.close();
		} catch (Exception ex) {
			CommonTasks.logMessage("Database Error", Message.MESSEGE_TYPE_ERROR, ex,
					"Database error on PostgresMasterRepository:getPaymentReportList() ");
		}
		return returnValue;
	}

	@Override
	public void save(ImportData data) throws Exception {
		try (Connection connection = CommonStorage.getConnection()) {
			String query = "INSERT INTO importdata.sourcedata(lotno, refno, name,"
					+ "region, amount, orderdate, restricted, remark, status,"
					+ "registeredon, registeredby) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmnt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1, data.getLotNo());
			stmnt.setString(2, data.getRefNo());
			stmnt.setString(3, data.getName());
			stmnt.setString(4, data.getRegion());
			stmnt.setDouble(5, data.getAmount());
			stmnt.setDate(6, data.getOrderDate());
			stmnt.setBoolean(7, data.isRestricted());
			stmnt.setString(8, data.getRemark());
			stmnt.setString(9, data.getStatus());
			stmnt.setTimestamp(10, data.getRegisteredOn());
			stmnt.setLong(11, data.getRegisteredBy().getId());
			int result = stmnt.executeUpdate();
			if (result < 1) {
				throw new Exception("Saving import data to database returend zero(0)");
			}
			try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					data.setId(generatedKeys.getLong(1));
				}
			}
//            query = "INSERT INTO Comm(userAccount, userGroup) VALUES (?, ?)";
//            for (int i = 0; i < user.getRoles().size(); i++) {
//                Role role = user.getRoles().get(i);
//                stmnt = connection.prepareStatement(query);
//                stmnt.setLong(1, user.getId());
//                stmnt.setByte(2, role.getCode());
//                result = stmnt.executeUpdate();
//                if (result < 1) {
//                    throw new Exception("Saving user role assignment to database returend zero(0)");
//                }
//            }
			/// TODO: Check if there is a need to log to activity log table.
		}

	}
}
