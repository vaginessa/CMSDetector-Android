package felixgiov.cmsdetector.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import felixgiov.cmsdetector.R;
import felixgiov.cmsdetector.adapter.CmsAdapter;
import felixgiov.cmsdetector.rest.ApiClient;
import felixgiov.cmsdetector.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "baf1270d5a9c8f3dcf4de5c11f4ca392";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<String>> call = apiService.detectCMS("ugm.ac.id",API_KEY);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cms = response.body();
                    recyclerView.setAdapter(new CmsAdapter(cms, R.layout.list_tab, getApplicationContext()));
                        Log.d("list", cms.toString());

                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
