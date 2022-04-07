package expense.track.application.controller;

import expense.track.application.entity.Users;
import expense.track.application.exception.ValidationException;
import expense.track.application.request.AdminRequest;
import expense.track.application.request.DeductBalanceRequest;
import expense.track.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adminControl")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateStatus/")
    public String updateUserStatus(@RequestBody AdminRequest adminRequest) {
        adminService.updateUserStatus(adminRequest);
        return "Status Updated";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getActiveUsersList")
    public List<Users> activeUsersList() {
        return adminService.activeUsersList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getDisabledUserList")
    public List<Users> disabledUsersList() {
        return adminService.disabledUsersList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/checkMinBalance/")
    public String checkMinimumBalance(@RequestBody DeductBalanceRequest deductBalanceRequest) throws ValidationException {
        return adminService.checkMinimumBalance(deductBalanceRequest);

    }
}
