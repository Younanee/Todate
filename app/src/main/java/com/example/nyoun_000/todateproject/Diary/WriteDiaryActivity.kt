package com.example.nyoun_000.todateproject.Diary

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.nyoun_000.todateproject.DB.DBManagerDiary
import com.example.nyoun_000.todateproject.DB.DiaryData
import com.example.nyoun_000.todateproject.Manifest
import com.example.nyoun_000.todateproject.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_write_diary.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class WriteDiaryActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_FROM_ALBUM = 1
        const val IMAGE_FROM_CROP = 2
    }
    init {
        DBManagerDiary.init(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_diary)
        val mStorageReference = FirebaseStorage.getInstance().reference

        var intent = intent
        var selectedYear = intent.extras.getString("selectedYear")
        var selectedMonth = intent.extras.getString("selectedMonth")
        var selectedDay = intent.extras.getString("selectedDay")


        btn_searchimg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, IMAGE_FROM_ALBUM)
        }


        bt_submit.setOnClickListener {
            val data = DiaryData(
                    et_title.text.toString(),
                    selectedYear+selectedMonth+selectedDay,
                    et_weather.text.toString(),
                    et_content.text.toString(),
                    selectedYear,
                    selectedMonth,
                    selectedDay)
            DBManagerDiary.addDiaryData(data)
            finish()
        }
        bt_cancel.setOnClickListener {
            finish()
        }
    }
    fun doTakeAlbumAction(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }


        if (requestCode == IMAGE_FROM_ALBUM){
            val uri : Uri = data!!.data
            val intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("outputX", 200) // CROP한 이미지의 x축 크기
            intent.putExtra("outputY", 200) // CROP한 이미지의 y축 크기
            intent.putExtra("aspectX", 1) // CROP 박스의 X축 비율
            intent.putExtra("aspectY", 1) // CROP 박스의 Y축 비율
            intent.putExtra("scale", true)
            intent.putExtra("return-data", true)
            startActivityForResult(intent, IMAGE_FROM_CROP)
        } else if (requestCode == IMAGE_FROM_CROP){
            val extra : Bundle = data!!.extras
            val filePath = Environment.getExternalStorageDirectory().absolutePath + "/todateAPP/" + System.currentTimeMillis() + ".jpg"

            if(extra!=null){
                val img : Bitmap = extra.getParcelable("data")
                iv_imgview.setImageBitmap(img)
                storeCropImage(img, filePath)

                // absoultePath 라는 변수에 filePath를 담아서 데이터베이스에 경로 저장
                // filePath를 디비에 저장시키기
            } else{
                val uri : Uri = data!!.data
                val file : File = File(uri.path)
                if(file.exists()){
                    file.delete()
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        DBManagerDiary.db_close()
    }
    fun storeCropImage(bitmap : Bitmap, filePath : String) {
       //폴더를 생성하여 이미지를 저장하는 방식이다.
        val dirPath = Environment.getExternalStorageDirectory().absolutePath+"/todateAPP"
        val dirTodateAPP = File(dirPath)

        if(!dirTodateAPP.exists()) {
            dirTodateAPP.mkdir()
        }

        val copyFile =File(filePath)

        var out : BufferedOutputStream
        try {
            copyFile.createNewFile()
            out = BufferedOutputStream(FileOutputStream(copyFile))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)))
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
