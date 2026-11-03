// Reo Fenua : couleurs du fenua (lagon, océan, sable, rouge hibiscus). Dérivé de bitchat-android (GPL-3.0).
package com.bitchat.android.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

// Standard UI semantics live in Material so stock components and custom Bitchat composables
// share one source of truth. LocalBitchatPalette below only supplies app-specific extra colors.
internal val DarkBitchatColorScheme = darkColorScheme(
    primary = Color(0xFF4FC3CC),
    onPrimary = Color(0xFF00363B),
    primaryContainer = Color(0xFF0B4F56),
    onPrimaryContainer = Color(0xFFC6EEF1),
    secondary = Color(0xFF8EC5E8),
    onSecondary = Color(0xFF062B42),
    secondaryContainer = Color(0xFF0B3C5D),
    onSecondaryContainer = Color(0xFFD2E3EE),
    tertiary = DarkBitchatPalette.accentOrange,
    onTertiary = Color.Black,
    background = Color(0xFF071E2E),
    onBackground = Color(0xFFF3ECE0),
    surface = Color(0xFF0B2A3E),
    onSurface = Color(0xFFF3ECE0),
    surfaceVariant = Color(0xFF123850),
    onSurfaceVariant = Color(0xFFA9BFCC),
    outline = Color(0xFF2C5570),
    outlineVariant = Color(0xFF1A4058),
    error = Color(0xFFFF6B7F),
    onError = Color.Black
)

internal val LightBitchatColorScheme = lightColorScheme(
    primary = Color(0xFF0E7C86),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD7ECEE),
    onPrimaryContainer = Color(0xFF0B3C5D),
    secondary = Color(0xFF0B3C5D),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD2E3EE),
    onSecondaryContainer = Color(0xFF072638),
    tertiary = LightBitchatPalette.accentOrange,
    onTertiary = Color.Black,
    background = Color(0xFFF7EFE2),
    onBackground = Color(0xFF1B2A33),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B2A33),
    surfaceVariant = Color(0xFFEFE5D5),
    onSurfaceVariant = Color(0xFF3D5260),
    outline = Color(0xFFC9BBA4),
    outlineVariant = Color(0xFFE6DCCB),
    error = Color(0xFFC8102E),
    onError = Color.White
)

@Composable
fun BitchatTheme(
    darkTheme: Boolean? = null,
    content: @Composable () -> Unit
) {
    // App-level override from ThemePreferenceManager
    val themePref by ThemePreferenceManager.themeFlow.collectAsState(initial = ThemePreference.System)
    val shouldUseDark = when (darkTheme) {
        true -> true
        false -> false
        null -> when (themePref) {
            ThemePreference.Dark -> true
            ThemePreference.Light -> false
            ThemePreference.System -> isSystemInDarkTheme()
        }
    }

    val colorScheme = if (shouldUseDark) DarkBitchatColorScheme else LightBitchatColorScheme
    val palette = if (shouldUseDark) DarkBitchatPalette else LightBitchatPalette

    val view = LocalView.current
    SideEffect {
        (view.context as? Activity)?.window?.let { window ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.setSystemBarsAppearance(
                    if (!shouldUseDark) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = if (!shouldUseDark) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else 0
            }
            window.navigationBarColor = colorScheme.background.toArgb()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
        }
    }

    CompositionLocalProvider(LocalBitchatPalette provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
