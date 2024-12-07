package br.com.yuriduarte.cheirinhodecafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.yuriduarte.cheirinhodecafe.ui.theme.CheirinhoDeCaféTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.jeffersonbm.fazenda02.DAO
import com.google.firebase.Firebase
import com.google.firebase.database.database

class TelaInsercao : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheirinhoDeCaféTheme {
                TelaDeInsercao()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDeInsercao() {

    val banco = DAO(Firebase.database.getReference("Cafe"))

    val cafe = Cafe("4", "Pé", EnumNota.Doce, 1, 1, 1, 1, 111.2)

    banco.inserir_atualizar(cafe)

    var nome by remember { mutableStateOf("") }

    // Lista de opções do enum
    val opcoes = EnumNota.values().toList()
    var preco by remember { mutableStateOf("") }
    var nota by remember {mutableStateOf(opcoes[0])}

    var expandido by remember { mutableStateOf(false) }

    // Lista de opções
    val notas = listOf(1, 2, 3, 4, 5)

    // Estado para rastrear a seleção
    var selecionado by remember { mutableStateOf(notas[0]) }

    Scaffold (
        Modifier.fillMaxHeight()
    ){
        paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){

            OutlinedTextField(
                value = nome,
                onValueChange = {nome = it},
                label = { Text("Nome")},
                placeholder = { Text("Digite aqui")}
            )

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = !expandido }
            ) {
                OutlinedTextField(
                    value = nota.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Perfil do Café") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    opcoes.forEach { opcao ->
                        DropdownMenuItem(
                            text = { Text(opcao.name) },
                            onClick = {
                                nota = opcao
                                expandido = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Aroma:",
                style = MaterialTheme.typography.bodyLarge, // Define estilo do texto
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, // Espaçamento uniforme entre os botões
                verticalAlignment = Alignment.CenterVertically, // Centraliza verticalmente os elementos
                modifier = Modifier.fillMaxWidth() // Ocupa toda a largura disponível
            ) {
                notas.forEach { opcao ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { selecionado = opcao } // Permite selecionar clicando na linha
                    ) {
                        RadioButton(
                            selected = selecionado == opcao,
                            onClick = { selecionado = opcao } // Atualiza o estado
                        )
                        Text(
                            text = opcao.toString(),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = preco,
                onValueChange = {preco = it},
                label = { Text("Preço")},
                placeholder = { Text("Digite aqui")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
    }
}

@Preview
@Composable
fun PreviewTelaDeInsercao() {
    TelaDeInsercao()
}