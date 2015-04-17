package uk.gov.homeoffice.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserDetailsService userRepo;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String username = request.getHeader("X-Username");
                try {
                    if (username != null) {
                        UserDetails user = userRepo.loadUserByUsername(username);
                        user.getPassword();
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("carrier"))));
                        return true;
                    }
                } catch (Exception e) {
                    // cannot find user
                }
                response.setStatus(401);
                throw new UsernameNotFoundException("not logged in");
            }
        } );
    }
}
