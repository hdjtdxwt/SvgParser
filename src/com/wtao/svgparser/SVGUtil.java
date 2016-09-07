package com.wtao.svgparser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class SVGUtil {
	/**
	 * 获取svg图片解析后的bitmap(获取到的是picture，然后转成bitmap)
	 * 
	 * @param context
	 * @param width
	 * @param height
	 * @param resId
	 * @return
	 */
	public static Bitmap getSvgBitmap(Context context, int width, int height,
			int resId) {
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		if (resId > 0) {
			SVG svg = SVGParser.getSVGFromInputStream(context.getResources()
					.openRawResource(resId), width, height);
			if (svg != null && svg.getPicture() != null) {
				canvas.drawPicture(svg.getPicture());
			} else {
				canvas.drawRect(new Rect(0, 0, width, height), paint);
			}
		}
		return bitmap;
	}

	/**
	 * 根据resId获取原始的bitmap,在和svg生成的bitmap合并成一个新的bitmap
	 * 
	 * @param context
	 * @param svgResId
	 *            svg图片的id（和context调用上面的方法就可以获取对应的bitmap）
	 * @param bitmap
	 *            原始图片
	 * @return
	 */
	public static Bitmap getResouceBitmap(Context context, int svgResId,
			Bitmap fg) {
		// 首先对原图片进行裁剪，裁剪留下最大的可用区域

		int size = Math.min(fg.getWidth(), fg.getHeight());
		int x = (fg.getWidth() - size) / 2;
		int y = (fg.getHeight() - size) / 2;

		Bitmap svgBitmap = getSvgBitmap(context, size, size, svgResId);

		Bitmap bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		canvas.drawBitmap(svgBitmap, new Matrix(), paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
		canvas.drawBitmap(Bitmap.createBitmap(fg, x, y, size, size), new Matrix(), paint);
		return bitmap;
	}
}
