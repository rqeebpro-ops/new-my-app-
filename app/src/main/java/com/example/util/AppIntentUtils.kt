package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast

object AppIntentUtils {

    const val RAQEEB_PHONE = "785502919" // Hotline / Support
    const val RAQEEB_WHATSAPP = "967785502919" // Direct WhatsApp
    const val RAQEEB_EMAIL = "abdulrqeebfarag2029@gmail.com"

    // App Owner Details (for registration notifications & subscription activation)
    const val APP_OWNER_PHONE = "785502919"
    const val APP_OWNER_WHATSAPP = "967785502919"

    // Secret 4-digit activation PIN codes known to the app owner & generated via Owner Dashboard
    const val ACTIVATION_SECRET_PIN = "8492"
    val VALID_ACTIVATION_PINS = mutableSetOf("8492", "7735", "7829")

    fun registerActivationPin(pin: String) {
        val clean = pin.trim()
        if (clean.length == 4) {
            VALID_ACTIVATION_PINS.add(clean)
        }
    }

    fun isValidActivationPin(pin: String): Boolean {
        return pin.trim() in VALID_ACTIVATION_PINS
    }

    // Official Payment Accounts
    const val KURAMI_ACCOUNT = "3001845477"
    const val JEEP_WALLET_PHONE = "782916997"
    const val ONE_CASH_PHONE = "782916997"
    const val JAWWALI_PHONE = "782916997"

    const val SERVICE_PRICE_YER = 2000
    const val SERVICE_PRICE_SAR = 15
    const val SERVICE_PRICE_USD = 4

    fun openWhatsApp(context: Context, message: String) {
        openWhatsAppWithNumber(context, RAQEEB_WHATSAPP, message)
    }

    fun openWhatsAppToOwner(context: Context, message: String) {
        openWhatsAppWithNumber(context, APP_OWNER_WHATSAPP, message)
    }

    fun openWhatsAppWithNumber(context: Context, phoneNumber: String, message: String) {
        try {
            val encodedMsg = Uri.encode(message)
            val url = "https://wa.me/$phoneNumber?text=$encodedMsg"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "يرجى تثبيت تطبيق واتساب للتواصل المباشر", Toast.LENGTH_SHORT).show()
        }
    }

    fun dialPhoneNumber(context: Context, phone: String = RAQEEB_PHONE) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(phone)}")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            copyToClipboard(context, "رقم الهاتف", phone)
            Toast.makeText(context, "تم نسخ الرقم إلى الحافظة", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(context: Context, subject: String, body: String) {
        sendEmail(context, RAQEEB_EMAIL, subject, body)
    }

    fun sendEmail(context: Context, toEmail: String, subject: String, body: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$toEmail")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            copyToClipboard(context, "البريد الإلكتروني", toEmail)
            Toast.makeText(context, "تم نسخ البريد الإلكتروني: $toEmail", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWebUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            copyToClipboard(context, "الرابط", url)
            Toast.makeText(context, "تم نسخ الرابط: $url", Toast.LENGTH_SHORT).show()
        }
    }

    fun openApnSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APN_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val fallbackIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(fallbackIntent)
            } catch (_: Exception) {
                Toast.makeText(context, "تعذر فتح إعدادات الشبكة تلقائياً، يرجى فتحها من الضبط", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openLocaleSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val intent = Intent(Settings.ACTION_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (_: Exception) {
                Toast.makeText(context, "يرجى فتح إعدادات اللغة من ضبط الهاتف", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openSecuritySettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_SECURITY_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val intent = Intent(Settings.ACTION_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (_: Exception) {
                Toast.makeText(context, "يرجى فتح إعدادات الأمان والقفل من ضبط الجهاز", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openDeveloperSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val intent = Intent(Settings.ACTION_DEVICE_INFO_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (_: Exception) {
                Toast.makeText(context, "لتفعيل خيارات المطور: الضبط > حول الهاتف > رقم الإصدار 7 مرات", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun openNetworkOperatorSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            openApnSettings(context)
        }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "تم نسخ: $label", Toast.LENGTH_SHORT).show()
    }
}
