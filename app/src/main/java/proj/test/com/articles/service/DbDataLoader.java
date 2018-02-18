package proj.test.com.articles.service;

import java.util.List;

import proj.test.com.articles.model.Article;
import proj.test.com.articles.storage.database.DataBaseAdapter;


public class DbDataLoader extends DataLoader {
    private final DataBaseAdapter dbAdapter;

    public DbDataLoader(DataBaseAdapter dbAdapter) {
        this.dbAdapter = dbAdapter;
    }

    @Override
    public void getArticles(String section, OnDataListener listener) {
        List<Article> articles = dbAdapter.getAllArticles();
        listener.onSuccess(articles);
    }
}
