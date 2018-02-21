package proj.test.com.articles.view;


import java.util.List;

import proj.test.com.articles.model.Article;
import proj.test.com.articles.presenter.BaseListContainerPresenter;

public interface ListContainerView extends BaseView<BaseListContainerPresenter> {
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void hideMessage();
    void showList(List<Article> list);
    void showEmptyList();
    void showDetailArticle(Article article);
}
