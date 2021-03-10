package com.example.workingwithapi.testing

object LoginUtil {


    /**
     * the input is not valid if
     * ...email/password is empty
     * ...email must have '@''.'
     * ...password must have 8 characters
     * ...check email have capital letters
     */


    fun validateLoginInput(
        email : String,
        password : String
    ) : Boolean{

        if (email.isEmpty() || password.isEmpty()){
            return false
        }
        if (password.count()<=8){
            return false
        }
        if (! (email.contains('@') && email.contains('.'))){
            return false
        }
        if (haveCapital(email)){
            return false
        }
        return true
    }

    private fun haveCapital(email: String): Boolean {

        for ( i in email){
            if (i in 'A'..'Z'){
                return true
            }
        }
        return false
    }
}