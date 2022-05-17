package depromeet.ohgzoo.iam.category;

import lombok.Getter;

@Getter
public enum FirstCategory {
    LETHARGY(SecondCategory.LETHARGY),
    DISAPPOINTMENT(SecondCategory.DISAPPOINTMENT),
    SADNESS(SecondCategory.SADNESS),
    REGRET(SecondCategory.REGRET),
    IRRITATION(SecondCategory.IRRITATION),
    ANXIOUS(SecondCategory.ANXIOUS),
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