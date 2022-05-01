package com.vaadin.example.oauth.ui;


import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.core.env.Environment;

/**
 * Adds a link that the user has to click to login.
 *
 * This view is marked with {@code @AnonymousAllowed} to allow all users access
 * to the login page.
 */
@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    /**
     * URL that Spring uses to connect to Google services
     */
    private static final String OAUTH_URL = "/oauth2/authorize-client/twitch";

    public LoginView(@Autowired Environment env) {
        setPadding(true);
        setAlignItems(Alignment.CENTER);

        String clientkey = env.getProperty("spring.security.oauth2.client.registration.twitch.client-id");

        // Check that oauth keys are present
        if (clientkey == null || clientkey.isEmpty()) {
            Paragraph text = new Paragraph("Could not find OAuth client key in application.properties. "
                    + "Please double-check the key and refer to the README.md file for instructions.");
            text.getStyle().set("padding-top", "100px");
            add(text);
        } else {
            Anchor loginLink = new Anchor(OAUTH_URL, "Login with TWITCH");
            // Set router-ignore attribute so that Vaadin router doesn't handle the login request
            loginLink.getElement().setAttribute("router-ignore", true);
            loginLink.getStyle().set("margin-top", "100px");
            add(loginLink);
        }
    }
}
