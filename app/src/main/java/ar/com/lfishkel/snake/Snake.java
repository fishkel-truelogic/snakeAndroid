package ar.com.lfishkel.snake;

import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfishkel on 20/01/15.
 */
public class Snake {

    private static final int RIGHT = 0;
    private static final int LEFT  = 1;
    private static final int UP    = 2;
    private static final int DOWN  = 3;


    private List<ImageView> body;
    private int direction;

    public Snake(ImageView head) {
        body = new ArrayList<ImageView>();
        body.add(head);
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
                this.getBody().get(0).animate().x(4).setDuration(80);
                break;
            case LEFT:
                this.getBody().get(0).animate().x(-4).setDuration(80);
                break;
            case UP:
                this.getBody().get(0).animate().y(-4).setDuration(80);
                break;
            case DOWN:
                this.getBody().get(0).animate().y(4).setDuration(80);
                break;
        }
    }

    public List<ImageView> getBody() {
        return body;
    }
}
