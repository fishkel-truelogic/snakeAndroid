package ar.com.lfishkel.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lfishkel on 20/01/15.
 */
public class Head extends ImageView {

    private static final int RIGHT = 0;
    private static final int LEFT  = 1;
    private static final int UP    = 2;
    private static final int DOWN  = 3;
    private static final int SIZE = 10 ;
    private static final int FRAME_RATE = 50;


    private int direction = UP;


    private Context mContext;
    int x = -1;
    int y = -1;
    private Handler h;
    private Tail next;
    private SnakeActivity activity;
    private float appleX;
    private float appleY;


    public Head(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        h = new Handler();
    }

    public void turnRight() {
        direction = LEFT;
    }

    public void turnLeft() {
        direction = RIGHT;
    }

    public void turnUp() {
        direction = UP;
    }
    public void turnDown() {
        direction = DOWN;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    protected void onDraw(Canvas c) {
        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.snake_skin);
        moveNext(x, y);
        switch (direction) {
            case RIGHT:
                x += SIZE;
                break;
            case LEFT:
                x -= SIZE;
                break;
            case UP:
                y -= SIZE;
                break;
            case DOWN:
                y += SIZE;
                break;
        }
        if ((x > this.getWidth() - ball.getBitmap().getWidth())) {
            x = 0;
        } else if((x < 0)) {
            x = this.getWidth() - ball.getBitmap().getWidth();
        }

        if ((y > this.getHeight() - ball.getBitmap().getHeight())) {
            y = 0;
        } else if (y < 0) {
            y = this.getHeight() - ball.getBitmap().getHeight();
        }
        if (appleX <= x + 20 && appleX >= x - 40 && appleY <= y + 30 && appleY >= y - 30) {
            activity.addTail();
            activity.randomApple();
            activity.addTime();
        }

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
        this.x = x;
    }

    public int getYY() {
        return y;
    }

    public void setYY(int y) {
        this.y = y;
    }

    public void setNext(Tail next) {
        this.next = next;
    }

    public void setAppleX(float appleX) {
        this.appleX = appleX;
    }

    public float getAppleX() {
        return appleX;
    }

    public void setAppleY(float appleY) {
        this.appleY = appleY;
    }

    public float getAppleY() {
        return appleY;
    }

    public void setActivity(SnakeActivity activity) {
        this.activity = activity;
    }

    public SnakeActivity getActivity() {
        return activity;
    }


}
