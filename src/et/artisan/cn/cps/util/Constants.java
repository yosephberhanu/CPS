package et.artisan.cn.cps.util;

/**
 *
 * @author Yoseph Berhanu<yoseph@artisan.et>
 * @since 1.0
 * @version 1.0
 */
public interface Constants {

    public static final int PAYMENT_ROWS_PAGE = 30;

    // Common
    public static final String ACTION_ALL_LOGIN_FORM = CommonStorage.md5("ALL_LOGIN_FORM");
    public static final String ACTION_ALL_LOGIN = CommonStorage.md5("ALL_LOGIN");
    public static final String ACTION_ALL_LOGOUT = CommonStorage.md5("ALL_LOGOUT");
    public static final String ACTION_ALL_FORBIDDEN = CommonStorage.md5("ALL_FORBIDDEN");

    public static final String ACTION_ALL_GET_PROFILE_PICTURE = CommonStorage.md5("ALL_GET_PROFILE_PICTURE");
    public static final String ACTION_ALL_GET_SIGNATURE = CommonStorage.md5("ALL_GET_SIGNATURE");

    public static final String ACTION_ALL_HELP = CommonStorage.md5("ALL_HELP");
    public static final String ACTION_ALL_WELCOME = CommonStorage.md5("ALL_WELCOME");
    public static final String ACTION_ALL_VIEW_PROFILE = CommonStorage.md5("ALL_VIEW_PROFILE");
    public static final String ACTION_ALL_EDIT_PROFILE = CommonStorage.md5("ALL_EDIT_PROFILE");
    public static final String ACTION_ALL_UPDATE_PROFILE = CommonStorage.md5("ALL_UPDATE_PROFILE");
    public static final String ACTION_ALL_VIEW_NOTIFICATIONS = CommonStorage.md5("ALL_VIEW_NOTIFICATIONS");

    //Administrator
    public static final String ACTION_ADMINISTRATOR_DASHBOARD = CommonStorage.md5("ADMINISTRATOR_DASHBOARD");

    public static final String ACTION_ADMINISTRATOR_USERS = CommonStorage.md5("ADMINISTRATOR_USERS");
    public static final String ACTION_ADMINISTRATOR_USERS_LIST = CommonStorage.md5("ADMINISTRATOR_USERS_LIST");
    public static final String ACTION_ADMINISTRATOR_USERS_CARD = CommonStorage.md5("ADMINISTRATOR_USERS_CARD");
    public static final String ACTION_ADMINISTRATOR_ADD_USER = CommonStorage.md5("ADMINISTRATOR_ADD_USER");
    public static final String ACTION_ADMINISTRATOR_SAVE_USER = CommonStorage.md5("ADMINISTRATOR_SAVE_USER");
    public static final String ACTION_ADMINISTRATOR_VIEW_USER = CommonStorage.md5("ADMINISTRATOR_VIEW_USER");
    public static final String ACTION_ADMINISTRATOR_EDIT_USER = CommonStorage.md5("ADMINISTRATOR_EDIT_USER");
    public static final String ACTION_ADMINISTRATOR_DEACTIVATE_USER = CommonStorage.md5("ADMINISTRATOR_DEACTIVATE_USER");
    public static final String ACTION_ADMINISTRATOR_REACTIVATE_USER = CommonStorage.md5("ADMINISTRATOR_REACTIVATE_USER");
    public static final String ACTION_ADMINISTRATOR_UPDATE_USER = CommonStorage.md5("ADMINISTRATOR_UPDATE_USER");
    public static final String ACTION_ADMINISTRATOR_DELETE_USER = CommonStorage.md5("ADMINISTRATOR_DELETE_USER");
    public static final String ACTION_ADMINISTRATOR_GET_USER_PHOTO = CommonStorage.md5("ADMINISTRATOR_GET_USER_PHOTO");
    public static final String ACTION_ADMINISTRATOR_GET_SIGNATURE = CommonStorage.md5("ADMINISTRATOR_GET_SIGNATURE");
    public static final String ACTION_ADMINISTRATOR_UPDATE_PASSWORD_USER = CommonStorage.md5("ADMINISTRATOR_UPDATE_PASSWORD_USER");

    public static final String ACTION_ADMINISTRATOR_LOOKUP = CommonStorage.md5("ADMINISTRATOR_LOOKUP");

