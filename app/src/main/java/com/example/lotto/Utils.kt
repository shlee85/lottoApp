package com.example.lotto

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Environment
import android.util.Log
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.Buffer

object Utils {
    private const val TAG = "Utils"
    fun dialogShowWithoutNavigationBar(dlg: Dialog?) {
        val window = dlg?.window ?: return
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        dlg.show()
        dlg.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    /* 생성된 번호를 저장하는 기능 */
    fun writeTextFile(directory: String, filename: String, content: String) {
        val dir = File(directory)

        Log.d(TAG, "dir: $directory, name: $filename, " +
                "content: $content, dir_return:$dir")

        if(!dir.exists()) {
            Log.d(TAG, "File not exists()")
            dir.mkdirs()
        }

        if(dir.exists()){
            Log.d(TAG, "폴더 생성 완료")
        } else {
            Log.d(TAG, "폴더 생성 안됨.")
        }

        /* true는 이어쓰기 한다는 설정 */
        val writer = FileWriter(directory + "/" + filename, true)
        writer.flush()
        Log.d(TAG, "FileWrite end : $writer")

        /* 쓰기 속도 향상 */
        val buffer = BufferedWriter(writer)
        buffer.write(content)
        buffer.newLine() //write후 다음 라인에서 새로운 데이터 저장하도록 설정.
        buffer.close()
    }

    fun readTextFile(path: String, numArray: ArrayList<NumbersInfo>): String {
        val file = File(path)

        if(!file.exists()) {
            Log.d(TAG, "파일이 존재하지 않음.")
            return ""
        }

        val reader = FileReader(file)
        val buffer = BufferedReader(reader)

        var temp: String? = ""
        var result = StringBuffer()

        while(true) {
            temp = buffer.readLine() //줄단위
            if(temp == null) {
                break
            } else {
                Log.d(TAG, "Read : $temp")
                val number = temp.split(" ")
                //Log.d(TAG, "number: ${number[0]}, ${number[1]}, ${number[2]}, ${number.size}")
                numArray.add(
                    NumbersInfo(number[0].toInt(),
                                number[1].toInt(),
                                number[2].toInt(),
                                number[3].toInt(),
                                number[4].toInt(),
                                number[5].toInt(),
                                number[6].toInt()
                    )
                )
                result.append(temp).append("\n")
            }
        }
        buffer.close()

        return result.toString()
    }
}