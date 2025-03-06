package com.ecommerce.api_gateway.filter;

import com.ecommerce.api_gateway.exception.InvalidRequestException;
import com.ecommerce.api_gateway.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;  // Correct import
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest(); // Use the correct reactive ServerHttpRequest
            ServerHttpRequest loggedInUser = null;

            // If the route is secured, check for the Authorization header
            if (routeValidator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new InvalidRequestException("Authorization header is missing");
                }

                String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7); // Remove "Bearer " from the token
                }

                try {
                    // Validate the token
                    jwtUtil.validateToken(authHeader);
                    log.info("Token is valid");

                    // Extract method and uri
                    String method = request.getMethod().toString();
                    String uri = request.getURI().getPath();
                    log.info("method={}, uri={}", method, uri);

                    // Check if email exists in token and allow access
                    String email = jwtUtil.extractEmail(authHeader);
                    if (email != null) {
                        log.info("User with email {} is authorized", email);
                    }

                    loggedInUser = exchange.getRequest().mutate()
                            .header("loggedInUser", email)
                            .build();

                } catch (Exception e) {
                    log.error("Token is invalid");
                    throw new InvalidRequestException("Token is invalid");
                }
            }
            return chain.filter(exchange.mutate().request(loggedInUser).build());
        };
    }

    public static class Config {
    }
}




//package com.ecommerce.api_gateway.filter;
//
//import com.ecommerce.api_gateway.exception.InvalidRequestException;
//import com.ecommerce.api_gateway.util.JwtUtil;
//import com.netflix.spectator.impl.Config;
//import org.apache.http.HttpHeaders;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config > {
//
//    public static final Logger log= LoggerFactory.getLogger(AuthenticationFilter.class);
//
//    @Autowired
//    private RouteValidator routeValidator;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
//    public GatewayFilter apply(AuthenticationFilter.Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpRequest loggedInUser=null;
//
//            if (routeValidator.isSecured.test(request)){
//                if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
//                    throw new InvalidRequestException("Authorization header is missing");
//                }
//                String authHeader=request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//                if(authHeader!=null&&authHeader.startsWith("Bearer ")){
//                    authHeader=authHeader.substring(7);
//                }
//                try {
//                    jwtUtil.validateToken(authHeader);
//                    log.info("Token is valid");
//
//                    //extract method and uri
//                    String method=request.getMethod().toString();
//                    String uri=request.getURI().getPath();
//                    log.info("method={},uri={}",method,uri);
//
//                    loggedInUser=exchange.getRequest().mutate()
//                            .header("loggedInUser",jwtUtil.extractUsername(authHeader))
//                            .build();
//                } catch (Exception e) {
//                    log.error("Token is invalid");
//                    throw new InvalidRequestException("Token is invalid");
//                }
//            }
//            return chain.filter(exchange.mutate().request(loggedInUser).build());
//        };
//    }
//    public static class Config {
//    }
//}