    public static final String ACTION_ADMINISTRATOR_LOG = CommonStorage.md5("ADMINISTRATOR_LOG");
    public static final String ACTION_ADMINISTRATOR_SETTINGS = CommonStorage.md5("ADMINISTRATOR_SETTINGS");
    public static final String ACTION_ADMINISTRATOR_UPDATE_SYSTEM = CommonStorage.md5("ADMINISTRATOR_UPDATE_SYSTEM");

    public static final String ACTION_DATAENTRY_BRANCHES = CommonStorage.md5("ADMINISTRATOR_BRANCHES");
    public static final String ACTION_DATAENTRY_BRANCHES_LIST = CommonStorage.md5("ADMINISTRATOR_BRANCHES_LIST");
    public static final String ACTION_DATAENTRY_BRANCHES_CARD = CommonStorage.md5("ADMINISTRATOR_BRANCHES_CARD");
    public static final String ACTION_DATAENTRY_ADD_BRANCH = CommonStorage.md5("ADMINISTRATOR_ADD_BRANCH");
    public static final String ACTION_DATAENTRY_SAVE_BRANCH = CommonStorage.md5("ADMINISTRATOR_SAVE_BRANCH");
    public static final String ACTION_DATAENTRY_VIEW_BRANCH = CommonStorage.md5("ADMINISTRATOR_VIEW_BRANCH");
    public static final String ACTION_DATAENTRY_EDIT_BRANCH = CommonStorage.md5("DATAENTRY_EDIT_BRANCH");
    public static final String ACTION_DATAENTRY_UPDATE_BRANCH = CommonStorage.md5("DATAENTRY_UPDATE_BRANCH");
    public static final String ACTION_DATAENTRY_DELETE_BRANCH = CommonStorage.md5("DATAENTRY_DELETE_BRANCH");

    public static final String ACTION_DATAENTRY_CLIENTS = CommonStorage.md5("DATAENTRY_CLIENTS");
    public static final String ACTION_DATAENTRY_CLIENTS_LIST = CommonStorage.md5("DATAENTRY_CLIENTS_LIST");
    public static final String ACTION_DATAENTRY_CLIENTS_CARD = CommonStorage.md5("DATAENTRY_CLIENTS_CARD");
    public static final String ACTION_DATAENTRY_ADD_CLIENT = CommonStorage.md5("DATAENTRY_ADD_CLIENT");
    public static final String ACTION_DATAENTRY_SAVE_CLIENT = CommonStorage.md5("DATAENTRY_SAVE_CLIENT");
    public static final String ACTION_DATAENTRY_VIEW_CLIENT = CommonStorage.md5("DATAENTRY_VIEW_CLIENT");
    public static final String ACTION_DATAENTRY_EDIT_CLIENT = CommonStorage.md5("DATAENTRY_EDIT_CLIENT");
    public static final String ACTION_DATAENTRY_UPDATE_CLIENT = CommonStorage.md5("DATAENTRY_UPDATE_CLIENT");
    public static final String ACTION_DATAENTRY_DELETE_CLIENT = CommonStorage.md5("DATAENTRY_DELETE_CLIENT");

    public static final String ACTION_DATAENTRY_CLIENT_REGIONS = CommonStorage.md5("DATAENTRY_CLIENT__REGIONS");
    public static final String ACTION_DATAENTRY_CLIENT_REGIONS_LIST = CommonStorage.md5("DATAENTRY_CLIENT_REGIONS_LIST");
    public static final String ACTION_DATAENTRY_CLIENT_REGIONS_CARD = CommonStorage.md5("DATAENTRY_CLIENT_REGIONS_CARD");
    public static final String ACTION_DATAENTRY_ADD_CLIENT_REGION = CommonStorage.md5("DATAENTRY_ADD_CLIENT_REGION");
    public static final String ACTION_DATAENTRY_SAVE_CLIENT_REGION = CommonStorage.md5("DATAENTRY_SAVE_CLIENT_REGION");
    public static final String ACTION_DATAENTRY_VIEW_CLIENT_REGION = CommonStorage.md5("DATAENTRY_VIEW_CLIENT_REGION");
    public static final String ACTION_DATAENTRY_EDIT_CLIENT_REGION = CommonStorage.md5("DATAENTRY_EDIT_CLIENT_REGION");
    public static final String ACTION_DATAENTRY_UPDATE_CLIENT_REGION = CommonStorage.md5("DATAENTRY_UPDATE_CLIENT_REGION");
    public static final String ACTION_DATAENTRY_DELETE_CLIENT_REGION = CommonStorage.md5("DATAENTRY_DELETE_CLIENT_REGION");

