package com.example.testingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static com.example.testingapp.DatabaseQueries.loadCategories;
import static com.example.testingapp.DatabaseQueries.wishlistModelList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {

    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishListRecyclerView;
    private WishlistAdapter wishlistAdapter;
    private ImageView noInternetConnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);

        noInternetConnection = view.findViewById(R.id.no_internet_connection);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            noInternetConnection.setVisibility(View.GONE);

            wishListRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            wishListRecyclerView.setLayoutManager(linearLayoutManager);
            wishlistAdapter = new WishlistAdapter(wishlistModelList);
            wishListRecyclerView.setAdapter(wishlistAdapter);
            if (wishlistModelList.size() == 0){
                loadCategories(wishlistAdapter,getContext());
            }else {
                wishlistAdapter.notifyDataSetChanged();
            }

        }else {
            Glide.with(this).load(R.drawable.cloud_refresh).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }

//        wishListRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        wishListRecyclerView.setLayoutManager(linearLayoutManager);

//        List<WishlistModel> wishlistModelList = new ArrayList<>();
//        wishlistModelList.add(new WishlistModel(R.drawable.image1,"Chair",1,"3",143,"Rs.999/-","Rs.1999/-","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.image1,"Sofa",0,"3",143,"Rs.999/-","Rs.1999/-","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.image1,"Cupboard",2,"3",143,"Rs.999/-","Rs.1999/-","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.image1,"Chair",4,"3",143,"Rs.999/-","Rs.1999/-","Cash on delivery"));
//        wishlistModelList.add(new WishlistModel(R.drawable.image1,"Chair",1,"3",143,"Rs.999/-","Rs.1999/-","Cash on delivery"));

//        wishlistAdapter = new WishlistAdapter(wishlistModelList,true);
//        wishListRecyclerView.setAdapter(wishlistAdapter);
//        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}
