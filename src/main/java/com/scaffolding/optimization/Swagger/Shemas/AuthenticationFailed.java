package com.scaffolding.optimization.Swagger.Shemas;

import com.scaffolding.optimization.database.Entities.Response.ResponseApi;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication Failed or Unauthorized")
public class AuthenticationFailed extends ResponseApi<String> {
    
    public AuthenticationFailed() {
        super(false, "Authentication failed", null);
    }
}
