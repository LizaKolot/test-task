package proj.test.com.articles.view;


import android.content.Context;

import proj.test.com.articles.presenter.DetailPresenter;

public interface DetailView  {
    void setPresenter(DetailPresenter presenter);
    Context getContext();
    void showArticle(String url);
    void showError(DetailPresenter.ErrorPresenter error);
    void hideError();
    void showFavoriteButton(boolean exist);
    void requestPermissionWriteFile();
    void saveWebArchive(String pathDest);
}
