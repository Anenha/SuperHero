package com.anenha.superhero.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.anenha.superhero.core.designsystem.R
import com.anenha.superhero.core.designsystem.theme.BackgroundColor
import com.anenha.superhero.core.designsystem.theme.LabelCaps
import com.anenha.superhero.core.designsystem.theme.PrimaryColor
import com.anenha.superhero.core.designsystem.theme.SuperheroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VanguardTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = LabelCaps.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = PrimaryColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = PrimaryColor
        )
    )
}

@Preview
@Composable
private fun VanguardTopBarPreview() {
    SuperheroTheme {
        VanguardTopBar(title = "ARCHIVE", onBackClick = {})
    }
}
