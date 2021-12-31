package com.example.mynotes.tools;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreNotesRepository extends ViewModel implements NotesRepository {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "date";
    private static final String COLLECTION = "notes";

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    ArrayList<Note> res = new ArrayList<>();
                    for (DocumentSnapshot doc : documents) {
                        String id = doc.getId();
                        String title = doc.getString(KEY_TITLE);
                        String body = doc.getString(KEY_BODY);
                        Date date = doc.getDate(KEY_DATE);
                        res.add(new Note(title, body, id, date));
                    }
                    callback.onSuccess(res);
                })
                .addOnFailureListener(callback::onError);
    }

    @Override
    public void updateNote(String noteId, String title, String body, Callback<Note> callback) {
        Date date = new Date();
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_TITLE, title);
        data.put(KEY_BODY, body);
        data.put(KEY_DATE, date);

        Note note = new Note(body, title, noteId, date);
        if (note.isEmpty()){
            db.collection(COLLECTION)
                    .document(noteId)
                    .delete()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        callback.onSuccess(null);
                    })
                    .addOnFailureListener(callback::onError);
        } else {
            db.collection(COLLECTION)
                    .document(noteId)
                    .set(data)
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        callback.onSuccess(new Note(body, title, noteId, date));
                    })
                    .addOnFailureListener(callback::onError);
        }
    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {
        db.collection(COLLECTION)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    callback.onSuccess(null);
                })
                .addOnFailureListener(callback::onError);
    }

    @Override
    public void clear(Callback<Void> callback) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot doc : documents) {
                        db.collection(COLLECTION)
                                .document(doc.getId())
                                .delete();
                    }
                    callback.onSuccess(null);
                })
                .addOnFailureListener(callback::onError);
    }
}
