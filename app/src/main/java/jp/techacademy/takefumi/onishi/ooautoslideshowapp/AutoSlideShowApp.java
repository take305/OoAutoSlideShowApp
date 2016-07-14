package jp.techacademy.takefumi.onishi.ooautoslideshowapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class AutoSlideShowApp extends AppCompatActivity {
    private static final int DELAY_TIME = 2000;
    ArrayList<Uri> imageUris = new ArrayList<>();
    static int pages = 1;
    boolean onOff = true;
    Handler _handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_slide_show_app);


        if (PermissionCheck.isPermissionCheck(this)) {
            viewCreate();
        }

        Button buttonNext = (Button) findViewById(R.id.buttonnext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewNext();
            }
        });

        Button buttonBack = (Button) findViewById(R.id.buttonback);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBack();
            }
        });

        Button buttonPlay = (Button) findViewById(R.id.buttonplay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPlay();
            }
        });
    }


    public void viewBack() {
        if (!onOff) {
            Toast toast = Toast.makeText(AutoSlideShowApp.this,
                    "スライドショー中はこの操作は無効です", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        --pages;
        if (pages == 0) {
            this.pages = imageUris.size() - 1;
        }
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(this.pages));
    }

    public void viewNext() {
        if (!onOff) {
            Toast toast = Toast.makeText(MainActivity.this,
                    "スライドショー中はこの操作は無効です", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        ++this.pages;
        if (this.pages == imageUris.size()) {
            this.pages = 1;
        }
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(this.pages));

    }

    public void viewPlay() {

        if (onOff) {
            buttonPlay.setText(R.string.stop);
            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ++this.pages;
                    if (this.pages == imageUris.size()) {
                        this.pages = 1;
                    }
                    ImageView imageView = (ImageView) findViewById(R.id.image);
                    imageView.setImageURI(imageUris.get(this.pages));

                    _handler.postDelayed(this, DELAY_TIME);
                }
            }, DELAY_TIME);
        } else {
            buttonPlay.setText(R.string.start);
            _handler.removeCallbacksAndMessages(null);
        }
        onOff = !onOff;
    }

    private void viewCreate() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                Long id = cursor.getLong(fieldIndex);
                imageUris.add(ContentUris.withAppendedId
                        (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id));

            } while (cursor.moveToNext());
        }
        cursor.close();
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(this.pages));

    }


}
