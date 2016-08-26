package com.facci.registrovotantesmaoo;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityMAOO extends AppCompatActivity {

    DBHelper dbSQLITE;
    EditText txtID, txtNombre, txtApellido, txtRecintoElectoral, txtAnoNacimiento;
    Button btnInsertar, btnModificar, btnBorrar, btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_maoo);

        dbSQLITE = new DBHelper(this);
    }

    public void insertarClick (View v){
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtRecintoElectoral = (EditText) findViewById(R.id.txtRecintoElectoral);
        txtAnoNacimiento = (EditText) findViewById(R.id.txtAnoNacimiento);

        boolean estaInsertado = dbSQLITE.insertar(txtNombre.getText().toString(), txtApellido.getText().toString(), txtRecintoElectoral.getText().toString(), Integer.parseInt(txtAnoNacimiento.getText().toString()));

        if (estaInsertado)
            Toast.makeText(MainActivityMAOO.this,"Datos ingresados correctamente",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityMAOO.this,"Ocurrio un error",Toast.LENGTH_SHORT).show();
    }

    public void consultarClick (View v){

        Cursor res = dbSQLITE.selectConsultar();
        if (res.getCount() == 0){
            mostrarMensaje("Error","No se encontraron registros!!");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()){
            buffer.append("Id : "+ res.getString(0)+"\n");
            buffer.append("Nombre : "+ res.getString(1)+"\n");
            buffer.append("Apellido : "+ res.getString(2)+"\n");
            buffer.append("Recinto Electoral : "+ res.getString(3)+"\n");
            buffer.append("Año Nacimiento : "+ res.getInt(4)+"\n\n");
        }

        mostrarMensaje("Registros", buffer.toString());
    }

    public void mostrarMensaje(String titulo, String Mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(Mensaje);
        builder.show();
    }

    public void modificarClick(View v){

        txtID = (EditText) findViewById(R.id.txtID);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtRecintoElectoral = (EditText) findViewById(R.id.txtRecintoElectoral);
        txtAnoNacimiento = (EditText) findViewById(R.id.txtAnoNacimiento);

        boolean estaAcutalizado = dbSQLITE.modificar(txtID.getText().toString(), txtNombre.getText().toString(), txtApellido.getText().toString(),
                txtRecintoElectoral.getText().toString(), Integer.parseInt(txtAnoNacimiento.getText().toString()));

        if (estaAcutalizado){
            Toast.makeText(MainActivityMAOO.this,"Registro Actualizado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityMAOO.this,"ERROR: Registro NO Actualizado",Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarClick(View v){

        txtID = (EditText) findViewById(R.id.txtID);

        Integer registrosEliminados = dbSQLITE.eliminar(txtID.getText().toString());

        if(registrosEliminados > 0 ){
            Toast.makeText(MainActivityMAOO.this,"Registro(s) Eliminado(s)",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityMAOO.this,"ERROR: Registro no eliminado",Toast.LENGTH_SHORT).show();
        }

    }

}
