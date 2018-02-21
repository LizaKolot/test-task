package proj.test.com.articles.presenter;


import android.util.Log;

import java.util.List;

import proj.test.com.articles.model.Article;
import proj.test.com.articles.service.DataLoader;
import proj.test.com.articles.view.ListContainerView;

import static android.R.attr.type;


public class BaseListContainerPresenter extends BasePresenter<ListContainerView> {
    private DataLoader dataLoader;
    private String section;

    private boolean isProgress;
    private List<Article> list;
    private String error;


    public BaseListContainerPresenter(ListContainerView view, DataLoader dataLoader) {
        super(view);
        this.dataLoader = dataLoader;
    }

    public void chooseArticle(Article article) {
        if (getView() != null) {
            getView().showDetailArticle(article);
        }
    }

    public void showContent(String section) {
        this.section = section;
        getContent();
    }

    public void getContent()
    {
        if (list == null && !isProgress) {
            loadData();
            return;
        }
        updateView();
    }

    private void updateView() {
        if (getView() == null) {
            return;
        }


        if (isProgress) {
            getView().showProgress();
            return;
        } else {
            getView().hideProgress();
        }
        if (list != null && list.size() > 0) {
            getView().showList(list);
        } else {
            getView().showEmptyList();
        }
        if (error != null) {
            getView().showMessage(error);
        }
    }



    private void setProgressState() {
        isProgress = true;
        error = null;
        list = null;
        updateView();
    }

    private void loadData() {
        setProgressState();
        Log.e("my test", " load data from data manager type = " + type + "    name presenter = ");
        dataLoader.getArticles(section, new DataLoader.OnDataListener() {
            @Override
            public void onSuccess(List<Article> result) {
                isProgress = false;
                list = result;
                error = null;
                updateView();
            }

            @Override
            public void onFail(String message) {
                isProgress = false;
                error = message;
                list = null;
                updateView();
            }
        });
    }

    public void resetData() {
        isProgress = false;
        list = null;
        error = null;
    }

    public void onChooseSection(String section) {
        resetData();
        showContent(section);
    }

    public void onFragmentVisibleUser(String newSection) {
        if (section != null && !section.equals(newSection)) {
            Log.e("my test", " resetData " + getView());
            resetData();
        }
        Log.e("my test", " show content in fragment visibility ");
        showContent(newSection);
    }

}
