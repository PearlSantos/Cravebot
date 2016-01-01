package cravebot.results.elysi.results;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by elysi on 12/30/2015.
 */
class BitmapWorkerTaskT extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private boolean fromURL;
    private String url;
    private int res;
    private final int RESOLUTION = 150;
    private Resources resource;
    private String data = "";
    private Context context;



    public BitmapWorkerTaskT(Context context, ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.context = context;
//        this.fromURL = fromURL;
//        this.url = url;
//        this.res = res;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(String ... params) {
        data = params[0];
        Bitmap img;
        img = decodeBitmapFromURL(data, RESOLUTION, RESOLUTION);

        return img;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTaskT bitmapWorkerTask =
                    getBitmapWorkerTask(imageView);
            if (this == bitmapWorkerTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }



    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTaskT bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == "" || !bitmapData.equals(data)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTaskT getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapWorkerTaskT.AsyncDrawableT) {
                final BitmapWorkerTaskT.AsyncDrawableT asyncDrawable = (BitmapWorkerTaskT.AsyncDrawableT) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
    static class AsyncDrawableT extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTaskT> bitmapWorkerTaskReference;

        public AsyncDrawableT(Resources res, Bitmap bitmap,
                             BitmapWorkerTaskT bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTaskT>(bitmapWorkerTask);
        }

        public BitmapWorkerTaskT getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static Bitmap decodeBitmapFromURL(String src, int reqWidth, int reqHeight) {
        try {
            System.out.println("DECODED: URL");

//            URL url = new URL(src);
//            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
//            InputStream is = new BufferedInputStream(connection.getInputStream());
//
            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//
//            BitmapFactory.decodeStream(is, null, options);
//            options.inSampleSize = BitmapWorkerTask.calculateInSampleSize(options, reqWidth, reqHeight);
//
            options.inJustDecodeBounds = false;
//
//            is.close();
//            is = connection.getInputStream();
//            Bitmap img = BitmapFactory.decodeStream(is, null, options);
//            is.close();
//
//            connection.disconnect();

            URL url = new URL(src);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

            InputStream is = connection.getInputStream();
            Bitmap img = BitmapFactory.decodeStream(is, null, options);
            return img;
        } catch (IOException e) {
            Log.e("Hub", "Error getting the image from server : " + e.getMessage().toString());
            // Log exception
            return null;
        }
    }
    public Bitmap decodeBitmapFromResource(int resId) {
        // First decode with inJustDecodeBounds=true to check dimensions
        System.out.println("DECODED: RES");
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, RESOLUTION, RESOLUTION);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
