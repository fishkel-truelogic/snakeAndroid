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


    private int direction;


    //ssads
    private Context mContext;
    int x = -1;
    int y = -1;
    private Handler h;
    private final int FRAME_RATE = 30;
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
        switch (direction) {
            case RIGHT:
                direction = DOWN;
                break;
            case LEFT:
                direction = UP;
                break;
            case UP:
                direction = RIGHT;
                break;
            case DOWN:
                direction = LEFT;
                break;
        }

    }

    public void turnLeft() {
        switch (direction) {
            case RIGHT:
                direction = UP;
                break;
            case LEFT:
                direction = DOWN;
                break;
            case UP:
                direction = LEFT;
                break;
            case DOWN:
                direction = RIGHT;
                break;
        }
    }

    public void move() {
        switch (direction) {
            case RIGHT:
                break;
            case LEFT:
                break;
            case UP:
                break;
            case DOWN:
                break;
        }
    }



    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    protected void onDraw(Canvas c) {
        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.snake_pixel);
        moveNext(x, y);
        switch (direction) {
            case RIGHT:
                x += 4;
                break;
            case LEFT:
                x -= 4;
                break;
            case UP:
                y -= 4;
                break;
            case DOWN:
                y += 4;
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
        if (appleX <= x + 4 && appleX >= x - 4 && appleY <= y + 4 && appleY >= y - 4) {
            activity.addTail();
            activity.randomApple();
        }

        c.drawBitmap(ball.getBitmap(), x, y, null);
        h.postDelayed(r, FRAME_RATE);

    }

    private void moveNext(int xOld, int yOld) {
        next.setXX(xOld);
        next.setYY(yOld);
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
