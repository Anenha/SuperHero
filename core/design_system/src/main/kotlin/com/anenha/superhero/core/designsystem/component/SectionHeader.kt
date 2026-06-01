package com.anenha.superhero.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.theme.LabelCaps
import com.anenha.superhero.core.designsystem.theme.OnSurfaceColor
import com.anenha.superhero.core.designsystem.theme.PrimaryColor
import com.anenha.superhero.core.designsystem.theme.SuperheroTheme

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
    barColor: Color = PrimaryColor,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(18.dp)
                .background(barColor)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = LabelCaps.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = OnSurfaceColor
            )
        )
    }
}

@Preview
@Composable
private fun SectionHeaderPreview() {
    SuperheroTheme {
        SectionHeader(text = "POWER STATS")
    }
}
