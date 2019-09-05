package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import android.graphics.Matrix
import android.view.TextureView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class MainActivity : AppCompatActivity() {

    fun toastMe(view: View) {
        // val myToast = Toast.makeText(this, message, duration);
        val myToast = Toast.makeText(this, "Hello Toast!", Toast.LENGTH_SHORT)
        myToast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editTextNumero1 = findViewById<View>(R.id.editTextNumero1) as EditText
        var editTextNumero2 = findViewById<View>(R.id.editTextNumero2) as EditText

        var txtViewResultado = findViewById<View>(R.id.txtViewRespuesta) as TextView

        var btnsumar = findViewById<View>(R.id.btnsumar) as Button
        var btnlimpiar = findViewById<View>(R.id.btnlimpiar) as Button
        var btnsalir = findViewById<View>(R.id.btnsalir) as Button



        btnsumar.setOnClickListener(View.OnClickListener {
            var valor_n1 = editTextNumero1.text.toString().toInt()
            var valor_n2 = editTextNumero2.text.toString().toInt()

            var rpta = valor_n1 + valor_n2

            txtViewResultado.setText("La suma es: " + rpta)
        })

        btnlimpiar.setOnClickListener(View.OnClickListener {
            editTextNumero1.setText("")
            editTextNumero2.setText("")
            txtViewResultado.setText("")
            editTextNumero1.requestFocus()
        })

        btnsalir.setOnClickListener(View.OnClickListener {
            System.exit(0)
        })

        viewFinder = findViewById(R.id.view_finder)

        // Request camera permissions
        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Every time the provided texture view changes, recompute layout
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }

    }

    private lateinit var viewFinder: TextureView

    private fun startCamera() {
        // TODO: Implement CameraX operations
    }

    private fun updateTransform() {
        // TODO: Implement camera viewfinder transformations
    }

    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    /**
     * Check if all permission specified in the manifest have been granted
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}
