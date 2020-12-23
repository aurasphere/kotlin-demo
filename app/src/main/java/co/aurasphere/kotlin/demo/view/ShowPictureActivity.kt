package co.aurasphere.kotlin.demo.view

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.aurasphere.kotlin.demo.R
import kotlinx.android.synthetic.main.activity_show_picture.*
import org.jetbrains.anko.imageBitmap


class ShowPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_picture)

        // Shows the up button on the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Shows the picture.
        val imagePath = intent.getStringExtra("imagePath")
        val opts = BitmapFactory.Options()
        opts.inMutable = true
        val picture = BitmapFactory.decodeFile(imagePath, opts)
        show_picture_content.imageBitmap = picture
    }

}