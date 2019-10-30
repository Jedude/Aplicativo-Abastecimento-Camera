package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.modelo.abastecimento;
import com.example.myapplication.modelo.abastecimentoDAO;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import fr.ganfra.materialspinner.MaterialSpinner;

import static java.sql.Types.NULL;

public class telaCadastro extends AppCompatActivity {


    private abastecimento objetoAbastecimento;
    private String idDoAbastecimento;
    private TextInputEditText etQuilometragem;
    private TextInputEditText etLitros;
    private TextInputEditText etData;
    private MaterialSpinner spPosto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        spPosto = findViewById(R.id.spPosto);
        etQuilometragem = findViewById(R.id.etQuilometragem);
        etLitros = findViewById(R.id.etLitros);
        etData = findViewById(R.id.etData);
        etData.setKeyListener(null);

        //populando o spinner com opções
        String[] opcoesPosto = getResources().getStringArray(R.array.opcoes_posto); //obtendo a partir do XML
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesPosto);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPosto.setAdapter(adapter);


        idDoAbastecimento = getIntent().getStringExtra("idAbastecimento");
        if(idDoAbastecimento == null){
            objetoAbastecimento = new abastecimento();

            Button btExcluir = findViewById(R.id.btExcluir);
            btExcluir.setVisibility( View.INVISIBLE );
        }else{
            objetoAbastecimento = abastecimentoDAO.obterInstancia().obterObjetoPeloId(idDoAbastecimento);

            etQuilometragem.setText( String.valueOf(objetoAbastecimento.getQuilometragem()));
            etLitros.setText( String.valueOf(objetoAbastecimento.getLitrosAbastecidos()));

          for(int n = 0; n < spPosto.getAdapter().getCount(); n++){
                if ((spPosto.getAdapter().getItem(n)).toString().equals( objetoAbastecimento.getEscolhaPosto())){
                    spPosto.setSelection(n+1);
                    break;
                }
          }
//            if(spPosto.getSelectedItemPosition()==1){
//                spPosto.setSelection(1);
//            }
//            if((objetoAbastecimento.getPosto()).equals("Texaco")){
//                spPosto.setSelection(1);
//            }
//            if(objetoAbastecimento.getPosto().equals("Shell")){
//                spPosto.setSelection(2);
//            }
//            if(objetoAbastecimento.getPosto().equals("Petrobras")){
//                spPosto.setSelection(3);
//            }
//            if(objetoAbastecimento.getPosto().equals("Ipiranga")){
//                spPosto.setSelection(4);
//            }
//            else{
//                spPosto.setSelection(5);
//            }

            DateFormat formatador = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
            String dataSelecionadaFormatada = formatador.format( objetoAbastecimento.getData().getTime() );
            etData.setText( dataSelecionadaFormatada );
        }
    }

    public void salvar(View v){
//        int t=abastecimentoDAO.obterInstancia().obterLista().size();
//        if(objetoAbastecimento.getQuilometragem()<abastecimentoDAO.obterInstancia().obterLista().get(t).getQuilometragem()
//         && idDoAbastecimento== null ){
//            Toast.makeText(this, "Quilometragem invalida!", Toast.LENGTH_LONG).show();
//        }else {
            objetoAbastecimento.setEscolhaPosto(spPosto.getSelectedItem().toString());
            //objetoAbastecimento.setEscolhaPosto(spPosto.getAdapter().toString());

            objetoAbastecimento.setLitrosAbastecidos(Double.parseDouble(etLitros.getText().toString()));
            objetoAbastecimento.setQuilometragem(Double.parseDouble(etQuilometragem.getText().toString()));
            if (idDoAbastecimento == null) {
                abastecimentoDAO.obterInstancia().adicionarNaLista(objetoAbastecimento);
                setResult(301);
            } else {
                int posicaoDoObjeto = abastecimentoDAO.obterInstancia().atualizaNaLista(objetoAbastecimento);
                Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
                intencaoDeFechamentoDaActivityFormulario.putExtra("posicaoDoObjetoEditado", posicaoDoObjeto);
                setResult(300, intencaoDeFechamentoDaActivityFormulario);
            }
            finish();
//        }

    }

    public void selecionarData(View v){
        Calendar dataPadrao = objetoAbastecimento.getData();;
        if(dataPadrao == null){
            dataPadrao = Calendar.getInstance();
        }

        DatePickerDialog dialogoParaPegarData = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar dataSelecionada = Calendar.getInstance();
                        dataSelecionada.set(year,month,dayOfMonth);
                        objetoAbastecimento.setData(dataSelecionada);

                        DateFormat formatador = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
                        String dataSelecionadaFormatada = formatador.format( dataSelecionada.getTime() );
                        etData.setText( dataSelecionadaFormatada );
                    }
                },
                dataPadrao.get(Calendar.YEAR),
                dataPadrao.get(Calendar.MONTH),
                dataPadrao.get(Calendar.DAY_OF_MONTH)
        );
        dialogoParaPegarData.show();
    }

    public void excluir(View v){
        new AlertDialog.Builder(this)
                .setTitle("Você tem certeza?")
                .setMessage("Você quer mesmo excluir?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int posicaoDoObjeto = abastecimentoDAO.obterInstancia().excluiDaLista(objetoAbastecimento);
                        Intent intencaoDeFechamentoCadastro = new Intent();
                        intencaoDeFechamentoCadastro.putExtra("posicaoDoObjetoExcluido", posicaoDoObjeto);
                        setResult(302, intencaoDeFechamentoCadastro);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate( R.menu.formulario_menu, menu );
//
//        if(idDoAbastecimento == null){
//            menu.removeItem(R.id.menuExcluir);
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() == R.id.menuSalvar){
//            salvar();
//        }
//        if (item.getItemId() == R.id.menuExcluir){
//            excluir();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    String caminhoDaFoto = null;
    private File criarArquivoParaSalvarFoto() throws IOException {
        String nomeFoto = UUID.randomUUID().toString();
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fotografia = File.createTempFile(nomeFoto,".jpg",diretorio);
        caminhoDaFoto = fotografia.getAbsolutePath();
        return fotografia;
    }

    public void abrirCamera(View v){
        Intent intecaoAbrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File arquivoDaFoto = null;
        try {
            arquivoDaFoto = criarArquivoParaSalvarFoto();
        } catch (IOException ex) {
            Toast.makeText(this, "Não foi possível criar arquivo para foto", Toast.LENGTH_LONG).show();
        }
        if (arquivoDaFoto != null) {
            Uri fotoURI = FileProvider.getUriForFile(this,
                    "com.example.myapplication.fileprovider",
                    arquivoDaFoto);
            intecaoAbrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
            startActivityForResult(intecaoAbrirCamera, 1);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                objetoAbastecimento.setCaminhoFotografia( caminhoDaFoto );
                atualizaFotografiaNaTela();

            }
        }
    }

    private void atualizaFotografiaNaTela(){
        if(objetoAbastecimento.getCaminhoFotografia() != null){
            ImageView ivFotografia = findViewById(R.id.ivFotografia);
            ivFotografia.setImageURI(Uri.parse(objetoAbastecimento.getCaminhoFotografia()));
        }
    }


}
