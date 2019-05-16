package com.example.android.colorballs;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class BallsActivity extends ActionBarActivity {

    RandomBallsView randomBalls;
    protected int level = 0;
    ViewGroup rootLayout;
    ImageView ball;
    int Delta_X;
    int Delta_Y;
    int ballColor;
    int score;
    TextView scoreShow;
    TextView levelShow;

    boolean paused = false;
    boolean stop = false;

    Random rnd = new Random();

    protected int randColor() {
        int a = rnd.nextInt(3);
        return a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balls);

/*        MediaPlayer ring= MediaPlayer.create(BallsActivity.this,R.raw.ring);
        ring.start();*/

        randomBalls = new RandomBallsView(this, 10);
        randomBalls.setOnTouchListener(new ChoiceTouchListener());

        FrameLayout container = (FrameLayout) findViewById(R.id.container);
        container.addView(randomBalls);

        scoreShow = (TextView) findViewById(R.id.score);
        levelShow = (TextView) findViewById(R.id.level);

        score = 0;
        level = 1;

/*        rootLayout = (ViewGroup) findViewById(R.id.root_view);
        ball = (ImageView) rootLayout.findViewById(R.id.ball);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(80, 80);
        ball.setLayoutParams(layoutParams);
        ball.setOnTouchListener(new ChoiceTouchListener());

        ballColor = randColor();
        if(ballColor == 0){
            ball.setBackgroundResource(R.drawable.redball);
        }
        else if(ballColor == 1){
            ball.setBackgroundResource(R.drawable.greenball);
        }
        else if(ballColor == 2){
            ball.setBackgroundResource(R.drawable.blueball);
        }*/


    }

    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

/*            final int x = (int) event.getRawX();
            final int y = (int) event.getRawY();

            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    Delta_X = x - lParams.leftMargin;
                    Delta_Y = y - lParams.rightMargin;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    layoutParams.leftMargin = x - Delta_X;
                    layoutParams.topMargin = y - Delta_Y;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    v.setLayoutParams(layoutParams);
                    break;
            }
            root.invalidate;
            return true;
            */
            final int action = MotionEventCompat.getActionMasked(event);

            switch (action) {
                case MotionEvent.ACTION_DOWN: {

                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    randomBalls.myBall.x = (int) event.getX();
                    randomBalls.myBall.y = (int) event.getY();
                    break;
                }
            }
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        randomBalls.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        randomBalls.stop();
    }

    @Override
    public void onStop() {
        super.onStop();

        randomBalls.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_balls, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Class RandomBallsView
    public class RandomBallsView extends View {
        protected Timer timer;
        protected RandomBall[] balls;
        protected PlayerBall myBall;
        protected RandomBallsView self;
        protected boolean isStarted = false;
        int n;
        int goldTime = 0;
        boolean golden = false;
        GoldBall gb;
        int second = 0;
        int twentySeconds = 0;


        public RandomBallsView(Context ctx, int n) {
            super(ctx);
            self = this;
            balls = new RandomBall[n];
            myBall = new PlayerBall();
            self.n = n;


            for (int i = 0; i < n; i++) {
                balls[i] = new RandomBall();
            }
        }

        public void start() {
            if (!isStarted) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        if(true/*!paused*/) {
                            second += 10;
                            twentySeconds += 10;
                            if(goldTime > 0){
                                goldTime -= 10;
                            }
                            self.postInvalidate();
                        }
                    }
                }, 1000, 10);

                isStarted = true;
            }
        }

        public void stop() {
            if (isStarted && timer != null) {
                timer.cancel();
                timer = null;
            }
            isStarted = false;
        }

        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);

            for (RandomBall ball : balls) {
                ball.draw(c);

            }

            myBall.draw(c);

            Random rnd = new Random();

            if(second >= 1000)
            {
                second = 0;
                score++;
            }

            if(twentySeconds > 1000){
                if(rnd.nextInt(2) == 0)//33% probable every 10secs
                {
                    gb = new GoldBall();
                    golden = true;
                }
                twentySeconds = 0;
            }

            if(golden)
            {
                gb.draw(c);
                if(gb.timer <= 0)
                {
                    gb = null;
                    golden = false;
                }
            }

            scoreShow.setText("Score: " + score);
            levelShow.setText("Level: " + level);


        }


        // Class for RandomBall objetcs
        class RandomBall {
            // normalized position and velocity variables
            protected float x, y, rad, dx, dy, drad;

            // color and color change rate variables
            protected float a, r, g, b, da, dr, dg, db;

            private Paint paint = new Paint();

            public RandomBall() {
                x = randUniform(0, 1);
                y = randUniform(0, 1);
                rad = 0.1f;

                dx = randUniform(-0.002f , 0.002f);
                dy = randUniform(-0.002f , 0.002f);


                drad = 0;

                a = 255;

                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int rand = randColor();
                        if (rand == 0) {
                            r = 200;
                            g = 0;
                            b = 0;
                        } else if (rand == 1) {
                            r = 0;
                            g = 200;
                            b = 0;
                        } else if (rand == 2) {
                            r = 0;
                            g = 0;
                            b = 200;
                        }
                        paint.setColor(Color.argb((int) (a), (int) (r), (int) (g), (int) (b)));
                    }
                }, 0, 10000);


