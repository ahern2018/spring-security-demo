package com.spring.security.app;

import com.spring.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.spring.security.core.properties.SecurityConstants;
import com.spring.security.core.properties.SecurityProperties;
import com.spring.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author sunxiaojun
 * @version 1.0
 * @className ImoocResourceServerConfig
 * @description TODO
 * @date 2018/10/18 19:23
 **/
@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    protected AuthenticationSuccessHandler springSecurityAuthenctiationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler springSecurityAuthenctiationFailureHandler;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer springSecuritySocialSecurityConfig;



    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().fullyAuthenticated();

        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(springSecurityAuthenctiationSuccessHandler)
                .failureHandler(springSecurityAuthenctiationFailureHandler);

        http//.apply(validateCodeSecurityConfig)
            //    .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(springSecuritySocialSecurityConfig)
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBorowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                        securityProperties.getBorowser().getSignUpUrl(),
                        "/user/regist", "/session/invalid","/oauth/authorize")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
