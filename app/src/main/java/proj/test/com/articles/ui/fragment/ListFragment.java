package proj.test.com.articles.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import proj.test.com.articles.R;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.presenter.BaseListContainerPresenter;
import proj.test.com.articles.ui.activity.DetailActivity;
import proj.test.com.articles.ui.adapter.ArticleAdapter;
import proj.test.com.articles.view.ListContainerView;


public class ListFragment extends Fragment implements ListContainerView {

    private BaseListContainerPresenter presenter;

    private RecyclerView recyclerView;
    private TextView errorView;
    private ProgressBar progressBar;
    private Spinner spinner;

    private static int positionSelection = 0;
    private boolean isContentShown = false;

    private ArticleAdapter adapter;


    public static ListFragment newInstance() {
        return new ListFragment();
    }


    private String getSection() {
        if (spinner == null) {
            spinner = (Spinner) getActivity().findViewById(R.id.section);
        }
        return spinner.getSelectedItem().toString();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        errorView.setText(message);
    }

    @Override
    public void hideMessage() {
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showList(List<Article> list) {
        errorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setArticles(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyList() {
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(getString(R.string.message_empty_view));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        errorView = (TextView) view.findViewById(R.id.error);
        recyclerView = (RecyclerView) view.findViewById(R.id.articles);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArticleAdapter(Collections.<Article>emptyList());
        adapter.setListener(new ArticleAdapter.OnArticleSelectedListener() {
            @Override
            public void onSelected(Article article) {
                presenter.selectedArticle(article);
            }
        });
        recyclerView.setAdapter(adapter);
        if (presenter != null && !isContentShown) {
            presenter.showContent(getSection());
            setSectionView();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
           presenter.showContent(getSection());}


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.detachView();
    }

    @Override
    public void showDetailArticle(Article article) {
        Intent intent = DetailActivity.newIntent(getContext(), article);
        startActivity(intent);
    }


    private void setSectionView() {
        if (spinner == null) {
            spinner = (Spinner) getActivity().findViewById(R.id.section);
        }
        if (getUserVisibleHint()) {
            spinner.setOnItemSelectedListener(null);
            spinner.setSelection(positionSelection, false);
            spinner.setOnItemSelectedListener(listener);
        }
    }


    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (presenter != null ) {
                positionSelection = position;
                presenter.onChooseSection(spinner.getItemAtPosition(position).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void setPresenter(BaseListContainerPresenter presenter) {
        this.presenter = presenter;
        this.presenter.attachView(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null) {
                setSectionView();
                presenter.onFragmentVisibleUser(getSection());
                isContentShown = true;
            } else {
                isContentShown = false;
            }
        }
    }
}
