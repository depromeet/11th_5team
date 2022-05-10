package depromeet.ohgzoo.iam.category;

import lombok.Getter;

import static depromeet.ohgzoo.iam.category.ImageLoader.TEMP_IMAGE;

@Getter
public enum FirstCategory {

    IRRITATION(CategoryType.NEGATIVE,1, "짜증나요", TEMP_IMAGE),
    DISAPPOINTMENT(CategoryType.NEGATIVE, 2, "실망했어요", TEMP_IMAGE),
    SADNESS(CategoryType.NEGATIVE, 3, "슬퍼요", TEMP_IMAGE),
    LETHARGY(CategoryType.NEGATIVE, 4, "무기력해요", TEMP_IMAGE),
    ANXIOUS(CategoryType.NEGATIVE, 5, "불안해요", TEMP_IMAGE),
    REGRET(CategoryType.NEGATIVE, 6, "후회해요", TEMP_IMAGE),
    DONTKNOW(CategoryType.NATURAL, 7, "모르겠어요", TEMP_IMAGE);

    private String type;
    private Integer categoryId;
    private String name;
    private String image;

    FirstCategory(String type, Integer categoryId, String name, String image) {
        this.type = type;
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }
}