package com.example.testingapp;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DatabaseQueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();

    public static void loadCategories(final WishlistAdapter wishlistAdapter, final Context context){
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                long no_of_products = (long) documentSnapshot.get("no_of_products");
                                for (long x = 1; x < no_of_products + 1; x++) {
                                    wishlistModelList.add(new WishlistModel(documentSnapshot.get("product_image_" + x).toString()
                                            , documentSnapshot.get("product_full_title_" + x).toString()
                                            , (long) documentSnapshot.get("free_coupens_" + x)
                                            , documentSnapshot.get("average_rating_" + x).toString()
                                            , (long) documentSnapshot.get("total_ratings_" + x)
                                            , documentSnapshot.get("product_price_" + x).toString()
                                            , documentSnapshot.get("cutted_price_" + x).toString()
                                            , (boolean) documentSnapshot.get("COD_" + x)));
                                }
                            }
                            wishlistAdapter.notifyDataSetChanged();
                        }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
    }