    public static final String ACTION_DATAENTRY_CLIENT_RATES = CommonStorage.md5("DATAENTRY_CLIENT_RATES");
    public static final String ACTION_DATAENTRY_CLIENT_RATES_LIST = CommonStorage.md5("DATAENTRY_CLIENT_RATES_LIST");
    public static final String ACTION_DATAENTRY_ADD_CLIENT_RATE = CommonStorage.md5("DATAENTRY_ADD_CLIENT_RATE");
    public static final String ACTION_DATAENTRY_SAVE_CLIENT_RATE = CommonStorage.md5("DATAENTRY_SAVE_CLIENT_RATE");
    public static final String ACTION_DATAENTRY_VIEW_CLIENT_RATE = CommonStorage.md5("DATAENTRY_VIEW_CLIENT_RATE");

    public static final String ACTION_DATAENTRY_PROJECTS = CommonStorage.md5("DATAENTRY_PROJECTS");
    public static final String ACTION_DATAENTRY_PROJECTS_LIST = CommonStorage.md5("DATAENTRY_PROJECTS_LIST");
    public static final String ACTION_DATAENTRY_PROJECTS_CARD = CommonStorage.md5("DATAENTRY_PROJECTS_CARD");
    public static final String ACTION_DATAENTRY_ADD_PROJECT = CommonStorage.md5("DATAENTRY_ADD_PROJECT");
    public static final String ACTION_DATAENTRY_SAVE_PROJECT = CommonStorage.md5("DATAENTRY_SAVE_PROJECT");
    public static final String ACTION_DATAENTRY_VIEW_PROJECT = CommonStorage.md5("DATAENTRY_VIEW_PROJECT");
    public static final String ACTION_DATAENTRY_EDIT_PROJECT = CommonStorage.md5("DATAENTRY_EDIT_PROJECT");
    public static final String ACTION_DATAENTRY_UPDATE_PROJECT = CommonStorage.md5("DATAENTRY_UPDATE_PROJECT");
    public static final String ACTION_DATAENTRY_DELETE_PROJECT = CommonStorage.md5("DATAENTRY_DELETE_PROJECT");

    public static final String ACTION_DATAENTRY_DOCUMENTS = CommonStorage.md5("DATAENTRY_DOCUMENTS");
    public static final String ACTION_DATAENTRY_FIND_DOCUMENTS = CommonStorage.md5("DATAENTRY_FIND_DOCUMENTS");
    public static final String ACTION_DATAENTRY_DOCUMENTS_LIST = CommonStorage.md5("DATAENTRY_DOCUMENTS_LIST");
    public static final String ACTION_DATAENTRY_DOCUMENTS_CARD = CommonStorage.md5("DATAENTRY_DOCUMENTS_CARD");
    public static final String ACTION_DATAENTRY_ADD_DOCUMENT = CommonStorage.md5("DATAENTRY_ADD_DOCUMENT");
    public static final String ACTION_DATAENTRY_SAVE_DOCUMENT = CommonStorage.md5("DATAENTRY_SAVE_DOCUMENT");
    public static final String ACTION_DATAENTRY_VIEW_DOCUMENT = CommonStorage.md5("DATAENTRY_VIEW_DOCUMENT");
    public static final String ACTION_DATAENTRY_EDIT_DOCUMENT = CommonStorage.md5("DATAENTRY_EDIT_DOCUMENT");
    public static final String ACTION_DATAENTRY_UPDATE_DOCUMENT = CommonStorage.md5("DATAENTRY_UPDATE_DOCUMENT");
    public static final String ACTION_DATAENTRY_DELETE_DOCUMENT = CommonStorage.md5("DATAENTRY_DELETE_DOCUMENT");
    public static final String ACTION_DATAENTRY_GET_DOCUMENT_ATTACHMENT = CommonStorage.md5("DATAENTRY_GET_DOCUMENT_ATTACHMENT");

