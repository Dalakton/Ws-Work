package me.project.wswork.ui.register

sealed class ValidationStateRegister {

    // atributos fields representas os campos da tela de regisro, nome , email e numero
    //que recebe uma lista de um par de chave e valores , a string vai ser o identificador do campo
    // e o Int o id da String de erro.

     object Authenticated : ValidationStateRegister()
     class InvalidValidation(val fields: List<Pair<String, Int>>) : ValidationStateRegister()
}
