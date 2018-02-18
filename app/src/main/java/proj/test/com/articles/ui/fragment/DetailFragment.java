package proj.test.com.articles.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import java.io.File;

import proj.test.com.articles.R;
import proj.test.com.articles.presenter.DetailPresenter;
import proj.test.com.articles.storage.database.DataBaseAdapter;
import proj.test.com.articles.model.Article;
import proj.test.com.articles.view.DetailView;

import static proj.test.com.articles.ui.activity.DetailActivity.ARG_ARTICLE;


public class DetailFragment extends Fragment implements DetailView{
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private WebView webView;
    private FloatingActionButton fab;


    private boolean exist;


    private DetailPresenter detailPresenter;

    public static DetailFragment  newInstance(Article article) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTICLE, article);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Article  article = getArguments().getParcelable(ARG_ARTICLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Log.e("my test", " url = " + article.getPath());

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        //  final TextView content = (TextView) view.findViewById(R.id.content);
        webView = (WebView) view.findViewById(R.id.webView);


        webView.getSettings().setDomStorageEnabled(true);//нужно ли
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //нужно ли

        String loadUrl = article.getPath();
        if (loadUrl.startsWith("/")) {
            loadUrl = "file://" + loadUrl;
            webView.loadUrl(loadUrl);
            File file = new File(article.getPath());

            if(!file.exists()){
                Toast.makeText(getContext(),
                        getResources().getString(R.string.message_web_archive_deleted),
                        Toast.LENGTH_LONG).show();
            }
            // webView.loadUrl("file:///"+Environment.getExternalStorageDirectory()
            //          + File.separator+"myArchive"+".mht");
        } else {


            webView.loadUrl(loadUrl);

        }

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



        final String finalLoadUrl = loadUrl;
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // if (!finalLoadUrl.startsWith("file")) {
                //      view.loadUrl(url);
                //  }
                Log.e("my test", " should url " + url);

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.e("my etst", " page finish " + url);
            }
        });



        exist = dbAdapter.existByTitle(article.getTitle());

        Log.e("my test", " exi st = " + exist);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (exist) {
                    deleteHtml();
                } else {
                    saveHTML();
                }
                setFab();
            }
        });
        setFab();


      /*  ArticlesApplication.getApi().getArticle(url).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                content.setText(response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("my test", t.getMessage());
            }
        });*/
        return view;
    }

    private void deleteHtml() {
        dbAdapter.deleteArticle(article);
        exist = false;
        File file = new File(article.getPath());
        boolean result = file.delete();
        Log.e("my test", "file deleted " + result);
    }

    private void setFab() {
        int idDrawable = android.R.drawable.btn_star_big_off;
        if (exist) {
            idDrawable = android.R.drawable.btn_star_big_on;
        }

        //fab.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), idDrawable));
        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), idDrawable));
        Log.e("my test", " set fab exist = " + exist);
    }

    private String getFileName() {
        return article.getTitle().replace(" ", "_");// + new Date().getTime();
    }

    private void saveDocument() {
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "articles");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        String fileName = storageDir.getAbsolutePath() + File.separator + getFileName() + ".mht";
        webView.saveWebArchive(fileName);

        article.setPath(fileName);
        long res = dbAdapter.addArticle(article);

        Log.e("my tesr", " name =  " + fileName + "  id = " + res);
    }

    private void saveHTML() {
        exist = true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            //RUNTIME PERMISSION Android M
            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                saveDocument();
            } else {
                requestPermission(getContext());
            }

        }

////...
    }

///...

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.permission_storage_success),
                            Toast.LENGTH_SHORT).show();
                    saveDocument();

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
                    .setMessage("нужны разрешения")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
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


}
