package androids.erikat.pruebascasaterror

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androids.erikat.pruebascasaterror.databinding.ActivityMainBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.setMargins

class MainActivity : AppCompatActivity() {
    var x = 0
    var y = 0
    lateinit var miBinding:ActivityMainBinding
    lateinit var cardsMultiList:MutableList<MutableList<TextView>>
    lateinit var valuesMultiArray:Array<Array<Int>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        miBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(miBinding.root)

        iniciarGridView();
        iniciarListeners()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun iniciarListeners() {
        miBinding.izquierdaBtt.setOnClickListener{
            y--
            realizarCambios(x,y+1)
        }

        miBinding.derechaBtt.setOnClickListener{
            y++
            realizarCambios(x, y-1)
        }

        miBinding.subirBtt.setOnClickListener{
            x--
            realizarCambios(x+1, y)
        }

        miBinding.bajarBtt.setOnClickListener{
            x++
            realizarCambios(x-1, y)
        }
    }

    private fun iniciarGridView() {
        valuesMultiArray = Array(4){numIndice:Int -> Array(4){numSegIndice:Int -> 0} }
        cardsMultiList = mutableListOf()
        for(x in 0..3){
            cardsMultiList.add(mutableListOf())
            for (y in 0..3){
                var carta:CardView = CardView(this)
                carta.layoutParams = ViewGroup.LayoutParams(
                    100,
                    100
                )
                var tview:TextView = TextView(this)
                tview.text = "A"
                carta.addView(tview)
                miBinding.cartasGridLayout.addView(carta)
                cardsMultiList[x].add(tview)
            }
        }
        realizarCambios(0, 0)
    }

    private fun realizarCambios(antiguoX: Int, antiguoY: Int) {
        valuesMultiArray[antiguoX][antiguoY] = 0
        valuesMultiArray[x][y] = 1
        actualizarTablero()
        miBinding.subirBtt.isEnabled = (x>0)
        miBinding.bajarBtt.isEnabled = (x<cardsMultiList.size-1)
        miBinding.izquierdaBtt.isEnabled = (y>0)
        miBinding.derechaBtt.isEnabled = (y<cardsMultiList[x].size-1)
    }

    private fun actualizarTablero() {
        for(x in valuesMultiArray.indices){
            for (y in valuesMultiArray[x].indices){
                cardsMultiList[x][y].text = if(valuesMultiArray[x][y]==1) "B" else "A"
            }
        }
    }
}