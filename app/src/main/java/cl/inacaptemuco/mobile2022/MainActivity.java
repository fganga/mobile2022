package cl.inacaptemuco.mobile2022;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import modelo.Entrada;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    TextView txtUser;
    EditText edtPatente,edtFecha,edtComentario;
    Button btnEnviar,btnListar;
    Spinner spnEstado;
    private String patente,estado,comentario;
    private Date fecha;

    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vincularElementos();
        habilitarListener();
        mostrarUsuario();
        iniciarFirestore();
    }

    private void iniciarFirestore() {
        //Iniciamos FireStore
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void habilitarListener() {
        //Habilitamos listener para ambos botones
        btnEnviar.setOnClickListener(this);
        btnListar.setOnClickListener(this);


    }

    private void mostrarUsuario() {
        //Desplegamos valors en la intergaz, en este caso el email del usuario que inicia sesión.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        txtUser.setText("Bienvenido " + user.getEmail());
    }

    private void vincularElementos() {
    txtUser = (TextView) findViewById(R.id.txt_user);
    edtFecha = (EditText) findViewById(R.id.edt_fecha);
    edtPatente = (EditText) findViewById(R.id.edt_patente);
    edtComentario = (EditText) findViewById(R.id.edt_comentario);
    btnEnviar = (Button) findViewById(R.id.btn_enviar);
    btnListar = (Button) findViewById(R.id.btn_listar);

    //Connfiguración de Spinner según documentación
    spnEstado = (Spinner) findViewById(R.id.spn_estado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spn_estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEstado.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_enviar) {
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

            //Invocamos método que agrega a firestore
            agregarFirestore(fecha, patente, estado, comentario);
        }
        if (view.getId() == R.id.btn_listar){
            Intent intento = new Intent(MainActivity.this,ListaActivity.class);
            startActivity(intento);
        }

    }

    private void agregarFirestore(Date fecha, String patente, String estado, String comentario) {
        //Colección en Firestore
        CollectionReference coleccionEntradas = firebaseFirestore.collection("Entrada");
        //Objeto Entrada
        Entrada entrada = new Entrada(patente,comentario,estado,fecha);
        //intentamos agregar
        coleccionEntradas.add(entrada).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //En caso de éxito mostramos mensaje mediante Toast
                Toast.makeText(MainActivity.this,"Entrada registrada correctamente",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //En caso de error, mensaje similar al anterior.
                Toast.makeText(MainActivity.this,"Error al agregar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}