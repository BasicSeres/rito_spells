package com.example.ritospells;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;


public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    ArrayList<Data> dataChamps = new ArrayList<Data>();
    ArrayList<Data> dataSpells = new ArrayList<Data>();
    HashMap<Integer,String> spells = new HashMap<Integer,String>();
    HashMap<Integer,String>  champs = new HashMap<Integer,String>();
    private Context context = SecondActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        if (spells.isEmpty())
        {
            spells.put(21,"barrier");
            spells.put(1,"cleanse");
            spells.put(14,"ignite");
            spells.put(3,"exhaust");
            spells.put(4,"flash");
            spells.put(6,"ghost");
            spells.put(7,"heal");
            spells.put(11,"smite");
            spells.put(12,"teleport");
        }

        if (getIntent().hasExtra("enemyTeam"))
        {
            /*
            String url = "http://ddragon.leagueoflegends.com/cdn/10.1.1/img/champion/Akali.png";
            ImageView summoner1 = findViewById(R.id.imgSmnr1);

            Glide.with(context).load(url).into(summoner1);
*/
            ArrayList<Summoner> enemyTeam = getIntent().getParcelableArrayListExtra("enemyTeam");

            //dataView.setText(data);
            parseChampName(enemyTeam);
            parseSpellName(enemyTeam);


        }

    }
    private void parseChampName(ArrayList<Summoner> enemyTeam)
    {
        String data = "";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("champions.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                String champ_name = mLine;

                int champ_id = Integer.parseInt(reader.readLine());
                champs.put(champ_id,champ_name);

            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        for(Summoner s:enemyTeam)
        {
            s.championName=champs.get(s.championId);
        }
        setImagesToIt(enemyTeam);

    }
    private void parseSpellName(ArrayList<Summoner> enemyTeam)
    {

        for(Summoner s:enemyTeam)
        {
            s.spell1Name=spells.get(s.spell1ID);

            s.spell2Name=spells.get(s.spell2ID);
        }

        setImagesToSpells(enemyTeam);

    }
    private void setImagesToIt(ArrayList<Summoner> enemyTeam)
    {

        ImageView summoner1 = findViewById(R.id.imgSmnr1);
        ImageView summoner2 = findViewById(R.id.imgSmnr2);
        ImageView summoner3 = findViewById(R.id.imgSmnr3);
        ImageView summoner4 = findViewById(R.id.imgSmnr4);
        ImageView summoner5 = findViewById(R.id.imgSmnr5);
        ArrayList<String> urls = new ArrayList<String>();

        String url = "http://ddragon.leagueoflegends.com/cdn/10.1.1/img/champion/xxx.png";

        for (Summoner s:enemyTeam)
        {
            String newurl = url.replace("xxx",s.championName);

            urls.add(newurl);

            Log.i(TAG,"newurl value:> "+newurl);
        }
        Glide.with(this).load(urls.get(0)).into(summoner1);
        Glide.with(this).load(urls.get(1)).into(summoner2);
        Glide.with(this).load(urls.get(2)).into(summoner3);
        Glide.with(this).load(urls.get(3)).into(summoner4);
        Glide.with(this).load(urls.get(4)).into(summoner5);


    }
    private void setImagesToSpells(final ArrayList<Summoner> enemyTeam)
    {
        final ImageView spell1Smnr1 = findViewById(R.id.spell1Smnr1);
        ImageView spell2Smnr1 = findViewById(R.id.spell2Smnr1);

        ImageView spell1Smnr2 = findViewById(R.id.spell1Smnr2);
        ImageView spell2Smnr2 = findViewById(R.id.spell2Smnr2);

        ImageView spell1Smnr3 = findViewById(R.id.spell1Smnr3);
        ImageView spell2Smnr3 = findViewById(R.id.spell2Smnr3);

        ImageView spell1Smnr4 = findViewById(R.id.spell1Smnr4);
        ImageView spell2Smnr4 = findViewById(R.id.spell2Smnr4);

        ImageView spell1Smnr5 = findViewById(R.id.spell1Smnr5);
        ImageView spell2Smnr5 = findViewById(R.id.spell2Smnr5);

        ArrayList<String> paths = new ArrayList<String>();

        String url = "file:///android_asset/xxx.png";


        for (Summoner s:enemyTeam)
        {

            String newurl = url.replace("xxx",s.spell1Name);
            String newurl2 = url.replace("xxx",s.spell2Name);

            Log.i(TAG,"SPELL 1:> "+newurl);
            Log.i(TAG,"SPELL 2:> "+newurl2);



            paths.add(newurl);
            paths.add(newurl2);

        }


        Glide.with(this).load(paths.get(0)).into(spell1Smnr1);
        Glide.with(this).load(paths.get(1)).into(spell2Smnr1);

        Glide.with(this).load(paths.get(2)).into(spell1Smnr2);
        Glide.with(this).load(paths.get(3)).into(spell2Smnr2);

        Glide.with(this).load(paths.get(4)).into(spell1Smnr3);
        Glide.with(this).load(paths.get(5)).into(spell2Smnr3);

        Glide.with(this).load(paths.get(6)).into(spell1Smnr4);
        Glide.with(this).load(paths.get(7)).into(spell2Smnr4);

        Glide.with(this).load(paths.get(8)).into(spell1Smnr5);
        Glide.with(this).load(paths.get(9)).into(spell2Smnr5);


        TextView smnr11 = findViewById(R.id.spell1Smnr1Overlay);
        TextView smnr12 = findViewById(R.id.spell2Smnr1Overlay);
        smnr11.setVisibility(smnr11.INVISIBLE);
        smnr12.setVisibility(smnr12.INVISIBLE);
        TextView smnr21 = findViewById(R.id.spell1Smnr2Overlay);
        TextView smnr22 = findViewById(R.id.spell2Smnr2Overlay);
        smnr21.setVisibility(smnr21.INVISIBLE);
        smnr22.setVisibility(smnr22.INVISIBLE);
        TextView smnr31 = findViewById(R.id.spell1Smnr3Overlay);
        TextView smnr32 = findViewById(R.id.spell2Smnr3Overlay);
        smnr31.setVisibility(smnr31.INVISIBLE);
        smnr32.setVisibility(smnr32.INVISIBLE);
        TextView smnr41 = findViewById(R.id.spell1Smnr4Overlay);
        TextView smnr42 = findViewById(R.id.spell2Smnr4Overlay);
        smnr41.setVisibility(smnr41.INVISIBLE);
        smnr42.setVisibility(smnr42.INVISIBLE);
        TextView smnr51 = findViewById(R.id.spell1Smnr5Overlay);
        TextView smnr52 = findViewById(R.id.spell2Smnr5Overlay);
        smnr51.setVisibility(smnr51.INVISIBLE);
        smnr52.setVisibility(smnr52.INVISIBLE);



        CountDownTimerSpell1(enemyTeam,spell1Smnr1,0,smnr11);
        CountDownTimerSpell2(enemyTeam,spell2Smnr1,0,smnr12);

        CountDownTimerSpell1(enemyTeam,spell1Smnr2,1,smnr21);
        CountDownTimerSpell2(enemyTeam,spell2Smnr2,1,smnr22);

        CountDownTimerSpell1(enemyTeam,spell1Smnr3,2,smnr31);
        CountDownTimerSpell2(enemyTeam,spell2Smnr3,2,smnr32);

        CountDownTimerSpell1(enemyTeam,spell1Smnr4,3,smnr41);
        CountDownTimerSpell2(enemyTeam,spell2Smnr4,3,smnr42);

        CountDownTimerSpell1(enemyTeam,spell1Smnr5,4,smnr51);
        CountDownTimerSpell2(enemyTeam,spell2Smnr5,4,smnr52);





    }
    private void CountDownTimerSpell1(final ArrayList<Summoner> enemyTeam,final ImageView image,final int counter,final TextView overlay)
    {
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                overlay.setVisibility(v.VISIBLE);

                image.setAlpha(0.5f);

                final CountDownTimer timer = new CountDownTimer(Spells.getCooldown(enemyTeam.get(counter).spell1Name)*1000,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        overlay.setText(millisUntilFinished/1000+"");
                    }

                    @Override
                    public void onFinish() {
                        overlay.setVisibility(v.INVISIBLE);
                        image.setAlpha(1f);
                    }
                }.start();
                overlay.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        timer.cancel();
                        timer.onFinish();

                        return false;
                    }
                });
            }
        });
    }
    private void CountDownTimerSpell2(final ArrayList<Summoner> enemyTeam, final ImageView image, final int counter, final TextView overlay)
    {
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                overlay.setVisibility(v.VISIBLE);

                image.setAlpha(0.4f);

                final CountDownTimer timer = new CountDownTimer(Spells.getCooldown(enemyTeam.get(counter).spell2Name)*1000,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        overlay.setText(millisUntilFinished/1000+"");
                    }

                    @Override
                    public void onFinish() {
                        overlay.setVisibility(v.INVISIBLE);
                        image.setAlpha(1f);
                    }
                }.start();
                overlay.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        timer.cancel();
                        timer.onFinish();

                        return false;
                    }
                });
            }
        });
    }
}
