package com.diets.dietplans.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.diets.dietplans.Config;
import com.diets.dietplans.POJOS.ItemOfSubsection;
import com.diets.dietplans.POJOS.Subsection;
import com.diets.dietplans.R;

import java.util.ArrayList;

public class FragmentItem extends Fragment {

    private RecyclerView recyclerView;
    private Subsection subsection;
    private ImageView ivCollapsingToolBar;
    private TextView tvCollapsingHead;

    public static FragmentItem newInstance(Subsection subsection) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Config.ID_ITEM, subsection);

        FragmentItem fragmentItem = new FragmentItem();
        fragmentItem.setArguments(bundle);

        return fragmentItem;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_article, container, false);
        recyclerView = view.findViewById(R.id.rvArticle);
        ivCollapsingToolBar = view.findViewById(R.id.ivCollapsing);
        Toolbar toolbar = view.findViewById(R.id.main_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.main_collapsing);

        Config.INDEX_ADMOB++;

        subsection = (Subsection) getArguments().getSerializable(Config.ID_ITEM);
        Picasso.with(getActivity()).load(subsection.getUrlOfImage()).into(ivCollapsingToolBar);
        toolbar.setTitle(subsection.getDescription());


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ItemAdapter((ArrayList<ItemOfSubsection>) subsection.getArrayOfItemOfSubsection()));


        return view;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView ivItem;
        private TextView tvImageDescription, tvBodyOfMainText;
        private CardView cardView;

        public ItemHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.item_of_article, viewGroup, false));

            ivItem = itemView.findViewById(R.id.ivBodyItemImage);
            tvBodyOfMainText = itemView.findViewById(R.id.tvBodyItemMainText);
            tvImageDescription = itemView.findViewById(R.id.tvBodyOfItemDescription);
            cardView = itemView.findViewById(R.id.cvArticle);
        }

        public void bind(ItemOfSubsection itemOfSubsection) {
            if (itemOfSubsection.getUrlOfImage().equals("empty")) {
                cardView.setVisibility(View.GONE);
                ivItem.setVisibility(View.GONE);
            } else {
                Picasso.with(getActivity()).load(itemOfSubsection.getUrlOfImage()).into(ivItem);
            }
            tvImageDescription.setText(Html.fromHtml(itemOfSubsection.getDescriptionOfImage()));
            tvBodyOfMainText.setText(Html.fromHtml(itemOfSubsection.getBodyOfText()));

        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        ArrayList<ItemOfSubsection> subsectionArrayList;

        public ItemAdapter(ArrayList<ItemOfSubsection> subsectionArrayList) {
            this.subsectionArrayList = subsectionArrayList;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.bind(subsectionArrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return subsectionArrayList.size();
        }
    }
}
