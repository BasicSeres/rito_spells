package com.example.ritospells;

public class Spells {
    public static int getCooldown(String spellname)
    {
        switch(spellname) {

            case "barrier":
                    return 180;

            case "cleanse":
                return 210;
             case "exhaust":
                 return 210;

             case "flash":
                 return 300;

             case "ghost":
                return 180;
            case "heal":
                return 240;

             case "smite":
                 return 15;

             case "teleport":
                 return 360;
                 case "ignite":
                 return 180;

            default:
                // code block
        }


        return 0;

    }

}
