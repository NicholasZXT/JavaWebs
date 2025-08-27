package pers.zxt.springboot.swagger.controller;

import java.util.List;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;  // 这个和上面的冲突了，只能使用全限定类名
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;


@RestController(value = "SpringDoc Controller Group One")
@RequestMapping(value = "/springdoc/g1")
public class GroupOneController {
    @Operation(
            method = "Get",
            summary = "Return Clients",
            description = "Some descriptions...",
            tags = {"client"}
    )
    @ApiResponse(
            description = "List<String>",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    @GetMapping(value = "/list")
    public List<String> listClients() {
        return Arrays.asList("First Client", "Second Client");
    }

}
