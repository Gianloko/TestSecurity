

package com.gian.conf;

import com.gian.filter.PreAuthenticaticatedSWFilter;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> userDetailsServiceImpl;

    //Security Controller Mapping: nello standalone.conf.bat ho settato una
    //variabile d'ambiente in Windows("GianSecurity = dev") quindi ho tutti gli accessi.
    //Se l'application server non contenesse questa variabile,
    //l'accesso per l'utente sarebbe limitato solo agli URL
    //definiti nel blocco ELSE.
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String env = System.getenv("GianSecurity");
        if ("dev".equals(env)) {
            http.authorizeUrls().anyRequest().permitAll();
        } else {
            http
                    .authorizeUrls()
                    .antMatchers(
                            "/",
                            "/pages/**",
                            "security/login",
                            "security/logout"
                    ).permitAll()                                               
                    .antMatchers("/api/**").hasRole("WRITE")                    
                    .anyRequest().authenticated();                              
        }                                                                       
        http.sessionManagement()
                .maximumSessions(1)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.stateless);

        PreAuthenticaticatedSWFilter filter = new PreAuthenticaticatedSWFilter();
        filter.setAuthenticationManager(authenticationManager());
        http.addFilter(filter);
        http.logout().invalidateHttpSession(true);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(userDetailsServiceImpl);
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder();
        builder.authenticationProvider(provider);
        AuthenticationManager authenticationManager = builder.build();
        return authenticationManager;
    }

    @Bean(autowire = Autowire.BY_NAME, name = "authenticationManager")
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManager();
    }
    
}