    public static final String ACTION_DATAENTRY_PAYMENTS = CommonStorage.md5("DATAENTRY_PAYMENTS");
    public static final String ACTION_DATAENTRY_PAYMENTS_LIST = CommonStorage.md5("DATAENTRY_PAYMENTS_LIST");
    public static final String ACTION_DATAENTRY_PAYMENTS_CARD = CommonStorage.md5("DATAENTRY_PAYMENTS_CARD");
    public static final String ACTION_DATAENTRY_ADD_PAYMENT = CommonStorage.md5("DATAENTRY_ADD_PAYMENT");
    public static final String ACTION_DATAENTRY_SAVE_PAYMENT = CommonStorage.md5("DATAENTRY_SAVE_PAYMENT");
    public static final String ACTION_DATAENTRY_VIEW_PAYMENT = CommonStorage.md5("DATAENTRY_VIEW_PAYMENT");
    public static final String ACTION_DATAENTRY_EDIT_PAYMENT = CommonStorage.md5("DATAENTRY_EDIT_PAYMENT");
    public static final String ACTION_DATAENTRY_UPDATE_PAYMENT = CommonStorage.md5("DATAENTRY_UPDATE_PAYMENT");
    public static final String ACTION_DATAENTRY_DELETE_PAYMENT = CommonStorage.md5("DATAENTRY_DELETE_PAYMENT");
    public static final String ACTION_DATAENTRY_POST_PAYMENT = CommonStorage.md5("DATAENTRY_POST_PAYMENT");
    public static final String ACTION_DATAENTRY_CANCEL_PAYMENT = CommonStorage.md5("DATAENTRY_CANCEL_PAYMENT");

    public static final String ACTION_DATAENTRY_CLAIMS = CommonStorage.md5("DATAENTRY_CLAIMS");
    public static final String ACTION_DATAENTRY_FIND_CLAIMS = CommonStorage.md5("DATAENTRY_FIND_CLAIMS");
    public static final String ACTION_DATAENTRY_CLAIMS_LIST = CommonStorage.md5("DATAENTRY_CLAIMS_LIST");
    public static final String ACTION_DATAENTRY_CLAIMS_CARD = CommonStorage.md5("DATAENTRY_CLAIMS_CARD");
    public static final String ACTION_DATAENTRY_ADD_CLAIM = CommonStorage.md5("DATAENTRY_ADD_CLAIM");
    public static final String ACTION_DATAENTRY_SAVE_CLAIM = CommonStorage.md5("DATAENTRY_SAVE_CLAIM");
    public static final String ACTION_DATAENTRY_VIEW_CLAIM = CommonStorage.md5("DATAENTRY_VIEW_CLAIM");
    public static final String ACTION_DATAENTRY_EDIT_CLAIM = CommonStorage.md5("DATAENTRY_EDIT_CLAIM");
    public static final String ACTION_DATAENTRY_UPDATE_CLAIM = CommonStorage.md5("DATAENTRY_UPDATE_CLAIM");
    public static final String ACTION_DATAENTRY_DELETE_CLAIM = CommonStorage.md5("DATAENTRY_DELETE_CLAIM");
    public static final String ACTION_DATAENTRY_GET_CLAIM_ATTACHMENT = CommonStorage.md5("DATAENTRY_GET_CLAIM_ATTACHMENT");

    public static final String ACTION_DATAENTRY_CLAIM_DETAILS = CommonStorage.md5("DATAENTRY_CLAIM_DETAILS");
    public static final String ACTION_DATAENTRY_ADD_CLAIM_DETAIL = CommonStorage.md5("DATAENTRY_ADD_CLAIM_DETAIL");
    public static final String ACTION_DATAENTRY_SAVE_CLAIM_DETAIL = CommonStorage.md5("DATAENTRY_SAVE_CLAIM_DETAIL");
    public static final String ACTION_DATAENTRY_VIEW_CLAIM_DETAIL = CommonStorage.md5("DATAENTRY_VIEW_CLAIM_DETAIL");
    public static final String ACTION_DATAENTRY_EDIT_CLAIM_DETAIL = CommonStorage.md5("DATAENTRY_EDIT_CLAIM_DETAIL");
    public static final String ACTION_DATAENTRY_UPDATE_CLAIM_DETAIL = CommonStorage.md5("DATAENTRY_UPDATE_CLAIM_DETAIL");
    public static final String ACTION_DATAENTRY_DELETE_CLAIM_DETAIL = CommonStorage.md5("DATAENTRY_DELETE_CLAIM_DETAIL");

