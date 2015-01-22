package ar.com.lfishkel.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lfishkel on 21/01/15.
 */
public class Tail extends ImageView {

    private Tail next;

    private static final int RIGHT = 0;
    private static final int LEFT  = 1;
    private static final int UP    = 2;
    private static final int DOWN  = 3;
    private static final int FRAME_RATE = 50;

    private Context mContext;

    private int xOld;
    private int yOld;
    private int x = -1;
    private int y = -1;
    private int direction;

    private Handler h;


    public Tail(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        h = new Handler();
    }

    public Tail(Context context, int x, int y, int direction) {
        super(context);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.h = new Handler();
        this.mContext = context;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
           invalidate();
        }
    };


    protected void onDraw(Canvas c) {
        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.snake_skin);
        moveNext(xOld, yOld);

        c.drawBitmap(ball.getBitmap(), x, y, null);
        h.postDelayed(r, FRAME_RATE);

    }

    private void moveNext(int xOld, int yOld) {
        if (next != null) {
            next.setXX(xOld);
            next.setYY(yOld);
        }

    }

    public int getXX() {
        return x;
    }

    public void setXX(int x) {
        this.xOld = this.x;
        this.x = x;
    }

    public int getYY() {
        return y;
    }

    public void setYY(int y) {
        this.yOld = this.y;
        this.y = y;
    }

    public void setNext(Tail next) {
        this.next = next;
    }

    public Tail getNext() {
        return next;
    }

    public int getxOld() {
        return xOld;
    }

    public void setxOld(int xOld) {
        this.xOld = xOld;
    }

    public int getyOld() {
        return yOld;
    }

    public void setyOld(int yOld) {
        this.yOld = yOld;
    }
}
