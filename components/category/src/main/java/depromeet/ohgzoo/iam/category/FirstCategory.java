package depromeet.ohgzoo.iam.category;

import lombok.Getter;

@Getter
public enum FirstCategory {

    IRRITATION(CategoryType.NEGATIVE,1, "짜증나요", "image"),
    DISAPPOINTMENT(CategoryType.NEGATIVE, 2, "실망했어요", "image"),
    SADNESS(CategoryType.NEGATIVE, 3, "슬퍼요", "image"),
    LETHARGY(CategoryType.NEGATIVE, 4, "무기력해요", "image"),
    ANXIOUS(CategoryType.NEGATIVE, 5, "불안해요", "image"),
    REGRET(CategoryType.NEGATIVE, 6, "후회해요", "image"),
    DONTKNOW(CategoryType.NATURAL, 7, "모르겠어요", "image"),

    // 기존에 존재하던 값인데 다 수정하면 일단 테스트가 깨져서 통일해야 될 것 같아요.
    NO1(CategoryType.NATURAL, -1, "NO1", "image"),
    NO2(CategoryType.NATURAL, -1, "NO2", "iamge"),
    NO3(CategoryType.NATURAL, -1, "NO3", "image"),
    UPSET(CategoryType.NATURAL, -1, "화났어요", "image"),
    ANGRY(CategoryType.NATURAL, -1, "화났어요", "image"),
    DEFAULT(CategoryType.NATURAL, -1, "기본값", "image");

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