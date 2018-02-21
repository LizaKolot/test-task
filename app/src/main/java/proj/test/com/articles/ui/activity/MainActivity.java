package proj.test.com.articles.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import proj.test.com.articles.R;
import proj.test.com.articles.presenter.PresenterManager;
import proj.test.com.articles.ui.fragment.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
    }


   private class ViewPagerAdapter extends FragmentPagerAdapter {
         ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return createFragment();
        }

        private Fragment createFragment() {
            return ListFragment.newInstance();

        }

        private PresenterManager.TypePresenter getType(int position) {
            switch (position) {
                case 0:
                    return PresenterManager.TypePresenter.MOSTSHARED_PRESENTER;
                case 1:
                    return PresenterManager.TypePresenter.MOSTEMAILED_PRESENTER;
                case 2:
                    return PresenterManager.TypePresenter.MOSTVIEWED_PRESENTER;
                case 3:
                    return PresenterManager.TypePresenter.FAVORITES_PRESENTER;
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ListFragment fragment = (ListFragment) super.instantiateItem(container, position);
            PresenterManager.getInstance().attachPresenter(fragment, getType(position));
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_name_shares);
                case 1:
                    return getString(R.string.tab_name_emails);
                case 2:
                    return getString(R.string.tab_name_views);
                case 3:
                    return getString(R.string.tab_name_favorites);
            }
            return null;
        }
    }
}

