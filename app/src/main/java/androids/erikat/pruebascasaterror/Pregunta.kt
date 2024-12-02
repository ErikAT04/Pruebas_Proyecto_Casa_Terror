package androids.erikat.pruebascasaterror

import java.io.Serializable

data class Pregunta <T>(var pregunta:String, var respuesta:T):Serializable{}