package com.ecommerce.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class RouteValidator {

    private static final Logger log = LoggerFactory.getLogger(RouteValidator.class);

    // List of open API endpoints (public routes)
    public static final List<String> openApiEndpoints = List.of(
            "/user/sign-up",
            "/user/login",
            "/eureka"
    );

    // Patterns for user endpoints that require specific matching
    private final List<Pattern> userEndpoints = List.of(
            Pattern.compile("POST:/user/addAddress/\\d+"),// e.g., POST:/user/addAddress/123
            Pattern.compile("GET:/user/getAddress/\\d+") // e.g., GET:/user/getAddress/123
    );

    /**
     * Predicate to determine if a route is secured.
     * Returns true if the route is NOT in the openApiEndpoints list.
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    /**
     * Checks if the given method, uri, and email combination matches a user endpoint.
     * @param method HTTP method (GET, POST, etc.)
     * @param uri URI path of the request
     * @param email Email extracted from the token
     * @return true if the endpoint is a user-related endpoint and email is present.
     */
    public boolean isUserEndpoint(String method, String uri, String email) {
        // Construct the key as combination of method and URI
        String key = method + ":" + uri;

        // If the email is not null or empty, allow access
        if (email != null && !email.isEmpty()) {
            log.info("Email provided: " + email + " - Allowing access.");
            return true; // Email exists, allow access
        }

        // Match against user-specific endpoints if no email
        boolean isMatch = userEndpoints
                .stream()
                .anyMatch(pattern -> pattern.matcher(key).matches());

        // If matching user endpoint, return true
        if (isMatch) {
            log.info("User endpoint detected for method: " + method + " and URI: " + uri);
        }

        return isMatch;
    }
}
