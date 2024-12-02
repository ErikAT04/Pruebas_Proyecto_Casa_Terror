package androids.erikat.pruebascasaterror

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Cuestionario {
    @RequiresApi(Build.VERSION_CODES.O)
    private var listaPreguntas:List<Pregunta<*>> = listOf(
        Pregunta<Int>("Escribe el número total de días que hay entre el 2 de enero y el 6 de abril", 94),
        Pregunta<Int>("", 0),
        Pregunta<String>("", ""),
        Pregunta<Char>("", 'A'),
        Pregunta<LocalDate>("", LocalDate.of(1, 1, 1))
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun darPreguntas(numPreguntas:Int):List<Pregunta<*>>{
        return listaPreguntas.shuffled().subList(0, numPreguntas)
    }
}