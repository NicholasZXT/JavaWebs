package pers.zxt.springboot.ssm.controller;

import java.util.List;
import java.util.Arrays;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 专门用于展示 Springdoc 集成 Swagger 的 Controller
 */
@RestController(value = "Swagger Controller")
@RequestMapping(value = "/swagger")
public class SwaggerController {

    @Operation(
            summary = "Return Clients",
            description = "Some descriptions...",
            tags = {"client"}
    )
    @GetMapping(value = "/list")
    public List<String> listClients() {
        return Arrays.asList("First Client", "Second Client");
    }
}
