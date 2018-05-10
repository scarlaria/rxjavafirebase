package firestore.retrieve;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import scla.com.coresdk.repository.firestore.FirestoreRepository;

/**
 * Created by martinomburajr on 10/25/2017.
 *
 */
public class FirestoreRetrieveRepository
        implements FirestoreRepository.IRetrieveByDocument, FirestoreRepository.IRetrieveLatest, FirestoreRepository.IRetrieveByCollection {

    private FirebaseFirestore mFirebaseFirestore;

    public FirestoreRetrieveRepository(FirebaseFirestore firebaseFirestore) {
        mFirebaseFirestore = firebaseFirestore;
    }

    /**
     * Returns a single document from Firestore
     * @param collection
     * @param document
     * @return
     */
    @Override
    public Single<DocumentSnapshot> retrieveByDocumentOnce$(String collection, String document) {
        Single<DocumentSnapshot> single$ = Single.create((SingleEmitter<DocumentSnapshot> emitter) -> {
            mFirebaseFirestore.collection(collection).document(document).addSnapshotListener((DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) -> {
                if(e != null) {
                    emitter.onError(e);
                }else {
                    emitter.onSuccess(documentSnapshot);
                }
            });
        });
        return single$;
    }

    /**
     * Retrieves a document snapshot of a particular document within the Firestore database.
     * @param collection
     * @param document
     * @return
     */
    @Override
    public Observable<DocumentSnapshot> retrieveByDocument$(String collection, String document) {
        Observable<DocumentSnapshot> observable = Observable.create((ObservableEmitter<DocumentSnapshot> emitter) -> {
            mFirebaseFirestore.collection(collection).document(document)
                .addSnapshotListener((DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) -> {
                    if(e != null) {
                        emitter.onError(e);
                    }else{
                        emitter.onNext(documentSnapshot);
                    }
                });
        });
        return observable;
    }

    /**
     * Retrieves the latest item by uploaddate
     * @param collection
     * @return
     */
    @Override
    public Single<QuerySnapshot> retrieveLatest$(String collection) {
        Single<QuerySnapshot> single = Single.create((SingleEmitter<QuerySnapshot> emitter) -> {
            mFirebaseFirestore.collection(collection)
                .orderBy("uploadDate", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener((QuerySnapshot documentSnapshots, FirebaseFirestoreException e) -> {
                    if (e != null) {
                        emitter.onError(e);
                    } else {
                        emitter.onSuccess(documentSnapshots);
                    }
                });
        });
        return single;
    }

    
//    @Override
//    public Single<T> retrieveLatestAsEntity(FirebaseFirestore firebaseFirestore, String collection) {
//        Single<T> single = Single.create((SingleEmitter<T> emitter) -> {
//            firebaseFirestore.collection(collection)
//                .orderBy("uploadDate", Query.Direction.DESCENDING)
////                .limit(1)
//                .addSnapshotListener((QuerySnapshot documentSnapshots, FirebaseFirestoreException e) -> {
//                    if(e != null) {
//                        emitter.onError(e);
//                    }else{
//                        if(documentSnapshots.size() > 0) {
//                            int size = documentSnapshots.size();
//                            DocumentSnapshot documentSnapshot = documentSnapshots.getDocuments().get(size-1);
//                            T t = documentSnapshot.toObject((Class<T>) mT.getClass());
//                            String id = documentSnapshot.getId();
//                            documentSnapshot.getReference().getId();
//                            t.setKey(id);
//                            emitter.onSuccess(t);
//                        }else{
//                            emitter.onSuccess(mT);
//                        }
//                    }
//                });
//        });
//        return single;
//    }

    /**
     * Retrieves a QuerySnapshot observable that has a list of documents within it. Those documents contain a list of added items to the database.
     * @param collection
     * @return
     */
    @Override
    public Observable<QuerySnapshot> retrieveByCollection$(String collection) {
        Observable<QuerySnapshot> observable$ = Observable.create((ObservableEmitter<QuerySnapshot> emitter) -> {
            mFirebaseFirestore.collection(collection).addSnapshotListener((QuerySnapshot documentSnapshots, FirebaseFirestoreException e) -> {
                if(e != null) {
                    emitter.onError(e);
                }else {
                    emitter.onNext(documentSnapshots);
                }
            });
        });
        return observable$;
    }

    /**
     * Retrieves a collection once. The connection is immediately cut once all the initial items found in the collection are returned. Futher changes do no persist. This method cleans itself and does not need to be disposed off immediately.
     * @param collection
     * @return
     */
    public Single<QuerySnapshot> retrieveByCollectionOnce$(String collection) {
        Single<QuerySnapshot> single$ = Single.create((SingleEmitter<QuerySnapshot> emitter) -> {
            mFirebaseFirestore.collection(collection).addSnapshotListener((QuerySnapshot documentSnapshots, FirebaseFirestoreException e) -> {
                if(e != null) {
                    emitter.onError(e);
                }else {
                    emitter.onSuccess(documentSnapshots);
                }
            });
        });
        return single$;
    }

    /**
     * Passes in a Query object and retrieves a querySnapshot. NOTE: the Query object must already contain a Firestore Collection Reference. That is why you do not include a reference to firestore and the collection in this method.
     * @param query
     * @return
     */
    @Override
    public Observable<QuerySnapshot> retrieveWithQuery$(Query query) {
        Observable<QuerySnapshot> observable$ = Observable.create((ObservableEmitter<QuerySnapshot> emitter) -> {
            query.addSnapshotListener((QuerySnapshot documentSnapshots, FirebaseFirestoreException e) -> {
                if(e != null) {
                    emitter.onError(e);
                }else {
                    emitter.onNext(documentSnapshots);
                }
            });
        });
        return observable$;
    }

    /**
     * Passes in a Query object and retrieves a querySnapshot. NOTE: the Query object must already contain a Firestore Collection Reference. That is why you do not include a reference to firestore and the collection in this method. The result is retrieved only once
     * @param query
     * @return
     */
    @Override
    public Single<QuerySnapshot> retrieveOnceWithQuery$(Query query) {
        Single<QuerySnapshot> single$ = Single.create((SingleEmitter<QuerySnapshot> emitter) -> {
            query.addSnapshotListener((QuerySnapshot documentSnapshots, FirebaseFirestoreException e) -> {
                if(e != null) {
                    emitter.onError(e);
                }else {
                    emitter.onSuccess(documentSnapshots);
                }
            });
        });
        return single$;
    }
}
