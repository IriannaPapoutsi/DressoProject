package gr.dresso.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CartDTO {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String productId;
}
