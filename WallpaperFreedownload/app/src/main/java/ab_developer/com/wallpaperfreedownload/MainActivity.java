package ab_developer.com.wallpaperfreedownload;

import android.app.WallpaperManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rlMain;
    RecyclerView rvWallpapers;
    EditText etText;
    Button btnSearch;
    String apiLink = "https://pixabay.com/api/?key=5867364-9959787e0bf9dae47919fa542&image_type=photo&per_page=200";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvWallpapers = findViewById(R.id.rv_wallpapers);

        etText = findViewById(R.id.et_text);
        rlMain = findViewById(R.id.rl_main);

        rlMain.requestFocus();
        etText.clearFocus();
        btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etText.getText().toString();
                rlMain.requestFocus();
                etText.clearFocus();
                apiLink = "https://pixabay.com/api/?key=5867364-9959787e0bf9dae47919fa542&image_type=photo&per_page=200&q=" + text;
                fetchData(apiLink);
            }
        });
//        apiLink = "https://pixabay.com/api/?key=5867364-9959787e0bf9dae47919fa542&image_type=photo&per_page=200";

        fetchData(apiLink);

    }

    public void fetchData(String apiCall){
        StringRequest request = new StringRequest(apiCall, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObject = new JSONObject(response);
                    JSONArray hitsArray = jObject.getJSONArray("hits");
                    final ArrayList<WallpaperItem> wallpaperList = new ArrayList<>();
                    for (int i=0; i<hitsArray.length(); i++){
                        JSONObject hitsObject  = hitsArray.getJSONObject(i);
                        WallpaperItem w = new WallpaperItem();
                        w.webformatURL = hitsObject.getString("webformatURL");
                        w.largeimageURL = hitsObject.getString("largeImageURL");
                        wallpaperList.add(w);
                    }
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
                    rvWallpapers.setLayoutManager(manager);
                    WallpaperAdapter adapter = new WallpaperAdapter(wallpaperList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, WallpaperDetailActivity.class);
                            intent.putExtra("largeImageURL", wallpaperList.get(position).largeimageURL);
                            startActivity(intent);
                        }
                    });
                    rvWallpapers.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Volley Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}
