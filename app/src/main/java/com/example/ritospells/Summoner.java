package com.example.ritospells;

import android.os.Parcel;
import android.os.Parcelable;

public class Summoner implements Parcelable {
    public int teamID;
    public String summonerID;

    public int spell1ID;
    public String spell1Name;

    public int spell2ID;
    public String spell2Name;

    public int championId;
    public String championName;


    public Summoner(int teamID,String summonerID,int spell1ID, int spell2ID,int championId)
    {
        this.teamID = teamID;
        this.summonerID= summonerID;
        this.spell1ID=spell1ID;
        this.spell2ID=spell2ID;
        this.championId=championId;
    }
    public void setChampionName(String name)
    {
        this.championName=name;

    }
    public void setSpell1Name(String name)
    {
        this.spell1Name=name;

    }
    public void setSpell2ID(String name)
    {
        this.spell2Name=name;

    }

    protected Summoner(Parcel in) {
        teamID = in.readInt();
        summonerID = in.readString();
        spell1ID = in.readInt();
        spell2ID = in.readInt();
        championId = in.readInt();
    }

    public static final Creator<Summoner> CREATOR = new Creator<Summoner>() {
        @Override
        public Summoner createFromParcel(Parcel in) {
            return new Summoner(in);
        }

        @Override
        public Summoner[] newArray(int size) {
            return new Summoner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(teamID);
        dest.writeString(summonerID);
        dest.writeInt(spell1ID);
        dest.writeInt(spell2ID);
        dest.writeInt(championId);
    }

}
