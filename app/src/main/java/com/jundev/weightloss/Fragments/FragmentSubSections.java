package com.jundev.weightloss.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.jundev.weightloss.Config;
import com.jundev.weightloss.POJOS.Section;
import com.jundev.weightloss.POJOS.Subsection;
import com.jundev.weightloss.R;

import java.util.ArrayList;

public class FragmentSubSections extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Subsection> subsectionArrayList;
    private final String TAG_FRAGMENT_SUBSECTION = "tag_fragment_subsection";


    public static FragmentSubSections newInstance(Section section) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.ID_SUBSECTIONS_ARGS, section);

        FragmentSubSections subSections = new FragmentSubSections();
        subSections.setArguments(bundle);
        return subSections;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_subsections, container, false);
        Section section = (Section) getArguments().getSerializable(Config.ID_SUBSECTIONS_ARGS);
        subsectionArrayList = (ArrayList<Subsection>) section.getArrayOfSubSections();

        Config.INDEX_ADMOB++;

        recyclerView = view.findViewById(R.id.rvSubSections);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SubSectionsAdapter(subsectionArrayList));


        return view;
    }

    private class SubSectionsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvBodyOfText;
        ImageView ivBackGroundOfItem;
        Animation animation = null;

        public SubSectionsHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_subsections, viewGroup, false));

            tvBodyOfText = itemView.findViewById(R.id.tvSubsections);
            ivBackGroundOfItem = itemView.findViewById(R.id.ivSubsections);
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_loading);

            ivBackGroundOfItem.startAnimation(animation);

            itemView.setOnClickListener(this);
        }

        public void bind(Subsection subsection) {
            tvBodyOfText.setText(subsection.getDescription());
            Picasso.with(getActivity()).load(subsection.getUrlOfImage()).noPlaceholder().into(ivBackGroundOfItem, new Callback() {
                @Override
                public void onSuccess() {
                    ivBackGroundOfItem.clearAnimation();
                }

                @Override
                public void onError() {

                }
            });

        }

        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FragmentItem.newInstance(subsectionArrayList.get(getAdapterPosition())))
                    .addToBackStack(TAG_FRAGMENT_SUBSECTION).commit();
        }
    }

    private class SubSectionsAdapter extends RecyclerView.Adapter<SubSectionsHolder> {
        ArrayList<Subsection> subsectionArrayList;

        public SubSectionsAdapter(ArrayList<Subsection> subsectionArrayList) {
            this.subsectionArrayList = subsectionArrayList;
        }

        @NonNull
        @Override
        public SubSectionsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SubSectionsHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SubSectionsHolder holder, int position) {
            holder.bind(subsectionArrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return subsectionArrayList.size();
        }
    }
}
