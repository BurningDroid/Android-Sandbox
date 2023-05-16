package com.youknow.study.chapter10

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.youknow.study.chapter10.ui.screen.BoxButtonDemo
import com.youknow.study.chapter10.ui.screen.SimpleButtonDemo
import com.youknow.study.chapter10.ui.screen.TAG1
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BoxButtonDemoTest {

    @get:Rule
    var name = TestName()

    @get:Rule
    var rule = createComposeRule()

    @Before
    fun setup() {
        rule.setContent {
            BoxButtonDemo()
        }
    }

    @Test
    fun testInitialLetterIsA() {
        rule.onNodeWithTag(TAG1).assertExists()
    }
}