package com.wtao.svgparser;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView);

		changeImage(R.raw.radial_gradient_ellipse);
		/*
		 * Bitmap svgBitmap = SVGUtil.getSvgBitmap(this, 300, 200,
		 * R.raw.shape_5);
		 * 
		 * imageView.setImageBitmap(svgBitmap);
		 */
	}

	public void changeImage(int id) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.tuzi);
		Bitmap result = SVGUtil.getResouceBitmap(this, id, bitmap);
		bitmap.recycle(); 
		imageView.setImageBitmap(result);
	}

	int id[] = { R.raw.shape_5, R.raw.shape_circle_2, R.raw.shape_flower,
			R.raw.shape_heart, R.raw.shape_star, R.raw.shape_star_2,
			R.raw.shape_star_3 };

	public void change(View view) {
		int num = new Random().nextInt(id.length);
		Log.e("chane", "num=" + num);
		changeImage(id[num]);
	}
}