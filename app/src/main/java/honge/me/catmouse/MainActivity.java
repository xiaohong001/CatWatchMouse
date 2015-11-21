package honge.me.catmouse;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import honge.me.library.CatProgress;

public class MainActivity extends AppCompatActivity {

    private honge.me.library.CatProgress cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.cat = (CatProgress) findViewById(R.id.cat);

//        Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                cat.setVisibility(View.VISIBLE);
//            }
//        }, 9000);
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                cat.setVisibility(View.GONE);
//            }
//        },20000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
