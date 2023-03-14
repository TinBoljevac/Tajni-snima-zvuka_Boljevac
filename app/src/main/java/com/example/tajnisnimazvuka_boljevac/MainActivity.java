package com.example.tajnisnimazvuka_boljevac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static int MICROPHONE_PERMISSION_CODE=200;//mikrofon
    MediaRecorder mediaRecorder;//snimač
    MediaPlayer mediaPlayer;//Player

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (jelPostojiMikrofon()){
            dopustenjeMikrofon();
        }

    }
    public void btnSnimaj(View v) throws IOException { //Pokretanje snimanja
    try {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(FormatZapisa());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.prepare();
        mediaRecorder.start();

        Toast.makeText(this, "Započeto snimanje", Toast.LENGTH_SHORT).show();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    }
    public void btnZaustavi(View v){ //Zaustavlja snimanje
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(this, "Snimanje zaustavljeno", Toast.LENGTH_SHORT).show();

    }
    public void btnPokreni(View v){ //pokreće snimljeni zapis
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(FormatZapisa());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Snimka je pokrenuta", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean jelPostojiMikrofon(){ //provjerava jel postoji mikrofon
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }else{
            return false;
        }
    }
    private void dopustenjeMikrofon(){ // omogucivanje dopustenja mikrofonu
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                ==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }
    private String FormatZapisa(){//Format audio zapisa
    ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
    File musicDirect=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
    File file= new File(musicDirect, "TestZapisaBoljevac"+".mp3");
    return file.getPath();

    }
}