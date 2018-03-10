package com.aiora.trendy.quiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aiora.trendy.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by HOME on 10-03-2018.
 */

public class QuizFragment extends Fragment {

    ArrayList<FetchQuizData> quizData;
    QuizRecyclerViewAdapter adapter;
    DatabaseReference reference;
    RecyclerView recyclerView;
    RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);

        recyclerView = view.findViewById(R.id.quizRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        layout = view.findViewById(R.id.loadingLayout);

        quizData = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("quiz/test");
        reference.orderByChild("date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                FetchQuizData data = dataSnapshot.getValue(FetchQuizData.class);
                quizData.add(data);

                Collections.reverse(quizData);
                adapter = new QuizRecyclerViewAdapter(quizData, getActivity());
                recyclerView.setAdapter(adapter);
                layout.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FetchQuizData data = dataSnapshot.getValue(FetchQuizData.class);
                quizData.add(data);

                Collections.reverse(quizData);
                adapter = new QuizRecyclerViewAdapter(quizData, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}

