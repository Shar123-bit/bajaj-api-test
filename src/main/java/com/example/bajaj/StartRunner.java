package com.example.bajaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class StartRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

            Map<String, String> request = new HashMap<>();
            request.put("name", "John Doe");
            request.put("regNo", "REG12347"); // change this
            request.put("email", "john@example.com");

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            Map<String, Object> body = response.getBody();

            String webhook = (String) body.get("webhook");
            String token = (String) body.get("accessToken");

            String finalQuery = "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, d.DEPARTMENT_NAME FROM PAYMENTS p JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID WHERE DAY(p.PAYMENT_TIME) != 1 ORDER BY p.AMOUNT DESC LIMIT 1;";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> answer = new HashMap<>();
            answer.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> entity =
                    new HttpEntity<>(answer, headers);

            restTemplate.postForEntity(webhook, entity, String.class);

            System.out.println("SUBMITTED SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}