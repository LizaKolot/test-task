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

import proj.test.com.articles.model.Article;
import proj.test.com.articles.service.DataLoader;
import proj.test.com.articles.ui.activity.DetailActivity;
import proj.test.com.articles.ui.adapter.ArticleAdapter;
import proj.test.com.articles.R;

public class ContentFragment extends Fragment {

    private static final String ARG_TYPE = "type";


    private String type;
    private String section;


    private RecyclerView recyclerView;
    private TextView errorView;
    private Spinner spinner;
    private ProgressBar progressBar;

    private ArticleAdapter adapter;


    public ContentFragment() {

    }


    public static ContentFragment newInstance(String type) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }
    }

    private void getContent() {
        showProgress();
        section = getSection();
        new DataLoader().getArticles(type, section, new DataLoader.OnDataListener() {
            @Override
            public void onSuccess(List<Article> result) {
                adapter.setArticles(result);
                adapter.notifyDataSetChanged();
                if (result.size() == 0) {
                    errorView.setText(getString(R.string.message_empty_view));
                }
                hideProgress();
            }

            @Override
            public void onFail(String message) {
                errorView.setText(message);
                hideProgress();
            }
        });
    }

    private String getSection() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.section);
        Log.e("my test", spinner.getSelectedItem().toString());
       return spinner.getSelectedItem().toString();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        spinner = (Spinner) getActivity().findViewById(R.id.section);
        spinner.setOnItemSelectedListener(listener);
        errorView = (TextView) view.findViewById(R.id.error);
        recyclerView = (RecyclerView) view.findViewById(R.id.articles);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ArticleAdapter(getContext(), Collections.<Article>emptyList());
        adapter.setListener(new ArticleAdapter.OnArticleChooseListener() {
            @Override
            public void onChoose(Article article) {
                Intent intent = DetailActivity.newIntent(getContext(),article);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        getContent();
        return view;
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            getContent();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && spinner != null) {
            spinner.setOnItemSelectedListener(listener);
            if(!getSection().equals(section)) {
                getContent();
                Log.e("my test", " get content " + getSection() + " was " + section);
            }
        }
    }
}
