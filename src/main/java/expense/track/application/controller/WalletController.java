package expense.track.application.controller;

import expense.track.application.entity.Wallet;
import expense.track.application.request.UpdateWalletRequest;
import expense.track.application.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getwallet")
    public List<Wallet> walletDetails() {
        return walletService.getWalletDetails();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/walletById/{id}")
    public Wallet getWalletById(String userId) {
        return walletService.getWalletByEmailId(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/updatewallet")
    public String updateWallet(UpdateWalletRequest updateWalletRequest) {
        walletService.updateWallet(updateWalletRequest);
        return "update successful";
    }

}
