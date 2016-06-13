package com.example.matrix;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

   private Matrix mMatrix1;

   private Matrix mMatrix2;

   private ViewGroup mMainLayout;

   private ImageView mImageView1;

   private ImageView mImageView2;

   private RectF imageBounds;

   private RectF imageView1Bounds;

   private RectF imageView2Bounds;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      imageBounds = new RectF();
      imageView1Bounds = new RectF();
      imageView2Bounds = new RectF();

      mMatrix1 = new Matrix();
      mMatrix2 = new Matrix();

      mMainLayout = (ViewGroup) findViewById(R.id.main_layout);

      // These two image views have the same overlapping coordinates
      mImageView1 = (ImageView) findViewById(R.id.imageView1);
      mImageView2 = (ImageView) findViewById(R.id.imageView2);

      mMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
         @Override
         public void onGlobalLayout() {

            if (mImageView1.getDrawable() == null) {   // if not initialized

               mImageView1.setImageResource(R.drawable.chrysanthemum);
               mImageView2.setImageResource(R.drawable.chrysanthemum);

               // Create a rectangle that is the size of the image.
               Drawable drawable = mImageView1.getDrawable();
               imageBounds.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

               // Create rectangles for the imageviews, separating top and bottom
               imageView1Bounds.set(0, 0, mImageView1.getMeasuredWidth(), mImageView1.getMeasuredHeight() / 2);
               imageView2Bounds.set(0, mImageView1.getMeasuredHeight() / 2, mImageView2.getMeasuredWidth(), mImageView2.getMeasuredHeight());

               // set the matrices to transform the images to their respective bounds
               mMatrix1.setRectToRect(imageBounds, imageView1Bounds, Matrix.ScaleToFit.FILL);
               mMatrix2.setRectToRect(imageBounds, imageView2Bounds, Matrix.ScaleToFit.FILL);

               mImageView1.setImageMatrix(mMatrix1);
               mImageView2.setImageMatrix(mMatrix2);
            }
         }
      });

      mMainLayout.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

               if (event.getY() >= 0F && event.getY() <= mMainLayout.getHeight()) {

                  // use the Y coord of touch event as bottom of imageView1 and top of imageView2
                  imageView1Bounds.set(0, 0, mImageView1.getMeasuredWidth(), event.getY());
                  imageView2Bounds.set(0, event.getY(), mImageView2.getMeasuredWidth(), mImageView2.getMeasuredHeight());

                  // set the matrices to transform the images to their respective bounds
                  mMatrix1.setRectToRect(imageBounds, imageView1Bounds, Matrix.ScaleToFit.FILL);
                  mMatrix2.setRectToRect(imageBounds, imageView2Bounds, Matrix.ScaleToFit.FILL);

                  mImageView1.setImageMatrix(mMatrix1);
                  mImageView2.setImageMatrix(mMatrix2);

                  return true;
               }
            }

            return false;
         }
      });
   }

}
