package com.example.guru.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import net.simplifiedcoding.recyclerviewexample.CustomAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main3Activity extends AppCompatActivity {

    ProgressBar pg;

    RecyclerView recyclerView;
    ArrayList<RetroPhoto> photoList2;
    private static final String TAG = "RxAndroidSamples";
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        pg=(ProgressBar)findViewById(R.id.pgku);
        disposables.add(sampleObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override public void onNext(String string) {
                        Log.d(TAG, "onNext(" + string + ")");
                    }
                }));
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    Observable<String> sampleObservable() {

        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation

                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<List<RetroPhoto>> call = service.getAllPhotos();
                call.enqueue(new Callback<List<RetroPhoto>>() {
                    @Override
                    public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                        generateDataList((ArrayList<RetroPhoto>) response.body());
                        pg.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {

                    }
                });


                return Observable.just("");
            }
        });
    }

    private void generateDataList(ArrayList<RetroPhoto> photoList) {
        this.photoList2=photoList;
        Log.d(TAG, "lebar data: "+photoList.size()+"");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_film);
        CustomAdapter adapter = new CustomAdapter(photoList2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Main3Activity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
