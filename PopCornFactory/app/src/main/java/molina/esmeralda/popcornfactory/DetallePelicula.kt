package molina.esmeralda.popcornfactory

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetallePelicula : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pelicula)

        val bundle = intent.extras
        val tvPelicula: ImageView = findViewById(R.id.tv_pelicula_imagen)
        val tvTitulo: TextView = findViewById(R.id.tv_nombre_pelicula)
        val tvDescripcion: TextView = findViewById(R.id.tv_pelicula_desc)
        val seatsLeft: TextView = findViewById(R.id.seatLeft)
        val buyTickets: Button = findViewById(R.id.buyTickets)

        var movieSeats = 0
        var id = -1
        var title = ""

        if (bundle != null) {
            movieSeats = bundle.getInt("numberSeats",20)
            title = bundle.getString("titulo") ?: ""
            tvPelicula.setImageResource(bundle.getInt("header"))
            tvTitulo.text = title
            tvDescripcion.text = bundle.getString("sinopsis") ?: ""
            seatsLeft.text = "$movieSeats seats available"
            id = bundle.getInt("pos")
        }

        val sharedPreferences = getSharedPreferences("reservas", MODE_PRIVATE)
        val claveAsientos = "asientos_reservados_$title"

        val reservedSeats = sharedPreferences.getStringSet(claveAsientos, mutableSetOf())?.size ?: 0
        val availableSeats = movieSeats - reservedSeats

        seatsLeft.text = "$availableSeats seats available"

        if (availableSeats == 0) {
            buyTickets.isEnabled = false
        } else {
            buyTickets.setOnClickListener {
                val intent = Intent(this, SeatSelection::class.java)
                intent.putExtra("id", id)
                intent.putExtra("name", title)
                startActivity(intent)
            }
        }
    }
}