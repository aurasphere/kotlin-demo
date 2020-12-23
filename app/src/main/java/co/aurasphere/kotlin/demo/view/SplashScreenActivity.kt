package co.aurasphere.kotlin.demo.view

import android.Manifest
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import co.aurasphere.kotlin.demo.R
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.startActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x.toFloat()

        //1
        val valueAnimator = ValueAnimator.ofFloat(width, 0f)

        //2
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            splash_app_name.translationX = value
        }

        //5
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 2000

        //6
        valueAnimator.start()
    }

    fun onSplashButtonClick(view: View) {
        checkGalleryPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            startActivity<ShowPictureActivity>("imagePath" to image.path)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(WRITE_STORAGE_PERMISSION)
    private fun checkGalleryPermission() {
        val perms = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            ImagePicker.create(this).single().folderMode(true).returnMode(ReturnMode.ALL).theme(R.style.ImagePickerTheme).start()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Please grant permission",
                    WRITE_STORAGE_PERMISSION, *perms)
        }
    }

    companion object {
        const val WRITE_STORAGE_PERMISSION = 1;
    }

}