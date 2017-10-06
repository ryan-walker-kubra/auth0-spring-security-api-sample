package com.auth0.example.controllers;

import com.auth0.example.AccessTokenResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class PhotosController {


    private static final String CLIENT_CREDENTIALS_GRANT = "client_credentials";
    private String clientId = "ZajjWfD1JnDUmE4qWeFjnlr1JbdNc5Lv";
    private String clientSecret = "C0itRM5Ulj4F-W-u5Mdxw2OFKoBkw-hEVdxkYhWsKtGf_NSBdE80qppV77aFy8y-";

    @RequestMapping(value = "/login")
    public String login() {
        return "You DO NOT need to be authenticated to call /login";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    public String getPhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'read:photos' scope";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.POST)
    public String createPhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'create:photos' scope";
    }

    @PutMapping(value = "/photos")
    public String updatePhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'update:photos' scope";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.DELETE)
    public String deletePhotos() {
        return "All good. You can see this because you are Authenticated with a Token granted the 'delete:photos' scope";
    }

    @RequestMapping(value = "/**")
    public String anyRequest() {
        return "All good. You can see this because you are Authenticated.";
    }

}
