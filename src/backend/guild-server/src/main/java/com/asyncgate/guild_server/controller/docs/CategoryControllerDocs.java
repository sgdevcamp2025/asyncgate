package com.asyncgate.guild_server.controller.docs;

import com.asyncgate.guild_server.dto.request.CategoryRequest;
import com.asyncgate.guild_server.dto.response.CategoryResponse;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Category API", description = "길드 카테고리 관련 API")
@SecurityRequirement(name = "JWT TOKEN")
public interface CategoryControllerDocs {

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다.")
    @PostMapping
    SuccessResponse<CategoryResponse> create(
            @AuthenticationPrincipal String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "카테고리 생성 요청 데이터",
                    required = true
            )
            @RequestBody CategoryRequest request
    );

    @Operation(summary = "카테고리 삭제", description = "길드 내 특정 카테고리를 삭제합니다.")
    @DeleteMapping("/{guildId}/{categoryId}")
    SuccessResponse<String> delete(
            @AuthenticationPrincipal String userId, // JWT 내부에서 추출, Swagger 문서에서 숨김

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId,

            @Parameter(description = "카테고리 ID", required = true, example = "category-67890")
            @PathVariable String categoryId
    );
}
