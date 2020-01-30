package cat.tecnocampus.omega.security;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class BaseSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/createUser").permitAll()
                .antMatchers("/allUsers").hasRole("ADMIN")
                //.antMatchers("/profile/users/*").permitAll()
                .antMatchers("/user").authenticated()
                .antMatchers("/users/{username}").authenticated()

                .antMatchers("/weather").permitAll()
                .antMatchers("/time").permitAll()

                .antMatchers("/exercise/**").authenticated()
                .antMatchers("/challenge/all").permitAll()
                .antMatchers("/challenge/do/**").authenticated()
                .antMatchers("/tutorial/create/**").authenticated()
                .antMatchers("/forum/create/**").authenticated()
                .antMatchers("/**").permitAll()
                .antMatchers("/challenge/**").authenticated()
                .antMatchers("/tutorial/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/users/{username}")

                .and()
                .httpBasic()
                .and()

                .rememberMe()
                .tokenValiditySeconds(241920)

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();

        http
                .csrf().disable()
                .headers()
                .frameOptions().disable();
                /*.and()
                .cors();*/
    }
}
