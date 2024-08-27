package com.example.formins;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    protected EditText nomeEdt, apelidoEdt, emailEdt, contactoEdt, senhaEdt, confirmaSenhaEdt;
    private RadioGroup generoGroup;
    private Spinner paisSpinner;
    protected CheckBox termosCB;
    protected Button submeterBtn, limparBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEdt = findViewById(R.id.Nome);
        apelidoEdt = findViewById(R.id.Apelido);
        emailEdt = findViewById(R.id.Email);
        contactoEdt = findViewById(R.id.Contacto);
        generoGroup = findViewById(R.id.Genero);
        paisSpinner = findViewById(R.id.Pais);
        senhaEdt = findViewById(R.id.Senha);
        confirmaSenhaEdt = findViewById(R.id.confirmaSenha);
        termosCB = findViewById(R.id.Termos);
        submeterBtn = findViewById(R.id.Submeter);
        limparBtn = findViewById(R.id.Limpar);

        // adaptador para pegar os paises e para o spinner personalizado
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paises_array, R.layout.custom_spinner_selected);
        adapter.setDropDownViewResource(R.layout.custom_spinner);
        paisSpinner.setAdapter(adapter);

        limparBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparForm();
            }
        });

        submeterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comparaSenha()) {
                    try {
                        submeterForm();
                    } catch (Exception e) {
                        e.getCause();
                    }
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nenhuma acao necessaria
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                verificaCamposVazios();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nenhuma acao necessaria
            }
        };

        nomeEdt.addTextChangedListener(textWatcher);
        apelidoEdt.addTextChangedListener(textWatcher);
        contactoEdt.addTextChangedListener(textWatcher);
        emailEdt.addTextChangedListener(textWatcher);
        senhaEdt.addTextChangedListener(textWatcher);
        confirmaSenhaEdt.addTextChangedListener(textWatcher);

        verificaCamposVazios();
    }

    private void verificaCamposVazios() {

        String nome = nomeEdt.getText().toString().trim();
        String apelido = apelidoEdt.getText().toString().trim();
        String contacto = contactoEdt.getText().toString().trim();
        String email = emailEdt.getText().toString().trim();
        String senha = senhaEdt.getText().toString().trim();
        String confirmaSenha = confirmaSenhaEdt.getText().toString().trim();

        limparBtn.setEnabled(!nome.isEmpty() || !apelido.isEmpty() || !contacto.isEmpty() || !email.isEmpty() || !senha.isEmpty() || !confirmaSenha.isEmpty());
    }

    private void limparForm() {
        nomeEdt.setText("");
        apelidoEdt.setText("");
        emailEdt.setText("");
        contactoEdt.setText("");
        generoGroup.clearCheck();
        paisSpinner.setSelection(0);
        senhaEdt.setText("");
        confirmaSenhaEdt.setText("");
        termosCB.setChecked(false);
    }

    private void submeterForm() {

        if (TextUtils.isEmpty(nomeEdt.getText())) {
            showToast("Insira o Nome.");
            return;
        }
        if (TextUtils.isEmpty(apelidoEdt.getText())) {
            showToast("Insira o Apelido.");
            return;
        }
        if (TextUtils.isEmpty(emailEdt.getText())) {
            showToast("Insira o Email.");
            return;
        }
        if (TextUtils.isEmpty(contactoEdt.getText())) {
            showToast("Insira o Contacto.");
            return;
        }
        if (generoGroup.getCheckedRadioButtonId() == -1) {
            showToast("Por favor, selecione o gênero.");
            return;
        }
        if (paisSpinner.getSelectedItemPosition() == 0) {
            showToast("Selecione uma nacionalidade válida.");
            return;
        }
        if (senhaEdt.getText().toString().length() < 6) {
            showToast("A senha deve ter pelo menos 6 caracteres.");
            return;
        }
        if (!termosCB.isChecked()) {
            showToast("Você deve aceitar os termos e condições.");
            return;
        }

        showToast("Dados gravados com sucesso!");

        // TODO: Code to save the registration data
    }

    private boolean comparaSenha() {
        String senhaValida = senhaEdt.getText().toString().trim();
        String confirmaValida = confirmaSenhaEdt.getText().toString().trim();

        if (!senhaValida.equals(confirmaValida)) {
            confirmaSenhaEdt.setError("As senhas não são iguais!");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
