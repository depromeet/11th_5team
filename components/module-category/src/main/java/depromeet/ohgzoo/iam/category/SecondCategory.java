package depromeet.ohgzoo.iam.category;

import lombok.Getter;

@Getter
public enum SecondCategory {

    JOY(7, "기뻐요", "image"), PROUD(8, "뿌듯해요", "image"),
    EASYGOING(9, "홀가분해요", "image"), RELIEF(10, "안도가돼요", "image"),
    CALMDOWN(11, "차분해졌어요", "image"), Unwritten(12, "Unwritten", "image"),

    // 기존에 존재하던 값인데 다 수정하면 일단 테스트가 깨져서 통일해야 될 것 같아요.
    NO1(-1, "NO1", "image"),
    Idk(-1, "Idk", "image"),
    UPSET(-1, "화났어요", "image"),
    ANGRY(-1, "화났어요", "image"),
    ANXIOUS(-1, "불안해요", "image");

    private Integer categoryId;
    private String name;
    private String image;

    SecondCategory(Integer categoryId, String name, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }
}