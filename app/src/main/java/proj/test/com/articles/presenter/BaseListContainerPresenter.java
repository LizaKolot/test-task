package proj.test.com.articles.presenter;


import android.util.Log;

import java.util.List;

import proj.test.com.articles.view.ListContainerView;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.service.DataLoader;
import proj.test.com.articles.storage.database.SettingsStorage;

import static android.R.attr.type;


public class BaseListContainerPresenter extends BasePresenter <ListContainerView>{


    private DataLoader dataLoader;
    private String section;

    private boolean isProgress;
    private List<Article> list;
    private String error;

    private SettingsStorage settingsStorage;

    public BaseListContainerPresenter(String name, ListContainerView view) {
        super(name, view);
        settingsStorage = new SettingsStorage();

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

    private void getContent() // как именовать
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
        Log.e("my test", " load data from data manager type = " + type + "    name presenter = " + getName());
        dataLoader.getArticles(section, new DataLoader.OnDataListener() {
            @Override
            public void onSuccess(List<Article> result) {
                isProgress = false;
                list = result;
                updateView();
            }

            @Override
            public void onFail(String message) {
                isProgress = false;
                BaseListContainerPresenter.this.error = message;
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
        settingsStorage.saveSection(section);
    }

    public void onFragmentVisibleUser() {
        String newSection = settingsStorage.getSection();
        getView().showFilterSection(newSection);
        if(!section.equals(newSection)){
            resetData();
            section = newSection;
            loadData();

        }
    }

}
