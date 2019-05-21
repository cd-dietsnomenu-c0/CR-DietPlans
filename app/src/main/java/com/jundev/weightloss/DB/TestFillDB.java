package com.jundev.weightloss.DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jundev.weightloss.POJOS.Global;
import com.jundev.weightloss.POJOS.ItemOfSubsection;
import com.jundev.weightloss.POJOS.Section;
import com.jundev.weightloss.POJOS.Subsection;

import java.util.ArrayList;

public class TestFillDB {
    public static void fillSection() {
        ItemOfSubsection ioss = new ItemOfSubsection("url", "description", "body");
        ArrayList<ItemOfSubsection> arrayOfIOSS = new ArrayList<>();
        arrayOfIOSS.add(ioss);
        Subsection subsection = new Subsection("descriptionSub", "urlSub", arrayOfIOSS);
        ArrayList<Subsection> arraySub = new ArrayList<>();
        arraySub.add(subsection);
        Section section = new Section("main_url", "5 статей по свечному анализу.", arraySub);
        Section section1 = new Section("main_url", "Индикатор RSI", arraySub);
        Section section2 = new Section("main_url", "Основы технического анализа при торговле криптовалютой", arraySub);
        Section section3 = new Section("main_url", "Лучшие биржи для торговли криптовалютой. Биржа №1 — Binance", arraySub);
        Section section4 = new Section("main_url", "Выбор лучшего мультивалютного кошелька для криптовалюты на 2018 год", arraySub);
        Section section5 = new Section("main_url", "Как перестать терять деньги: Торговый план", arraySub);
        Section section6 = new Section("main_url", "Словарь рынка криптовалют", arraySub);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("adb");
        ArrayList<Section> arrayList = new ArrayList<>();
        arrayList.add(section);
        arrayList.add(section1);
        arrayList.add(section2);
        arrayList.add(section3);
        arrayList.add(section4);
        arrayList.add(section5);
        arrayList.add(section6);
        Global global = new Global(arrayList, "GLOBAL_NAME");
        databaseReference.setValue(global);

    }
}
