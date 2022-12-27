package sia.tacocloud.data.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import java.util.List;

@Data
public class TacoDTO {
    private Long id;
    @NotBlank( message = "You must call your taco")
    @Size(min = 4, message = "Tacos name must be longer" )
    private String name;
    private LocalDate createAt;
    @NotBlank
    @Size (message = " ")
    List<String> ingredientsList;
}
