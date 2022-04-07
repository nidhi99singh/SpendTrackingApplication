package expense.track.application.service;

import expense.track.application.entity.Users;
import expense.track.application.entity.Wallet;
import expense.track.application.repository.WalletRepository;
import expense.track.application.request.UpdateWalletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    public List<Wallet> getWalletDetails() {
        return walletRepository.findAll();
    }

    public Wallet getWalletByEmailId(String id) {
        return walletRepository.getUserWallet(id);
    }

    public String updateWallet(UpdateWalletRequest updateWalletRequest) {
        walletRepository.updateWalletBalance(updateWalletRequest.getWalletBalance(), updateWalletRequest.getUserid());
        return "wallet updated successfully";
    }
}
