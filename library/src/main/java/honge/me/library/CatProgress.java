package honge.me.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by honge on 15/11/21.
 */
public class CatProgress extends View implements Runnable {

    private Drawable mouse;
    private Drawable cat;

    private int width;
    private int height;

    private int leftEyeLidColor = Color.parseColor("#C9C8C9");
    private int rightEyeLidColor = Color.parseColor("#D3D1D3");
    private Paint eyesPaint;

    private float centerY;
    private float eyesRadius = 16;

    private float leftEyeX;
    private float leftEyeCX;

    private float rightEyeX;
    private float rightEyeCX;

    private Paint eyeLidPaint;
    private RectF leftOval;
    private RectF rightOval;

    private float eyeLidDegrees;

    private float startAngle;
    private float sweepAngle;

    private boolean isStart = false;

    private String loadTxt = "L  o  a  d  i  n  g  .  .  .";


    public CatProgress(Context context) {
        super(context);
        init();
    }

    public CatProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CatProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mouse = getResources().getDrawable(R.drawable.mouse);
        cat = getResources().getDrawable(R.drawable.cat);
        eyesPaint = new Paint();
        eyesPaint.setColor(Color.BLACK);
        eyesPaint.setStyle(Paint.Style.FILL);
        eyesPaint.setStrokeWidth(10);
        eyesPaint.setAntiAlias(true);
        eyesPaint.setTextSize(80);

        eyeLidPaint = new Paint();
        eyeLidPaint.setColor(leftEyeLidColor);
        eyeLidPaint.setAntiAlias(true);
        eyeLidPaint.setStrokeWidth(1);
        eyeLidPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        centerY = height / 2;

        int mouseWidth = mouse.getIntrinsicWidth();
        int mouseHeight = mouse.getIntrinsicHeight();

        mouse.setBounds(width / 2 - mouseWidth / 2, (int) (centerY - mouseHeight / 2), width / 2 + mouseWidth / 2, (int) (centerY + mouseHeight / 2));

        int catWidth = cat.getIntrinsicWidth();
        int catHeight = cat.getIntrinsicHeight();
        cat.setBounds(width / 2 - catWidth / 2, (int) (centerY - catHeight / 2), width / 2 + catWidth / 2, (int) (centerY + catHeight / 2));

        double diffCatWidth = 5.4;
        double diffLeftEyeLeft = 0.4;
        double diffLeftEyeRight = 2.55;

        double diffRightEycLeft = 2.85;
        double diffRightEycRight = 5.0;

        double diffEyeTop = 1.5;
        double diffEyeBotton = 3.6;

        double diffCatCenter = diffCatWidth / 2;

        double diffEyeRadius = (diffLeftEyeRight - diffLeftEyeLeft) / 2;

        float scale = (float) (catWidth / diffCatWidth);


        leftEyeX = (float) (width / 2 - scale * (diffCatCenter - diffLeftEyeLeft) + eyesRadius);
        leftEyeCX = (float) (width / 2 - scale * (diffCatCenter - diffLeftEyeLeft - diffEyeRadius));


        rightEyeX = (float) (width / 2 + scale * (diffRightEycLeft - diffCatCenter) + eyesRadius);
        rightEyeCX = (float) (width / 2 + scale * (diffRightEycRight - diffCatCenter - diffEyeRadius));

        float eyesRadius = (float) (scale * diffEyeRadius);


        leftOval = new RectF((float) (width / 2 - scale * (diffCatCenter - diffLeftEyeLeft) - this.eyesRadius), centerY - eyesRadius - this.eyesRadius * 3, (float) (width / 2 - scale * (diffCatCenter - diffLeftEyeRight) + this.eyesRadius), centerY + eyesRadius);

        rightOval = new RectF((float) (width / 2 + scale * (diffRightEycLeft - diffCatCenter) ), centerY - eyesRadius - this.eyesRadius * 3, (float) (width / 2 + scale * (diffRightEycRight - diffCatCenter) + this.eyesRadius), centerY + eyesRadius);


        textX = width / 2 - mouseWidth / 2;
        textY = height / 2 + mouseHeight / 2 + 80;


        new Thread(this).start();

    }

    float degrees = 0;
    float textX = 0;
    float textY = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //background
        canvas.drawColor(Color.BLACK);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        //mouse
        //默认是55°位置
        canvas.rotate(degrees - 55, width / 2, height / 2);
        mouse.draw(canvas);
        canvas.restore();

        //cat
        cat.draw(canvas);

        eyesPaint.setColor(Color.BLACK);

        //left
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.rotate(degrees, leftEyeCX, centerY);
        canvas.drawCircle(leftEyeX, centerY, eyesRadius, eyesPaint);
        canvas.restore();

        //right
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.rotate(degrees, rightEyeCX, centerY);
        canvas.drawCircle(rightEyeX, centerY, eyesRadius, eyesPaint);
        canvas.restore();

        eyeLidPaint.setColor(leftEyeLidColor);
        canvas.drawArc(leftOval, startAngle, sweepAngle, false, eyeLidPaint);
        eyeLidPaint.setColor(rightEyeLidColor);
        canvas.drawArc(rightOval, startAngle, sweepAngle, false, eyeLidPaint);

        eyesPaint.setColor(Color.WHITE);
        canvas.drawText(loadTxt, textX, textY, eyesPaint);

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            if (!isStart) {
                new Thread(this).start();
            }
        }
    }

    @Override
    public void run() {
        while (getVisibility() == VISIBLE) {
            isStart = true;

            if (degrees > -360) {
//                switch ((int)degrees){
//                    case -10:loadTxt = "L";break;
//                    case -60:loadTxt = "L  o";break;
//                    case -110:loadTxt = "L  o  a";break;
//                    case -160:loadTxt = "L  o  a  d";break;
//                    case -210:loadTxt = "L  o  a  d  i";break;
//                    case -260:loadTxt = "L  o  a  d  i  n";break;
//                    case -310:loadTxt = "L  o  a  d  i  n  g";break;
//                    case -340:loadTxt = "";break;
//                }

                degrees -= 5;
            } else {
                degrees = 0;
            }

            eyeLidDegrees = degrees;

            //眼睛一开始在0°的位置
            if (degrees >= -180) {
                eyeLidDegrees = -180;
            } else if (degrees <= -270) {
                eyeLidDegrees = degrees + 180;
            }

            //-90是因为startAngle默认是从3点钟方向,-90是重置到12点方向
            startAngle = (eyeLidDegrees >= -180) ? eyeLidDegrees / 2 - 90 : -((eyeLidDegrees / 2 + 360) - 90);

            sweepAngle = (eyeLidDegrees >= -180) ? Math.abs(eyeLidDegrees) : (360 + eyeLidDegrees);


            postInvalidate();
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
