package depromeet.ohgzoo.iam.category;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryResponse {

    private List<Category> positive = new ArrayList<>();
    private List<Category> negative = new ArrayList<>();
    private List<Category> natural = new ArrayList<>();

    public CategoryResponse(List<Category> positive, List<Category> negative, List<Category> natural) {
        this.positive.addAll(positive);
        this.negative.addAll(negative);
        this.natural.addAll(natural);
    }
}