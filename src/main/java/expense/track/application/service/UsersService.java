package expense.track.application.service;

import expense.track.application.entity.*;
import expense.track.application.repository.*;
import expense.track.application.request.JwtRequest;
import expense.track.application.request.UserLoginRequest;
import expense.track.application.request.UserUpdateRequest;
import expense.track.application.request.UsersRequest;
import expense.track.application.security.CustomUserDetailService;
import expense.track.application.util.CommonUtils;
import expense.track.application.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import expense.track.application.exception.ValidationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletDescriptionRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    //user Activity in MongoDB
    public String userActivity(String email, String description) {
        UserActivity userActivity = new UserActivity();
        userActivity.setId(CommonUtils.generateUUID());
        userActivity.setEmail(email);
        userActivity.setActivityDescription(description);
        userActivity.setLocalDateTime(LocalDateTime.now());
        userActivityRepository.save(userActivity);
        return "history saved";
    }

    public String newUser(UsersRequest usersRequest) throws ValidationException {
        Users user = new Users();
        user.setUserId(CommonUtils.generateUUID());
        user.setFirstName(usersRequest.getFirstName());
        user.setLastName(usersRequest.getLastName());
        user.setEmailId(usersRequest.getEmail());
        user.setPassword(passwordEncoder.encode(usersRequest.getPassword()));
        user.setPhoneNumber(usersRequest.getPhoneNumber());
        user.setUserStatus("Active");

        //set wallet
        Wallet wallet = new Wallet();
        wallet.setWalletId(CommonUtils.generateUUID());
        wallet.setWalletBalance(repository.getCredits(usersRequest.getUserType()));
        wallet.setWalletDescription(repository.getWalletDescription(usersRequest.getUserType()));
        wallet.setUsers(user);

        //set role
        List<Users> users = usersRepository.findAll();
        if (users.isEmpty()) {
            user.setUserRole("ADMIN");
        } else
            user.setUserRole("USER");
        if (Objects.isNull(usersRepository.findUserByEmailId(usersRequest.getEmail()))) {
            usersRepository.save(user);
            walletRepository.save(wallet);

            //user Activity in MongoDB
            userActivity(usersRequest.getEmail(), "user login");
            return "new User Added";

        } else
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "User with this email already exist");
    }

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public String generateToken(JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));

        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("bad Credentials");
        }

        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtRequest.getUserName());
        Users user = usersRepository.findUserByEmailId(jwtRequest.getUserName());
        String token = this.jwtUtil.generateToken(userDetails, user);
        userActivity(jwtRequest.getUserName(), "token generated");
        return token;
    }


    public String updateUser(UserUpdateRequest userUpdateRequest) {
        // Users updateUser=usersRepository.findUserByEmailIdAndPassword(userUpdateRequest.getEmail(),userUpdateRequest.getPassword());
        Users updateUser = usersRepository.findUserByEmailId(userUpdateRequest.getEmail());
        System.out.println(updateUser);
        updateUser.setLastName(userUpdateRequest.getLastName());
        updateUser.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        usersRepository.save(updateUser);
        userActivity(userUpdateRequest.getEmail(), "user profile updated");
        return "User Updated successfully";

    }

    public Wallet getUserWallet(String userId) throws ValidationException {
        return walletRepository.getUserWallet(userId);
    }

    public List<Order> getPurchaseHistory(String userId, String email) throws ValidationException {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "you have not purchased any products yet");
        } else {
            userActivity(email, "purchase history checked");
            return orders;
        }
    }

    public String userLogout(String email) {
        userActivity(email, "user Logged out");
        return "logout successfully";
    }
}