package cl.inacaptemuco.mobile2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import modelo.Entrada;

public class ListaActivity extends AppCompatActivity {

    //RecyclerView
    private RecyclerView rcvEntrada;
    //Lista de objetos de la clase Entrada
    private ArrayList<Entrada> entradaArrayList;
    //Adaptador
    private EntradaAdapter entradaAdapter;
    //Referencia a base de datos en Firestore
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        rcvEntrada =  findViewById(R.id.rcv_entrada);
        db = FirebaseFirestore.getInstance();

        entradaArrayList = new ArrayList<>();
        //Propiedades de RecylerView
        rcvEntrada.setHasFixedSize(true);
        rcvEntrada.setLayoutManager(new LinearLayoutManager(this));
        //Se inicia adaptador
        entradaAdapter = new EntradaAdapter(entradaArrayList,this);
        //Se asigna adaptador a REcycler
        rcvEntrada.setAdapter(entradaAdapter);

        //Definimos la colección desde la cual obtenemos datos
        db.collection("Entrada").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Chequeamos si viene vacío
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Si no viene vacío conformamos lista
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                //Se obtienen los datos y se instancia por cada documento un objeto de la clase Entrada
                                Entrada entrada = d.toObject(Entrada.class);

                                //El objeto se agrega a la lista de objetos de la clase Entrada
                                entradaArrayList.add(entrada);

                            }
                            //Notificación de actualización de datos
                            entradaAdapter.notifyDataSetChanged();

                        } else {
                            // Si no se encuentran datos
                            Toast.makeText(ListaActivity.this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // En caso de excepciones
                        Toast.makeText(ListaActivity.this, "Error al obtener datos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}