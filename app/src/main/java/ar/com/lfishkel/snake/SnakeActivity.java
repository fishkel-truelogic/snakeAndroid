package ar.com.lfishkel.snake;

import android.app.ActionBar;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeActivity extends Activity {

    private Head head;
    private List<Tail> body;
    private MySensorListener mySensorListener;
    private SensorManager mSensorManager;
    private boolean canTurn = true;
    private FrameLayout fl;
    private ImageView apple;
    private ImageView star;
    private int height;
    private int width;

    private int points;
    private TextView tv;
    MyCount counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_snake);
        mySensorListener = new MySensorListener(this);
        mSensorManager = (SensorManager) getSystemService(this.getApplicationContext().SENSOR_SERVICE);
        mSensorManager.registerListener(mySensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        fl = (FrameLayout) findViewById(R.id.activity_snake);
        tv = (TextView) findViewById(R.id.chrono);
        counter = new MyCount(50000,1000);
        counter.start();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        initSnake();
        initApple();

    }

    private void initApple() {
        apple = (ImageView) findViewById(R.id.apple);
        star = (ImageView) findViewById(R.id.star);
        randomApple();

    }


    private void initSnake() {
        head = (Head) findViewById(R.id.head);
        head.setActivity(this);
        Tail tail = (Tail) findViewById(R.id.tail);
        head.setNext(tail);
        body = new ArrayList<Tail>();
        body.add(tail);
        addTail();
        addTail();
        addTail();
        addTail();
    }



    public int getRandom(int from, int to) {
        if (from < to)
            return from + new Random().nextInt(Math.abs(to - from));
        return from - new Random().nextInt(Math.abs(to - from));
    }

    public void addTail() {
        Tail tail = new Tail(getApplicationContext(), null);
        tail.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        body.get(body.size() -1).setNext(tail);
        body.add(tail);
        fl.addView(tail);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public void randomApple() {
        star.setVisibility(View.VISIBLE);
        star.setX(apple.getX());
        star.setY(apple.getY());
        apple.setX(getRandom(50, width -50));
        apple.setY(getRandom(50, height - 50));
        head.setAppleX(apple.getX());
        head.setAppleY(apple.getY());
    }

    public void addTime() {
        counter.cancel();
        int timeLeft = Integer.parseInt(tv.getText().toString().split(": ")[1]);
        counter = new MyCount(timeLeft * 1000 + 10000, 1000);
        counter.start();
    }

    private final class MySensorListener implements SensorEventListener {



        private static final int TURN_RIGHT = 4;
        private static final int TURN_LEFT = 5;
        private static final int TURN_CENTER_UP = 6;
        private static final int TURN_CENTER_DOWN = 6;

       private SnakeActivity activity;

       public MySensorListener(SnakeActivity activity) {
           this.activity = activity;
       }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[1] > 4.5) {
                if (canTurn) {
                    activity.getHead().turnRight();
                    canTurn = false;
                }
            } else if (event.values[1] < -4.5) {
                if (canTurn) {
                    activity.getHead().turnLeft();
                    canTurn = false;
                }
            } else if (event.values[1] > -3 && event.values[1] < 3) {
                if (!canTurn) {
                    if (event.values[0] > 0) {
                        activity.getHead().turnUp();
                    } else {
                        activity.getHead().turnDown();
                    }
                    canTurn = true;
                }
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            tv.setText("done!");
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tv.setText("Left: " + millisUntilFinished / 1000);
        }
    }

    public Head getHead() {
        return head;
    }


}
