package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.FirstCategory;

public class CoverImageUrl {
    static final String upsetImage = "https://firebasestorage.googleapis.com/v0/b/cardna-29f5b.appspot.com/o/20220317_172729_412500527401_720x720.png?alt=media";
    static final String angryImage = "https://firebasestorage.googleapis.com/v0/b/cardna-29f5b.appspot.com/o/20220330_073908_966740019233_720x720.jpg?alt=media";
    static final String defaultImage = "https://firebasestorage.googleapis.com/v0/b/cardna-29f5b.appspot.com/o/20220330_073908_966740019233_720x720.jpg?alt=media";

    public static String returnCoverImage(FirstCategory firstCategory) {
        return firstCategory.getImage();
    }
}
