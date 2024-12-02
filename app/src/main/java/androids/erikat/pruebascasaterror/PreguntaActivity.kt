package androids.erikat.pruebascasaterror

//Actividad 2
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androids.erikat.pruebascasaterror.databinding.ActivityPreguntaBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import java.io.Serializable

class PreguntaActivity : AppCompatActivity() {
    lateinit var mibinding:ActivityPreguntaBinding
    lateinit var respuestaRgroup:RadioGroup
    lateinit var respuestaEText:EditText
    lateinit var preguntas:List<Pregunta<*>>
    lateinit var preguntaActual:Pregunta<*>
    lateinit var preguntaIterator: Iterator<Pregunta<*>> //Iterador para recorrer la lista de preguntas
    lateinit var campoPregunta:TextView
    lateinit var responderBtt:Button
    var cuentaBien = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mibinding = ActivityPreguntaBinding.inflate(layoutInflater)
        setContentView(mibinding.root)

        iniciarDatos()

        iniciarBoton()
    }

    private fun iniciarBoton() {
        responderBtt = mibinding.respuestaBtt

        responderBtt.setOnClickListener{
            var respuesta:String
            if (preguntaActual.respuesta is Char){
                respuesta = (findViewById<RadioButton>(respuestaRgroup.checkedRadioButtonId)).text.toString()
            } else {
                respuesta = respuestaEText.text.toString()
            }
            if (respuesta!=preguntaActual.respuesta.toString()){
                var intentRes:Intent = Intent()
                intentRes.putExtra("aciertos", cuentaBien)
                setResult(RESULT_CANCELED, intentRes)
                finish()
            } else {
                cuentaBien++
                if (preguntaIterator.hasNext()){
                    cargarPregunta()
                } else {
                    var intentRes:Intent = Intent()
                    intentRes.putExtra("aciertos", cuentaBien)
                    setResult(RESULT_OK, intentRes)
                    finish()
                }
            }
        }
    }

    private fun iniciarDatos() {
        var intent:Intent = getIntent()
        preguntas = (intent.getSerializableExtra("preguntas") as Array<Pregunta<*>>).toList()
        preguntaIterator = preguntas.iterator()
        respuestaRgroup = RadioGroup(this)
        var rba:RadioButton = RadioButton(this)
        rba.text = "A"
        respuestaRgroup.addView(rba)
        var rbb:RadioButton = RadioButton(this)
        rbb.text = "B"
        respuestaRgroup.addView(rbb)
        var rbc:RadioButton = RadioButton(this)
        rbc.text = "C"
        respuestaRgroup.addView(rbc)
        var rbd:RadioButton = RadioButton(this)
        rbd.text = "D"
        respuestaRgroup.addView(rbd)
        respuestaEText = EditText(this)
        campoPregunta = mibinding.preguntaTXT
        cargarPregunta()
    }

    private fun cargarPregunta(){
        preguntaActual = preguntaIterator.next()
        campoPregunta.text = preguntaActual.pregunta
        mibinding.respuestaLayout.removeAllViews()
        when(preguntaActual.respuesta!!::class){
            String::class -> {mibinding.respuestaLayout.addView(respuestaEText); respuestaEText.inputType = InputType.TYPE_CLASS_TEXT}
            Int::class -> {mibinding.respuestaLayout.addView(respuestaEText); respuestaEText.inputType = InputType.TYPE_CLASS_NUMBER}
            Char::class -> {mibinding.respuestaLayout.addView(respuestaRgroup)}
            else -> {}
        }
    }
}