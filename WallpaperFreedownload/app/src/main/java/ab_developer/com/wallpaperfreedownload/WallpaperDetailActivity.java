package ab_developer.com.wallpaperfreedownload;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import uk.co.senab.photoview.PhotoView;

public class WallpaperDetailActivity extends AppCompatActivity {

    PhotoView photoView;
    Button btnSetWallpaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_detail);
        photoView = findViewById(R.id.photoview);
        btnSetWallpaper = findViewById(R.id.btn_set_wallpaper);

        Bundle bundle = getIntent().getExtras();
        final String largeImageURL = bundle.getString("largeImageURL");
        Picasso.get().load(largeImageURL).into(photoView);

        btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WallpaperManager wallpaperManager =(WallpaperManager) getSystemService(WALLPAPER_SERVICE);
//                WallpaperManager wallpaperManager2 =WallpaperManager.getInstance(WallpaperDetailActivity.this);
                Picasso.get().load(largeImageURL).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(WallpaperDetailActivity.this, "Wallpaper has been set", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(WallpaperDetailActivity.this, "DownLoad Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Toast.makeText(WallpaperDetailActivity.this, "DownLoad Starting... Please Wait", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