    public static final String ACTION_DATAENTRY_AMENDMENTS = CommonStorage.md5("DATAENTRY_AMENDMENTS");
    public static final String ACTION_DATAENTRY_AMENDMENTS_LIST = CommonStorage.md5("DATAENTRY_AMENDMENTS_LIST");
    public static final String ACTION_DATAENTRY_AMENDMENTS_CARD = CommonStorage.md5("DATAENTRY_AMENDMENTS_CARD");
    public static final String ACTION_DATAENTRY_ADD_AMENDMENT = CommonStorage.md5("DATAENTRY_ADD_AMENDMENT");
    public static final String ACTION_DATAENTRY_SAVE_AMENDMENT = CommonStorage.md5("DATAENTRY_SAVE_AMENDMENT");
    public static final String ACTION_DATAENTRY_VIEW_AMENDMENT = CommonStorage.md5("DATAENTRY_VIEW_AMENDMENT");
    public static final String ACTION_DATAENTRY_EDIT_AMENDMENT = CommonStorage.md5("DATAENTRY_EDIT_AMENDMENT");
    public static final String ACTION_DATAENTRY_UPDATE_AMENDMENT = CommonStorage.md5("DATAENTRY_UPDATE_AMENDMENT");
    public static final String ACTION_DATAENTRY_DELETE_AMENDMENT = CommonStorage.md5("DATAENTRY_DELETE_AMENDMENT");

    public static final String ACTION_DATAENTRY_ADD_IMPORT = CommonStorage.md5("DATAENTRY_ADD_IMPORT");
    public static final String ACTION_DATAENTRY_SAVE_IMPORT = CommonStorage.md5("DATAENTRY_SAVE_IMPORT");

