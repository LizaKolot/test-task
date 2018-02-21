package proj.test.com.articles.view;

import android.content.Context;

import proj.test.com.articles.presenter.BasePresenter;


public interface BaseView<T extends BasePresenter<? extends BaseView>> {
    void setPresenter(T presenter);
    Context getContext();
   // String getContentTag();
}
