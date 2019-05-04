
package et.artisan.cn.cps.entity;

import java.util.ArrayList;


/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public interface Entity {
    public ArrayList<String> getValidationMessage();
    public boolean valideForSave();
    public boolean valideForUpdate();
    //public String getFilter();

}