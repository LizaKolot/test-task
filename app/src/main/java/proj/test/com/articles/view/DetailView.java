package proj.test.com.articles.view;


import proj.test.com.articles.presenter.DetailPresenter;

public interface DetailView extends BaseView<DetailPresenter> {
    void showArticle(String url);
    void showError(DetailPresenter.ErrorPresenter error);
    void hideError();
    void showFavoriteButton(boolean exist);
    void requestPermissionWriteFile();
    void saveWebArchive(String pathDest);
}
