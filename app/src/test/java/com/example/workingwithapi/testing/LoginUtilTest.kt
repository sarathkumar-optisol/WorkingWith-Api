package com.example.workingwithapi.testing


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginUtilTest{


    @Test
    fun `empty email and password returns false`(){

        val result = LoginUtil.validateLoginInput(
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty email returns false`(){

        val result = LoginUtil.validateLoginInput(
            "",
            "12345678"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`(){

        val result = LoginUtil.validateLoginInput(
            "androidtest@gmail.com",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid email and password returns false`(){

        val result = LoginUtil.validateLoginInput(
            "AndroidTest@Gmail.com",
            "123456789"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid email and password returns true`(){

        val result = LoginUtil.validateLoginInput(
            "androidtest@gmail.com",
            "123456789"
        )
        assertThat(result).isTrue()
    }

}