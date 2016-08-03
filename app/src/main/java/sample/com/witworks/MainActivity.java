package sample.com.witworks;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    private ObservableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ObservableListView) findViewById(R.id.listView);
        mListView.setScrollViewCallbacks(this);

        String[] values = {"Music", "Fitness", "Store", "Apps", "Settings", "Music",
                "Fitness", "Store", "Apps", "Settings", "Music", "Fitness", "Store", "Apps", "Settings", "Music",
                "Fitness", "Store", "Apps", "Settings"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
//        Log.d("Logger",scrollY+"");
        int count = mListView.getChildCount();
        int midCount = count/2;
        if(count % 2 == 0){
            midCount--;
        }

        for(int i=0;i<count; i++){

            View c = mListView.getChildAt(i);

            float height = c.getHeight();
            float offset = (scrollY % height)/height;
            Log.d("Logger"+i, offset+"");
            float stepOffset = midCount - (i);

            float scale = (1 - (1+Math.abs(stepOffset))/count);
            float translation = 0f;
            float textSize = 32 * scale;

            if(stepOffset >= 0){
                textSize = textSize - offset*3;
            }else{
                textSize = textSize + offset*3;
            }

            stepOffset = Math.abs(stepOffset);

            TextView textView = (TextView) c.findViewById(android.R.id.text1);
            textView.setTextSize(textSize);

            if(i == midCount){
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else{
                textView.setTextColor(Color.BLACK);
            }
            if((midCount %2 !=0 && stepOffset == midCount-1) || stepOffset == midCount){
                translation = 0;
            } else {
                translation = 100 - (100/midCount)*stepOffset;
            }
            textView.setTranslationX(translation);

        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
