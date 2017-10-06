package com.auth0.example.controllers;

import com.auth0.example.AccessTokenResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class PermissionsController {


    private static final String CLIENT_CREDENTIALS_GRANT = "client_credentials";
    private String clientId = "ZajjWfD1JnDUmE4qWeFjnlr1JbdNc5Lv";
    private String clientSecret = "C0itRM5Ulj4F-W-u5Mdxw2OFKoBkw-hEVdxkYhWsKtGf_NSBdE80qppV77aFy8y-";

    @GetMapping(value = "/permissions")
    public String token() throws UnirestException {
        String token =  authorizationAccessToken();

        HttpResponse<String> response = Unirest.get("https://ryanwalker.us.webtask.io/adf6e2f2b84784b57522e3b19dfc9201/api/permissions")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .asString();
//                .asObject(Permissions.class);
        return response.getBody();
    }

    private String authorizationAccessToken() throws UnirestException {
        String audience = "urn:auth0-authz-api";

        JSONObject body = new JSONObject();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", audience);
        body.put("grant_type", CLIENT_CREDENTIALS_GRANT);

        HttpResponse<AccessTokenResponse> response = Unirest.post("https://ryanwalker.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body(body.toString())
                .asObject(AccessTokenResponse.class);

        return response.getBody().getAccessToken();
    }


}
