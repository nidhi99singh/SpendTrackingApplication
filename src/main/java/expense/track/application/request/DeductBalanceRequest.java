package expense.track.application.request;

import io.swagger.models.auth.In;

public class DeductBalanceRequest {

    private String walletDescriptionId;
    private String userId;

    public String getWalletDescriptionId() {
        return walletDescriptionId;
    }

    public void setWalletDescriptionId(String walletDescriptionId) {
        this.walletDescriptionId = walletDescriptionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
