package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DarkButtonBg
import com.example.ui.theme.DarkButtonBorder
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.ImmersiveBg
import com.example.ui.theme.TextSlate
import com.example.ui.theme.TextWhite
import com.example.util.AppIntentUtils

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaqeebTopBar(
    title: String,
    onContactWhatsApp: () -> Unit,
    onOpenProfile: (() -> Unit)? = null,
    onOpenOwnerDatabaseSecret: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var logoTapCount by remember { mutableIntStateOf(0) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkButtonBg)
                        .border(1.2.dp, EmeraldPrimary.copy(alpha = 0.7f), RoundedCornerShape(14.dp))
                        .clickable {
                            logoTapCount++
                            if (logoTapCount >= 10) {
                                logoTapCount = 0
                                Toast.makeText(context, "تم تفعيل وضع المالك بنجاح", Toast.LENGTH_SHORT).show()
                                onOpenOwnerDatabaseSecret?.invoke()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rqeebpro_logo_1791221152168),
                        contentDescription = "شعار رقيب برو",
                        modifier = Modifier.size(36.dp)
                    )
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "رقيب برو",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = " | " + title,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextWhite.copy(alpha = 0.9f)
                        )
                    }
                    Text(
                        text = "Rqeebpro Tech Mark • برمجة وتسويق",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSlate,
                        letterSpacing = 0.2.sp
                    )
                }
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                // Owner database icon is intentionally hidden from all normal users!
                // Accessed strictly via 10 consecutive taps on the app logo.
                if (onOpenProfile != null) {
                    IconButton(
                        onClick = onOpenProfile,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(DarkButtonBg)
                            .border(1.dp, DarkButtonBorder, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "حسابي واشتراكي",
                            tint = EmeraldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                IconButton(
                    onClick = {
                        AppIntentUtils.openWhatsApp(context, "مرحباً مركز رقيب للبرمجة والتسويق، أحتاج لمساعدة في جهازي/مشروعي.")
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(DarkButtonBg)
                        .border(1.dp, DarkButtonBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "محادثة واتساب",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(
                    onClick = {
                        AppIntentUtils.dialPhoneNumber(context)
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(DarkButtonBg)
                        .border(1.dp, DarkButtonBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "اتصال مباشر",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ImmersiveBg,
            titleContentColor = TextWhite
        )
    )
}

