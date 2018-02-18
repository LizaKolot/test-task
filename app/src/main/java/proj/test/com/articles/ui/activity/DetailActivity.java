package proj.test.com.articles.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import proj.test.com.articles.R;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.ui.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    public static String ARG_ARTICLE = "article";
    private Fragment fragment;


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
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        Article article = getIntent().getExtras().getParcelable(ARG_ARTICLE);
        fragment = DetailFragment.newInstance(article);
        manager.replace(R.id.fragment, fragment);
        manager.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = false;
        if (fragment instanceof DetailFragment) {
            result = ((DetailFragment) fragment).onKeyDown(keyCode, event);
        }
        if (result) return true;
        else return super.onKeyDown(keyCode, event);
    }
}
