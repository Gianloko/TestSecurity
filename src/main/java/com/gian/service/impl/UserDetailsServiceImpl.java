package com.gian.service.impl;

import com.gian.conf.HibernateConfiguration;
import com.gian.dao.UserDao;
import com.gian.dao.impl.GenericDaoImpl;
import com.gian.dao.impl.UserDaoImpl;
import com.gian.entities.User;
import com.gian.utils.TOTPUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements
        AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private static final UserDao userDao;
    private static final Logger _log = Logger.getLogger(UserDetailsServiceImpl.class);
    private static final Integer SESSION_TIME = 15; // 15 min

    static {
        userDao = new UserDaoImpl();
        ((GenericDaoImpl<User, Long>) userDao).setSessionFactory(HibernateConfiguration.getSessionFactory());
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
            throws UsernameNotFoundException {
        UserDetails userDetails = null;
        String[] credentials = (String[]) token.getCredentials();
        User user = getUser(credentials[0]);
        String errorMsg = "";
        if (user != null) {
            Calendar sessionStartTime = user.getSessionStartTime();
            Long startTime = user.getStartTime();
            Calendar serverCurrentTime = Calendar.getInstance();
            long delta = sessionStartTime.getTimeInMillis() - startTime;
            long timeInSeconds = (serverCurrentTime.getTimeInMillis() - delta) / 1000;
            Calendar sessionDeadTime = user.getSessionStartTime();
            sessionDeadTime.add(Calendar.MINUTE, SESSION_TIME);
            if ((Calendar.getInstance()).before(sessionDeadTime)
                    && credentials[1].equals(TOTPUtils.currentTOTP(user.getSeed(), timeInSeconds))
                    ) {
                if ("RW".equalsIgnoreCase(user.getRole())) {
                    userDetails = getWriterUser(user);
                } else if ("RO".equalsIgnoreCase(user.getRole())) {
                    userDetails = getReaderUser(user);
                }
            }
            if (!(Calendar.getInstance()).before(sessionDeadTime)) {
                errorMsg += " - Session expired!";
                _log.info("Session expired!");
            }
            if (!credentials[1].equals(TOTPUtils.currentTOTP(user.getSeed(), timeInSeconds))) {
                errorMsg += " - Invalid TOTP_PASSWORD!";
                _log.info("Invalid TOTP Password!");
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException("Could not load user : "
                    + token.getName() + errorMsg);
        }
        token.setDetails(user);
        return userDetails;
    }

    private UserDetails getWriterUser(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_WRITE"));
        return getSpringUser(user, grantedAuthorities);
    }

    private UserDetails getReaderUser(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_READ"));
        return getSpringUser(user, grantedAuthorities);
    }

    private User getUser(String username) {
        User user = null;
        if (username != null && !username.isEmpty()) {
            user = userDao.getUserByUsername(username);
        }
        return user;
    }

    private org.springframework.security.core.userdetails.User getSpringUser(User user, Collection<GrantedAuthority> grantedAuthorities) {
        org.springframework.security.core.userdetails.User fPUser =
                new org.springframework.security.core.userdetails.User(user.getLogin(), "notused", true, true, true, true, grantedAuthorities);
        return fPUser;
    }
}