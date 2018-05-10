package firestore.update;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import scla.com.coresdk.entity.AbstractEntity;
import scla.com.coresdk.repository.firestore.FirestoreRepository;

/**
 * Created by martinomburajr on 10/20/2017.
 */

public class FirestoreUpdateRepository <T extends AbstractEntity> implements FirestoreRepository.IUpdateWithHashMap<T>, FirestoreRepository.IUpdateWithObject, FirestoreRepository.ISetWithHashMap<T>, FirestoreRepository.ISetWithObject<T> {

    private FirebaseFirestore mFirebaseFirestore;

    public FirestoreUpdateRepository(FirebaseFirestore firebaseFirestore) {
        mFirebaseFirestore = firebaseFirestore;
    }

    @Override
    public Completable update(String collection, String document, HashMap<String, Object> hashMap) {
        return Completable.create((CompletableEmitter emitter) -> {
            mFirebaseFirestore.collection(collection)
                .document(document)
                .update(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onFailure(e);
                    }
                });
        });
    }

    @Override
    public Completable update(String collection, String document, String field, Object t) {
        return Completable.create((CompletableEmitter emitter) -> {
            mFirebaseFirestore.collection(collection)
                .document(document)
                .update(field,"")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onFailure(e);
                    }
                });
        });
    }

    @Override
    public Completable set(String collection, String document, HashMap<String, Object> hashMap) {
        return Completable.create((CompletableEmitter emitter) -> {
            mFirebaseFirestore.collection(collection)
                .document(document)
                .set(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onFailure(e);
                    }
                });
        });
    }

    @Override
    public Completable set(String collection, String document, T t) {
        return Completable.create((CompletableEmitter emitter) -> {
            mFirebaseFirestore.collection(collection)
                .document(document)
                .set(t)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onFailure(e);
                    }
                });
        });
    }
}
