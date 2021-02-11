package com.example.ritospells;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView dataText;
    private RequestQueue mQueue;
    private String summoner_id ="";
    private String summoner_name = "";
    private ArrayList<Summoner> summoners = new ArrayList<Summoner>();
    private ArrayList<Summoner> enemyTeam = new ArrayList<Summoner>();

    private String TAG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        mQueue = Volley.newRequestQueue(this);
        dataText=  dataText = (TextView) findViewById(R.id.dataText);


        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                EditText summoner_name_text = (EditText) findViewById(R.id.summonerText);
                summoner_name = summoner_name_text.getText().toString();
                //json parser adja vissza summoner id-t
                jsonParser(summoner_name);
                String data = "";
                for(Summoner i: enemyTeam)
                {
                   data+= i.summonerID + " "+ i.teamID;
                    Log.i(TAG,"ADATOK: "+summoner_id);
                }


            }
        });

    }
    //Elkuldi az első kérdést a névfordításra
    private void jsonParser(String inputName) {
    //TODO ne legyen a headerben az API Key, legyen választhato a régió
        String firstPart = "http://[IP_HERE]:8080/lol/summoner/v4/summoners/by-name/";
        String[] arrName = inputName.split(" ");
        String actualURL = firstPart;
        for (int i = 0;i<arrName.length;i++)
        {
            actualURL+=arrName[i]+"%20";
        }
        Log.i(TAG,actualURL);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, actualURL, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    summoner_id = response.getString("id");
                    Log.i(TAG,"SUMMONER ID----->: "+summoner_id);

                    spectatorAPI(summoner_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }
    //Elkuldi a masodik kerest a summoner actual matcheert
    private void spectatorAPI(String summoner_idactual)
    {
        String firstPart = "http://[IP_HERE]:8080/lol/spectator/v4/active-games/by-summoner/";
        String actualURL = firstPart;
        actualURL+=summoner_idactual;

        /*ez a rész amit atirtam*/
        Log.i(TAG,actualURL);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, actualURL, null/*null*/, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("players");
                    for (int i = 0;i<jsonArray.length();i++)
                    {
                        JSONObject summoner = jsonArray.getJSONObject(i);

                        int teamID = summoner.getInt("team");
                        String summonerID = summoner.getString("summonerId");
                        int spell1ID= summoner.getInt("summonerSpellDId");
                        int spell2ID= summoner.getInt("summonerSpellFId");
                        int championId = summoner.getInt("championId");
                        Summoner newSummoner = new Summoner(teamID,summonerID,spell1ID,spell2ID,championId);
                        summoners.add(newSummoner);

                    }
                    filterListForEnemyTeam();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

    }
    private void filterListForEnemyTeam() {//Filters the enemy team
        int enemyteamID = 0;
        for (Summoner i :summoners)
        {
            if ( i.summonerID.equals(summoner_id))
            {
                if (i.teamID == 100)
                {
                    enemyteamID =200;

                }
                else
                {
                    enemyteamID =100;


                }

            }
        }
        for(Summoner i: summoners)
        {
            if ( i.teamID==enemyteamID) {
                enemyTeam.add(i);
                Log.i(TAG,"ADATOK: "+i.teamID +i.championId);

            }

        }

        Log.i(TAG,"DONE WITH FILTER");

        Intent gotoSecondActivity = new Intent(getApplicationContext(),SecondActivity.class);
        gotoSecondActivity.putExtra("enemyTeam",enemyTeam);
        startActivity(gotoSecondActivity);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"ONRESUME: ");
        summoners = new ArrayList<Summoner>();
        enemyTeam = new ArrayList<Summoner>();


    }
}
