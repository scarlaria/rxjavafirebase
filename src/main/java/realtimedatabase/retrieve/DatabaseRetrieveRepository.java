package realtimedatabase.retrieve;

import com.google.firebase.database.*;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import realtimedatabase.DatabaseRepository;

public class DatabaseRetrieveRepository<T>
        implements DatabaseRepository.IRetrieve.Observable<T>,
        DatabaseRepository.IRetrieve.Single<T>,
        DatabaseRepository.IRetrieve.Flowable<T>{

    @Override
    public Single<T> retrieveOnce(DatabaseReference databaseReference, Query query) {
        return Single.create((SingleEmitter<T> emitter) -> {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        T value = snapshot.getValue();
                        emitter.onSuccess(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        emitter.onError(error.toException());
                    }
                });
            });
    }

    @Override
    public Observable<T> retrieve(DatabaseReference databaseReference, Query query) {
        return null;
    }

    @Override
    public Flowable<T> retrieveFlowable(DatabaseReference databaseReference, Query query) {
        return null;
    }
}
