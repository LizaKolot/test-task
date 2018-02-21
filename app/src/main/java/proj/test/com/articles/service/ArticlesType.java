package proj.test.com.articles.service;

public enum ArticlesType {
    MOSTEMAILED("mostemailed"),
    MOSTSHARED("mostshared"),
    MOSTVIEWED("mostviewed");


    private final String name;

    ArticlesType(String type) {
        this.name = type;
    }

    public String getName() {
        return name;
    }


}
