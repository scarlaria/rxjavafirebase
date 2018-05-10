package firestore.create;

import java.util.HashMap;

import com.google.cloud.firestore.DocumentReference;
import com.google.firebase.tasks.OnFailureListener;
import com.google.firebase.tasks.OnSuccessListener;
import firestore.FirestoreRepository;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.annotations.NonNull;

/**
 * Created by martinomburajr on 10/20/2017.
 *
 * @param <T> the type parameter
 */
public class FirestoreCreateRepository implements FirestoreRepository.ICreateWithHashMap<T>, FirestoreRepository.ICreateWithObject<T> {

    private FirebaseFirestore mFirebaseFirestore;

    public FirestoreCreateRepository(FirebaseFirestore firebaseFirestore) {
        mFirebaseFirestore = firebaseFirestore;
    }

    /**
     * Creates a document in a particular collection. The document is based on the hashMap
     * @param collection
     * @param hashMap
     * @return
     */
    @Override
    public Single<DocumentReference> create(String collection, HashMap<String, T> hashMap) {
        return Single.create((SingleEmitter<DocumentReference> emitter) -> {
            mFirebaseFirestore.collection(collection).add(hashMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        emitter.onSuccess(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
        });
    }


    /**
     * Creates an document in a particular collection. The document is based on an object.
     * @param collection
     * @param t
     * @return
     */
    @Override
    public Single<DocumentReference> create(String collection, T t) {
        return Single.create((SingleEmitter<DocumentReference> emitter) -> {
            mFirebaseFirestore.collection(collection).add(t)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        emitter.onSuccess(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
        });
    }
}