    /*
    public static final String ACTION_SUPERVISOR_BRANCHES = CommonStorage.md5("SUPERVISOR_BRANCHES");
    public static final String ACTION_SUPERVISOR_BRANCHES_LIST = CommonStorage.md5("SUPERVISOR_BRANCHES_LIST");
    public static final String ACTION_SUPERVISOR_BRANCHES_CARD = CommonStorage.md5("SUPERVISOR_BRANCHES_CARD");
    public static final String ACTION_SUPERVISOR_ADD_BRANCH = CommonStorage.md5("SUPERVISOR_ADD_BRANCH");
    public static final String ACTION_SUPERVISOR_SAVE_BRANCH = CommonStorage.md5("SUPERVISOR_SAVE_BRANCH");
    public static final String ACTION_SUPERVISOR_VIEW_BRANCH = CommonStorage.md5("SUPERVISOR_VIEW_BRANCH");
    public static final String ACTION_SUPERVISOR_EDIT_BRANCH = CommonStorage.md5("SUPERVISOR_EDIT_BRANCH");
    public static final String ACTION_SUPERVISOR_UPDATE_BRANCH = CommonStorage.md5("SUPERVISOR_UPDATE_BRANCH");
    public static final String ACTION_SUPERVISOR_DELETE_BRANCH = CommonStorage.md5("SUPERVISOR_DELETE_BRANCH");

    public static final String ACTION_SUPERVISOR_CLIENTS = CommonStorage.md5("SUPERVISOR_CLIENTS");
    public static final String ACTION_SUPERVISOR_CLIENTS_LIST = CommonStorage.md5("SUPERVISOR_CLIENTS_LIST");
    public static final String ACTION_SUPERVISOR_CLIENTS_CARD = CommonStorage.md5("SUPERVISOR_CLIENTS_CARD");
    public static final String ACTION_SUPERVISOR_ADD_CLIENT = CommonStorage.md5("SUPERVISOR_ADD_CLIENT");
    public static final String ACTION_SUPERVISOR_SAVE_CLIENT = CommonStorage.md5("SUPERVISOR_SAVE_CLIENT");
    public static final String ACTION_SUPERVISOR_VIEW_CLIENT = CommonStorage.md5("SUPERVISOR_VIEW_CLIENT");
    public static final String ACTION_SUPERVISOR_EDIT_CLIENT = CommonStorage.md5("SUPERVISOR_EDIT_CLIENT");
    public static final String ACTION_SUPERVISOR_UPDATE_CLIENT = CommonStorage.md5("SUPERVISOR_UPDATE_CLIENT");
    public static final String ACTION_SUPERVISOR_DELETE_CLIENT = CommonStorage.md5("SUPERVISOR_DELETE_CLIENT");

    public static final String ACTION_SUPERVISOR_CLIENT_REGIONS = CommonStorage.md5("SUPERVISOR_CLIENT__REGIONS");
    public static final String ACTION_SUPERVISOR_CLIENT_REGIONS_LIST = CommonStorage.md5("SUPERVISOR_CLIENT_REGIONS_LIST");
    public static final String ACTION_SUPERVISOR_CLIENT_REGIONS_CARD = CommonStorage.md5("SUPERVISOR_CLIENT_REGIONS_CARD");
    public static final String ACTION_SUPERVISOR_ADD_CLIENT_REGION = CommonStorage.md5("SUPERVISOR_ADD_CLIENT_REGION");
    public static final String ACTION_SUPERVISOR_SAVE_CLIENT_REGION = CommonStorage.md5("SUPERVISOR_SAVE_CLIENT_REGION");
    public static final String ACTION_SUPERVISOR_VIEW_CLIENT_REGION = CommonStorage.md5("SUPERVISOR_VIEW_CLIENT_REGION");
    public static final String ACTION_SUPERVISOR_EDIT_CLIENT_REGION = CommonStorage.md5("SUPERVISOR_EDIT_CLIENT_REGION");
    public static final String ACTION_SUPERVISOR_UPDATE_CLIENT_REGION = CommonStorage.md5("SUPERVISOR_UPDATE_CLIENT_REGION");
    public static final String ACTION_SUPERVISOR_DELETE_CLIENT_REGION = CommonStorage.md5("SUPERVISOR_DELETE_CLIENT_REGION");

    public static final String ACTION_SUPERVISOR_PROJECTS = CommonStorage.md5("SUPERVISOR_PROJECTS");
    public static final String ACTION_SUPERVISOR_PROJECTS_LIST = CommonStorage.md5("SUPERVISOR_PROJECTS_LIST");
    public static final String ACTION_SUPERVISOR_PROJECTS_CARD = CommonStorage.md5("SUPERVISOR_PROJECTS_CARD");
    public static final String ACTION_SUPERVISOR_ADD_PROJECT = CommonStorage.md5("SUPERVISOR_ADD_PROJECT");
    public static final String ACTION_SUPERVISOR_SAVE_PROJECT = CommonStorage.md5("SUPERVISOR_SAVE_PROJECT");
    public static final String ACTION_SUPERVISOR_VIEW_PROJECT = CommonStorage.md5("SUPERVISOR_VIEW_PROJECT");
    public static final String ACTION_SUPERVISOR_EDIT_PROJECT = CommonStorage.md5("SUPERVISOR_EDIT_PROJECT");
    public static final String ACTION_SUPERVISOR_UPDATE_PROJECT = CommonStorage.md5("SUPERVISOR_UPDATE_PROJECT");
    public static final String ACTION_SUPERVISOR_DELETE_PROJECT = CommonStorage.md5("SUPERVISOR_DELETE_PROJECT");

    public static final String ACTION_SUPERVISOR_DOCUMENTS = CommonStorage.md5("SUPERVISOR_DOCUMENTS");
    public static final String ACTION_SUPERVISOR_DOCUMENTS_LIST = CommonStorage.md5("SUPERVISOR_DOCUMENTS_LIST");
    public static final String ACTION_SUPERVISOR_DOCUMENTS_CARD = CommonStorage.md5("SUPERVISOR_DOCUMENTS_CARD");
    public static final String ACTION_SUPERVISOR_ADD_DOCUMENT = CommonStorage.md5("SUPERVISOR_ADD_DOCUMENT");
    public static final String ACTION_SUPERVISOR_SAVE_DOCUMENT = CommonStorage.md5("SUPERVISOR_SAVE_DOCUMENT");
    public static final String ACTION_SUPERVISOR_VIEW_DOCUMENT = CommonStorage.md5("SUPERVISOR_VIEW_DOCUMENT");
    public static final String ACTION_SUPERVISOR_EDIT_DOCUMENT = CommonStorage.md5("SUPERVISOR_EDIT_DOCUMENT");
    public static final String ACTION_SUPERVISOR_UPDATE_DOCUMENT = CommonStorage.md5("SUPERVISOR_UPDATE_DOCUMENT");
    public static final String ACTION_SUPERVISOR_DELETE_DOCUMENT = CommonStorage.md5("SUPERVISOR_DELETE_DOCUMENT");

    public static final String ACTION_SUPERVISOR_PAYMENTS = CommonStorage.md5("SUPERVISOR_PAYMENTS");
    public static final String ACTION_SUPERVISOR_PAYMENTS_TABLE = CommonStorage.md5("SUPERVISOR_PAYMENTS_TABLE");
    public static final String ACTION_SUPERVISOR_PAYMENTS_GRID = CommonStorage.md5("SUPERVISOR_PAYMENTS_GRID");
    public static final String ACTION_SUPERVISOR_ADD_PAYMENT = CommonStorage.md5("SUPERVISOR_ADD_PAYMENT");
    public static final String ACTION_SUPERVISOR_SAVE_PAYMENT = CommonStorage.md5("SUPERVISOR_SAVE_PAYMENT");
    public static final String ACTION_SUPERVISOR_VIEW_PAYMENT = CommonStorage.md5("SUPERVISOR_VIEW_PAYMENT");
    public static final String ACTION_SUPERVISOR_EDIT_PAYMENT = CommonStorage.md5("SUPERVISOR_EDIT_PAYMENT");
    public static final String ACTION_SUPERVISOR_UPDATE_PAYMENT = CommonStorage.md5("SUPERVISOR_UPDATE_PAYMENT");
    public static final String ACTION_SUPERVISOR_DELETE_PAYMENT = CommonStorage.md5("SUPERVISOR_DELETE_PAYMENT");
    public static final String ACTION_SUPERVISOR_POST_PAYMENT = CommonStorage.md5("SUPERVISOR_POST_PAYMENT");
    public static final String ACTION_SUPERVISOR_CANCEL_PAYMENT = CommonStorage.md5("SUPERVISOR_CANCEL_PAYMENT");

    public static final String ACTION_SUPERVISOR_CLAIMS = CommonStorage.md5("SUPERVISOR_CLAIMS");
     */
    public static final String ACTION_MANAGER_DASHBOARD = CommonStorage.md5("MANAGER_DASHBOARD");
    public static final String ACTION_MANAGER_REPORT = CommonStorage.md5("MANAGER_REPORT");
    public static final String ACTION_MANAGER_GENERATE_REPORT = CommonStorage.md5("MANAGER_GENERATE_REPORT");
    public static final String ACTION_MANAGER_DOWNLOAD_REPORT = CommonStorage.md5("MANAGER_DOWNLOAD_REPORT");

