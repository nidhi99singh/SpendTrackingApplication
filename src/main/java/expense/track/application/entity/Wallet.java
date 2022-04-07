package expense.track.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(length = 50)
    private String walletId;

    private Integer walletBalance;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Users users;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "walletDescriptionId")
    private WalletDescription walletDescription;


    public Users getUsers(Users users) {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Wallet(Users users) {
        this.users = users;
    }

    public Wallet() {

    }

    public Users getUsers() {
        return users;
    }

    public WalletDescription getWalletDescription(WalletDescription walletDescription) {
        return this.walletDescription;
    }

    public void setWalletDescription(WalletDescription walletDescription) {
        this.walletDescription = walletDescription;
    }

    public Integer getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Integer walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
}
