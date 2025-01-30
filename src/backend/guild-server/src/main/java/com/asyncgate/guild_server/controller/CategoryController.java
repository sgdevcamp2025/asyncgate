package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.CategoryRequest;
import com.asyncgate.guild_server.dto.response.CategoryResponse;
import com.asyncgate.guild_server.service.CategoryService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/category")
@RequiredArgsConstructor
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
}
