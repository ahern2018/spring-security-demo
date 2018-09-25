package com.spring.security.borowser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 处理用户校验的逻辑
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名：" + username);
        /**
         * 根据用户名查找用户信息
         */

        /**
         * 根据查找到的用户判断用户是否被冻结
         */
        String password = passwordEncoder.encode("123456");
        logger.info("用户的密码是：" + password);
        return new User(username, password,
                true,
                true,
                true,
                true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}