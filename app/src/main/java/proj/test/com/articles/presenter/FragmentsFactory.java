package proj.test.com.articles.presenter;


import android.support.v4.app.Fragment;

import proj.test.com.articles.model.Article;
import proj.test.com.articles.ui.fragment.DetailFragment;
import proj.test.com.articles.ui.fragment.ListFragment;

public class FragmentsFactory {


    public static Fragment createFragment(PresenterManager.TypePresenter typePresenter) {
        ListFragment fragment = ListFragment.newInstance(typePresenter);
        PresenterManager.getInstance().attachPresenter(fragment, typePresenter);
        return fragment;

    }

    public static Fragment createDetailFragment(PresenterManager.TypePresenter typePresenter, Article article) {
        DetailFragment fragment = DetailFragment.newInstance();
        PresenterManager.getInstance().attachPresenter(fragment, typePresenter, article);
        return fragment;
    }

}

