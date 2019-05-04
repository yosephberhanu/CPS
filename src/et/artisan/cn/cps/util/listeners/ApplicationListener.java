package et.artisan.cn.cps.util.listeners;

import et.artisan.cn.cps.dao.PostgresMasterRepository;
import et.artisan.cn.cps.util.CommonStorage;
import javax.servlet.*;


/**
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 */
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ///TODO: Read configration from jndi
        ///TODO: Setup the Common Storage components
        /// ??? Fetch the Database Connection from context and save it to CommonStorage
        CommonStorage.readSettings();

        switch (CommonStorage.getDatabaseType().toLowerCase()) {
            case "postgresql":
                CommonStorage.setRepository(new PostgresMasterRepository());
                break;
            /*
            case "mysql":
                CommonStorage.setRepository(new MySQLMasterRepository());
                break;
            case "oracle":
                CommonStorage.setRepository(new OracleMasterRepository());
                break;
             */
        }

        //System.err.println("Method Not Fully Implemented:" + ApplicationListener.class.getCanonicalName() + " - contextInitialized()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ///TODO:?????: Close the database Connection in CommonStorage
    }

}
