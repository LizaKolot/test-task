package proj.test.com.articles.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import proj.test.com.articles.R;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.presenter.PresenterManager;
import proj.test.com.articles.ui.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {
    private static final String NAME_FRAGMENT = "detail";
    private static String ARG_ARTICLE = "article";
    private DetailFragment fragment;


    public static Intent newIntent(Context context, Article article) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ARG_ARTICLE, article);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setFragment();
    }

    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = (DetailFragment) fm.findFragmentByTag(NAME_FRAGMENT);

        if (fragment == null) {
            Article article = getIntent().getExtras().getParcelable(ARG_ARTICLE);
            fragment = DetailFragment.newInstance();
            PresenterManager.getInstance().attachPresenter(fragment, PresenterManager.TypePresenter.DETAIL_PRESENTER, article);
            fm.beginTransaction().replace(R.id.fragment, fragment, NAME_FRAGMENT).commit();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = fragment.onKeyDown(keyCode, event);
        if (result) return true;
        else return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        PresenterManager.getInstance().deletePresenter(PresenterManager.TypePresenter.DETAIL_PRESENTER);
    }
}
