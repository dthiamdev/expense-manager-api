package com.daouda.expensetracker.config;

import com.daouda.expensetracker.security.CustomUserDetailsService;
import com.daouda.expensetracker.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class webConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter(){
        return new JwtRequestFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // first approach to create inMemory multiple users
        /*auth.inMemoryAuthentication()
                .withUser("daouda").password("12345").authorities("admin")
                .and()
                .withUser("mamy").password("12345").authorities("user")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());*/

        //Second approach to create inMemory multiple users
        /*InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("daouda").password("123456").authorities("admin").build();
        UserDetails user2 = User.withUsername("mamy").password("123456").authorities("user").build();

        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);*/

        // Use multiple users from database
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
