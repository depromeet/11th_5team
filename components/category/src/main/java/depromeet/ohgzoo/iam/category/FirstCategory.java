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
    private String name;
    private String image;

    FirstCategory(SecondCategory category) {
        this.type = category.getType();
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.image = category.getImage();
    }
}