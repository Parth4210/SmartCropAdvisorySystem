package com.samadhanl.smartcropadivsorysystem.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FooterBtns() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {},
            modifier = Modifier.weight(1f).height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F5F0), contentColor = Color.Black)
        ) {
            Text(text = "Contact Us", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {},
            modifier = Modifier.weight(1f).height(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7F5F0), contentColor = Color.Black)
        ) {
            Text(text = "Soil Guide", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
        }
    }
}