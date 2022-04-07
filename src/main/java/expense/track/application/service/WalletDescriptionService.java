package expense.track.application.service;

import expense.track.application.entity.WalletDescription;
import expense.track.application.exception.ValidationException;
import expense.track.application.repository.WalletDescriptionRepository;
import expense.track.application.request.WalletDescriptionRequest;
import expense.track.application.request.WalletDescriptionUpdateRequest;
import expense.track.application.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WalletDescriptionService {

    @Autowired
    WalletDescriptionRepository repository;

    public String updateWallet(WalletDescriptionUpdateRequest request) throws ValidationException {
        WalletDescription walletDescription = repository.findById(request.getWalletDescriptionId()).orElse(null);
        if(Objects.isNull(walletDescription)){
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(),"wallet description does not exist");
        }
        else{
            walletDescription.setMinimumBalance(request.getMinimumBalance());
            walletDescription.setCreditAmount(request.getCreditAmount());
            repository.save(walletDescription);
            return "wallet updated successfully";
        }

    }

    public String saveWalletDescription(WalletDescriptionRequest request) {
        WalletDescription wallet = new WalletDescription();
        wallet.setWalletDescriptionId(CommonUtils.generateUUID());
        wallet.setCreditAmount(request.getCreditAmount());
        wallet.setType(request.getType());
        wallet.setMinimumBalance(request.getMinimumBalance());
        repository.save(wallet);
        return "wallet Saved";
    }
}
