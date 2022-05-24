package depromeet.ohgzoo.iam.search;

import lombok.Getter;

@Getter
public class TagRank {
    private String tag;
    private int frequency;

    public TagRank(String tag, int frequency) {
        this.tag = tag;
        this.frequency = frequency;
    }

    public void increase() {
        this.frequency++;
    }
}
