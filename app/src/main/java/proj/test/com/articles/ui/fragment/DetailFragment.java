package proj.test.com.articles.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import proj.test.com.articles.ArticleApplication;
import proj.test.com.articles.R;
import proj.test.com.articles.presenter.DetailPresenter;
import proj.test.com.articles.presenter.DetailPresenter.ErrorPresenter;
import proj.test.com.articles.view.DetailView;


public class DetailFragment extends Fragment implements DetailView {
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private WebView webView;
    private FloatingActionButton fab;
    private DetailPresenter presenter;

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();

        // Bundle args = new Bundle();
        //args.putParcelable(ARG_ARTICLE, article);
        //fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickFavoriteButton();
            }
        });
        webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);//нужно ли
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //нужно ли


        presenter.loadData();
        webView.setWebChromeClient(new WebChromeClient() {
                                       public void onProgressChanged(WebView view, int progress) {
                                           if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                                               progressBar.setVisibility(ProgressBar.VISIBLE);
                                           }

                                           progressBar.setProgress(progress);
                                           if (progress == 100) {
                                               progressBar.setVisibility(ProgressBar.GONE);

                                           }
                                       }


                                   }
        );


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        return view;
    }


    @Override
    public void requestPermissionWriteFile() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                presenter.saveDocument();
            } else {
                requestPermission(getContext());
            }

        }
    }

    @Override
    public void saveWebArchive(String pathDest) {
        webView.saveWebArchive(pathDest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.permission_storage_success),
                            Toast.LENGTH_SHORT).show();
                    presenter.saveDocument();

                } else {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.permission_storage_failure),
                            Toast.LENGTH_SHORT).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }
        }
    }

    private void requestPermission(final Context context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.permission_message)
                    .setPositiveButton(R.string.button_positive_message, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();

        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }


    @Override
    public void setPresenter(DetailPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showArticle(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void showError(ErrorPresenter error) {
        int errorMes = R.string.message_web_error;

        switch (error) {
            case FILE_WAS_DELETED:
                errorMes = R.string.message_web_archive_deleted;
                break;
            case IMPOSSIBLE_SAVE:
                errorMes = R.string.message_web_impossible_save;
                break;
        }
        Toast.makeText(getContext(),
                getResources().getString(errorMes),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideError() {

    }

    @Override
    public void showFavoriteButton(boolean exist) {
        int idDrawable = android.R.drawable.btn_star_big_off;
        if (exist) {
            idDrawable = android.R.drawable.btn_star_big_on;
        }
        fab.setImageDrawable(ContextCompat.getDrawable(ArticleApplication.getContext(), idDrawable));
    }

}
