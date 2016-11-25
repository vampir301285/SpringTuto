package com.minhduc.tuto.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * String plainClientCredentials="myusername:mypassword";
 * 
 * String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
 * 
 * HttpHeaders headers = getHeaders(); headers.add("Authorization", "Basic " + base64ClientCredentials);
 * 
 * @author UE1PHOT
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // The equivalent of the Spring Security XML file :
    //    <http auto-config="true">
    //    <intercept-url pattern="/user/**" access="ROLE_ADMIN" />
    //    <http-basic />
    //    <!-- enable csrf protection -->
    //    <csrf/>
    //    </http>
    //
    //    <authentication-manager>
    //    <authentication-provider>
    //      <user-service>
    //      <user name="bill" password="abc123" authorities="ROLE_ADMIN" />
    //      <user name="tom" password="abc123" authorities="ROLE_USER" />
    //      </user-service>
    //    </authentication-provider>
    //    </authentication-manager>

    private static String REALM = "MY_TEST_REALM";

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config
     * .annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN"); // Authorization:Basic YmlsbDphYmMxMjM=
        auth.inMemoryAuthentication().withUser("tom").password("abc123").roles("USER");// Authorization:Basic dG9tOmFiYzEyMw==
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .antMatchers("/user/**").hasRole("ADMIN")
        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint());
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
        return new CustomBasicAuthenticationEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
