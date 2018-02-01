package ru.dimasokol.demo.roomcars;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import ru.dimasokol.demo.roomcars.adapter.VendorsAdapter;
import ru.dimasokol.demo.roomcars.database.CarsDatabase;
import ru.dimasokol.demo.roomcars.model.Vendor;

public class MainActivity extends AppCompatActivity {

    private VendorsAdapter mAdapter;
    private Flowable<List<Vendor>> mVendors;
    private DisposableSubscriber<List<Vendor>> mSubscriber = new DisposableSubscriber<List<Vendor>>() {
        @Override
        public void onNext(List<Vendor> vendors) {
            mAdapter.setVendors(vendors);
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVendorDialogFragment fragment = new AddVendorDialogFragment();
                fragment.show(getSupportFragmentManager(), null);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new VendorsAdapter();
        recyclerView.setAdapter(mAdapter);

        mVendors = ((CarsApplication) getApplication()).getDatabase().getDao().getVendors();
        mVendors.observeOn(AndroidSchedulers.mainThread()).subscribeWith(mSubscriber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSubscriber != null && !mSubscriber.isDisposed()) {
            mSubscriber.dispose();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_parking) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createVendor(final String vendor) {
        final CarsDatabase database = ((CarsApplication) getApplication()).getDatabase();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Vendor v = new Vendor();
                v.setBrandName(vendor);

                try {
                    long id = database.getDao().insertSingle(v);
                    android.util.Log.i("Insert", "New vendor id: " + id);
                } catch (SQLiteConstraintException alreadyExists) {
                    android.util.Log.e("Insert", "Record is already exists");
                }
            }
        }).start();
    }
}
