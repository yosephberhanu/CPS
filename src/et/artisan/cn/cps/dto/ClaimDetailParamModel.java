package et.artisan.cn.cps.dto;

/**
 *
 * @author Yoseph Berhanu <yoseph@artisan.et>
 * @version 1.0
 * @since 1.0
 *
 */
public class ClaimDetailParamModel extends JQueryDataTableParamModel {

	private long claimId;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}
	
}