/*                a = randUniform(0, 1);
                r = randUniform(0, 1);
                g = randUniform(0, 1);
                b = randUniform(0, 1);

                a = 10;
                r = 0;
                g = 0;
                b = 0;


                da = randUniform(-0.01f, 0.01f);
                dr = randUniform(-0.01f, 0.01f);
                dg = randUniform(-0.01f, 0.01f);
                db = randUniform(-0.01f, 0.01f);*/
            }

            // prepare a uniform random generator
            protected Random rnd = new Random();

            protected float randUniform(float start, float end) {
                float f = start + ((float) rnd.nextInt(100000) / 100000) * (end - start);
                return f;
            }

            // function for clipping values to be in range [0,1]
            protected float checkRange(float f) {
                return (f < 0) ? 0 : ((f > 1) ? 1 : f);
            }


            public void update(Canvas canvas) {

                /*
                if(randUniform(0,1) < 0.1){
                    dx = randUniform(-0.02f, 0.02f);
                    dy = randUniform(-0.02f, 0.02f);
                }
                if(randUniform(0,1) < 0.1) {
                    drad = randUniform(-0.01f,0.01f);
                }

                if(randUniform(0,1) < 0.1) {
                    da = randUniform(-0.01f,0.01f);
                    dr = randUniform(-0.01f,0.01f);
                    dg = randUniform(-0.01f,0.01f);
                    db = randUniform(-0.01f,0.01f);
                }*/
                checkBoundaryCollision(canvas);


                for (RandomBall ball : balls) {
                    if (this != ball) {
                        //checkCollision(ball, canvas);
                    }

                }
                x = checkRange(x + dx * level);
                y = checkRange(y + dy * level);

                //rad = checkRange(rad + drad);

                //a = checkRange(a + da);
                //r = checkRange(r + dr);
                //  g = checkRange(g + dg);
                // b = checkRange(b + db);
            }


