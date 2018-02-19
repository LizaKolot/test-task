package proj.test.com.articles.presenter;


import android.os.Environment;
import android.util.Log;

import java.io.File;

import proj.test.com.articles.model.Article;
import proj.test.com.articles.storage.database.DataBaseAdapter;
import proj.test.com.articles.view.DetailView;

public class DetailPresenter extends BasePresenter<DetailView> {


    public enum ErrorPresenter {FILE_WAS_DELETED, IMPOSSIBLE_SAVE}

    private Article article;
    private DataBaseAdapter dbAdapter;
    private boolean existInStorage;

    public DetailPresenter(DetailView view, DataBaseAdapter adapter, Article article) {
        super(view);
        dbAdapter = adapter;
        this.article = article;
        this.existInStorage = dbAdapter.existByTitle(article.getTitle());
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void loadData() {
        String loadUrl = article.getPath();
        if (loadUrl.startsWith("/")) {
            loadUrl = "file://" + loadUrl;
            File file = new File(article.getPath());
            if (!file.exists()) {
                getView().showError(ErrorPresenter.FILE_WAS_DELETED);
            }

        }
        getView().showArticle(loadUrl);
        getView().showFavoriteButton(existInStorage);
    }

    public void clickFavoriteButton() {
        if (existInStorage) {
            deleteArticle();
        } else {
            checkAccess();
        }
        getView().showFavoriteButton(existInStorage);
    }

    public void saveDocument() {
        boolean resultSaving = true;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "articles");
        if (!storageDir.exists()) {
            resultSaving = storageDir.mkdirs();
        }
        String fileName = storageDir.getAbsolutePath() + File.separator + getFileName() + ".mht";
        getView().saveWebArchive(fileName);

        article.setPath(fileName);
        long res = dbAdapter.addArticle(article);
        if (res < 0) {
            resultSaving = false;
        }
        if (resultSaving) {
            existInStorage = true;
        } else {
            getView().showError(ErrorPresenter.IMPOSSIBLE_SAVE);
        }
        Log.e("my tesr", " name =  " + fileName + "  id = " + res);
    }

    private void deleteArticle() {
        dbAdapter.deleteArticle(article);
        existInStorage = false;
        File file = new File(article.getPath());
        file.delete();
    }

    private void checkAccess() {
        getView().requestPermissionWriteFile();
    }

    private String getFileName() {
        return article.getTitle().replace(" ", "_");
    }
}
