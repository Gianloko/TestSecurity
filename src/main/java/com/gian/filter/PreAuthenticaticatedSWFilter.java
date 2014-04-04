package com.gian.filter;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 *
 * @author Francesco Arciello
 */
public class PreAuthenticaticatedSWFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static Logger _log = Logger.getLogger(PreAuthenticaticatedSWFilter.class);

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
//        setContinueFilterChainOnUnsuccessfulAuthentication(Boolean.FALSE);

        String username = request.getHeader("USERNAME");
        String totpPassword = request.getHeader("TOTP_PASSWORD");
        return ((username != null) && (totpPassword != null)) 
                && 
               (!username.isEmpty() && !totpPassword.isEmpty());
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String[] credentials = new String[5];
        credentials[0] = request.getHeader("USERNAME");
        credentials[1] = request.getHeader("TOTP_PASSWORD");
        _log.debug("credentials:" + Arrays.toString(credentials));
        return credentials;
    }
}
