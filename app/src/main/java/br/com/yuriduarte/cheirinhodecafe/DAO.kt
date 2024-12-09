package br.com.yuriduarte.cheirinhodecafe

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class DAO (banco : DatabaseReference){
    var banco : DatabaseReference

    init{
        this.banco = banco
    }

    fun inserir_atualizar(cafe: Cafe){
        this.banco.child(cafe.id).setValue(cafe)
    }

    fun mostrarDados(callback: (ArrayList<Cafe>) -> Unit) {
        val listaCafe = ArrayList<Cafe>()
        this.banco.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val gson = Gson()
                    for (i in snapshot.children) {
                        val json = gson.toJson(i.value)
                        val cafe = gson.fromJson(json, Cafe::class.java)
                        listaCafe.add(cafe)
                    }
                    callback(listaCafe) // Retorna a lista carregada pelo callback
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Teste", "Erro: $error")
            }
        })
    }

    fun getId(callback: (String) -> Unit) {
        val nextIdRef = this.banco // Referência ao nó "nextId"

        nextIdRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Obtém o valor atual de "nextId" como Int
                    val id = snapshot.getValue(Int::class.java) ?: 0
                    val newid = id + 1
                    Log.i("Teste", "nextId atualizado para $newid")

                    // Atualiza o valor de "nextId" para newid
                    nextIdRef.setValue(newid).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i("Teste", "nextId atualizado para $newid")
                            callback(id.toString()) // Retorna o valor atual antes do incremento
                        } else {
                            Log.e("Teste", "Erro ao atualizar nextId: ${task.exception?.message}")
                            callback("")
                        }
                    }
                } else {
                    // Caso o nó não exista, inicializa com 1
                    nextIdRef.setValue(1).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i("Teste", "nextId inicializado com 1")
                            callback("0") // Retorna "0" como valor inicial
                        } else {
                            Log.e("Teste", "Erro ao inicializar nextId: ${task.exception?.message}")
                            callback("")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Teste", "Erro ao acessar o nó: ${error.message}")
                callback("") // Em caso de erro, retorna null
            }
        })
    }


    fun informacoes(callback: (MutableList<String>) -> Unit){
        val info = mutableListOf<String>("nenhum", "Nenhum Café", "Nenhum Café", "Nenhum Café", "Nenhum Café")
        var qttCafe = 0
        var caro = 0.0
        var medPreco = 0.0
        var maisAroma = 0
        var menosAcidez = 10

        this.banco.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val gson = Gson()
                    for (i in snapshot.children) {
                        val json = gson.toJson(i.value)
                        val cafe = gson.fromJson(json, Cafe::class.java)

                        if(cafe.preco > caro){
                            caro = cafe.preco
                            info[1] = cafe.id
                        }

                        medPreco += cafe.preco

                        if(cafe.aroma > maisAroma){
                            maisAroma = cafe.aroma
                            info[3] = cafe.id
                        }

                        if(cafe.acidez < menosAcidez){
                            menosAcidez = cafe.acidez
                            info[4] = cafe.id
                        }

                        qttCafe++
                    }

                    medPreco /= qttCafe

                    info[0] = qttCafe.toString()

                    info[2] = medPreco.toString()

                }

                callback(info)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Teste", "Erro: $error")
            }
        })



    }

    fun excluir(registro : String){
        this.banco.child(registro).removeValue()
    }
}