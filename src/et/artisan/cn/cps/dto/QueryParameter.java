package et.artisan.cn.cps.dto;

import java.util.ArrayList;

/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public interface QueryParameter {
  
    public void addStatus(String status);

    public String getFilter();

    public String getSort();

    public String getPagination();
}
