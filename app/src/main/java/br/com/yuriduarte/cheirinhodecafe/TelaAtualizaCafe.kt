package br.com.yuriduarte.cheirinhodecafe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.jeffersonbm.fazenda02.DAO
import br.com.yuriduarte.cheirinhodecafe.EnumNota.Doce
import br.com.yuriduarte.cheirinhodecafe.EnumNota.Especiarias
import br.com.yuriduarte.cheirinhodecafe.EnumNota.Floral
import br.com.yuriduarte.cheirinhodecafe.EnumNota.Frutado
import br.com.yuriduarte.cheirinhodecafe.ui.theme.CheirinhoDeCaféTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

class TelaAtualizaCafe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheirinhoDeCaféTheme {
                AtualizaCafe(intent)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtualizaCafe(intent: Intent){

    val banco = DAO(Firebase.database.getReference("Cafe"))

    var nome by remember { mutableStateOf("") }

    // Lista de opções do enum
    val opcoes = EnumNota.entries
    var nota by remember { mutableStateOf(opcoes[0]) }

    var expandido by remember { mutableStateOf(false) }

    // Lista de opções
    val notas = listOf(1, 2, 3, 4, 5)

    var aroma by remember { mutableIntStateOf(notas[0]) }
    var acidez by remember { mutableIntStateOf(notas[0]) }
    var amargor by remember { mutableIntStateOf(notas[0]) }
    var sabor by remember { mutableIntStateOf(notas[0]) }

    var preco by remember { mutableStateOf("") }

    val id = intent.getStringExtra("idcafe")
    nome = intent.getStringExtra("nome").toString()
    nota = toEnumNota(intent.getStringExtra("nota").toString())

    // TODO: Achar uma forma de inicializar
    //aroma = notas[intent.getStringExtra("aroma").toString().toInt() - 1]
    //acidez = notas[intent.getStringExtra("acidez").toString().toInt()  - 1]
    //amargor = notas[intent.getStringExtra("amargor").toString().toInt() - 1]
    //sabor = notas[intent.getStringExtra("sabor").toString().toInt() - 1]

    preco = intent.getStringExtra("preco").toString()

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
                label = { Text("Nome") },
                placeholder = { Text("Digite aqui") }
            )

            ExposedDropdownMenuBox(
                expanded = expandido,
                onExpandedChange = { expandido = !expandido }
            ) {
                OutlinedTextField(
                    value = nota.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Notas") },
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
                        modifier = Modifier.clickable { aroma = opcao } // Permite selecionar clicando na linha
                    ) {
                        RadioButton(
                            selected = aroma == opcao,
                            onClick = { aroma = opcao } // Atualiza o estado
                        )
                        Text(
                            text = opcao.toString(),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Text(
                text = "Acidez:",
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
                        modifier = Modifier.clickable { acidez = opcao } // Permite selecionar clicando na linha
                    ) {
                        RadioButton(
                            selected = acidez == opcao,
                            onClick = { acidez = opcao } // Atualiza o estado
                        )
                        Text(
                            text = opcao.toString(),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Text(
                text = "Amargor:",
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
                        modifier = Modifier.clickable { amargor = opcao } // Permite selecionar clicando na linha
                    ) {
                        RadioButton(
                            selected = amargor == opcao,
                            onClick = { amargor = opcao } // Atualiza o estado
                        )
                        Text(
                            text = opcao.toString(),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Text(
                text = "Sabor:",
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
                        modifier = Modifier.clickable { sabor = opcao } // Permite selecionar clicando na linha
                    ) {
                        RadioButton(
                            selected = sabor == opcao,
                            onClick = { sabor = opcao } // Atualiza o estado
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
                label = { Text("Preço") },
                placeholder = { Text("Digite aqui") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row {
                Button(
                    onClick = {

                        try {
                            val cafe = Cafe(id.toString(), nome, nota, aroma, acidez, amargor, sabor, preco.toDouble())

                            banco.inserir_atualizar(cafe)

                            Log.i("Teste", "Atualizado " + id)

                            finish()
                        }

                        catch (e: Exception){
                            Log.i("Teste", e.toString())
                        }
                    }
                ) {
                    Text("Atualizar")
                }
            }

        }
    }

}

fun finish() {
    finish()
}

fun toEnumNota(nota: String): EnumNota{
    if (nota == "Doce")
        return Doce
    else if (nota == "Floral")
        return Floral
    else if (nota == "Frutado")
        return Frutado
    else
        return Especiarias
}
