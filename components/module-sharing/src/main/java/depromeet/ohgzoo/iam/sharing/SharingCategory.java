package depromeet.ohgzoo.iam.sharing;

import depromeet.ohgzoo.iam.category.CategoryType;
import lombok.Getter;

import static depromeet.ohgzoo.iam.category.ImageLoader.TEMP_IMAGE;

@Getter
public enum SharingCategory {
    UNSELECT(1, "선택안함", TEMP_IMAGE),
    SORRY(2, "미안해요", TEMP_IMAGE),
    THANKS(3, "고마워요", TEMP_IMAGE),
    UNDERSTAND(4, "이해해요", TEMP_IMAGE),
    RECONCILED(5, "화해해요", TEMP_IMAGE),
    SAD(6, "서운해요", TEMP_IMAGE),
    TIRED(7, "지쳤어요", TEMP_IMAGE);

    private Integer categoryId;
    private String name;
    private String image;

    SharingCategory(Integer categoryId, String name, String image) {
        this.categoryId = categoryId;
        this.name = name;
        this.image = image;
    }
}
