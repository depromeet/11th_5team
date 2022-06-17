package depromeet.ohgzoo.iam.category;

import lombok.Getter;

import static depromeet.ohgzoo.iam.category.ImageLoader.ANXIOUS_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.CALMDOWN_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.DISAPPOINTMENT_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.DONTKNOW_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.EASYGOING_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.IRRITATION_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.JOY_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.LETHARGY_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.PROUD_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.REGRET_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.RELIEF_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.SADNESS_IMAGE;
import static depromeet.ohgzoo.iam.category.ImageLoader.TEMP_IMAGE;

@Getter
public enum SecondCategory {

    JOY(CategoryType.POSITIVE, 1, "기뻐요", JOY_IMAGE),
    PROUD(CategoryType.POSITIVE, 2, "뿌듯해요", PROUD_IMAGE),
    RELIEF(CategoryType.POSITIVE, 3, "안도돼요", RELIEF_IMAGE),
    EASYGOING(CategoryType.POSITIVE, 4, "홀가분해요", EASYGOING_IMAGE),
    CALMDOWN(CategoryType.POSITIVE, 5, "차분해요", CALMDOWN_IMAGE),

    REGRET(CategoryType.NEGATIVE, 9, "후회해요", REGRET_IMAGE),
    SADNESS(CategoryType.NEGATIVE, 8, "슬퍼요", SADNESS_IMAGE),
    DISAPPOINTMENT(CategoryType.NEGATIVE, 7, "실망했어요", DISAPPOINTMENT_IMAGE),
    LETHARGY(CategoryType.NEGATIVE, 6, "무기력해요", LETHARGY_IMAGE),
    ANXIOUS(CategoryType.NEGATIVE, 11, "불안해요", ANXIOUS_IMAGE),
    IRRITATION(CategoryType.NEGATIVE, 10, "짜증나요", IRRITATION_IMAGE),
    DONTKNOW(CategoryType.NATURAL, 12, "모르겠어요", DONTKNOW_IMAGE),
    Unwritten(CategoryType.NONE, 13, "Unwritten", TEMP_IMAGE);

    private CategoryType type;
    private Integer categoryId;
    private String description;
    private String image;

    SecondCategory(CategoryType type, Integer categoryId, String description, String image) {
        this.type = type;
        this.categoryId = categoryId;
        this.description = description;
        this.image = image;
    }
}