    // Indices
    public static final int INDEX_ALL_NOT_FOUND = 0;
    public static final int INDEX_ALL_WELCOME = 1;
    public static final int INDEX_ALL_VIEW_PROFILE = 2;
    public static final int INDEX_ALL_EDIT_PROFILE = 3;
    public static final int INDEX_ALL_VIEW_NOTIFICATIONS = 4;

    public static final int INDEX_ADMINISTRATOR_DASHBOARD = 10;
    public static final int INDEX_ADMINISTRATOR_USERS_LIST = 20;
    public static final int INDEX_ADMINISTRATOR_USERS_CARD = 21;
    public static final int INDEX_ADMINISTRATOR_ADD_USER = 22;
    public static final int INDEX_ADMINISTRATOR_VIEW_USER = 24;
    public static final int INDEX_ADMINISTRATOR_EDIT_USER = 23;

    public static final int INDEX_ADMINISTRATOR_LOG = 30;
    public static final int INDEX_ADMINISTRATOR_LOOKUP = 40;
    public static final int INDEX_ADMINISTRATOR_SETTINGS = 50;
    public static final int INDEX_ADMINISTRATOR_SYSTEM = 60;

    public static final int INDEX_DATAENTRY_BRANCHES_LIST = 100;
    public static final int INDEX_DATAENTRY_BRANCHES_CARD = 101;
    public static final int INDEX_DATAENTRY_ADD_BRANCH = 102;
    public static final int INDEX_DATAENTRY_VIEW_BRANCH = 103;
    public static final int INDEX_DATAENTRY_EDIT_BRANCH = 104;

    public static final int INDEX_DATAENTRY_CLIENTS_LIST = 110;
    public static final int INDEX_DATAENTRY_CLIENTS_CARD = 111;
    public static final int INDEX_DATAENTRY_ADD_CLIENT = 112;
    public static final int INDEX_DATAENTRY_VIEW_CLIENT = 113;
    public static final int INDEX_DATAENTRY_EDIT_CLIENT = 114;

    public static final int INDEX_DATAENTRY_CLIENT_REGIONS_LIST = 120;
    public static final int INDEX_DATAENTRY_CLIENT_REGIONS_CARD = 121;
    public static final int INDEX_DATAENTRY_ADD_CLIENT_REGION = 122;
    public static final int INDEX_DATAENTRY_VIEW_CLIENT_REGION = 123;
    public static final int INDEX_DATAENTRY_EDIT_CLIENT_REGION = 124;

    public static final int INDEX_DATAENTRY_CLIENT_RATES_LIST = 130;
    public static final int INDEX_DATAENTRY_ADD_CLIENT_RATE = 131;
    public static final int INDEX_DATAENTRY_VIEW_CLIENT_RATE = 132;

