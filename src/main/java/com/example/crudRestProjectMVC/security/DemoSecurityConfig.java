package com.example.crudRestProjectMVC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // add support for jdbc .. no more hardcoded users, roles , and passwords
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // define query to retreive user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?");

        // define query to retreive authprities/rols by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                          .requestMatchers(HttpMethod.GET, "/").hasRole("EMPLOYEE")
                          .requestMatchers(HttpMethod.GET, "/employees/list").hasRole("EMPLOYEE")
                          .requestMatchers(HttpMethod.GET, "/employees/showFormForAdd").hasRole("MANAGER")
                          .requestMatchers(HttpMethod.GET, "/employees/showFormForUpdate/**").hasRole("MANAGER")
                          .requestMatchers(HttpMethod.GET, "/employees/delete/**").hasRole("ADMIN")
                          .anyRequest().authenticated()
        )
        .formLogin(form ->
                form
                        .loginPage("/showMyLoginPage")   // spring security will redirect any starting request(ie any request to access any route) to this route of "/showMyLoginPage", thus we must have a controller code that will handle getMapping for this route and it willo render/return the suitable html login view/page. So we create a login controller(just for purpose of handling requests made related to login and handle this route there.
                        // Note that we dont need to create a seperate controller ie LoginController, w can write the code for handling any route in any controller, but just for the sake of seperation of concerns, we create a seperate LoginController, especially for handling login related routes.
                        .loginProcessingUrl("/authenticateTheUser")   // we do not need to implement this route in login controller, its implementation is given by spring security itself.
                        .permitAll()
        )

        .logout(logout -> logout.permitAll()
        )

        .exceptionHandling(configurer->
                configurer.accessDeniedPage("/access-denied")    // whenever authrorization based on roles fails, spring security will redirect us to this route of "access-denied", hence our loginController should handle this route and render the suitable access denied page/view.
        );

        /*

        //USE HTTP basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable cross site request forgery(csrf)
        http.csrf(csrf -> csrf.disable());


         */

        return http.build();   // returns an instance of SecurityFilterChain
    }


    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();
        UserDetails mary= User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();
        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(john, mary, susan);
    }
     */


}

