package depromeet.ohgzoo.iam.category;

import lombok.Getter;

import static depromeet.ohgzoo.iam.category.ImageLoader.TEMP_IMAGE;

@Getter
public enum SecondCategory {

    JOY(CategoryType.POSITIVE,1, "기뻐요", TEMP_IMAGE),
    PROUD(CategoryType.POSITIVE, 2, "뿌듯해요", TEMP_IMAGE),
    RELIEF(CategoryType.POSITIVE, 3, "안도돼요", TEMP_IMAGE),
    EASYGOING(CategoryType.POSITIVE, 4, "홀가분해요", TEMP_IMAGE),
    CALMDOWN(CategoryType.POSITIVE, 5, "차분해요", TEMP_IMAGE),

    LETHARGY(CategoryType.NEGATIVE, 6, "무기력해요", TEMP_IMAGE),
    DISAPPOINTMENT(CategoryType.NEGATIVE, 7, "실망했어요", TEMP_IMAGE),
    SADNESS(CategoryType.NEGATIVE, 8, "슬퍼요", TEMP_IMAGE),
    REGRET(CategoryType.NEGATIVE, 9, "후회해요", TEMP_IMAGE),
    IRRITATION(CategoryType.NEGATIVE,10, "짜증나요", TEMP_IMAGE),
    ANXIOUS(CategoryType.NEGATIVE, 11, "불안해요", TEMP_IMAGE),
    DONTKNOW(CategoryType.NATURAL, 12, "모르겠어요", TEMP_IMAGE),
    Unwritten(CategoryType.NATURAL, 13, "Unwritten", TEMP_IMAGE);




    private String type;
    private Integer categoryId;
    private String name;
    private String image;

    SecondCategory(String type, Integer categoryId, String name, String image) {
        this.type = type;
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }
}