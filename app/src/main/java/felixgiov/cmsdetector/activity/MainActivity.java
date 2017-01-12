package felixgiov.cmsdetector.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

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
    private AdView mAdView;
    ImageButton klik;
    EditText url;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        klik = (ImageButton) findViewById(R.id.klik_button);
        url = (EditText) findViewById(R.id.url_et);

        mProgressBar.setVisibility(View.GONE);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first!", Toast.LENGTH_LONG).show();
            return;
        }

        klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rest(url.getText().toString());
                mProgressBar.setVisibility(View.VISIBLE);
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, "ca-app-pub-2029021861713537/7143027207");

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

    }
    public void rest(String url){
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<String>> call = apiService.detectCMS(url,API_KEY);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> cms = response.body();
                    recyclerView.setAdapter(new CmsAdapter(cms, R.layout.list_tab, getApplicationContext()));
                    mProgressBar.setVisibility(View.GONE);
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
