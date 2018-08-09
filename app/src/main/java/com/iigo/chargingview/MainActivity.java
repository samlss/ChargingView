package com.iigo.chargingview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iigo.library.ChargingView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ChargingView chargingView1;
    private ChargingView chargingView2;
    private ChargingView chargingView3;

    private Disposable updateProgressDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        chargingView1 = findViewById(R.id.cv1);
        chargingView2 = findViewById(R.id.cv2);
        chargingView3 = findViewById(R.id.cv3);

        progressUpdate(chargingView1);
        attrSetting(chargingView3);
    }

    //delay to set attr
    private void attrSetting(final ChargingView chargingView) {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        chargingView.setProgress(95);
                        chargingView.setBgColor(Color.parseColor("#aaaaaa"));
                        chargingView.setChargingColor(Color.YELLOW);
                        chargingView.setTextColor(Color.RED);
                        chargingView.setTextSize(25);
                    }
                });
    }

    private void progressUpdate(final ChargingView chargingView){
        updateProgressDisposable = Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        int progress = (chargingView.getProgress() + 1) % 100;
                        chargingView.setProgress(progress);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (updateProgressDisposable != null){
            updateProgressDisposable.dispose();
        }
    }
}
