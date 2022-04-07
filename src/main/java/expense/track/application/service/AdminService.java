package expense.track.application.service;

import expense.track.application.entity.Users;
import expense.track.application.exception.ValidationException;
import expense.track.application.repository.UsersRepository;
import expense.track.application.repository.WalletDescriptionRepository;
import expense.track.application.repository.WalletRepository;
import expense.track.application.request.AdminRequest;
import expense.track.application.request.DeductBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WalletDescriptionRepository walletDescriptionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public String updateUserStatus(AdminRequest adminRequest) {
        Users user = usersRepository.findByUserId(adminRequest.getUserId()).orElse(null);
        user.setUserStatus(adminRequest.getUserStatus());
        usersRepository.save(user);
        return "User Status Updated";
    }

    public List<Users> activeUsersList() {
        return usersRepository.getUsersList("Active");
    }

    public List<Users> disabledUsersList() {
        return usersRepository.getUsersList("Disabled");
    }


    public String checkMinimumBalance(DeductBalanceRequest deductBalanceRequest) throws ValidationException {
        Integer minimumBalance = walletDescriptionRepository.getMinimumBalance(deductBalanceRequest.getWalletDescriptionId());
        Integer existingBalance = walletRepository.getWalletBalance(deductBalanceRequest.getUserId());
        Integer newBalance = existingBalance - 500;

        if (newBalance <= 0)
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Insufficient balance, can not deduct charges");
        if (existingBalance < minimumBalance && existingBalance > 0) {
            walletRepository.updateWalletBalance((existingBalance - 500), deductBalanceRequest.getUserId());
            return "charges deducted";
        } else
            return "Sufficient Balance";
    }
}
