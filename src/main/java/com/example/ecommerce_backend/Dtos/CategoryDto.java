package com.example.ecommerce_backend.Dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data //toString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotEmpty(message = "Cant not be empty")
    private String name;

}
