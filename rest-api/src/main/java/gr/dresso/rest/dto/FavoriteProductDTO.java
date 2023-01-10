package gr.dresso.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteProductDTO {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String ProductId;
}
