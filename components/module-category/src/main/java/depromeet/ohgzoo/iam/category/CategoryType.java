package depromeet.ohgzoo.iam.category;

public final class CategoryType {

    public static final String NEGATIVE = "negative";
    public static final String POSITIVE = "positive";
    public static final String NATURAL = "natural";

    private CategoryType() {
        throw new AssertionError("생성할 수 없는 클래스입니다.");
    }
}
