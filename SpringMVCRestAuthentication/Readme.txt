That is an example from http://websystique.com/spring-security/secure-spring-rest-api-using-basic-authentication/

1) What is Basic Authentication?

Traditional authentication approaches like login pages or session identification are good for web based clients involving human interaction 
but does not really fit well when communicating with [REST] clients which may not even be a web application. 
Think of an API over a server which tries to communicate with another API on a totally different server, without any human intervention.

Basic Authentication provides a solution for this problem, although not very secure. 
With Basic Authentication, clients send it�s Base64 encoded credentials with each request, 
using HTTP [Authorization] header . 
That means each request is independent of other request and server may/does not maintain any state information for the client,
which is good for scalability point of view.

A Word on HTTPS : For any sort of Security implementation, ranging from Basic authentication to a full fledged OAuth2 implementation, 
HTTPS is a must have. Without HTTPS, no matter what your implementation is, security is vulnerable to be compromised. 

The following code is to enable login form and rest api

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                        .anyRequest().hasAnyRole("ADMIN", "API")
                        .and()
                    .httpBasic();
        }
    }

    @Configuration
    @Order(2)
    public static class FormWebSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable() //HTTP with Disable CSRF
                    .authorizeRequests() //Authorize Request Configuration
                        .antMatchers("/connect/**").permitAll()
                        .antMatchers("/", "/register").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .and() //Login Form configuration for all others
                    .formLogin()
                        .loginPage("/login").permitAll()
                        .and() //Logout Form configuration
                    .logout().permitAll();
        }
    }
}

or
@EnableWebSecurity
    public class MultiHttpSecurityCustomConfig {
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("user").password("password").roles("USER").and().withUser("admin").password("password")
                    .roles("USER", "ADMIN");
        }

        @Configuration
        @Order(1)
        public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
            protected void configure(HttpSecurity http) throws Exception {
                http.antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic();
            }
        }

        @Configuration
        public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests().anyRequest().authenticated().and().formLogin();
            }


   }
}