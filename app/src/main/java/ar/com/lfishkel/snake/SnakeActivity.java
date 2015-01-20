package ar.com.lfishkel.snake;

import ar.com.lfishkel.snake.util.SystemUiHider;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class SnakeActivity extends Activity {

    private MySensorListener mySensorListener;
    private SensorManager mSensorManager;
    boolean canTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        mySensorListener = new MySensorListener(this);
        mSensorManager = (SensorManager) getSystemService(this.getApplicationContext().SENSOR_SERVICE);
        mSensorManager.registerListener(mySensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
    private final class MySensorListener implements SensorEventListener {

       private Activity activity;

       public MySensorListener(Activity ac) {
           this.activity = ac;
       }

        @Override
        public void onSensorChanged(SensorEvent event) {
      /*  if (event.values[Sensor.TYPE_ACCELEROMETER] > 3) {
            //se mueve a la derecha
        } else if (event.values[Sensor.TYPE_ACCELEROMETER] < -3) {
            //se mueve a la derecha
        }*/


            View y = activity.findViewById(R.id.y);
            if (event.values[1] > 3) {
                ((TextView) y).setText("DERECHA");
                if (canTurn) {
                    //dobla a la derecha
                    canTurn = false;
                }

            } else if (event.values[1] < -3) {
                ((TextView) y).setText("IZQUIERDA");
                if (canTurn) {
                    //dobbla a la izq
                    canTurn = false;
                }
            } else {
                ((TextView) y).setText("MEDIO");
                canTurn = true;
            }



        }



        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

/*
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

        }
    };

    */

}
