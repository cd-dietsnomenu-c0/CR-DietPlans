package com.diets.dietplans.common;

import com.google.firebase.database.FirebaseDatabase;
import com.diets.dietplans.model.Global;

public class FBWorker {

    public static void clearListAndPush(Global g){
        for (int i = 0; i < g.getAllDiets().getDietList().size() - 1; i++) {
            for (int j = 0; j < g.getAllDiets().getDietList().get(i).getDays().size() - 1; j++) {
                int k = 0;
                while (k < g.getAllDiets().getDietList().get(i).getDays().get(j).getEats().size()){
                    if (g.getAllDiets().getDietList().get(i).getDays().get(j).getEats().get(k).getText().equals("")){
                        g.getAllDiets().getDietList().get(i).getDays().get(j).getEats().remove(k);
                        k --;
                    }
                    k ++;
                }
            }
        }
        FirebaseDatabase.getInstance().getReference("clearAdb").setValue(g);

    }
}
