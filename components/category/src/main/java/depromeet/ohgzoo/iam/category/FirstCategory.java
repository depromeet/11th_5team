package depromeet.ohgzoo.iam.category;

import lombok.Getter;

@Getter
public enum FirstCategory {
    REGRET(SecondCategory.REGRET),
    SADNESS(SecondCategory.SADNESS),
    DISAPPOINTMENT(SecondCategory.DISAPPOINTMENT),
    LETHARGY(SecondCategory.LETHARGY),
    ANXIOUS(SecondCategory.ANXIOUS),
    IRRITATION(SecondCategory.IRRITATION),
    DONTKNOW(SecondCategory.DONTKNOW);

    private CategoryType type;
    private Integer categoryId;
    private String description;
    private String image;

    FirstCategory(SecondCategory category) {
        this.type = category.getType();
        this.categoryId = category.getCategoryId();
        this.description = category.getDescription();
        this.image = category.getImage();
    }

}