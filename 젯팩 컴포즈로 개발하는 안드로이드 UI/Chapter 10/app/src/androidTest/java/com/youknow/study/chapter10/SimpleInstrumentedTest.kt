package com.youknow.study.chapter10

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.youknow.study.chapter10.ui.screen.SimpleButtonDemo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleInstrumentedTest {

    @get:Rule
    var name = TestName()

    @get:Rule
    var rule = createComposeRule()

    @Before
    fun setup() {
        rule.setContent {
            SimpleButtonDemo()
        }
    }

    @Test
    fun testInitialLetterIsA() {
        rule.onNodeWithText("A").assertExists()
    }

    @Test
    fun testLetterAfterButtonClickIsB() {
        rule.onNodeWithText("A")
            .performClick()
            .assert(hasText("B"))
    }
}