/*            public boolean win( Canvas canvas) {

*//*                float radius = ball.getHeight() / 2;
                float xc = ball.getX();;
                float yc = ball.getY();*//*

                int W = canvas.getWidth();
                int H = canvas.getHeight();
                int R = (W < H) ? W : H;

                float radius = (ball.getHeight() / 2) * R;
                float xc = ball.getX() * W;
                float yc = ball.getY() * H;

                float radius2 = rad * R;
                float xc2 = x;
                float yc2 = y;


*//*                if (xc + radius + radius2 > xc2
                        && xc < xc2 + radius + radius2
                        && yc + radius + radius2 > yc2
                        && yc < yc2 + radius + radius2){*//*
                double distance = Math.sqrt(((xc - xc2) * (xc - xc2)) + ((yc - yc2) * (yc - yc2)));

                if (distance < 0.15f * R) {
                    if (ballColor == 0 && r == 200 || ballColor == 1 && g == 200 || ballColor == 2 && b == 200) {
                        balls.remove(this);
                        balls.add(new RandomBall());
                        System.out.println("hiiiiiiii");
                        return true;
                    } else
                        System.out.println("byeeee");
                        return false;
                }
                return true;
            }*/


            void checkBoundaryCollision(Canvas canvas) {

                if (x <= 0 || x >= 1)
                    dx *= -1;
                if (y <= 0 || y >= 1)
                    dy *= -1;

/*                int W = canvas.getWidth();
                int H = canvas.getHeight();
                int R = (W < H) ? W : H;

                float radius = rad * R;
                float xc = x * W;
                float yc = y * H;


                if (xc  >= W -radius) {
                    x = (W-radius) / W;
                    dx *= -1;
                    x = checkRange(x + dx);
                } else if (xc  <= radius) {
                    x = radius / W;
                    dx *= -1;
                    x = checkRange(x + dx);
                } else if (yc  >= H-radius) {
                    y = (H-radius) / H;
                    dy *= -1;
                    y = checkRange(y + dy);
                } else if (yc <= radius) {
                    y = radius / H;
                    dy *= -1;
                    y = checkRange(y + dy);
                }*/
            }

            public void checkCollision(RandomBall secondBall, Canvas canvas) {

                int W = canvas.getWidth();
                int H = canvas.getHeight();
                int R = (W < H) ? W : H;

                float radius = rad * R;
                float xc = x * W;
                float yc = y * H;


                float radius2 = secondBall.rad * R;
                float xc2 = secondBall.x * W;
                float yc2 = secondBall.y * H;


                if (xc + radius + radius2 > xc2
                        && xc < xc2 + radius + radius2
                        && yc + radius + radius2 > yc2
                        && yc < yc2 + radius + radius2) {
                    double distance = Math.sqrt(((xc - xc2) * (xc - xc2)) + ((yc - yc2) * (yc - yc2)));

                    if (distance + 111 < radius + radius2) {
                        float tmp1 = dx;
                        float tmp2 = dy;
                        dx = secondBall.dx;
                        dy = secondBall.dy;
                        secondBall.dx = tmp1;
                        secondBall.dy = tmp2;


                        x = checkRange(x + dx);
                        y = checkRange(y + dy);
                        secondBall.x = checkRange(secondBall.x + secondBall.dx);
                        secondBall.y = checkRange(secondBall.y + secondBall.dy);

                    }
                }
            }


            public void draw(Canvas canvas) {


                update(canvas);

                int W = canvas.getWidth();
                int H = canvas.getHeight();
                int R = (W < H) ? W : H;


                // RandomBall should draw itself on the given Canvas
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.argb((int) (a), (int) (r), (int) (g), (int) (b)));

                float radius = rad * R;
                float xc = x * W;
                float yc = y * H;


                //Log.d("WHR", "W=" + W + " H=" + H + " R=" + R + " xc=" + xc + " yc=" + yc + " radius=" + radius + " a=" + a + " r" + r + " g=" + g + " b=" + b + "\n");

                canvas.drawOval(new RectF(xc - radius / 2, yc - radius / 2, xc + radius / 2, yc + radius / 2), paint);
            }
        }

        class PlayerBall {
            // normalized position and velocity variables
            protected float x, y, rad;

            // color and color change rate variables
            protected float a, r, g, b;
            private Paint paint = new Paint();

            public PlayerBall() {
                rad = 0.05f;
                x = 500;
                y = 1500;

                a = 255;
                int rand = randColor();
                if (rand == 0) {
                    r = 200;
                    g = 0;
                    b = 0;
                } else if (rand == 1) {
                    r = 0;
                    g = 200;
                    b = 0;
                } else if (rand == 2) {
                    r = 0;
                    g = 0;
                    b = 200;
                }

            }

            public void draw(Canvas canvas) {

                int W = canvas.getWidth();
                int H = canvas.getHeight();

                int R = (W < H) ? W : H;
                // RandomBall should draw itself on the given Canvas
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.argb((int) (a), (int) (r), (int) (g), (int) (b)));


                float radius = rad * R;
                float xc = x;
                float yc = y;

                if (golden) {
                    if (sqrt(pow((gb.x * W) - xc, 2) + pow((gb.y * H - yc), 2)) <= 0.15f * R) {
                        score += 5;
                        gb = null;
                        golden = false;
                        goldTime = 5000;
                    }
                }

                for (int i = 0; i < n; i++) {
                    if (sqrt(pow((balls[i].x * W) - xc, 2) + pow((balls[i].y * H - yc), 2)) <= radius) {
                        System.out.println("r = " + balls[i].r + " g = " + balls[i].g + " b " + balls[i].b );
                        if (balls[i].r == r && balls[i].g == g && balls[i].b == b) //pos point
                        {
                            balls[i] = new RandomBall();
                            score += 5;
                            if (score >= 90) {
                                score = 0;
                                level += 1;
                                Toast.makeText(getApplicationContext(), "You Won! :)", Toast.LENGTH_SHORT).show();
/*
                                paused = true;
*/
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You Lost! :(", 1000).show();
                            writeScore((level - 1) * 90 + " ", getContext());
                            finish();                        }
                    }
                }

                canvas.drawOval(new RectF(xc - radius / 2, yc - radius / 2, xc + radius / 2, yc + radius / 2), paint);

                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.argb((255), (0), (0), (0)));
                paint.setStrokeWidth(20);
                canvas.drawOval(new RectF(xc - radius / 2, yc - radius / 2, xc + radius / 2, yc + radius / 2), paint);

                if (goldTime > 0) {
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.argb((255), (188), (155), (27)));
                    paint.setStrokeWidth(20);
                    canvas.drawOval(new RectF(xc - radius / 2, yc - radius / 2, xc + radius / 2, yc + radius / 2), paint);
                }
            }
        }

        class GoldBall {
            // normalized position and velocity variables
            protected float x, y, rad, dx, dy;

            // color and color change rate variables
            protected float a, r, g, b;
            int timer = 5000; //five seconds of presence

            private Paint paint = new Paint();

            public GoldBall() {
                x = randUniform(0, 1);
                y = randUniform(0, 1);
                rad = 0.15f;
                dx = randUniform(-0.01f, 0.01f);
                dy = randUniform(-0.01f, 0.01f);
                a = 0.5f;
                r = 188;
                g = 155;
                b = 27;

            }

            // prepare a uniform random generator
            protected Random rnd = new Random();

            protected float randUniform(float start, float end) {
                return start + ((float) rnd.nextInt(100000) / 100000) * (end - start);
            }

            // function for clipping values to be in range [0,1]
            protected float checkRange(float f) {
                return (f < 0) ? 0 : ((f > 1) ? 1 : f);
            }

            public void update() {

                timer -= 10;

                if (x <= 0 || x >= 1)
                    dx *= -1;
                if (y <= 0 || y >= 1)
                    dy *= -1;

                x = checkRange(x + dx);
                y = checkRange(y + dy);

            }

            public void draw(Canvas canvas) {

                int W = canvas.getWidth();
                int H = canvas.getHeight();

                update();

                int R = (W < H) ? W : H;

                // RandomBall should draw itself on the given Canvas
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.argb((int) (a * 255), (int) (r), (int) (g), (int) (b)));

                float radius = rad * R;
                float xc = x * W;
                float yc = y * H;

                //                Log.d("WHR", "W=" + W + " H=" + H + " R=" + R + " xc=" + xc + " yc=" + yc + " radius=" + radius + " a=" + a + " r" + r + " g=" + g + " b=" + b + "\n");

                canvas.drawOval(new RectF(xc - radius / 2, yc - radius / 2, xc + radius / 2, yc + radius / 2), paint);
            }
        }

    }


    private void writeScore(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("scores.txt", Context.MODE_APPEND));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch(Exception e) {}
    }

}
