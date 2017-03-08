package com.example.alienware.lab_haoweizhang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends Activity {
    private static final int CAMERA_REQUEST = 101;
    private TextView time;
    private TextView address;
    private Team team;
    private ImageView teamLogo;
    private TextView teamName, name;
    private TextView slogan;
    private TextView score;
    private TextView ts1, ts2, ps1, ps2, total, pTotal;
    private Button camera;
    private static String pictureAb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        team = (Team) getIntent().getSerializableExtra("team");
        time = (TextView) findViewById(R.id.time);
        address = (TextView) findViewById(R.id.address);
        teamLogo = (ImageView) findViewById(R.id.teamLogo);
        teamName = (TextView) findViewById(R.id.teamName);
        slogan = (TextView) findViewById(R.id.slogan);
        score = (TextView) findViewById(R.id.score);
        ts1 = (TextView) findViewById(R.id.ts1);
        ts2 = (TextView) findViewById(R.id.ts2);
        ps1 = (TextView) findViewById(R.id.ps1);
        ps2 = (TextView) findViewById(R.id.ps2);
        total = (TextView) findViewById(R.id.total);
        pTotal = (TextView) findViewById(R.id.ptotal);
        camera = (Button) findViewById(R.id.camera);
        name = (TextView) findViewById(R.id.name);


        time.setText(team.getTime());
        address.setText(team.getAddress());
        int resID = getResources().getIdentifier(team.getTeamLogo(), "drawable", getPackageName());
        teamLogo.setImageResource(resID);
        teamName.setText(team.getTeamName());
        name.setText(team.getTeamName());
        slogan.setText(team.getSlogan());
        score.setText(team.tScore + " - " + team.pScore);

        ts1.setText(String.valueOf(team.ts1));
        ts2.setText(team.ts2 + "");
        ps1.setText(team.ps1 + "");
        ps2.setText(team.ps2 + "");
        total.setText(team.tScore + "");
        pTotal.setText(team.pScore + "");


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                if (!pictureDirectory.exists()) {
                    pictureDirectory.mkdirs();
                }
                pictureAb = pictureDirectory.getAbsolutePath() + File.separator + getPictureName();
                File imageFile = new File(pictureAb);

                if (!imageFile.exists()) {
                    try {
                        imageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Uri pictureUri = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(pictureAb, options);

                    ImageView imgView = (ImageView) findViewById(R.id.photo_taken);
                    imgView.setImageBitmap(bm);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "BestMoments" + timestamp + ".jpg";
    }
}
