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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CurvedListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (CurvedListView) findViewById(R.id.listView);

        String[] values = {"Music", "Fitness", "Store", "Apps", "Settings","Music",
                "Fitness", "Store", "Apps", "Settings", "Music", "Fitness", "Store", "Apps", "Settings","Music",
                "Fitness", "Store", "Apps", "Settings"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mListView.setAdapter(adapter);


        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                Log.d("Position", "first"+firstVisibleItem+" visibleitemcount"+visibleItemCount+" total"+totalItemCount);
                int count = absListView.getChildCount();
                Log.d("ChildCount" , ""+ count);
                int midCount = count/2;
                if(count % 2 == 0){
                    midCount--;
                }

                for(int i=0;i<count; i++){
                    float offset = Math.abs(midCount - (i));
                    Log.d("Offset", offset+"");
                    float scale = (1 - (1+offset)/count);
                    float translation = 0f;
                    float textSize = 32 * scale;
                    View c = absListView.getChildAt(i);
                    TextView textView = (TextView) c.findViewById(android.R.id.text1);
                    textView.setTextSize(textSize);

                    if(i == midCount){
                        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else{
                        textView.setTextColor(Color.BLACK);
                    }
                    if((midCount %2 !=0 && offset == midCount-1) || offset == midCount){
                        translation = 0;
                    } else {
                        translation = 100 - (100/midCount)*offset;
                    }
                    textView.setTranslationX(translation);

                }

            }
        });

    }
}
