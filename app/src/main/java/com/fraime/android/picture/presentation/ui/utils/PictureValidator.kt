package com.fraime.android.picture.presentation.ui.utils

import android.util.Patterns
import java.util.regex.Pattern

class PictureValidator {

    fun isValidEmail(email: String?) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isStringContainNumber(text: String?) : Boolean {
        val pattern = Pattern.compile(".*\\d.*")
        val matcher = pattern.matcher(text)
        return matcher.matches()
    }

    fun isStringLowerAndUpperCase(text: String?) : Boolean {
        val lowerCasePattern = Pattern.compile(".*[a-z]*.")
        val upperCasePattern = Pattern.compile(".*[A-Z]*.")
        val lowerCasePatternMatcher = lowerCasePattern.matcher(text)
        val upperCasePatternMatcher = upperCasePattern.matcher(text)
        return if(!lowerCasePatternMatcher.matches()) {
            false
        } else upperCasePatternMatcher.matches()
    }

    fun isStringContainSpecialCharacter(text: String?) : Boolean {
        val specialCharacterPattern = Pattern.compile("[~!@#$%^&*]")
        val specialCharacterMatcher = specialCharacterPattern.matcher(text)
        return specialCharacterMatcher.find()
    }

    fun isStringDoNotContainSpecialCharacter(text: String?) : Boolean {
        val specialCharacterPattern = Pattern.compile(""".*[~!@#$%^*()=+{}:'"<>,.?/|`-].*""")
        val matcher = specialCharacterPattern.matcher(text)
        return matcher.matches()
    }
}