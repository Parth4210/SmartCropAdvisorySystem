package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun MarketPriceCard() {
    Card(
        modifier = Modifier.padding(16.dp).width(368.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.trend), contentDescription = "Market trend", modifier = Modifier.size(20.dp))
                Text(modifier = Modifier.padding(start = 15.dp), text = "Today's Market Prices", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.Black)
            }
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Card(modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E4DC))) {}
                Spacer(modifier = Modifier.width(12.dp))
                Card(modifier = Modifier.weight(1f).height(100.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E4DC))) {}
            }
            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F5F0), contentColor = Color.Black)
            ) {
                Text(text = "View All Prices", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}