package ru.dimasokol.demo.roomcars;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import ru.dimasokol.demo.roomcars.adapter.ModelsAdapter;
import ru.dimasokol.demo.roomcars.database.CarsDatabase;
import ru.dimasokol.demo.roomcars.model.Model;
import ru.dimasokol.demo.roomcars.model.Vendor;

public class ModelsActivity extends AppCompatActivity {

    public static final String ARG_VENDOR_ID = "vendor_id";

    private long mVendorId;
    private ModelsAdapter mAdapter;

    private DisposableSubscriber<Vendor> mVendorSubscriber = new DisposableSubscriber<Vendor>() {
        @Override
        public void onNext(Vendor vendor) {
            setTitle(vendor.getBrandName());
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };

    private DisposableSubscriber<List<Model>> mModelsSubscriber = new DisposableSubscriber<List<Model>>() {
        @Override
        public void onNext(List<Model> models) {
            mAdapter.setModels(models);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onComplete() {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().hasExtra(ARG_VENDOR_ID)) {
            finish();
            return;
        }

        mVendorId = getIntent().getLongExtra(ARG_VENDOR_ID, 0);

        setContentView(R.layout.activity_models);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddModelDialogFragment.newInstance(getTitle().toString())
                        .show(getSupportFragmentManager(), null);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        mAdapter = new ModelsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        CarsDatabase database = ((CarsApplication) getApplication()).getDatabase();

        Flowable<Vendor> vendorFlowable = database.getDao().getVendorById(mVendorId);
        vendorFlowable.observeOn(AndroidSchedulers.mainThread()).subscribeWith(mVendorSubscriber);

        Flowable<List<Model>> modelsFlowable = database.getDao().getModelsByVendorId(mVendorId);
        modelsFlowable.observeOn(AndroidSchedulers.mainThread()).subscribeWith(mModelsSubscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVendorSubscriber != null && !mVendorSubscriber.isDisposed()) {
            mVendorSubscriber.dispose();
        }
    }

    public void createModel(final String modelName) {

        final CarsDatabase database = ((CarsApplication) getApplication()).getDatabase();
        final long vendorId = mVendorId;

        new Thread(new Runnable() {
            @Override
            public void run() {
                database.getDao().insert(new Model(vendorId, modelName));
            }
        }).start();
    }
}
