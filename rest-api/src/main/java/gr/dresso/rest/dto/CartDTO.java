package gr.dresso.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String productId;
}
