package realtimedatabase;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;
import io.reactivex.Completable;

import java.util.HashMap;


public final class DatabaseRepository {
    public interface ICreate<T> {
        interface Maybe<T> {
            io.reactivex.Maybe<String> create(T object);
            io.reactivex.Maybe<String> create(HashMap<String, T> hashMap);
        }

        interface Completable<T> {
            io.reactivex.Completable create(T t);
            io.reactivex.Completable create(HashMap<String, T> hashMap);
        }
    }
    public interface IRetrieve<T> {
        interface Single<T> {
            io.reactivex.Single<T> retrieveOnce(DatabaseReference databaseReference, @Nullable Query query);
        }
        interface Observable<T> {
            io.reactivex.Observable<T> retrieve(DatabaseReference databaseReference, @Nullable Query query);
        }
        interface Flowable<T> {
            io.reactivex.Flowable<T> retrieveFlowable(DatabaseReference databaseReference, @Nullable Query query);
        }
    }

    public interface IUpdate<T> {
        interface Maybe<T> {
            io.reactivex.Maybe<T> update(DatabaseReference databaseReference, HashMap<String, T> hashMap);
        }
        interface Completable<T> {
            io.reactivex.Completable update(DatabaseReference databaseReference, HashMap<String, T> hashMap);
        }
    }

    public interface ISet<T> {
        interface Maybe<T> {
            io.reactivex.Maybe<T> set(DatabaseReference databaseReference, T t);
        }
        interface Completable<T> {
            io.reactivex.Completable set(DatabaseReference databaseReference, T t);
        }
    }

    public interface IDelete {
        interface Maybe {
            io.reactivex.Maybe delete(DatabaseReference databaseReference);
        }
        interface Completable {
            Completable delete(DatabaseReference databaseReference);
        }
    }

//    void  v() {
//        DatabaseReference databaseReference;
//        //databaseReference.
//    }
}
