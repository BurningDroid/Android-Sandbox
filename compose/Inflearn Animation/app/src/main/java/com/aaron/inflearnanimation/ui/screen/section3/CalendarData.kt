package com.aaron.inflearnanimation.ui.screen.section3

object CalendarData {
    val spendingData = (1..31).associateWith {
        listOf(
            IncomeOutcomeModel("수입", (1000..5000).random()),
            IncomeOutcomeModel("지출", (1000..5000).random())
        )
    }
}