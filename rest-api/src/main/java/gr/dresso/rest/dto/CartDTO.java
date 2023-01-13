package gr.dresso.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
// TODO: Remove unused imports
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    // TODO: Newline here
    @NotEmpty
    private String userId;

    @NotEmpty
    private String productId;
    // TODO: Newline here
}
