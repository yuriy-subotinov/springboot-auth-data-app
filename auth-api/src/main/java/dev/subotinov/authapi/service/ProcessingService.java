package dev.subotinov.authapi.service;

import dev.subotinov.authapi.api.dto.ProcessRequest;
import dev.subotinov.authapi.api.dto.ProcessResponse;
import dev.subotinov.authapi.api.dto.TransformRequest;
import dev.subotinov.authapi.api.dto.TransformResponse;
import dev.subotinov.authapi.domain.ProcessingLog;
import dev.subotinov.authapi.domain.User;
import dev.subotinov.authapi.repo.ProcessingLogRepository;
import dev.subotinov.authapi.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    private final RestClient dataApiClient;
    private final ProcessingLogRepository logs;
    private final UserRepository users;


    public ProcessResponse process(ProcessRequest request, String email)
    {
        User user = users.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TransformResponse response = dataApiClient.post()
                .uri("/api/transform")
                .body(new TransformRequest(request.text()))
                .retrieve()
                .body(TransformResponse.class);

        if (response == null || response.result() == null) {
            throw new IllegalStateException("data-api returned empty result");
        }

        String result = response.result();

        ProcessingLog log = new ProcessingLog();
        log.setUserId(user.getId());
        log.setInputText(request.text());
        log.setOutputText(result);

        logs.save(log);

        return new ProcessResponse(result);
    }
}
