package expense.track.application.service;

import expense.track.application.entity.JwtResponse;
import expense.track.application.entity.Order;
import expense.track.application.entity.Users;
import expense.track.application.entity.Wallet;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import expense.track.application.exception.ValidationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    private AuthenticationManager authenticationManager;

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


//        user.setUserRole(roleRepository.getRole(usersRequest.getRoleName()));
        if (Objects.isNull(usersRepository.findUserByEmailId(usersRequest.getEmail()))) {
            usersRepository.save(user);
            walletRepository.save(wallet);
            return "new User Added";
        } else
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "User with this email already exist");
    }

    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    @PostMapping("/create")
    public String generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
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
        return token;
    }


    public String updateUser(UserUpdateRequest userUpdateRequest) {
        Users updateUser = usersRepository.findUserByEmailIdAndPassword(userUpdateRequest.getEmail(), userUpdateRequest.getPassword());
        updateUser.setLastName(userUpdateRequest.getLastName());
        updateUser.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        usersRepository.save(updateUser);
        return "User Updated successfully";

    }

    public Wallet getUserWallet(String userId) throws ValidationException {
        return walletRepository.getUserWallet(userId);
    }

    public List<Order> getPurchaseHistory(String userId) throws ValidationException {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "you have not purchased any products yet");
        } else {
            return orders;
        }

    }
}