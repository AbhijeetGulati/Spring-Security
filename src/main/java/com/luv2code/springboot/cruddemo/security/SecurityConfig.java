package com.luv2code.springboot.cruddemo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    //now we will add user,password and roles

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        //for custom tables
        JdbcUserDetailsManager jdbcUDM=new JdbcUserDetailsManager(dataSource);//here jdbcUDM is our local variable
        //create a query to retrieve users by username
        jdbcUDM.setUsersByUsernameQuery("select user_id,pw,active from members where user_id=?");
        //here user_id,pw,active are the fields of the table members
        //instead of ? we will get the username from the login form
        jdbcUDM.setAuthoritiesByUsernameQuery("select user_id,role from roles where user_id=?");

       return jdbcUDM;
    }

    //this method is for BCrypt encryption
   /* @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    */

     //this method is for hardcoding the users and their roles instead we will use the users and roles we defined in the database
    /*@Bean
    public UserDetailsService userDetailsService(){
        //now we will build our users
        UserDetails abhijeet= User.withUsername("abhijeet")
                .password(passwordEncoder().encode("abhijeet10"))
                .roles("ADMIN")
                .build();

        //one user built
        UserDetails ranbir= User
                .withUsername("ranbir")
                .password(passwordEncoder().encode("ranbir10"))
                .roles("MANAGER")
                .build();

        UserDetails saurabh= User
                .withUsername("saurabh")
                .password(passwordEncoder().encode("saurabh10"))
                .roles("EMPLOYEE")
                .build();
        return new InMemoryUserDetailsManager(abhijeet,ranbir,saurabh);
        //now spring will not need to use username and password defined in application.properties file

    }

     */

    //restrict access to URLs based on roles
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http.authorizeHttpRequests(configure->
                configure
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE,"/api/employees/**").hasRole("ADMIN")
                       // .requestMatchers(HttpMethod.GET,"/hello").hasRole("ADMIN")

    );
        //use HTTP Basic Authentication
        http.httpBasic();

        //disable CSRF(Cross Site Request Forgery)
        //not required for stateless REST APIS that use POST,PUT,DELETE
        //we use csrf with web apps that use web forms
        http.csrf().disable();

        return http.build();
    }

}
