package dev.peertosir.difftoolui.service;

import dev.peertosir.difftoolui.model.Diff;
import dev.peertosir.difftoolui.model.response.TeamCityResponseBuildCreated;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TeamCityResponseBuildCreated createTeamCityDiffBuild(Diff diff) {
        String url = "http://localhost:8000/create-ts-build";


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        Map<String, Object> map = new HashMap<>();
        map.put("service", diff.getService());
        map.put("host1", diff.getHost1());
        map.put("host2", diff.getHost2());
        map.put("excludeRegExps", diff.getExcludeRegExps());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<TeamCityResponseBuildCreated> response = this.restTemplate.postForEntity(url, entity, TeamCityResponseBuildCreated.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
