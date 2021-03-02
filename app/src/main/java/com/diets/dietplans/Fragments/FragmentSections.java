package com.diets.dietplans.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.diets.dietplans.Activities.ActivitySettings;
import com.diets.dietplans.Config;
import com.diets.dietplans.POJOS.Global;
import com.diets.dietplans.POJOS.Section;
import com.diets.dietplans.R;

import java.util.ArrayList;

public class FragmentSections extends Fragment {
    private RecyclerView rvSections;
    private Global global;
    private String TAG_SECTION = "tag_of_sec";
    private String TAG_SUBSECTION = "tag_of_sec";
    private ArrayList<Section> sectionArrayList;

    private FloatingActionButton fab;


    public static FragmentSections newInstance(Global global) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.ID_SECTIONS_ARGS, global);
        FragmentSections fragmentSections = new FragmentSections();
        fragmentSections.setArguments(bundle);
        return fragmentSections;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_sections, container, false);
        global = (Global) getArguments().getSerializable(Config.ID_SECTIONS_ARGS);
        sectionArrayList = (ArrayList<Section>) global.getSectionsArray();

        rvSections = view.findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSections.setAdapter(new SectionsAdapter(sectionArrayList));
        rvSections.setItemViewCacheSize(50);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivitySettings.class);
                startActivity(intent);
            }
        });
        return view;
    }


    private class SectionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItem;
        ImageView ivItem;
        Animation animation = null;

        public SectionsHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_sections, viewGroup, false));
            itemView.setOnClickListener(this);
            tvItem = itemView.findViewById(R.id.tv_item);
            ivItem = itemView.findViewById(R.id.iv_item);

            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loading);
            ivItem.startAnimation(animation);
        }

        public void bind(Section section) {
            Picasso.with(getActivity()).load(section.getUrlOfImage()).noPlaceholder().into(ivItem, new Callback() {
                @Override
                public void onSuccess() {
                    ivItem.clearAnimation();
                }

                @Override
                public void onError() {

                }
            });
            tvItem.setText(section.getDescription());
        }

        @Override
        public void onClick(View v) {
            if(sectionArrayList.get(getAdapterPosition()).getArrayOfSubSections().size() == 1){
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, FragmentItem.newInstance(sectionArrayList.get(getAdapterPosition()).getArrayOfSubSections().get(0)))
                        .addToBackStack(TAG_SUBSECTION).commit();
                /*check one-size subsection*/
            }else {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, FragmentSubSections.newInstance(sectionArrayList.get(getAdapterPosition())))
                        .addToBackStack(TAG_SECTION).commit();
            }

        }
    }

    private class SectionsAdapter extends RecyclerView.Adapter<SectionsHolder> {
        ArrayList<Section> sectionArrayList;

        public SectionsAdapter(ArrayList<Section> sectionArrayList) {
            this.sectionArrayList = sectionArrayList;
        }

        @NonNull
        @Override
        public SectionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SectionsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SectionsHolder holder, int position) {
            holder.bind(sectionArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return sectionArrayList.size();
        }
    }
}
