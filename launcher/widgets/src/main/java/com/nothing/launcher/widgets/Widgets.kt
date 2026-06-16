package com.nothing.launcher.widgets

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nothing.core.data.AppModel
import com.nothing.core.theme.NothingDimens
import com.nothing.launcher.home.MonochromeAppIcon
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min

// -----------------------------------------------------------------------------
// Clock Widget (4×2)
// -----------------------------------------------------------------------------
@Composable
fun ClockWidget(modifier: Modifier = Modifier) {
    var timeText by remember { mutableStateOf(currentTime()) }
    var dateText by remember { mutableStateOf(currentDate()) }

    LaunchedEffect(Unit) {
        while (true) {
            timeText = currentTime()
            dateText = currentDate()
            delay(60_000L)
        }
    }

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Text(
            text = timeText,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = dateText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

private fun currentTime(): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

private fun currentDate(): String =
    SimpleDateFormat("EEE, dd MMM", Locale.getDefault()).format(Date()).uppercase()

// -----------------------------------------------------------------------------
// Compact Clock Widget (2×1)
// -----------------------------------------------------------------------------
@Composable
fun CompactClockWidget(modifier: Modifier = Modifier) {
    var timeText by remember { mutableStateOf(currentTime()) }
    var dayName by remember { mutableStateOf(currentDay()) }

    LaunchedEffect(Unit) {
        while (true) {
            timeText = currentTime()
            dayName = currentDay()
            delay(60_000L)
        }
    }

    Column(modifier = modifier) {
        Text(
            text = timeText,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = dayName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

private fun currentDay(): String =
    SimpleDateFormat("EEE", Locale.getDefault()).format(Date()).uppercase()

// -----------------------------------------------------------------------------
// Date Widget (2×1)
// -----------------------------------------------------------------------------
@Composable
fun DateWidget(modifier: Modifier = Modifier) {
    val date = Date()
    val dayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(date)
    val dayName = SimpleDateFormat("EEE", Locale.getDefault()).format(date).uppercase()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Text(
            text = dayNumber,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = dayName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

// -----------------------------------------------------------------------------
// Battery Widget (2×1)
// -----------------------------------------------------------------------------
@Composable
fun BatteryWidget(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var level by remember { mutableIntStateOf(getBatteryLevel(context)) }

    LaunchedEffect(Unit) {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(null, filter)?.let { intent ->
            level = getBatteryLevel(intent)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "$level%",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = dotBar(level),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

private fun dotBar(level: Int): String {
    val segments = 10
    val filled = (level / 100f * segments).toInt().coerceIn(0, segments)
    val empty = segments - filled
    return "●".repeat(filled) + "○".repeat(empty)
}

private fun getBatteryLevel(context: Context): Int {
    val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    return intent?.let { getBatteryLevel(it) } ?: 0
}

private fun getBatteryLevel(intent: Intent): Int {
    val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    return if (level >= 0 && scale > 0) (level * 100 / scale) else 0
}

// -----------------------------------------------------------------------------
// Step Counter Widget (2×2)
// -----------------------------------------------------------------------------
@Composable
fun StepWidget(
    steps: Int = 0,
    goal: Int = 10_000,
    modifier: Modifier = Modifier
) {
    val progress = min(steps.toFloat() / goal, 1f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val strokeWidth = 8.dp.toPx()
                val radius = size.minDimension / 2f - strokeWidth

                drawArc(
                    color = MaterialTheme.colorScheme.outline,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )
                drawArc(
                    color = MaterialTheme.colorScheme.onBackground,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )
            }
            Text(
                text = "$steps",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = "Steps",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

// -----------------------------------------------------------------------------
// Weather Widget (2×2)
// -----------------------------------------------------------------------------
@Composable
fun WeatherWidget(
    temperature: Int = 22,
    city: String = "London",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$temperature°",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "C",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackgroundMuted
            )
        }
        Text(
            text = city,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

// -----------------------------------------------------------------------------
// Music Now Playing Widget (4×2)
// -----------------------------------------------------------------------------
@Composable
fun MusicWidget(
    track: String = "Not Playing",
    artist: String = "",
    modifier: Modifier = Modifier
) {
    val isPlaying = track.isNotBlank() && track != "Not Playing"
    if (!isPlaying) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Text(
            text = track,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = artist,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted
        )
    }
}

// -----------------------------------------------------------------------------
// App Shortcuts Widget (2×2)
// -----------------------------------------------------------------------------
@Composable
fun ShortcutWidget(
    apps: List<AppModel> = emptyList(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(NothingDimens.WidgetCornerRadius)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Shortcuts",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackgroundMuted,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            apps.take(4).forEach { app ->
                MonochromeAppIcon(
                    app = app,
                    size = 48.dp,
                    onClick = {
                        val intent = context.packageManager.getLaunchIntentForPackage(app.packageName)
                            ?.apply { flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK }
                        if (intent != null) context.startActivity(intent)
                    }
                )
            }
        }
    }
}
