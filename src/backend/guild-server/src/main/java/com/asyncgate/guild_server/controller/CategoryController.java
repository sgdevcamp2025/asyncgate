package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.CategoryRequest;
import com.asyncgate.guild_server.dto.response.CategoryResponse;
import com.asyncgate.guild_server.service.CategoryService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public SuccessResponse<CategoryResponse> create(
            final @AuthenticationPrincipal String userId,
            final @RequestBody CategoryRequest request
    ) {
        CategoryResponse response = categoryService.create(userId, request);
        return SuccessResponse.created(response);
    }

    @DeleteMapping("/{guildId}/{categoryId}")
    public SuccessResponse<String> delete(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId,
            final @PathVariable String categoryId
    ) {
        categoryService.delete(userId, guildId, categoryId);
        return SuccessResponse.ok(String.format("Guild Id[%s] Category Id[%s] 삭제 완료되었습니다.", guildId, categoryId));
    }
}
