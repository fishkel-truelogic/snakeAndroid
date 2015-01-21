package ar.com.lfishkel.snake;

import android.app.ActionBar;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeActivity extends Activity {

    private static final long DELAY = 80;

    private Head head;
    private List<Tail> body;
    private MySensorListener mySensorListener;
    private SensorManager mSensorManager;
    private boolean canTurn = true;
    private FrameLayout fl;
    private ImageView apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        mySensorListener = new MySensorListener(this);
        mSensorManager = (SensorManager) getSystemService(this.getApplicationContext().SENSOR_SERVICE);
        mSensorManager.registerListener(mySensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        fl = (FrameLayout) findViewById(R.id.activity_snake);

        initSnake();
        initApple();

    }

    private void initApple() {
        apple = (ImageView) findViewById(R.id.apple);
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
        apple.setX(getRandom(0, 200));
        apple.setY(getRandom(0, 200));
        head.setAppleX(apple.getX());
        head.setAppleY(apple.getY());
    }

    private final class MySensorListener implements SensorEventListener {

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
            } else {
                canTurn = true;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

    public Head getHead() {
        return head;
    }


}
