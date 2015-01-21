package ar.com.lfishkel.snake;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SnakeActivity extends Activity {

    private static final long DELAY = 80;

    private Snake snake;
    private MySensorListener mySensorListener;
    private SensorManager mSensorManager;
    private boolean canTurn = true;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        mySensorListener = new MySensorListener(this);
        mSensorManager = (SensorManager) getSystemService(this.getApplicationContext().SENSOR_SERVICE);
        mSensorManager.registerListener(mySensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        timer = new Timer();
        timer.schedule(new MovingTask(this), DELAY);
        snake = new Snake((ImageView)findViewById(R.id.imageView));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private final class MySensorListener implements SensorEventListener {

       private SnakeActivity activity;

       public MySensorListener(SnakeActivity activity) {
           this.activity = activity;
       }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[1] > 3) {
                if (canTurn) {
                    activity.getSnake().turnRight();
                    canTurn = false;
                }
            } else if (event.values[1] < -3) {
                if (canTurn) {
                    activity.getSnake().turnLeft();
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

    private final class MovingTask extends TimerTask {

        private SnakeActivity activity;

        public MovingTask(SnakeActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            activity.getSnake().move();
        }
    }

    public Snake getSnake() {
        return snake;
    }

}
