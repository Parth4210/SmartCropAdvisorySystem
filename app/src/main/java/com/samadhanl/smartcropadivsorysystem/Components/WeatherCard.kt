package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samadhanl.smartcropadivsorysystem.R

@Composable
fun WeatherCard(weatherData: WeatherData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Icon(painter = painterResource(id = R.drawable.ic_weather_sun), contentDescription = "Weather condition", modifier = Modifier.size(48.dp))
                Text(text = weatherData.temperature, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(text = weatherData.condition, color = Color.White, fontSize = 16.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = R.drawable.ic_humidity), contentDescription = "Humidity", modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(text = weatherData.humidity, color = Color.White, fontSize = 16.sp)
                }
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = R.drawable.ic_thermostat), contentDescription = "High/Low temperature", modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(text = weatherData.highLow, color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
