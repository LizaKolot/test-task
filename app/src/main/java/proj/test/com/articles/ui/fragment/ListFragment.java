package proj.test.com.articles.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import proj.test.com.articles.presenter.BaseListContainerPresenter;
import proj.test.com.articles.view.ListContainerView;
import proj.test.com.articles.presenter.PresenterManager;
import proj.test.com.articles.R;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.ui.activity.DetailActivity;
import proj.test.com.articles.ui.adapter.ArticleAdapter;

import static android.R.attr.type;

public class ListFragment extends Fragment implements ListContainerView {

    private BaseListContainerPresenter presenter;

    private RecyclerView recyclerView;
    private TextView errorView;
    private ProgressBar progressBar;
    private Spinner spinner;

    private ArticleAdapter adapter;


    public static ListFragment newInstance(String type) {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    public void setPresenter(BaseListContainerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterManager.getInstance().getPresenter(type, this);
    }

    private String getSection() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.section);
        Log.e("my test", spinner.getSelectedItem().toString());
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
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setArticles(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyList() {
        recyclerView.setVisibility(View.GONE);
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
        adapter = new ArticleAdapter(getContext(), Collections.<Article>emptyList());
        adapter.setListener(new ArticleAdapter.OnArticleChooseListener() {
            @Override
            public void onChoose(Article article) {
                presenter.chooseArticle(article);
            }
        });
        recyclerView.setAdapter(adapter);
        presenter.showContent(type, getSection());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showDetailArticle(Article article) {
        Intent intent = DetailActivity.newIntent(getContext(), article);
        startActivity(intent);
    }

    @Override
    public void showFilterSection(String section) {
        spinner = (Spinner) getActivity().findViewById(R.id.section);
        spinner.setOnItemSelectedListener(listener);
        spinner.setVisibility(View.VISIBLE);
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            presenter.onChooseSection(spinner.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void hideFilterSection() {
        spinner.setVisibility(View.GONE);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            presenter.onFragmentVisibleUser();
        }
    }
}
