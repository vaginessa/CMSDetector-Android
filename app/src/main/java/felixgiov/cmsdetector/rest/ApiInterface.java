package felixgiov.cmsdetector.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by felix on 11 Jan 2017.
 */

public interface ApiInterface {

    String API_CMS = "cms-detector";

    @GET(API_CMS)
    Call<List<String>> detectCMS(@Query("u") String u, @Query("k") String k);
}
