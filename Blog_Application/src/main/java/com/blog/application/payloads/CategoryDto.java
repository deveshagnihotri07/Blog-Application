package com.blog.application.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;

    @NotBlank
    private String categoryName;

    @NotBlank
    @Size(max = 200, message = "About can have at max 200 chars")
    private String categoryDescription;
}
