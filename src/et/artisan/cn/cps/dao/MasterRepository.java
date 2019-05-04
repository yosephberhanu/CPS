package et.artisan.cn.cps.dao;

import java.util.*;

import et.artisan.cn.cps.dto.*;
import et.artisan.cn.cps.entity.*;

/**
 * An interface describing the possible database interactions. Concrete
 * implementation for each of the supported DBMS types derive from this
 * interface
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public interface MasterRepository {

    public void setQueryParamters(QueryParameter parameter);

    /**
     * @return the list of notifications for a user
     * @param id of the user to get notifications for
     * @since version 1.0
     */
    public ArrayList<Notification> getNotifications(long id);

    /**
     * @return the number of unread notifications for a user
     * @param id of the user to get notification count for
     * @since version 1.0
     */
    public int getUnreadNotificationsCount(long id);

    /**
     * Checks login attempts against the database
     *
     * @param userName - username provided by the client
     * @param password - password provided by the client
     * @param host - address of the host the client is trying to login from
     * @return if login is successful a CurrentUserDTO object representing the
     * current user else null is returned
     * @since version 1.0
     */
    public CurrentUserDTO tryLogin(String userName, String password, String host);

    /**
     * Logs start of sessions at login time
     *
     * @param currentUser a CurrentUserDTO object representing the currently
     * logged in user
     * @since version 1.0
     */
    public void logSessionStart(CurrentUserDTO currentUser);

    /**
     * Logs endo of sessions at logout time or when the session expires
     *
     * @param currentUser a CurrentUserDTO object representing the currently
     * logged in user
     * @since version 1.0
     */
    public void logSessionEnd(CurrentUserDTO currentUser);

    /**
     * @return all user accounts in the system
     * @since version 1.0
     */
    public ArrayList<User> getAllUsers(UsersQueryParameter param);

    /**
     * @return all user accounts in the system
     * @param sortKey the column to sort by
     * @since version 1.0
     */
    public long getUsersCount(UsersQueryParameter param);

    /**
     * @return all user accounts in the system
     * @param sortKey the column to sort by
     * @param ascending if true the result will be in ascending order
     * @since version 1.0
     */
    public ArrayList<User> getAllApproverUsers();

    /**
     * @param userId - id of the user to delete
     * @return username of the deleted user or null if a user with the specified
     * user id does not exit
     * @since version 1.0
     */
    public String deleteUser(long userId);

    public User getUser(long userId);

    /**
     *
     * @return all user role types in the system
     */
    public ArrayList<Role> getAllRoles();

    /**
     * Checks if a username exists in the database
     *
     * @param username - The username to be checked
     * @return true if the username exists, otherwise false
     */
    public boolean userNameExists(String username);

    /**
     * Checks if a username exists in the database that does not have the Id
     * specified
     *
     * @param username - The username to be checked
     * @param id - The user id to exclude form the checking
     * @return true if the username exists, otherwise false
     */
    public boolean userNameExists(String username, long id);

    /**
     *
     * @param id - Id of the role to be fetched
     * @return a specific user role type from the system
     * @since version 1.0
     */
    public Role getRole(int id);

    /**
     *
     * Saves a user object to the database
     *
     * @param user - A User object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(User user) throws Exception;

    /**
     *
     * Updates a user object in the database
     *
     * @param user - A User object representing the data to be updated
     * @param changePassword - Flag indicating if the password is to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(User user, boolean changePassword) throws Exception;

    /**
     * @param branchId - id of the branch to delete
     * @return name of the deleted branch or null if a branch with the specified
     * user id does not exit
     * @since version 1.0
     */
    public String deleteBranch(long branchId);

    /**
     * @return all branch types in the system
     * @since version 1.0
     */
    public ArrayList<BranchType> getAllBranchTypes();

    /**
     * @return a single branch type in the system
     * @param id - id of the branch type to return
     * @since version 1.0
     */
    public BranchType getBranchType(byte id);

    /**
     *
     * Updates a branch object in the database
     *
     * @param branch - A Branch object representing the data to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Branch branch) throws Exception;

    /**
     * @return all branch types in the system
     * @since version 1.0
     */
    public ArrayList<BranchRegion> getAllBranchRegions();

    /**
     * @return a branch region in the system
     * @param id the id of the branch to return
     * @since version 1.0
     */
    public BranchRegion getRegion(byte id);

    /**
     * @return all branch types in the system
     * @since version 1.0
     */
    
    public ArrayList<ClientRegion> getAllClientRegions(ClientRegionsParamModel param);

    /**
     * @return all client regions in the system
     * @param sortKey the column to sort by
     * @since version 1.0
     */
    public long getClientRegionCount(ClientRegionsParamModel param);
    
    /**
     * @param clientRegionId - id of the client region to delete
     * @return name of the deleted client region or null if a client region with
     * the specified client region id does not exit
     * @since version 1.0
     */
    public String deleteClientRegions(long clientRegionId);


    public ArrayList<Branch> getAllBranches(BranchesParamModel param);

    public int getBranchesCount(BranchesParamModel param);

    /**
     * Checks if a branchName exists in the database
     *
     * @param branchName - The branchName to be checked
     * @param type - The type of branch(Bank) to be checked
     * @return true if the username exists, otherwise false
     */
    public boolean branchNameExists(String branchName, int type);

    /**
     * Checks if a branch exists in the database that does not have the Id
     * specified
     *
     * @param branchName - The branchName to be checked
     * @param type - The type of branch(Bank) to be checked
     * @param id - The branch id to exclude form the checking
     * @return true if the branchName exists, otherwise false
     */
    public boolean branchNameExists(String branchName, int type, long id);

    /**
     * @return The specified branch if found, null otherwise
     * @param id of the branch to return
     * @since version 1.0
     */
    public Branch getBranch(long id);

    /**
     *
     * Saves a branch object to the database
     *
     * @param branch - A Branch object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(Branch branch) throws Exception;

    /**
     * @return all clients in the system
     * @since version 1.0
     */
    public ArrayList<Client> getAllClients();

    /**
     * @return all clients in the system
     * @param sortKey the column to sort by
     * @since version 1.0
     */
    public ArrayList<Client> getAllClients(String sortKey);

    /**
     * @return all clients in the system
     * @param sortKey the column to sort by
     * @param ascending if true the result will be in ascending order
     * @since version 1.0
     */
    public ArrayList<Client> getAllClients(String sortKey, boolean ascending);

    /**
     * Checks if a client exists in the database
     *
     * @param name - Name of the client be checked
     * @return true if the name exists, otherwise false
     */
    public boolean clientExists(String name);

    /**
     * Checks if a client exists in the database that does not have the Id
     * specified
     *
     * @param name - Name of the client be checked
     * @param id - The client id to exclude form the checking
     * @return true if the name exists, otherwise false
     */
    public boolean clientExists(String name, long id);

    /**
     * @return a client with the specified id
     * @param id - of the client to return
     * @since version 1.0
     */
    public Client getClient(long id);

    /**
     *
     * Saves a client object to the database
     *
     * @param client - A Client object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(Client client) throws Exception;

    /**
     *
     * Updates a client object in the database
     *
     * @param client - A Client object representing the data to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Client client) throws Exception;

    /**
     *
     * Saves a client region object to the database
     *
     * @param clientRegion - A ClientRegion object representing the data to be
     * saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(ClientRegion clientRegion) throws Exception;

    /**
     * @param clientId - id of the client to delete
     * @return name of the deleted client or null if a client with the specified
     * client region id does not exit
     * @since version 1.0
     */
    public String deleteClients(long clientId);

    /**
     *
     * Saves a client service charge to the database
     *
     * @param scRate - A Client Service Charge Rate object representing the data
     * to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(ClientServiceChargeRate scRate) throws Exception;

    /**
     * @return a client region with the specified id
     * @param id - of the client region to return
     * @since version 1.0
     */
    public ClientRegion getClientRegion(long id);

    /**
     * Checks if a client region exists in the database
     *
     * @param regionName - The region name to be checked
     * @param client - The client the region belongs to
     * @return true if the regionName exists, otherwise false
     */
    public boolean clientRegionExists(String regionName, long client);

    /**
     * Checks if a client region exists in the database that does not have the
     * Id
     *
     * @param regionName - The region name to be checked
     * @param client - The client the region belongs to
     * @param id - The client region id to exclude form the checking
     * @return true if the regionName exists, otherwise false
     */
    public boolean clientRegionExists(String regionName, long client, long id);

    /**
     *
     * Updates a client region object in the database
     *
     * @param clientRegion - A ClientRegion object representing the data to be
     * updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(ClientRegion clientRegion) throws Exception;

    /**
     * Checks if a projectName exists in the database
     *
     * @param projectName - The project name to be checked
     * @param client - The client of the project to be checked
     * @param code - The project code to be checked
     * @return true if the projectName exists, otherwise false
     */
    public boolean projectExists(String projectName, String code, long client);

    /**
     * Checks if a project exists in the database that does not have the Id
     *
     * @param projectName - The project name to be checked
     * @param client - The client of the project to be checked
     * @param id - The project id to exclude form the checking
     * @param code - The project code to exclude form the checking
     * @return true if the projectName exists, otherwise false
     */
    public boolean projectExists(String projectName, String code, long client, long id);

    /**
     * @return all projects in the system
     * @since version 1.0
     */
    public ArrayList<Project> getAllProjects(JQueryDataTableParamModel param);

    public int getProjectsCount(JQueryDataTableParamModel param);
    
    public ArrayList<Project> getAllProjects(long clientId);

    /**
     * @return all projects in the system for a single client
     * @param sortKey the column to sort by
     * @param clientId id of the client to filter by
     * @since version 1.0
     */
    public ArrayList<Project> getAllProjects(long clientId, String sortKey);

    /**
     * @return a project in the system
     * @param id of the document to return
     * @since version 1.0
     */
    public Project getProject(long id);

    public Project getProject(String projectCode);

    /**
     *
     * Saves a project object to the database
     *
     * @param project - A Project object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public Project save(Project project) throws Exception;

    /**
     *
     * Updates a project object in the database
     *
     * @param project - A Project object representing the data to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Project project) throws Exception;

    /**
     * @param projectId - id of the project to delete
     * @return name of the deleted project or null if a project with the
     * specified project id does not exit
     * @since version 1.0
     */
    public String deleteProject(long projectId);

    /**
     * @return all documents in the system
     * @since version 1.0
     */
    public ArrayList<Document> getAllDocuments();

    /**
     * @return all documents in the system
     * @param sortKey the column to sort by
     * @since version 1.0
     */
    public ArrayList<Document> getAllDocuments(String sortKey);

    /**
     * @return all documents in the system
     * @param sortKey the column to sort by
     * @param ascending if true the result will be in ascending order
     * @since version 1.0
     */
    public ArrayList<Document> getAllDocuments(String sortKey, boolean ascending);

    public ArrayList<Document> getAllDocuments(long projectId, String sortKey, boolean ascending);

    public ArrayList<Document> getAllDocuments(long projectId);

    /**
     * Checks if a document exists in the database
     *
     * @param project- The project to be checked
     * @param inComingDocumentNo - The incoming document number to be checked
     * @param year - The document year to be checked
     * @return true if the username exists, otherwise false
     */
    public boolean documentExists(long project, String inComingDocumentNo, int year);

    /**
     * Checks if a document exists in the database
     *
     * @param project - The project to be checked
     * @param inComingDocumentNo - The incoming document number to be checked
     * @param year - The document year to be checked
     * @param id - id of the document exclude
     * @return true if the username exists, otherwise false
     */
    public boolean documentExists(long project, String inComingDocumentNo, int year, long id);

    /**
     * @return a document in the system
     * @param id of the document to return
     * @since version 1.0
     */
    public Document getDocument(long id);

    /**
     * @return a document in the system
     * @since version 1.0
     */
    public ArrayList<Document> findDocument(String projectCode, String documentNo, int documentYear);

    public ArrayList<Document> findDocument(double amount);
    
    public ArrayList<Document> findDocument(String cnnumber);

    public Document getDocument(ClientRegion clientRegion, Branch branch, String projectCode, String documenNo, int documentYear);

    public int getDocumentCount(DocumentQueryParameter param);
    
    public ArrayList<Document> getAllDocuments(DocumentQueryParameter param);
    
    /**
     *
     * Saves a document object to the database
     *
     * @param document - A Document object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public Document save(Document document) throws Exception;

    /**
     *
     * Updates a document object in the database
     *
     * @param document - A Document object representing the data to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Document document) throws Exception;

    /**
     * @param documentId - id of the document to delete
     * @return name of the deleted document or null if a document with the
     * specified document id does not exit
     * @since version 1.0
     */
    public String deleteDocument(long documentId);

    /**
     * @return all payments in the system
     * @since version 1.0
     */

    public ArrayList<Payment> getAllPayments(PaymentQueryParameter param);

    public int getPaymentsCount(PaymentQueryParameter param);
    
    public Payment getPayment(long id);

    /**
     * Checks if a payment exists in the database with the specified lot number
     *
     * @param lotNumber - The lot number to be checked
     * @param document of the payment
     * @return true if the lot number exists, otherwise false
     */
    public boolean paymentExists(int lotNumber, long document);

    /**
     * Checks if a payment exists in the database with the specified lot number
     * that does not have the Id specified
     *
     * @param lotNumber - The lot number to be checked
     * @param document of the payment
     * @param id - The user id to exclude form the checking
     * @return true if the lot number exists, otherwise false
     */
    public boolean paymentExists(int lotNumber, long document, long id);

    /**
     *
     * Saves a payment object to the database
     *
     * @param payment - A Payment object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(Payment payment) throws Exception;

    /**
     *
     * Updates a payment object in the database
     *
     * @param payment - A Payment object representing the data to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Payment payment) throws Exception;

    /**
     * @param paymentId - id of the payment to delete
     * @return name of the deleted payment or null if a payment with the
     * specified payment id does not exit
     * @since version 1.0
     */
    public String deletePayment(long paymentId);

    /**
     * @return all claims in the system
     * @since version 1.0
     */
    public ArrayList<Claim> getAllClaims(ClaimParamModel param);

    /**
     * @return all client regions in the system
     * @param sortKey the column to sort by
     * @since version 1.0
     */
    public long getClaimCount(ClaimParamModel param);

    /**
     * @return all claim CN numbers in the system
     * @since version 1.0
     */
    public ArrayList<String> getAllClaimCNNumbers();

    /**
     * @return a claim in the system
     * @param id of the claim to return
     * @since version 1.0
     */
    public Claim getClaim(long id);

    /**
     *
     * Saves a claim object to the database
     *
     * @param claim - A Claim object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(Claim claim) throws Exception;

    /**
     *
     * Updates a claim object in the database
     *
     * @param claim - A Claim object representing the data to be updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Claim claim) throws Exception;

    /**
     * @param claimId - id of the claim to delete
     * @return reference number of the deleted claim or null if a claim with the
     * specified claim id does not exit
     * @since version 1.0
     */
    public String deleteClaim(long claimId);

    /**
     * @return all claim details in the system
     * @since version 1.0
     */
    public ArrayList<ClaimDetail> getAllClaimDetails(ClaimDetailParamModel param);

    /**
     * @return all claim details in the system
     * @param sortKey the column to sort by
     * @since version 1.0
     */
    public long getClaimDetailCount(ClaimDetailParamModel param);
    
    public void save(ClaimDetail claimDetail) throws Exception;

    /**
     * @return a claim detail in the system identified by
     * @param id
     * @since version 1.0
     */
    public ClaimDetail getClaimDetail(long id);

    /**
     *
     * Updates a claim detail object in the database
     *
     * @param claimDetail - A ClaimDetail object representing the data to be
     * updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(ClaimDetail claimDetail) throws Exception;

    /**
     * @param claimDetailId - id of the ClaimDetail to delete
     * @return name of the deleted claim detail or null if a ClaimDetail with
     * the specified id does not exit
     * @since version 1.0
     */
    public String deleteClaimDetail(long claimDetailId);

    /**
     * @return all amendments in the system
     * @since version 1.0
     */
    public ArrayList<Amendment> getAllAmendments();

    /**
     *
     * Saves an amendment object to the database
     *
     * @param amendment - An amendment object representing the data to be saved
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void save(Amendment amendment) throws Exception;

    /**
     * @return an amendment in the system identified by
     * @param id
     * @since version 1.0
     */
    public Amendment getAmendment(long id);

    /**
     *
     * Updates an amendment object in the database
     *
     * @param amendment - An amendment object representing the data to be
     * updated
     * @throws java.lang.Exception
     * @since version 1.0
     */
    public void update(Amendment amendment) throws Exception;

    /**
     * @param amendmentId - id of the Amendment to delete
     * @return amount of the deleted Amendment or null if a Amendment with the
     * specified id does not exit
     * @since version 1.0
     */
    public String deleteAmendment(long amendmentId);

    /**
     * @param claimCNNumber - CN claim number of the Client to return
     * @return Client registered with the claim number or null the specified id
     * does not exit
     * @since version 1.0
     */
    public Client getClientFromCNClaimNo(String claimCNNumber);

    /**
     * returns a single report type
     *
     * @return a report type if there exists one with the specified
     * @param id - of the report type to get
     * @since version 1.0
     */
    public ReportType getReportType(byte id);

    /**
     * returns the list of available report types
     *
     * @return list of report types
     * @since version 1.0
     */
    public ArrayList<ReportType> getAllReportTypes();

    /**
     * returns the payment for a client region with the specified data range
     *
     * @param clientRegion object representing client region of the client to
     * report on
     * @param from starting date of the report
     * @param to ending date of the report
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(ClientRegion clientRegion, java.sql.Date from, java.sql.Date to);

    /**
     * returns the payment for a claim document
     *
     * @param claim object representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Claim claim);

    /**
     * returns the payment for a claim document
     *
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber);

    /**
     * returns the payment for a claim document
     *
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber, boolean branchBased);

    /**
     * returns the payment for a claim document
     *
     * @param clientRegion object representing client region of the client to
     * report on
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber, ClientRegion clientRegion);

    /**
     * @param branch object representing paying to report on
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(String claimCNNumber, Branch branch);

    /**
     * returns the payment for a claim document for a client region
     *
     * @param clientRegion object representing client region of the client to
     * report on
     * @param claim object representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Claim claim, ClientRegion clientRegion);

    /**
     * returns the payment for a client with the specified data range
     *
     * @param client object representing the client to report on
     * @param from starting date of the report
     * @param to ending date of the report
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Client client, java.sql.Date from, java.sql.Date to);

    /**
     * returns the payment for a client with the specified data range
     *
     * @param client object representing the client to report on
     * @param branchBased if true the report is on the bases of paying branch
     * @param from starting date of the report
     * @param to ending date of the report
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public HashMap<String, ArrayList<PaymentReport>> getPaymentReport(Client client, java.sql.Date from, java.sql.Date to, boolean branchBased);

    /**
     * returns the payment list for a client region with the specified data
     * range
     *
     * @param clientRegion object representing client region of the client to
     * report on
     * @param from starting date of the report
     * @param to ending date of the report
     * @return list of payments posted
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(ClientRegion clientRegion, java.sql.Date from, java.sql.Date to);

    /**
     * returns the payment for a claim document
     *
     * @param claim object representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(Claim claim);

    /**
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(String claimCNNumber);

    /**
     * @param clientRegion object representing client region of the client to
     * report on
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(String claimCNNumber, ClientRegion clientRegion);

    /**
     * @param branch object representing paying to report on
     * @param claimCNNumber CN Number representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(String claimCNNumber, Branch branch);

    public ArrayList<PaidUnpaidReport> getPaidUpaidSummary(Client client, java.sql.Date from, java.sql.Date to);

    public ArrayList<PaidUnpaidReport> getPaidUpaidSummary(ClientRegion clientRegion, java.sql.Date from, java.sql.Date to);

    public ArrayList<PaidUnpaidReport> getPaidUpaidSummary(Client client, Branch branch, java.sql.Date from, java.sql.Date to);

    /**
     * returns the payment for a claim document for a client region
     *
     * @param clientRegion object representing client region of the client to
     * report on
     * @param claim object representing claim to report on
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(Claim claim, ClientRegion clientRegion);

    /**
     * returns the payment for a client with the specified data range
     *
     * @param client object representing the client to report on
     * @param from starting date of the report
     * @param to ending date of the report
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(Client client, java.sql.Date from, java.sql.Date to);

    /**
     * returns the payment for a client with the specified data range
     *
     * @param client object representing the client to report on
     * @param branchBased if true the report is on the bases of paying branch
     * @param from starting date of the report
     * @param to ending date of the report
     * @return list of report types with a project based key
     * @since version 1.0
     */
    public ArrayList<PaymentDetailReport> getPaymentReportList(Client client, java.sql.Date from, java.sql.Date to, boolean branchBased);

    public ArrayList<PaymentDetailReport> getPaymentDetail(ClientRegion clientRegion, java.sql.Date from, java.sql.Date to);

    public ArrayList<PaymentDetailReport> getPaymentDetail(Client client, java.sql.Date from, java.sql.Date to);

    public ArrayList<PaymentDetailReport> getPaymentDetail(Client client, Branch branch, java.sql.Date from, java.sql.Date to);

    public void updateClientServiceChargeRate(long id) throws Exception;

    public void save(ImportData data) throws Exception;
}
