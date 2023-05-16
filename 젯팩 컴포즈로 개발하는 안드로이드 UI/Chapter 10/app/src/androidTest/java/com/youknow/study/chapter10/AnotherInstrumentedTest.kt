package com.youknow.study.chapter10

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.youknow.study.chapter10.ui.screen.ImageDemo
import com.youknow.study.chapter10.ui.screen.SimpleButtonDemo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AnotherInstrumentedTest {

    @get:Rule
    var name = TestName()

    @get:Rule
    var rule = createComposeRule()

    @Test
    fun testImage() {
        var contentDesc = ""
        rule.setContent {
            ImageDemo()
            contentDesc = stringResource(id = R.string.airport_shuttle_desc)
        }

        rule.onNodeWithContentDescription(contentDesc)
            .assertExists()
            .assertWidthIsEqualTo(128.dp)
    }
}