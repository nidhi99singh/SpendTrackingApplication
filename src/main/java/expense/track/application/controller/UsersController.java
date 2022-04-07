package expense.track.application.controller;

import expense.track.application.entity.Users;
import expense.track.application.enums.ApiResponseCode;
import expense.track.application.exception.ValidationException;
import expense.track.application.request.JwtRequest;
import expense.track.application.request.UserUpdateRequest;
import expense.track.application.request.UsersRequest;
import expense.track.application.response.generics.ResponseDTO;
import expense.track.application.response.utils.ResponseUtil;
import expense.track.application.service.UsersService;
import expense.track.application.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    public static final String[] Admin = {"Admin"};
    @Autowired
    private UsersService usersService;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private WalletService walletService;

    @PostMapping("/createuser")
    public String newUser(@RequestBody UsersRequest usersRequest) throws expense.track.application.exception.ValidationException {
        return usersService.newUser(usersRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getusers")
    public List<Users> allUsers() {
        return usersService.getUsers();
    }

    //login
    @PostMapping("/loginAndGenerateToken")
    public ResponseDTO<?> userLogIn(@RequestBody JwtRequest jwtRequest) throws Exception {
        //  return usersService.userLogIn(usersRequest);

        return responseUtil.ok(usersService.generateToken(jwtRequest), ApiResponseCode.SUCCESS);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public String updateUser(UserUpdateRequest userUpdateRequest) {
        usersService.updateUser(userUpdateRequest);
        return "User updated successfully";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/userwallet/{userId}")
    public ResponseDTO<?> getUserWallet(@PathVariable String userId) throws ValidationException {
        return responseUtil.ok(usersService.getUserWallet(userId), ApiResponseCode.SUCCESS);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("purchaseHistory/{userId}")
    public ResponseDTO<?> getPurchaseHistory(@PathVariable String userId) throws ValidationException {
        return responseUtil.ok(usersService.getPurchaseHistory(userId), ApiResponseCode.SUCCESS);
    }

}

