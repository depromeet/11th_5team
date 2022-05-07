package depromeet.ohgzoo.iam.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
public class CategoryResponse {

    private ArrayList<Category> positive = new ArrayList<>();
    private ArrayList<Category> negative = new ArrayList<>();

    @AllArgsConstructor
    static class Category {
        private Integer categoryId;
        private String cardgoryName;
        private String name;
        private String image;
    }

}