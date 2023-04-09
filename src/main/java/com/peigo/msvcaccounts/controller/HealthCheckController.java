package com.peigo.msvcaccounts.controller;

import com.peigo.msvcaccounts.common.route.Route;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = Route.HEALTH)
public class HealthCheckController {

    @GetMapping
    @ResponseBody
    public Single<Map<String, String>> getHealthCheck() {
        return Single.just(Collections.singletonMap("status", "UP"));
    }
}
