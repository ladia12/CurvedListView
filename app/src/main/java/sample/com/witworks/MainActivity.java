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
        int count = mListView.getChildCount();

        int heightList = mListView.getHeight();
        int midPoint = heightList/2;

        for(int i=0;i<count; i++){

            View c = mListView.getChildAt(i);
            float posC = c.getY();

            float maxTextSize = 32f;
            float minTextSize = 15f;

            float height = c.getHeight();
            float diffFromMid = Math.abs(midPoint - posC);
            float textSize = maxTextSize - diffFromMid/(midPoint+height-1)*(maxTextSize - minTextSize);
            TextView textView = (TextView) c.findViewById(android.R.id.text1);
            textView.setTextSize(textSize);

            float maxTranslation = 150f;
            float minTranslation = 0f;
            float translation = maxTranslation - diffFromMid/(midPoint+height-1)*(maxTranslation - minTranslation);
            textView.setTranslationX(translation);

            float minRange = (heightList - height)/2;
            float maxRange = (heightList + height)/2;
            if(posC >= minRange && posC <= maxRange){
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else{
                textView.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