    public static final int INDEX_DATAENTRY_PROJECTS_LIST = 140;
    public static final int INDEX_DATAENTRY_PROJECTS_CARD = 141;
    public static final int INDEX_DATAENTRY_ADD_PROJECT = 142;
    public static final int INDEX_DATAENTRY_VIEW_PROJECT = 143;
    public static final int INDEX_DATAENTRY_EDIT_PROJECT = 144;

    public static final int INDEX_DATAENTRY_DOCUMENTS_LIST = 150;
    public static final int INDEX_DATAENTRY_DOCUMENTS_CARD = 151;
    public static final int INDEX_DATAENTRY_ADD_DOCUMENT = 152;
    public static final int INDEX_DATAENTRY_VIEW_DOCUMENT = 153;
    public static final int INDEX_DATAENTRY_EDIT_DOCUMENT = 154;
    public static final int INDEX_DATAENTRY_DOCUMENTS = 155;

    public static final int INDEX_DATAENTRY_PAYMENTS_LIST = 160;
    public static final int INDEX_DATAENTRY_PAYMENTS_CARD = 161;
    public static final int INDEX_DATAENTRY_ADD_PAYMENT = 162;
    public static final int INDEX_DATAENTRY_VIEW_PAYMENT = 163;
    public static final int INDEX_DATAENTRY_EDIT_PAYMENT = 164;

    public static final int INDEX_DATAENTRY_CLAIMS = 170;
    public static final int INDEX_DATAENTRY_CLAIMS_LIST = 171;
    public static final int INDEX_DATAENTRY_CLAIMS_CARD = 172;
    public static final int INDEX_DATAENTRY_ADD_CLAIM = 173;
    public static final int INDEX_DATAENTRY_VIEW_CLAIM = 174;
    public static final int INDEX_DATAENTRY_EDIT_CLAIM = 175;

    public static final int INDEX_DATAENTRY_CLAIM_DETAILS = 180;
    public static final int INDEX_DATAENTRY_ADD_CLAIM_DETAIL = 181;
    public static final int INDEX_DATAENTRY_VIEW_CLAIM_DETAIL = 182;
    public static final int INDEX_DATAENTRY_EDIT_CLAIM_DETAIL = 183;

    public static final int INDEX_DATAENTRY_AMENDMENTS = 190;
    public static final int INDEX_DATAENTRY_ADD_AMENDMENT = 191;
    public static final int INDEX_DATAENTRY_VIEW_AMENDMENT = 192;
    public static final int INDEX_DATAENTRY_EDIT_AMENDMENT = 193;

    public static final int INDEX_DATAENTRY_ADD_IMPORT = 200;

    /*public static final int INDEX_SUPERVISOR_BRANCHES_LIST = 210;
    public static final int INDEX_SUPERVISOR_BRANCHES_CARD = 211;
    public static final int INDEX_SUPERVISOR_ADD_BRANCH = 212;
    public static final int INDEX_SUPERVISOR_VIEW_BRANCH = 213;
    public static final int INDEX_SUPERVISOR_EDIT_BRANCH = 214;

    public static final int INDEX_SUPERVISOR_CLIENTS_LIST = 220;
    public static final int INDEX_SUPERVISOR_CLIENTS_CARD = 221;
    public static final int INDEX_SUPERVISOR_ADD_CLIENT = 222;
    public static final int INDEX_SUPERVISOR_VIEW_CLIENT = 223;
    public static final int INDEX_SUPERVISOR_EDIT_CLIENT = 224;
     */
    public static final int INDEX_MANAGER_DASHBOARD = 301;
    public static final int INDEX_MANAGER_REPORT = 302;

    // Status
    public static final String STATUS_DELETED = "deleted";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_INACTIVE = "inactive";

    public static final String STATUS_FULLY_PAID = "paid";
    public static final String STATUS_PARTIALLY_PAID = "partially_paid";
    //  Numbers
    public static final int SHORT_TEXT_LENGTH = 10;

    // User roles
    public static final String USER_ROLES_ADMINISTRATOR = "administrator";
    public static final String USER_ROLES_DATA_ENTRY = "dataentry";
    public static final String USER_ROLES_SUPERVISOR = "supervisor";
    public static final String USER_ROLES_MANAGER = "manager";

    public static final String REPORT_TIMEBOUND = "timebound";
    public static final String REPORT_BRANCH = "branch";
    public static final String REPORT_PROJECT = "project";
    public static final String REPORT_CLIENT = "client";

}
