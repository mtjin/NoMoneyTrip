import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Base64
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File

object BitmapConverter {
    var MegaByte = 1000000

    /*
     * String형을 BitMap으로 변환시켜주는 함수
     * */
    fun stringToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    /*
     * Bitmap을 String형으로 변환
     * */
    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos)
        val bytes = baos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    /*
     * Bitmap을 byte배열로 변환
     * */
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        return baos.toByteArray()
    }

    fun uriToByteArray(uri: Uri): ByteArray { //컴프레션까지 처리. (
        var compressQuality = 100
        val size = File(uri.path).length()
        compressQuality = if (size < MegaByte) {
            100
        } else if (MegaByte <= size && size <= 18 * MegaByte) {
            104 - (5 * size / MegaByte).toInt()
        } else {
            10
        }
        val bitmap = BitmapFactory.decodeFile(uri.path)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, baos)
        return baos.toByteArray()
    }

    fun getUploadTask(ref: StorageReference, uri: Uri): UploadTask {
        val uploadTask: UploadTask
        val size = File(uri.path).length()
        uploadTask = if (size > MegaByte) { //1메가 바이트보다 큰 경우
            val data = uriToByteArray(uri)
            ref.putBytes(data)
        } else { //1메가바이트보다 작은 경우 그대로 업로
            ref.putFile(uri)
        }
        return uploadTask
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        var width = drawable.intrinsicWidth
        width = if (width > 0) width else 1
        var height = drawable.intrinsicHeight
        height = if (height > 0) height else 1
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}