package com.example.tarun.khana;


import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartlistAdapterHolder> {
    private Cart context;
    private Singleton var = Singleton.getInstance();

    public CartListAdapter(Cart object){
        context = object;
    }
    @NonNull
    @Override
    public CartlistAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cart_card_recycler_view,parent,false);
        CartlistAdapterHolder cartlistAdapterHolder = new CartlistAdapterHolder(v);

        return cartlistAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartlistAdapterHolder holder, final int position) {
        Log.v("cart",""+var.cartFoodInfo.size());

            Glide.with(context).load(var.cartFoodImageUrl.get(position)).into(holder.foodImage);
            holder.foodDetails.setText("Delicious "+ var.cartFoodInfo.get(position).dish_name +", " +var.cartFoodInfo.get(position).dist_type+ " with "+ var.cartFoodInfo.get(position).dish_spiciness + ".");
            holder.foodQuantity.setText(var.quantity.get(position));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                    var.cartFoodInfo.remove(position);
                var.cartFoodImageUrl.remove(position);
                var.quantity.remove(position);
                if(var.quantity.isEmpty() && var.cartFoodImageUrl.isEmpty() && var.cartFoodInfo.isEmpty()){
                    var.showCart = false;
                    Toast.makeText(context, "Cart is empty", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,SeekerHome.class);
                    intent.putExtra("username",var.getUserInfo);
                    context.startActivity(intent);
                }
                else {

                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return var.cartFoodInfo.size();
    }

    public static class CartlistAdapterHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView foodDetails,foodQuantity;
        Button delete;
        public CartlistAdapterHolder(View itemView) {
            super(itemView);
            foodImage = (ImageView) itemView.findViewById(R.id.selectedFoodImage);
            foodDetails = (TextView) itemView.findViewById(R.id.foodDetail);
            foodQuantity = (TextView) itemView.findViewById(R.id.foodQuantity);
            delete = (Button) itemView.findViewById(R.id.removeFromCart);
        }
    }


}
