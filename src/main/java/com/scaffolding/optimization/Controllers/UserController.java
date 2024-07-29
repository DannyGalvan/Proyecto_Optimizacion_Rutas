package com.scaffolding.optimization.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scaffolding.optimization.Entities.Response.ResponseApi;
import com.scaffolding.optimization.Entities.Response.UserResponse;
import com.scaffolding.optimization.Services.UserService;
import com.scaffolding.optimization.Swagger.Shemas.AuthenticationFailed;
import com.scaffolding.optimization.Swagger.Shemas.ListUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "User", description = "API de usuarios")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Obtiene los usuarios", description = "Este endpoint devuelve los usuarios disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListUser.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationFailed.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationFailed.class))),
    })
    public ResponseEntity<?> getChatBotResponse() {
        List<UserResponse> userData = userService.findAll();

        ResponseApi<List<UserResponse>> apiResponse = new ResponseApi<>(true, "User Response",
                userData);

        return ResponseEntity.ok(apiResponse);
    }
}
