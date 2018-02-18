package proj.test.com.articles.view;


import java.util.List;

import proj.test.com.articles.model.Article;

public interface ListContainerView {
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void hideMessage();
    void showList(List<Article> list);
    void showEmptyList();
    void showDetailArticle(Article article);
    void showFilterSection(String section);
    void hideFilterSection();
}
