package proj.test.com.articles.api;

import proj.test.com.articles.model.ResponseAllArticle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NytimesApi {


    @GET("{type}/{section}/{time-period}.json")
    Call<ResponseAllArticle> getArticles(@Path("type") String type, @Path("section") String section, @Path("time-period") String timePeriod);

    /*@GET("mostshared/{section}/{time-period}.json")
    Call<ResponseAllArticle> getMostShared(@Path("section") String section, @Path("time-period") String timePeriod);

    @GET("mostviewed/{section}/{time-period}.json")
    Call<ResponseAllArticle> getMostViewed(@Path("section") String section, @Path("time-period") String timePeriod);*/

}
