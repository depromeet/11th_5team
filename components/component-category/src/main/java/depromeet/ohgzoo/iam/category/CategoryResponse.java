package depromeet.ohgzoo.iam.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
public class CategoryResponse {

    private ArrayList<Category> positive = new ArrayList<>();
    private ArrayList<Category> negative = new ArrayList<>();
    private ArrayList<Category> natural = new ArrayList<>();
}