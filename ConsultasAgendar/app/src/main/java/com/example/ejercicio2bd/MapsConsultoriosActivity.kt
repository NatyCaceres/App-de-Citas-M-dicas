package com.example.ejercicio2bd

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ejercicio2bd.databinding.ActivityMapsConsultoriosBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.Manifest
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class MapsConsultoriosActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap// mapa en pantalla
    private lateinit var db: AppDatabase//acceso a la bd
    private lateinit var fusedLocationClient: FusedLocationProviderClient//obtener la última ubicación conocida o actualizaciones en tiempo real.
    private lateinit var binding: ActivityMapsConsultoriosBinding//layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsConsultoriosBinding.inflate(layoutInflater)//otra forma de inicializar sin usar findViewById.
        setContentView(binding.root)
        db = AppDatabase.getDatabase(this)
        // inicializando el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // preparando el fragmento del mapa para que se cargue y notifique cuando esté listo (onMapReady).
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        verificarPermisosYMostrar()
    }
    private fun verificarPermisosYMostrar() {
        //verifica si ya se otorgó el permiso de ubicación.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            //si el permiso ya fue otorgado, muestra la ubicación del usuario en el mapa.
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                //intenta obtener la última ubicación conocida del usuario. Si existe, se muestra en el mapa.
                if (location != null) {//se centra el mapa en esa ubicacion
                    val posicionUsuario = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionUsuario, 14f))
                }
                //llama la funcion
                mostrarTodosLosConsultorios()
            }
        } else {
            // Si nunca se ha solicitado o fue denegado antes
            if (permisoYaFueSolicitado()) {
                Toast.makeText(this, "Activa el permiso de ubicación desde Ajustes", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        }
    }
    //Verifica si ya se mostró antes la solicitud de permiso y fue rechazada.
    private fun permisoYaFueSolicitado(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }
    //Consulta todos los consultorios registrados en la base de datos.
    private fun mostrarTodosLosConsultorios() {
        //hilo de entrada/salida para no bloquear la UI.
        CoroutineScope(Dispatchers.IO).launch {
            val consultorios = db.consultorioDao().obtenerTodos()
            //se recorren los consultorios y se agregan marcadores en el mapa
            withContext(Dispatchers.Main) {
                for (c in consultorios) {
                    val ubicacion = LatLng(c.latitud, c.longitud)
                    mMap.addMarker(
                        MarkerOptions()
                            .position(ubicacion)
                            .title(c.nombre)
                            .snippet(c.direccion)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    )
                }
            }
        }
    }
    //Cuando el usuario responde a la solicitud de permisos, si los concede se continúa con el flujo normal.
    //Si los deniega, se muestra un mensaje.
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            verificarPermisosYMostrar()
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }
}
