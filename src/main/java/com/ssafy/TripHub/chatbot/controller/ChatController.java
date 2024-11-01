package com.ssafy.TripHub.chatbot.controller;

import com.ssafy.TripHub.chatbot.service.GPTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private GPTService gptService;

    @Operation(summary = "챗봇에게 메시지를 전송", description = "사용자가 전송한 메시지에 대해 GPT-3가 응답을 제공합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @PostMapping("/message")
    public ResponseEntity<String> getChatResponse(
            @RequestBody(description = "사용자가 GPT에게 전송할 메시지", required = true,
                    content = @Content(schema = @Schema(example = "{\"message\": \"여행지 추천해줘\"}")))
            @org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
        String prompt = request.getOrDefault("message", "기본 메시지입니다.");
        String response = gptService.getResponseFromGPT(prompt);
        return ResponseEntity.ok(response);
    }
}
