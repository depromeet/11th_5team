package depromeet.ohgzoo.iam.category;

import lombok.Getter;

@Getter
public enum FirstCategory {

    IRRITATION(0, "짜증나요", "image"), DISAPPOINTMENT(1, "실망했어요", "image"),
    SADNESS(2, "슬퍼요", "image"), LETHARGY(3, "무기력해요", "image"),
    ANXIOUS(4, "불안해요", "image"), REGRET(5, "후회해요", "image"),
    DONTKNOW(6, "모르겠어요", "image"),

    // 기존에 존재하던 값인데 다 수정하면 일단 테스트가 깨져서 통일해야 될 것 같아요.
    NO1(-1, "NO1", "image"),
    NO2(-1, "NO2", "iamge"),
    NO3(-1, "NO3", "image"),
    UPSET(-1, "화났어요", "image"),
    ANGRY(-1, "화났어요", "image"),
    DEFAULT(-1, "기본값", "image");

    private Integer categoryId;
    private String name;
    private String image;

    FirstCategory(Integer categoryId, String name, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }
}