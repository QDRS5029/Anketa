package com.example.anketa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                UserQuestionnaire()
            }
        }
    }
}

@Composable
fun UserQuestionnaire() {
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf(25f) }
    var gender by rememberSaveable { mutableStateOf("") }
    var subscribeToNews by rememberSaveable { mutableStateOf(false) }
    var showSummary by rememberSaveable { mutableStateOf(false) }
    var nameError by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Анкета пользователя",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Имя",
                style = MaterialTheme.typography.bodyLarge
            )
            TextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = it.isBlank()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Введите ваше имя") },
                isError = nameError,
                singleLine = true
            )
            if (nameError) {
                Text(
                    text = "Пожалуйста, введите ваше имя",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Возраст",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Slider(
                    value = age,
                    onValueChange = { age = it },
                    valueRange = 1f..100f,
                    steps = 98,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${age.toInt()}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Пол",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Мужской",
                        onClick = { gender = "Мужской" }
                    )
                    Text(
                        text = "Мужской",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Женский",
                        onClick = { gender = "Женский" }
                    )
                    Text(
                        text = "Женский",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = subscribeToNews,
                onCheckedChange = { subscribeToNews = it }
            )
            Text(
                text = "Хочу получать новости",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    showSummary = true
                    nameError = false
                } else {
                    nameError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = name.isNotBlank() && gender.isNotBlank()
        ) {
            Text(
                text = "Отправить",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (showSummary) {
            SummaryCard(
                name = name,
                age = age.toInt(),
                gender = gender,
                subscribeToNews = subscribeToNews,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SummaryCard(
    name: String,
    age: Int,
    gender: String,
    subscribeToNews: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Сводка",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Имя: $name",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Возраст: $age",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Пол: $gender",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Подписка: ${if (subscribeToNews) "да" else "нет"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserQuestionnairePreview() {
    UserQuestionnaire()
}