package com.example.mind_games_app;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    ArrayList<Card> cards;
    OnMatchListener listener;

    Card firstCard = null;
    Card secondCard = null;

    public CardAdapter(ArrayList<Card> cards, OnMatchListener listener){
        this.cards = cards;
        this.listener = listener;
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
        //Used AI to help me figure this part out
        Card card = cards.get(position);

        if(card.faceUp || card.isMatched){
            holder.cardText.setText(String.valueOf(card.value));
        }else{
            holder.cardText.setText("");
        }

        holder.itemView.setOnClickListener(v ->{
            if(card.faceUp || card.isMatched) return;

            card.faceUp = true;
            notifyItemChanged(position);

            if(firstCard == null){
                firstCard = card;
            }else if(secondCard == null){
                secondCard = card;
                listener.onMove();

                if(firstCard.value == secondCard.value){
                    firstCard.isMatched = true;
                    secondCard.isMatched = true;

                    listener.onMatch();

                    firstCard = null;
                    secondCard = null;
                }else{
                    new Handler().postDelayed(() -> {
                        firstCard.faceUp =false;
                        secondCard.faceUp =false;

                        notifyDataSetChanged();

                        firstCard = null;
                        secondCard = null;
                    }, 1000);
                }
            }
        });
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

    public interface OnMatchListener{
        void onMatch();
        void onMove();
    }
}
