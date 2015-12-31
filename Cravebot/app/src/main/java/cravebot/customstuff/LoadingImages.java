package cravebot.customstuff;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import cravebot.R;

/**
 * Created by elysi on 12/31/2015.
 */
public class LoadingImages {
    private Context context;
    public LoadingImages(Context context){
        this.context=context;
    }


    public void loadBitmapFromRes(int resId, ImageView imageView, int width, int height) {
//            BitmapWorkerTask task = new BitmapWorkerTask(getContext().getApplicationContext(),imageView);
//            final String imageKey = String.valueOf(resId);
//
//            final Bitmap bitmap = task.getBitmapFromMemCache(imageKey);
//            if (bitmap != null) {
//                imageView.setImageBitmap(bitmap);
//            } else {
//                imageView.setImageResource(R.drawable.back_button);
//                task = new BitmapWorkerTask(getContext().getApplicationContext(),imageView);
//                task.execute(resId);
//            }
        final BitmapWorkerTaskT task = new BitmapWorkerTaskT(context, imageView);
        Bitmap bm = task.decodeBitmapFromResource(resId, width, height);
        imageView.setImageBitmap(bm);

    }


    public void loadBitmapFromURL(String resId, ImageView imageView) {
        if (BitmapWorkerTaskT.cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTaskT task = new BitmapWorkerTaskT(context, imageView);
            Bitmap mPlaceHolderBitmap = task.decodeBitmapFromResource(R.drawable.back_button, 100, 100);
            final BitmapWorkerTaskT.AsyncDrawableT asyncDrawable =
                    new BitmapWorkerTaskT.AsyncDrawableT(context.getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }
}
