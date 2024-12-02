package androids.erikat.pruebascasaterror

//Actividad 1

import android.app.ComponentCaller
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment

class InicioPrueba : AppCompatActivity() {
    lateinit var cuestionario: Cuestionario
    lateinit var pregunta1btt:Button
    lateinit var pregunta2btt:Button
    lateinit var intentChange:Intent
    lateinit var preguntaActual:Pregunta<*>
    lateinit var listaPreguntas:List<Pregunta<*>>
    lateinit var preguntaIterator:Iterator<Pregunta<*>>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_prueba)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        pregunta1btt = findViewById(R.id.pregBtt)
        pregunta2btt = findViewById(R.id.preg2Btt)
        cuestionario = Cuestionario()
        intentChange = Intent(this, PreguntaActivity::class.java)
        pregunta1btt.setOnClickListener{
            intentChange.putExtra("preguntas", cuestionario.darPreguntas(1).toTypedArray())
            startActivityForResult(intentChange, 0)

        }
        pregunta2btt.setOnClickListener{
            intentChange.putExtra("preguntas", cuestionario.darPreguntas(2).toTypedArray())
            startActivityForResult(intentChange, 1)
        }
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(this, "Ha pasado", Toast.LENGTH_SHORT).show()
        var resultado:String = if (resultCode== RESULT_OK)
            "¡Ganaste!"
        else
            "¡Perdiste!"

        var puntuacion = data?.getIntExtra("aciertos", 0) ?: 0

        var dialogBuilder:AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Resultado")
        dialogBuilder.setMessage("$resultado Tuviste un total de ${puntuacion} aciertos.\nQuieres compartir tu resultado?")
        dialogBuilder.setPositiveButton("¡Claro!"){ val1, val2 ->
            var intentCompartir:Intent = Intent(Intent.ACTION_SEND)
            intentCompartir.setType("text/plain")
            intentCompartir.putExtra(Intent.EXTRA_TEXT, "He ${if(resultCode== RESULT_OK)"ganado" else "perdido"} jugando a este juegazo. ¡Hice $puntuacion punto${if(puntuacion!=1) "s" else ""}!")
            startActivity(intentCompartir)
        }
        var dialog:Dialog = dialogBuilder.create()
        dialog.show()
    }
}