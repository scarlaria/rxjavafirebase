package firestore;
import java.util.HashMap;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by martinomburajr on 10/31/2017.
 */

public class FirestoreRepository {

    //region Updateable Interfaces
    public interface IUpdateWithHashMap<T> {
        Completable update(String collection, String document, HashMap<String, Object> hashMap);
    }
    public interface IUpdateWithObject<T> {
        Completable update(String collection, String document, String field, T t);
    }

    public interface ISetWithHashMap<T> {
        Completable set(String collection, String document, HashMap<String, Object> hashMap);
    }
    public interface ISetWithObject<T> {
        Completable set(String collection, String document, T t);
    }
    //endregion

    //region Creational Interfaces
    public interface ICreateWithHashMap<T> {
        Single<DocumentReference> create(String colleciton, HashMap<String, T> hashMap);
    }
    public interface ICreateWithObject<T> {
        Single<DocumentReference> create(String collections, T t);
    }
    //endregion

    //region Deletion Interfaces
    public interface IDeleteField<T> {
        /**
         * Deletes a specified field from a document, and returns a completeable
         * @param collection
         * @param document
         * @param field
         * @return
         */
        Completable deleteField(String collection, String document, String field);
    }
    public interface IDeleteDocument<T> {
        /**
         * Deletes a single document from the database;
         * @param collection
         * @param document
         * @return
         */
        Completable deleteDocument(String collection, String document);
    }
    //endregion

    //region Retrieval Interfaces
    public interface IRetrieveByDocument {
        Observable<DocumentSnapshot> retrieveByDocument(String collection, String document);
        Single<DocumentSnapshot> retrieveByDocumentOnce(String collection, String document);
    }


    public interface IRetrieveLatest {
        Single<QuerySnapshot> retrieveLatest$(String collection);
    }

    public interface IRetrieveByCollection {
        Observable<QuerySnapshot> retrieveByCollection(String collection);
        Single<QuerySnapshot> retrieveByCollectionOnce(String collection);
        Observable<QuerySnapshot> retrieveWithQuery(Query query);
        Single<QuerySnapshot> retrieveOnceWithQuery(Query query);
    }
    //endregion
}
