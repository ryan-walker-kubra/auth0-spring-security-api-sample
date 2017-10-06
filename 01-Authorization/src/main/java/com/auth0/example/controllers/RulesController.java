package com.auth0.example.controllers;

import com.auth0.example.AccessTokenResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class RulesController {

    private static final String CLIENT_CREDENTIALS_GRANT = "client_credentials";
    private static final String BASE_URL = "https://ryanwalker.auth0.com";

    private String clientId = "YKwl1K2t23Fq59uqa4CUAPSXoRN7C0aR";
    private String clientSecret = "djveptNrvj4kVdE3769FtlDt7zKiNxfLzqvr4JruK6kk_yHWMC19tri8oM9WCBI-";
    private String audience = "https://ryanwalker.auth0.com/api/v2/";

    @GetMapping(value = "/rules")
    public String getRules() throws UnirestException {
        return getRequest(accessToken(), "/api/v2/rules");
    }

    @PostMapping(value = "/rules")
    public String createRule() throws UnirestException {
        JSONObject ruleBody = new JSONObject();
        ruleBody.put("name", "rule-from-api");
        ruleBody.put("enabled", true);
        ruleBody.put("script", "function (user, context, callback) {\n  callback(null, user, context);\n}");

        return postRequest(accessToken(), "/api/v2/rules", ruleBody);
    }

    @PatchMapping(value = "/rules/{id}")
    public String updateRule(@RequestBody RuleScriptUpdateRequest ruleScriptUpdateRequest, @PathVariable("id") String id) throws UnirestException {
        JSONObject json = new JSONObject();
        json.put("script", ruleScriptUpdateRequest.getScript());
        return patchRequest(accessToken(), "/api/v2/rules/" + id, json);

    }

    private String getRequest(String token, String path) throws UnirestException {
        HttpResponse<String> response = Unirest.get(BASE_URL + path)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .asString();
        return response.getBody();
    }

    private String postRequest(String token, String path, JSONObject body) throws UnirestException {
        HttpResponse<String> response = Unirest.post(BASE_URL + path)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
        return response.getBody();
    }

    private String patchRequest(String token, String path, JSONObject body) throws UnirestException {
        HttpResponse<String> response = Unirest.patch(BASE_URL + path)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
        return response.getBody();
    }


    private String accessToken() throws UnirestException {
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
