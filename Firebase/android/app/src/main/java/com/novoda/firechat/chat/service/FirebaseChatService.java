package com.novoda.firechat.chat.service;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.novoda.firechat.chat.data.model.Chat;
import com.novoda.firechat.chat.data.model.Message;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;

public class FirebaseChatService implements ChatService {

    private final DatabaseReference databaseReference;

    public FirebaseChatService(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public Observable<Chat> getChat() {
        return Observable.create(new Observable.OnSubscribe<Chat>() {
            @Override
            public void call(final Subscriber<? super Chat> subscriber) {
                final ValueEventListener eventListener = databaseReference.child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Message> messages = toMessages(dataSnapshot);
                        subscriber.onNext(new Chat(messages));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        subscriber.onError(databaseError.toException()); //TODO handle errors in pipeline
                    }

                });
                subscriber.add(BooleanSubscription.create(new Action0() {
                    @Override
                    public void call() {
                        databaseReference.removeEventListener(eventListener);
                    }
                }));
            }
        });
    }

    @Override
    public void sendMessage(Message message) {
        databaseReference.child("messages").push().setValue(message);
    }

    private List<Message> toMessages(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        List<Message> messages = new ArrayList<Message>();
        for (DataSnapshot child : children) {
            Message message = child.getValue(Message.class);
            messages.add(message);
        }
        return messages;
    }

}