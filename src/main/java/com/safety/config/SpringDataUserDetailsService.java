package com.safety.config;



import com.safety.dao.AdminDao;
import com.safety.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Administrator on 2017/2/21.
 */
@Service(value="SpringDataUserDetailsService")
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminUser au = adminDao.findByLoginName(username);
        if (au == null) {
            throw new UsernameNotFoundException("no user found");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        //GrantedAuthority authority = new GrantedAuthority(au.getRoleName());

       // authorities.add(authority);

        User webUserDetails = new User(au.getLoginName(),au.getPassword(),au.getEnable(),true,true,true,authorities);

        return webUserDetails;
    }
}
