package com.example.data

import com.example.model.ApnProfile

object ArabCarrierDatabase {

    val arabCountries = listOf(
        "الكل",
        "السعودية",
        "اليمن",
        "مصر",
        "الإمارات",
        "الكويت",
        "قطر",
        "سلطنة عمان",
        "البحرين",
        "العراق",
        "الأردن",
        "المغرب",
        "الجزائر",
        "تونس",
        "السودان",
        "ليبيا",
        "فلسطين",
        "سوريا",
        "لبنان"
    )

    fun getAllProfiles(): List<ApnProfile> = listOf(
        // 🇸🇦 السعودية
        ApnProfile(
            id = "sa_stc",
            carrierName = "اس تي سي (STC السعودية)",
            country = "السعودية",
            networkType = "5G / 4G LTE",
            apnName = "STC Jawalnet",
            apnValue = "jawalnet.com.sa",
            dialNumber = "*900#",
            instructions = "ادخل الاسم: STC ، الـ APN: jawalnet.com.sa ، اترك اسم المستخدم وكلمة المرور فارغين، ونوع APN: default,supl",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "لفك قفل SIM Lock للأجهزة المقفلة على STC: استخدم كود NCK المكون من 8 أو 16 رقماً عبر شاشة إدخال الشريحة الأجنبية، أو من وضع الاختبار ServiceMode *#0011# واختيار SIM UNLOCK.",
            mccMnc = "420-01"
        ),
        ApnProfile(
            id = "sa_mobily",
            carrierName = "موبايلي (Mobily)",
            country = "السعودية",
            networkType = "5G / 4G LTE",
            apnName = "Mobily Web",
            apnValue = "web1",
            dialNumber = "*1100#",
            instructions = "مسبق الدفع APN: web1 ، المفوتر APN: web2 ، اسم المستخدم وكلمة السر فارغين، نوع الترخيص: None أو PAP",
            unlockCode = "*#2263#",
            unlockInstructions = "لتثبيت الترددات وفك قفل الشبكة على راوترات وأجهزة موبايلي: استخدم كود *#2263# أو *#0808# لتمكين منفذ التشخيص DM Port وتمرير ملفات QCN/NVRAM.",
            mccMnc = "420-03"
        ),
        ApnProfile(
            id = "sa_zain",
            carrierName = "زين السعودية (Zain KSA)",
            country = "السعودية",
            networkType = "5G / 4G LTE",
            apnName = "Zain Data",
            apnValue = "zain",
            dialNumber = "*959#",
            instructions = "الاسم: Zain ، الـ APN: zain ، نوع APN: default,supl ، البروتوكول: IPv4/IPv6",
            unlockCode = "*#0808#",
            unlockInstructions = "أجهزة راوتر وهواتف زين المقفلة: إدخال كود إلغاء قفل الشبكة Network Control Key (NCK) عبر شريحة أخرى، أو إعادة كتابة الـ IMEI الأصلي وفك الباند المغلق.",
            mccMnc = "420-04"
        ),
        ApnProfile(
            id = "sa_salam",
            carrierName = "سلام موبايل (Salam Mobile)",
            country = "السعودية",
            networkType = "5G / 4G",
            apnName = "Salam Net",
            apnValue = "salam",
            dialNumber = "*1101#",
            instructions = "الاسم: Salam ، الـ APN: salam ، اسم المستخدم وكلمة السر فارغين",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "تفعيل Roaming الداخلي وقفل الشبكة التلقائي على أبراج الشريك المحلي.",
            mccMnc = "420-07"
        ),
        ApnProfile(
            id = "sa_virgin",
            carrierName = "فيرجن موبايل (Virgin Mobile KSA)",
            country = "السعودية",
            networkType = "5G / 4G",
            apnName = "Virgin Internet",
            apnValue = "virgin",
            dialNumber = "*100#",
            instructions = "الاسم: Virgin Mobile ، الـ APN: virgin ، نوع الـ APN: default",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "تأكد من تفعيل تجوال البيانات المحلي واستخدام كود فك الشفرة الافتراضي للشبكة الافتراضية.",
            mccMnc = "420-06"
        ),

        // 🇾🇪 اليمن
        ApnProfile(
            id = "ye_ymobile_4g",
            carrierName = "يمن موبايل 4G (Yemen Mobile LTE)",
            country = "اليمن",
            networkType = "4G LTE (VoLTE)",
            apnName = "Yemen Mobile 4G",
            apnValue = "ymobile",
            dialNumber = "#777",
            username = "ymobile",
            password = "v4g",
            instructions = "الاسم: Yemen Mobile ، الـ APN: ymobile ، اسم المستخدم: ymobile ، كلمة المرور: v4g ، نوع APN: default,supl ، بروتوكول APN: IPv4/IPv6",
            unlockCode = "##3424#",
            unlockInstructions = "لأجهزة فرايزون وسبرينت وأمريكا المقفلة على يمن موبايل: إدخال كود SPC: 000000 أو 342400 وتحويل وضع الشبكة إلى LTE/CDMA عبر *#*#4636#*#* مع كتابة ملف PRL 4G الرسمي.",
            mccMnc = "421-03"
        ),
        ApnProfile(
            id = "ye_ymobile_3g",
            carrierName = "يمن موبايل 3G (CDMA)",
            country = "اليمن",
            networkType = "3G EVDO / CDMA",
            apnName = "Yemen Mobile 3G",
            apnValue = "#777",
            dialNumber = "#777",
            username = "ymobile",
            password = "ymobile",
            instructions = "الاسم: YM3G ، الـ APN: #777 ، اسم المستخدم: ymobile ، كلمة المرور: ymobile ، رقم الاتصال: #777",
            unlockCode = "##3282#",
            unlockInstructions = "فك شفرة CDMA: كود برمجة MSL/SPC عبر برنامج DFS أو أداة رقيب برمجياً لتفعيل وضع RUIM وكتابة ملفات NVRAM.",
            mccMnc = "421-03"
        ),
        ApnProfile(
            id = "ye_you_4g",
            carrierName = "يو للاتصالات (YOU 4G / MTN سابقاً)",
            country = "اليمن",
            networkType = "4G LTE / 3G",
            apnName = "YOU Internet",
            apnValue = "internet",
            dialNumber = "*121#",
            instructions = "الاسم: YOU Internet ، الـ APN: internet ، اسم المستخدم وكلمة السر فارغين، نوع الـ APN: default",
            unlockCode = "*#06#",
            unlockInstructions = "فك شفرة أجهزة GSM المقفلة: إدخال كود NCK المكون من 8 أرقام، أو تفليش مودم متوافق لإلغاء قيود المشغل الأجنبي.",
            mccMnc = "421-02"
        ),
        ApnProfile(
            id = "ye_sabafon",
            carrierName = "سبأفون 4G/3G (SabaFon)",
            country = "اليمن",
            networkType = "4G LTE / 3G",
            apnName = "SabaFon Net",
            apnValue = "sabafon.net",
            dialNumber = "*121#",
            instructions = "الاسم: SabaFon ، الـ APN: sabafon.net ، نوع الـ APN: default ، أرسل 4G إلى 211 لتفعيل الباقة",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "ضبط نمط الشبكة على LTE/WCDMA وفك قفل شفرة الشبكة عبر تمرير كود NCK لفتح القفل الدولي.",
            mccMnc = "421-01"
        ),
        ApnProfile(
            id = "ye_ytelecom",
            carrierName = "واي للاتصالات (Y Telecom)",
            country = "اليمن",
            networkType = "4G / 3G",
            apnName = "Y Internet",
            apnValue = "y-internet",
            dialNumber = "*121#",
            instructions = "الاسم: Y Telecom ، الـ APN: y-internet ، نوع APN: default",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "تثبيت شبكة GSM/WCDMA وإلغاء قفل مزود الخدمة.",
            mccMnc = "421-04"
        ),

        // 🇪🇬 مصر
        ApnProfile(
            id = "eg_vodafone",
            carrierName = "فودافون مصر (Vodafone Egypt)",
            country = "مصر",
            networkType = "4G LTE / 3G",
            apnName = "Vodafone Internet",
            apnValue = "internet.vodafone.net",
            dialNumber = "*888*4#",
            instructions = "الاسم: Vodafone Net ، الـ APN: internet.vodafone.net ، اسم المستخدم: internet ، كلمة السر: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "للهواتف المقفلة على فودافون أوروبا أو مصر: إدخال كود NCK عبر نافذة SIM Network Unlock PIN، أو استخدام كود الخدمة *#7465625# لمعاينة أقفال الشبكة.",
            mccMnc = "602-02"
        ),
        ApnProfile(
            id = "eg_orange",
            carrierName = "أورنج مصر (Orange Egypt)",
            country = "مصر",
            networkType = "4G LTE / 3G",
            apnName = "Orange Web",
            apnValue = "mobinilweb",
            dialNumber = "*100#",
            instructions = "الاسم: Orange Web ، الـ APN: mobinilweb ، نوع APN: default",
            unlockCode = "*#0011#",
            unlockInstructions = "فك قفل SIM Lock لأجهزة Orange عبر استخراج كود فك الشفرة أو ضبط التردد B3/B1.",
            mccMnc = "602-01"
        ),
        ApnProfile(
            id = "eg_etisalat",
            carrierName = "اتصالات مصر (Etisalat Misr)",
            country = "مصر",
            networkType = "4G LTE / 3G",
            apnName = "Etisalat Web",
            apnValue = "etisalat",
            dialNumber = "*566#",
            instructions = "الاسم: Etisalat ، الـ APN: etisalat ، اسم المستخدم وكلمة السر فارغين",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة الشبكة وإتاحة استخدام جميع الشرائح عبر كود NCK.",
            mccMnc = "602-03"
        ),
        ApnProfile(
            id = "eg_we",
            carrierName = "وي المصرية للاتصالات (WE Telecom Egypt)",
            country = "مصر",
            networkType = "4G LTE",
            apnName = "WE Internet",
            apnValue = "internet.te.eg",
            dialNumber = "*015#",
            instructions = "الاسم: WE Net ، الـ APN: internet.te.eg ، نوع APN: default,supl",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "أجهزة راوتر وهواتف WE المقفلة: تمرير سوفت وير المصنع أو إدخال كود إلغاء القفل 16 رقماً.",
            mccMnc = "602-04"
        ),

        // 🇦🇪 الإمارات
        ApnProfile(
            id = "ae_etisalat",
            carrierName = "اتصالات إي آند (e& / Etisalat UAE)",
            country = "الإمارات",
            networkType = "5G / 4G LTE",
            apnName = "Etisalat 5G",
            apnValue = "etisalat",
            dialNumber = "*101#",
            instructions = "الاسم: Etisalat ، الـ APN: etisalat ، نوع APN: default,supl,hipri",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة أجهزة المشغل الأجنبي لاستقبال شبكة 5G في الإمارات عبر ضبط نمط NR/LTE.",
            mccMnc = "424-02"
        ),
        ApnProfile(
            id = "ae_du",
            carrierName = "دو (du Telecom UAE)",
            country = "الإمارات",
            networkType = "5G / 4G LTE",
            apnName = "du Internet",
            apnValue = "du",
            dialNumber = "*135#",
            instructions = "الاسم: du Web ، الـ APN: du ، اترك اسم المستخدم وكلمة السر فارغين",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك أقفال الشبكات الدولية وتفعيل مكالمات VoLTE عبر du.",
            mccMnc = "424-03"
        ),
        ApnProfile(
            id = "ae_virgin",
            carrierName = "فيرجن موبايل الإمارات (Virgin Mobile UAE)",
            country = "الإمارات",
            networkType = "5G / 4G",
            apnName = "Virgin UAE",
            apnValue = "virgin",
            dialNumber = "*106#",
            instructions = "الاسم: Virgin UAE ، الـ APN: virgin ، نوع APN: default",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة eSIM وتفعيل الباقة مباشرة.",
            mccMnc = "424-04"
        ),

        // 🇰🇼 الكويت
        ApnProfile(
            id = "kw_zain",
            carrierName = "زين الكويت (Zain Kuwait)",
            country = "الكويت",
            networkType = "5G / 4G LTE",
            apnName = "Zain Kuwait",
            apnValue = "zain",
            dialNumber = "*107#",
            instructions = "الاسم: Zain KW ، الـ APN: zain ، نوع APN: default",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل الشبكة NCK لراوترات وهواتف زين الكويت.",
            mccMnc = "419-02"
        ),
        ApnProfile(
            id = "kw_ooredoo",
            carrierName = "أوريدو الكويت (Ooredoo Kuwait)",
            country = "الكويت",
            networkType = "5G / 4G LTE",
            apnName = "Ooredoo KW",
            apnValue = "action.ooredoo.kw",
            dialNumber = "*121#",
            instructions = "الاسم: Ooredoo KW ، الـ APN: action.ooredoo.kw",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "تجاوز قفل مشغل SIM وتنشيط 5G.",
            mccMnc = "419-03"
        ),
        ApnProfile(
            id = "kw_stc",
            carrierName = "إس تي سي الكويت (stc Kuwait)",
            country = "الكويت",
            networkType = "5G / 4G LTE",
            apnName = "stc KW",
            apnValue = "viva.com.kw",
            dialNumber = "*500#",
            instructions = "الاسم: stc Kuwait ، الـ APN: viva.com.kw",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة الراوتر والهواتف عبر كود فك الشفرة المصنعي.",
            mccMnc = "419-04"
        ),

        // 🇶🇦 قطر
        ApnProfile(
            id = "qa_ooredoo",
            carrierName = "أوريدو قطر (Ooredoo Qatar)",
            country = "قطر",
            networkType = "5G / 4G LTE",
            apnName = "Ooredoo QA",
            apnValue = "gprs.web.qtel",
            dialNumber = "*123#",
            instructions = "الاسم: Ooredoo ، الـ APN: gprs.web.qtel",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إدخال كود NCK لإلغاء القفل الدولي واستقبال 5G.",
            mccMnc = "427-01"
        ),
        ApnProfile(
            id = "qa_vodafone",
            carrierName = "فودافون قطر (Vodafone Qatar)",
            country = "قطر",
            networkType = "5G / 4G LTE",
            apnName = "Vodafone QA",
            apnValue = "web.vodafone.com.qa",
            dialNumber = "*100#",
            instructions = "الاسم: Vodafone QA ، الـ APN: web.vodafone.com.qa",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة الأجهزة المقفلة عالمياً.",
            mccMnc = "427-02"
        ),

        // 🇴🇲 سلطنة عمان
        ApnProfile(
            id = "om_omantel",
            carrierName = "عمانتل (Omantel)",
            country = "سلطنة عمان",
            networkType = "5G / 4G LTE",
            apnName = "Omantel Net",
            apnValue = "omantel",
            dialNumber = "*123#",
            instructions = "الاسم: Omantel ، الـ APN: omantel",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك تشفير الشبكة وتثبيت نطاقات 5G عمانتل.",
            mccMnc = "422-02"
        ),
        ApnProfile(
            id = "om_ooredoo",
            carrierName = "أوريدو عمان (Ooredoo Oman)",
            country = "سلطنة عمان",
            networkType = "5G / 4G LTE",
            apnName = "Ooredoo Oman",
            apnValue = "isp.om",
            dialNumber = "*141#",
            instructions = "الاسم: Ooredoo OM ، الـ APN: isp.om",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إلغاء قفل بطاقة SIM عبر NCK.",
            mccMnc = "422-03"
        ),

        // 🇧🇭 البحرين
        ApnProfile(
            id = "bh_batelco",
            carrierName = "بتلكو البحرين (Batelco)",
            country = "البحرين",
            networkType = "5G / 4G LTE",
            apnName = "Batelco Net",
            apnValue = "batelco.com",
            dialNumber = "*122#",
            instructions = "الاسم: Batelco ، الـ APN: batelco.com",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرات الهواتف المستوردة لتشغيل بتلكو.",
            mccMnc = "426-01"
        ),
        ApnProfile(
            id = "bh_stc",
            carrierName = "إس تي سي البحرين (stc Bahrain)",
            country = "البحرين",
            networkType = "5G / 4G LTE",
            apnName = "stc Bahrain",
            apnValue = "viva",
            dialNumber = "*124#",
            instructions = "الاسم: stc BH ، الـ APN: viva",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك أقفال المشغل وتفعيل 5G.",
            mccMnc = "426-04"
        ),

        // 🇮🇶 العراق
        ApnProfile(
            id = "iq_asiacell",
            carrierName = "آسياسيل (Asiacell العراق)",
            country = "العراق",
            networkType = "4G LTE / 3G",
            apnName = "Asiacell Internet",
            apnValue = "asiacell",
            dialNumber = "*133#",
            instructions = "الاسم: Asiacell ، الـ APN: asiacell ، نوع APN: default",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرات أجهزة أمريكا وكوريا للعمل على أبراج آسياسيل.",
            mccMnc = "418-05"
        ),
        ApnProfile(
            id = "iq_zain",
            carrierName = "زين العراق (Zain Iraq)",
            country = "العراق",
            networkType = "4G LTE / 3G",
            apnName = "Zain Iraq",
            apnValue = "internet",
            dialNumber = "*217#",
            instructions = "الاسم: Zain IQ ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إلغاء قفل بطاقة SIM عبر NCK وكود *#*#4636#*#*.",
            mccMnc = "418-20"
        ),
        ApnProfile(
            id = "iq_korek",
            carrierName = "كورك تيليكوم (Korek Telecom)",
            country = "العراق",
            networkType = "4G LTE",
            apnName = "Korek Net",
            apnValue = "korek",
            dialNumber = "*211#",
            instructions = "الاسم: Korek ، الـ APN: korek",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة الشبكة للأجهزة المقفلة دولياً.",
            mccMnc = "418-40"
        ),

        // 🇯🇴 الأردن
        ApnProfile(
            id = "jo_zain",
            carrierName = "زين الأردن (Zain Jordan)",
            country = "الأردن",
            networkType = "5G / 4G LTE",
            apnName = "Zain Jordan",
            apnValue = "internet",
            dialNumber = "*111#",
            instructions = "الاسم: Zain JO ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل SIM Lock وتثبيت شبكة 5G.",
            mccMnc = "416-01"
        ),
        ApnProfile(
            id = "jo_orange",
            carrierName = "أورنج الأردن (Orange Jordan)",
            country = "الأردن",
            networkType = "5G / 4G LTE",
            apnName = "Orange Net",
            apnValue = "net",
            dialNumber = "*150#",
            instructions = "الاسم: Orange JO ، الـ APN: net",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرات الراوترات والأجهزة المقفلة.",
            mccMnc = "416-77"
        ),
        ApnProfile(
            id = "jo_umniah",
            carrierName = "أمنية الأردن (Umniah)",
            country = "الأردن",
            networkType = "5G / 4G LTE",
            apnName = "Umniah Net",
            apnValue = "internet",
            dialNumber = "*133#",
            instructions = "الاسم: Umniah ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك حظر الشبكة للأجهزة المستوردة.",
            mccMnc = "416-03"
        ),

        // 🇲🇦 المغرب
        ApnProfile(
            id = "ma_iam",
            carrierName = "اتصالات المغرب (Maroc Telecom - IAM)",
            country = "المغرب",
            networkType = "4G LTE / 3G",
            apnName = "IAM Web",
            apnValue = "www.iamgprs1.ma",
            dialNumber = "*580#",
            instructions = "الاسم: IAM ، الـ APN: www.iamgprs1.ma",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "Desimlockage: إدخال كود NCK لفك قفل أجهزة فرنسا وأوروبا على شبكة اتصالات المغرب.",
            mccMnc = "604-01"
        ),
        ApnProfile(
            id = "ma_orange",
            carrierName = "أورنج المغرب (Orange Maroc)",
            country = "المغرب",
            networkType = "4G LTE / 3G",
            apnName = "Orange Maroc",
            apnValue = "orange",
            dialNumber = "*111#",
            instructions = "الاسم: Orange MA ، الـ APN: orange",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرات أجهزة Orange Europe للعمل في المغرب.",
            mccMnc = "604-00"
        ),
        ApnProfile(
            id = "ma_inwi",
            carrierName = "إنوي (inwi المغرب)",
            country = "المغرب",
            networkType = "4G LTE",
            apnName = "inwi Net",
            apnValue = "www.inwi.ma",
            dialNumber = "*120#",
            instructions = "الاسم: inwi ، الـ APN: www.inwi.ma",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل رواتر وهواتف inwi لتشغيل كافة الشبكات.",
            mccMnc = "604-02"
        ),

        // 🇩🇿 الجزائر
        ApnProfile(
            id = "dz_mobilis",
            carrierName = "موبيليس الجزائر (Mobilis)",
            country = "الجزائر",
            networkType = "4G LTE / 3G",
            apnName = "Mobilis Internet",
            apnValue = "internet",
            dialNumber = "*600#",
            instructions = "الاسم: Mobilis ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "Déblocage réseau: كود NCK لفك شفرات الهواتف الأجنبية على موبيليس.",
            mccMnc = "603-01"
        ),
        ApnProfile(
            id = "dz_djezzy",
            carrierName = "جيزي الجزائر (Djezzy)",
            country = "الجزائر",
            networkType = "4G LTE / 3G",
            apnName = "Djezzy Web",
            apnValue = "djezzy.internet",
            dialNumber = "*707#",
            instructions = "الاسم: Djezzy ، الـ APN: djezzy.internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل الشبكة عبر رمز NCK وتفعيل 4G.",
            mccMnc = "603-02"
        ),
        ApnProfile(
            id = "dz_ooredoo",
            carrierName = "أوريدو الجزائر (Ooredoo Algeria)",
            country = "الجزائر",
            networkType = "4G LTE / 3G",
            apnName = "Ooredoo DZ",
            apnValue = "internet",
            dialNumber = "*151#",
            instructions = "الاسم: Ooredoo DZ ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل مودم وهواتف Ooredoo.",
            mccMnc = "603-03"
        ),

        // 🇹🇳 تونس
        ApnProfile(
            id = "tn_tt",
            carrierName = "اتصالات تونس (Tunisie Telecom)",
            country = "تونس",
            networkType = "4G LTE / 3G",
            apnName = "TT Internet",
            apnValue = "internet.tn",
            dialNumber = "*140#",
            instructions = "الاسم: TT ، الـ APN: internet.tn",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة الهاتف عبر كود NCK للعمل في تونس.",
            mccMnc = "605-02"
        ),
        ApnProfile(
            id = "tn_ooredoo",
            carrierName = "أوريدو تونس (Ooredoo Tunisia)",
            country = "تونس",
            networkType = "4G LTE / 3G",
            apnName = "Ooredoo TN",
            apnValue = "internet.ooredoo.tn",
            dialNumber = "*124#",
            instructions = "الاسم: Ooredoo TN ، الـ APN: internet.ooredoo.tn",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إلغاء قفل المشغل للأجهزة المستوردة.",
            mccMnc = "605-01"
        ),

        // 🇸🇩 السودان
        ApnProfile(
            id = "sd_zain",
            carrierName = "زين السودان (Zain Sudan)",
            country = "السودان",
            networkType = "4G LTE / 3G",
            apnName = "Zain SD",
            apnValue = "internet",
            dialNumber = "*555#",
            instructions = "الاسم: Zain SD ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة الشبكة للأجهزة المقفلة على مشغلات دولية.",
            mccMnc = "634-01"
        ),
        ApnProfile(
            id = "sd_mtn",
            carrierName = "إم تي إن السودان (MTN Sudan)",
            country = "السودان",
            networkType = "4G LTE / 3G",
            apnName = "MTN Sudan",
            apnValue = "internet",
            dialNumber = "*123#",
            instructions = "الاسم: MTN SD ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إلغاء حظر الشبكة وضبط إعدادات 4G.",
            mccMnc = "634-02"
        ),
        ApnProfile(
            id = "sd_sudani",
            carrierName = "سوداني (Sudani Telecom)",
            country = "السودان",
            networkType = "4G LTE / 3G",
            apnName = "Sudani Net",
            apnValue = "sudani",
            dialNumber = "*111#",
            instructions = "الاسم: Sudani ، الـ APN: sudani",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرات أجهزة سوداني لتشغيل كل الشبكات.",
            mccMnc = "634-07"
        ),

        // 🇱🇾 ليبيا
        ApnProfile(
            id = "ly_libyana",
            carrierName = "ليبيانا (Libyana)",
            country = "ليبيا",
            networkType = "4G LTE",
            apnName = "Libyana 4G",
            apnValue = "libyana.net",
            dialNumber = "*121#",
            instructions = "الاسم: Libyana ، الـ APN: libyana.net",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرات أجهزة أمريكا وأوروبا للعمل على شبكة ليبيانا 4G.",
            mccMnc = "606-00"
        ),
        ApnProfile(
            id = "ly_almadar",
            carrierName = "المدار الجديد (Almadar Aljadid)",
            country = "ليبيا",
            networkType = "4G LTE",
            apnName = "Almadar Net",
            apnValue = "almadar.net",
            dialNumber = "*140#",
            instructions = "الاسم: Almadar ، الـ APN: almadar.net",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل SIM Lock وتشغيل ترددات المدار 4G.",
            mccMnc = "606-01"
        ),

        // 🇵🇸 فلسطين
        ApnProfile(
            id = "ps_jawwal",
            carrierName = "جوال فلسطين (Jawwal)",
            country = "فلسطين",
            networkType = "3G / 4G",
            apnName = "Jawwal Net",
            apnValue = "internet",
            dialNumber = "*111#",
            instructions = "الاسم: Jawwal ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل أجهزة الجوال المستوردة.",
            mccMnc = "425-05"
        ),
        ApnProfile(
            id = "ps_ooredoo",
            carrierName = "أوريدو فلسطين (Ooredoo Palestine)",
            country = "فلسطين",
            networkType = "3G / 4G",
            apnName = "Ooredoo Palestine",
            apnValue = "ooredoo",
            dialNumber = "*100#",
            instructions = "الاسم: Ooredoo PS ، الـ APN: ooredoo",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إلغاء قفل المشغل SIM وتثبيت الترددات.",
            mccMnc = "425-06"
        ),

        // 🇸🇾 سوريا
        ApnProfile(
            id = "sy_syriatel",
            carrierName = "سيريتل (Syriatel 4G)",
            country = "سوريا",
            networkType = "4G LTE / 3G",
            apnName = "Syriatel 4G",
            apnValue = "net.syriatel.com",
            dialNumber = "*111#",
            instructions = "الاسم: Syriatel ، الـ APN: net.syriatel.com",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "جمركة وتفعيل شفرة الأجهزة الأجنبية وتثبيت 4G.",
            mccMnc = "417-01"
        ),
        ApnProfile(
            id = "sy_mtn",
            carrierName = "إم تي إن سوريا (MTN Syria)",
            country = "سوريا",
            networkType = "4G LTE / 3G",
            apnName = "MTN Net",
            apnValue = "internet",
            dialNumber = "*111#",
            instructions = "الاسم: MTN SY ، الـ APN: internet",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك قفل المشغل وتفعيل بيانات الجوال.",
            mccMnc = "417-02"
        ),

        // 🇱🇧 لبنان
        ApnProfile(
            id = "lb_touch",
            carrierName = "تاتش لبنان (touch Lebanon)",
            country = "لبنان",
            networkType = "4G LTE",
            apnName = "touch Web",
            apnValue = "touch",
            dialNumber = "*111#",
            instructions = "الاسم: touch ، الـ APN: touch",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "فك شفرة SIM Lock للأجهزة الأوروبية والأمريكية.",
            mccMnc = "415-03"
        ),
        ApnProfile(
            id = "lb_alfa",
            carrierName = "ألفا لبنان (Alfa Lebanon)",
            country = "لبنان",
            networkType = "4G LTE",
            apnName = "Alfa Net",
            apnValue = "alfa",
            dialNumber = "*111#",
            instructions = "الاسم: Alfa ، الـ APN: alfa",
            unlockCode = "*#*#4636#*#*",
            unlockInstructions = "إدخال كود NCK لفتح القفل واستقبال 4G.",
            mccMnc = "415-01"
        )
    )
}
