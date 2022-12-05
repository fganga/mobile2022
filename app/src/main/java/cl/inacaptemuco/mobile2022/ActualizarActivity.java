package cl.inacaptemuco.mobile2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import modelo.Entrada;

public class ActualizarActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables globales
    // creating variables for our edit text
    private EditText edtPatente,edtFecha,edtComentario;
    private Spinner spnEstado;
    private Button btnActualizar,btnEliminar;
    private Date fecha;

    private String idUpdate;


    private String patente, comentario,estado;


    private FirebaseFirestore db;

    Entrada entrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        //Obtenemos valores actuales
        entrada = (Entrada) getIntent().getSerializableExtra("entrada");

        //Preparaciòn de actualizaciòn
        // getting our instance from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        vincularElementos();
        habilitarListener();


        //Obtenemos datos

        edtFecha.setText(entrada.getFecha().toString());
        edtPatente.setText(entrada.getPatente());
        edtFecha.setText(entrada.getFecha().toString());
        edtComentario.setText(entrada.getComentario());

        idUpdate = entrada.getId();
        Toast.makeText(ActualizarActivity.this, "ID " + entrada.getId(), Toast.LENGTH_SHORT).show();





    }

    private void habilitarListener() {

        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
    }

    private void vincularElementos() {
        // initializing our edittext and buttons
        edtFecha = findViewById(R.id.edt_fecha);
        edtPatente = findViewById(R.id.edt_patente);
        edtComentario = findViewById(R.id.edt_comentario);
        btnActualizar = findViewById(R.id.btn_actualizar);
        btnEliminar = findViewById(R.id.btn_eliminar);
        spnEstado = findViewById(R.id.spn_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spn_estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstado.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_actualizar){



        //Obtenemos datos
        String fechaIngreso = edtFecha.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(fechaIngreso);
            fecha = date;
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        patente = edtPatente.getText().toString();

        comentario = edtComentario.getText().toString();
        estado = spnEstado.getSelectedItem().toString();
        
        updateCourses(entrada,patente,fecha,estado,comentario);
        }
        if (view.getId() == R.id.btn_eliminar){
            eliminarEntrada(idUpdate);
        }
        
    }

    private void updateCourses(Entrada entrada,String patente, Date fecha, String estado, String comentario) {

        Entrada entradaActualizada = new Entrada(patente,comentario,estado,fecha);

        HashMap<String, Object> updateEntrada = new HashMap<>();
        updateEntrada.put("fecha", entradaActualizada.getFecha());
        updateEntrada.put("estado", entradaActualizada.getEstado());
        updateEntrada.put("patente", entradaActualizada.getPatente());
        updateEntrada.put("comentario", entradaActualizada.getComentario());

        db.collection("Entrada").


                        document(idUpdate).


                        update(updateEntrada).


                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ActualizarActivity.this, "Entrada Actulizada", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActualizarActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void eliminarEntrada(String idUpdate) {

        db.collection("Entrada").

                        document(idUpdate).

                        delete().

                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(ActualizarActivity.this, "Entrada eliminada.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ActualizarActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {

                            Toast.makeText(ActualizarActivity.this, "Error al eliminar. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
