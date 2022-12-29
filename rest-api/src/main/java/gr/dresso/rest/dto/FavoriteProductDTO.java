package gr.dresso.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FavoriteProductDTO {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String ProductId;
}
