package com.aaron.geminisample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = ""
    )

    var translatedText: String by mutableStateOf("")
        private set

    fun translate(inputText: String) {
        viewModelScope.launch {
            val prompt = "다음 문구를 한국어로 번역해주세요, 다른 부가적인 설명은 필요치 않습니다. 그냥 번역 결과만 알려주세요: '$inputText'"
            val response = generativeModel.generateContent(prompt)
            translatedText = response.text ?: ""
        }
    }
}