package expense.track.application.request;

public class WalletDescriptionUpdateRequest {

    private String walletDescriptionId;
    private Integer creditAmount;
    private Integer minimumBalance;

    public String getWalletDescriptionId() {
        return walletDescriptionId;
    }

    public void setWalletDescriptionId(String walletDescriptionId) {
        this.walletDescriptionId = walletDescriptionId;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Integer minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
}
