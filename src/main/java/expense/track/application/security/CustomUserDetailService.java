package expense.track.application.security;

import expense.track.application.CustomUsersDetail;
import expense.track.application.entity.Users;
import expense.track.application.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findUserByEmailId(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in database");
        }
        CustomUsersDetail customUsersDetail=new CustomUsersDetail(user);
//        return customUsersDetail;
return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), getAuthority(user));
    }
    private Set getAuthority(Users user){
        Set authorities=new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ user.getUserRole()));
        return authorities;

    }

}
