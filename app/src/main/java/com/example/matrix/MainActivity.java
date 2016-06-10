package com.example.matrix;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

   private Matrix mMatrix1;

   private Matrix mMatrix2;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      mMatrix1 = new Matrix();
      mMatrix2 = new Matrix();

   }

   @Override
   protected void onResume() {
      super.onResume();

      // These two image views have the same overlapping coordinates
      ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
      ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);

      imageView1.setImageResource(R.drawable.chrysanthemum);
      imageView2.setImageResource(R.drawable.chrysanthemum);

      // Create a rectangle that is the size of the image.
      Drawable drawable = imageView1.getDrawable();
      RectF imageBounds = new RectF(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

      // Create rectangles for the imageviews, separating top and bottom
      RectF imageView1Bounds = new RectF(0, 0, imageView1.getMeasuredWidth(), (imageView1.getMeasuredHeight() / 2) - 1);
      RectF imageView2Bounds = new RectF(0, imageView1.getMeasuredHeight() / 2, imageView2.getMeasuredWidth(), imageView2.getMeasuredHeight());

      // set the matrices to scale the images to top and bottom
      mMatrix1.setRectToRect(imageBounds, imageView1Bounds, Matrix.ScaleToFit.FILL);
      mMatrix2.setRectToRect(imageBounds, imageView2Bounds, Matrix.ScaleToFit.FILL);

      imageView1.setImageMatrix(mMatrix1);
      imageView2.setImageMatrix(mMatrix2);

      imageView1.setImageResource(R.drawable.chrysanthemum);
      imageView2.setImageResource(R.drawable.chrysanthemum);
   }

}
