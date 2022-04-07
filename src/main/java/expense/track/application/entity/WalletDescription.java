package expense.track.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "wallet_description")
public class WalletDescription {

    @Id
    @Column(length = 50)
    private String walletDescriptionId;
    private String type;
    private Integer creditAmount;
    private Integer minimumBalance;

    @JsonIgnore
    @OneToMany(mappedBy = "walletDescription", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Wallet> wallet;

    public String getWalletDescriptionId() {
        return walletDescriptionId;
    }

    public void setWalletDescriptionId(String walletDescriptionId) {
        this.walletDescriptionId = walletDescriptionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Set<Wallet> getWallet() {
        return wallet;
    }

    public void setWallet(Set<Wallet> wallet) {
        this.wallet = wallet;
    }
}
