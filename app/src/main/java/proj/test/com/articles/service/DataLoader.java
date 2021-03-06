package proj.test.com.articles.service;


import java.util.List;

import proj.test.com.articles.model.Article;

public interface DataLoader {

    void getArticles(String section, final OnDataListener listener);

    interface OnDataListener {
        void onSuccess(List<Article> result);
        void onFail(String message);
    }
}

