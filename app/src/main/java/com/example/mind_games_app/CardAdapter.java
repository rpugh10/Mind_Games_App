package com.example.mind_games_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    ArrayList<Integer> cards;

    public CardAdapter(ArrayList<Integer> cards){
        this.cards = cards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.cardText.setText(String.valueOf(cards.get(position)));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        TextView cardText;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardText = itemView.findViewById(R.id.cardText);
        }
    }
}
