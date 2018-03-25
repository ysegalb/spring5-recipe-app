package guru.springframework.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jt on 6/21/17.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCommand {
    private Long id;
    private String description;
}
