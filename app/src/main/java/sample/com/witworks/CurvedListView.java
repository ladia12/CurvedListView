package sample.com.witworks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by ladia on 30/07/16.
 */
public class CurvedListView extends ListView {
    public interface ListViewObserver {
        void onScroll(CurvedListView listView, float deltaY);
    }

    private ListViewObserver mObserver;
    private View mTrackedChild;
    private int mTrackedChildPrevPosition;
    private int mTrackedChildPrevTop;

    public CurvedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mTrackedChild == null) {
            if (getChildCount() > 0) {
                mTrackedChild = getChildInTheMiddle();
                mTrackedChildPrevTop = mTrackedChild.getTop();
                mTrackedChildPrevPosition = getPositionForView(mTrackedChild);
            }
        } else {
            boolean childIsSafeToTrack = mTrackedChild.getParent() == this && getPositionForView(mTrackedChild) == mTrackedChildPrevPosition;
            if (childIsSafeToTrack) {
                int top = mTrackedChild.getTop();
                if (mObserver != null) {
                    float deltaY = top - mTrackedChildPrevTop;
                    mObserver.onScroll(this, deltaY);
                }
                mTrackedChildPrevTop = top;
            } else {
                mTrackedChild = null;
            }
        }
    }

    private View getChildInTheMiddle() {
        return getChildAt(getChildCount() / 2);
    }

    public void setObserver(ListViewObserver observer) {
        mObserver = observer;
    }


}
