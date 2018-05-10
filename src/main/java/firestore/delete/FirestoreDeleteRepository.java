package firestore.delete;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import scla.com.coresdk.repository.firestore.FirestoreRepository;

/**
 * Created by martinomburajr on 10/20/2017.
 */

public class FirestoreDeleteRepository implements FirestoreRepository.IDeleteDocument, FirestoreRepository.IDeleteField {
    private FirebaseFirestore firebaseFirestore;

    public FirestoreDeleteRepository(@NonNull FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    /**
     * Performs a delete operation on a specified document. Returns an RxJava Completeable
     * @param collection
     * @param document
     * @return
     */
    @Override
    public Completable deleteDocument(String collection, String document) {
        Completable completable$ = Completable.create((CompletableEmitter emitter) -> {
            firebaseFirestore.collection(collection).document(document)
                .delete()
                .addOnSuccessListener((Void aVoid) -> emitter.onComplete())
                .addOnFailureListener((Exception e) -> emitter.onError(e))
                .addOnCompleteListener((Task<Void> task) -> {
                    if(task.getException() != null) {
                        emitter.onError(task.getException());
                    }
                    if(task.isComplete()) { //try task.isSuccessful()
                        emitter.onComplete();
                    }
                });
        });
        return completable$;
    }

    /**
     * Deletes a specified field from a document.
     * @param collection
     * @param document
     * @param field
     * @return
     */
    @Override
    public Completable deleteField(String collection, String document, String field) {
        Completable completable$ = Completable.create((CompletableEmitter emitter) -> {
            firebaseFirestore.collection(collection).document(document)
                .update(field, FieldValue.delete())
                .addOnSuccessListener((Void aVoid) -> emitter.onComplete())
                .addOnFailureListener((Exception e) -> emitter.onError(e))
                .addOnCompleteListener((Task<Void> task) -> emitter.onComplete());
        });
        return completable$;
    }
}
