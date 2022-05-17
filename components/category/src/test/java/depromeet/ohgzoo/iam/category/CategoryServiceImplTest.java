package depromeet.ohgzoo.iam.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryServiceImplTest {

    private CategoryService sut;

    @BeforeEach
    void setUp() {
        sut = new CategoryServiceImpl();
    }

    @Test
    public void categoryList_returnsCategoryResponse() throws Exception {
        var result = sut.categoryList();

        assertThat(result.getPositive()).hasSize(5);
        assertThat(result.getPositive().get(0).getName()).isEqualTo("기뻐요");
        assertThat(result.getPositive().get(1).getName()).isEqualTo("뿌듯해요");
        assertThat(result.getPositive().get(2).getName()).isEqualTo("안도돼요");
        assertThat(result.getPositive().get(3).getName()).isEqualTo("홀가분해요");
        assertThat(result.getPositive().get(4).getName()).isEqualTo("차분해요");

        assertThat(result.getNegative()).hasSize(6);
        assertThat(result.getNegative().get(0).getName()).isEqualTo("무기력해요");
        assertThat(result.getNegative().get(1).getName()).isEqualTo("실망했어요");
        assertThat(result.getNegative().get(2).getName()).isEqualTo("슬퍼요");
        assertThat(result.getNegative().get(3).getName()).isEqualTo("후회해요");
        assertThat(result.getNegative().get(4).getName()).isEqualTo("짜증나요");
        assertThat(result.getNegative().get(5).getName()).isEqualTo("불안해요");

        assertThat(result.getNatural()).hasSize(1);
        assertThat(result.getNatural().get(0).getName()).isEqualTo("모르겠어요");
    }

    @Test
    public void firstCategoryList_returnsCategoryResponse() throws Exception {
        var result = sut.firstCategoryList();

        assertThat(result).hasSize(7);
        assertThat(result.get(0).getName()).isEqualTo("무기력해요");
        assertThat(result.get(1).getName()).isEqualTo("실망했어요");
        assertThat(result.get(2).getName()).isEqualTo("슬퍼요");
        assertThat(result.get(3).getName()).isEqualTo("후회해요");
        assertThat(result.get(4).getName()).isEqualTo("짜증나요");
        assertThat(result.get(5).getName()).isEqualTo("불안해요");
        assertThat(result.get(6).getName()).isEqualTo("모르겠어요");
    }

    @Test
    public void secondCategoryList_returnsCategoryResponse() throws Exception {
        var result = sut.secondCategoryList();

        assertThat(result).hasSize(12);
        assertThat(result.get(0).getName()).isEqualTo("기뻐요");
        assertThat(result.get(1).getName()).isEqualTo("뿌듯해요");
        assertThat(result.get(2).getName()).isEqualTo("안도돼요");
        assertThat(result.get(3).getName()).isEqualTo("홀가분해요");
        assertThat(result.get(4).getName()).isEqualTo("차분해요");
        assertThat(result.get(5).getName()).isEqualTo("무기력해요");
        assertThat(result.get(6).getName()).isEqualTo("실망했어요");
        assertThat(result.get(7).getName()).isEqualTo("슬퍼요");
        assertThat(result.get(8).getName()).isEqualTo("후회해요");
        assertThat(result.get(9).getName()).isEqualTo("짜증나요");
        assertThat(result.get(10).getName()).isEqualTo("불안해요");
        assertThat(result.get(11).getName()).isEqualTo("모르겠어요");
    }
}