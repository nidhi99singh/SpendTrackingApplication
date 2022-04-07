package expense.track.application.controller;

import expense.track.application.exception.ValidationException;
import expense.track.application.request.WalletDescriptionRequest;
import expense.track.application.request.WalletDescriptionUpdateRequest;
import expense.track.application.service.WalletDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/walletDescription")
public class WalletDescriptionController {

    @Autowired
    private WalletDescriptionService service;

    @PostMapping("/add")
    public String addCredit(@RequestBody WalletDescriptionRequest request) {
        return service.saveWalletDescription(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{walletDescriptionId}")
    public String updateWallet(@RequestBody WalletDescriptionUpdateRequest request) throws ValidationException {
        return service.updateWallet(request);
    }

}
