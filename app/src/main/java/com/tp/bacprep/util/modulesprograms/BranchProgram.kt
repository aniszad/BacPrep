package com.tp.bacprep.util.modulesprograms

import android.os.Parcelable
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.util.Constants
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat

@Parcelize
open class BranchProgram:Parcelable {
    open val BRANCH_NAME = ""

    val SCI_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Science", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Mathématique", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Physique", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Histoire", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Géographie", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Anglais", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Français", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("Arabe", Constants.SCIENCE_BRANCH,listOf()),
        ModuleProgress("S.Islamique", Constants.SCIENCE_BRANCH, listOf()),
        ModuleProgress("Philo", Constants.SCIENCE_BRANCH,listOf())
    )

    val SCI_MODULES_AND_COEF : List<Pair<String, Int>> = listOf(
        Pair("Science", 6),
        Pair("Physique", 5),
        Pair("Mathématique", 5),
        Pair("Arabe", 3),
        Pair("Français", 2),
        Pair("Anglais", 2),
        Pair("Philosophie", 2),
        Pair("Histoire et Géographie", 2),
        Pair("Tamazight", 2),
        Pair("Science Islamique", 2),
        Pair("Sport", 1)
    )

    open fun getScienceProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الوحدة التعلمية 1: آليات تركيب البروتين\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات",
            "الوحدة التعلمية 2: العلاقة بين بنية ووظيفة البروتين\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات",
            "الوحدة التعلمية 3: دور البروتينات في التحفيز إنزيمي\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات",
            "الوحدة التعلمية 4: دور البروتينات في الدفاع عن الذات\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات",
            "الوحدة التعلمية 5: دور البروتينات في إيصال العصبي\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات",
            "الوحدة التعلمية 1: آليات تحويل الطاقة الضوئية إلى طاقة كيميائية كامنة\nاملجال التعلمي الثاني: تحويل الطاقة على مستوى مافوق البنية الخلوية",
            "الوحدة التعلمية 2: آليات تحويل الطاقة الكيميائية الكامنة في الجزيئات العضوية إلى طاقة قابلة للاستعمال (ATP)\nاملجال التعلمي الثاني: تحويل الطاقة على مستوى مافوق البنية الخلوية",
            "الوحدة التعلمية 3: حوصلة التحوالت الطاقوية على مستوى الخلية\nاملجال التعلمي الثاني: تحويل الطاقة على مستوى مافوق البنية الخلوية",
            "الوحدة التعلمية 1: بنية الكرة ألأرضية\nاملجال التعلمي الثالث: التكتونية العامة",
            "الوحدة التعلمية 2: النشاط التكتوني والظواهر والبنيات الجيولوجية املرتبطة به\nاملجال التعلمي الثالث: التكتونية العامة"
        )
        val items = hashMapOf(
            "الوحدة التعلمية 1: آليات تركيب البروتين\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات" to listOf(
                "- مقر تركيب البروتين في الخلية.",
                "- الوسيط الناقل للمعلومة الوراثية.",
                "- آلية الاستنساخ.",
                "- الشفرة الوراثية.",
                "- آلية الترجمة."
            ),
            "الوحدة التعلمية 2: العلاقة بين بنية ووظيفة البروتين\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات" to listOf(
                "- العلاقة بين البنية والتخصص الوظيفي للبروتين."
            ),
            "الوحدة التعلمية 3: دور البروتينات في التحفيز إنزيمي\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات" to listOf(
                "- التخصص الوظيفي للبروتين في التحفيز الإنزيمي."
            ),
            "الوحدة التعلمية 4: دور البروتينات في الدفاع عن الذات\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات" to listOf(
                "- الذات والألذات.",
                "- دور البروتينات في حالة الرد المناعي الخلطي.",
                "- دور البروتينات في حالة الرد المناعي الخلوي.",
                "- تحفيز الخلايا اللمفاوية.",
                "- فقدان المناعة المكتسبة."
            ),
            "الوحدة التعلمية 5: دور البروتينات في إيصال العصبي\nاملجال التعلمي الأول: التخصص الوظيفي للبروتينات" to listOf(
                "- آليات النقل المشبكي.",
                "- كمون الراحة.",
                "- كمون العمل.",
                "- آلية الإدماج العصبي.",
                "- تأثير المخدرات."
            ),
            "الوحدة التعلمية 1: آليات تحويل الطاقة الضوئية إلى طاقة كيميائية كامنة\nاملجال التعلمي الثاني: تحويل الطاقة على مستوى مافوق البنية الخلوية" to listOf(
                "- مقر التركيب الضوئي ومراحله."
            ),
            "الوحدة التعلمية 2: آليات تحويل الطاقة الكيميائية الكامنة في الجزيئات العضوية إلى طاقة قابلة للاستعمال (ATP)\nاملجال التعلمي الثاني: تحويل الطاقة على مستوى مافوق البنية الخلوية" to listOf(
                "- تحويل الطاقة الكيميائية الكامنة في الوسط الهوائي.",
                "- تحويل الطاقة الكيميائية الكامنة في الوسط الأهوائي."
            ),
            "الوحدة التعلمية 3: حوصلة التحوالت الطاقوية على مستوى الخلية\nاملجال التعلمي الثاني: تحويل الطاقة على مستوى مافوق البنية الخلوية" to listOf(
                "- حوصلة التحوالت الطاقوية على مستوى الخلية."
            ),
            "الوحدة التعلمية 1: بنية الكرة ألأرضية\nاملجال التعلمي الثالث: التكتونية العامة" to listOf(
                "- بنية الكرة الأرضية."
            ),
            "الوحدة التعلمية 2: النشاط التكتوني والظواهر والبنيات الجيولوجية املرتبطة به\nاملجال التعلمي الثالث: التكتونية العامة" to listOf(
                "- حركات الصفائح التكتونية.",
                "- الظواهر المرتبطة بالبناء على مستوى الظهرة.",
                "- إختفاء اللوح المحيطي، والظواهر المرتبطة به."
            )
        )
        return Pair(groupList, items)
    }
    open fun getMathProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الدوال العددية (الشتقاقية والاستمرارية)",
            "الدالتان الأسية واللوغاريتمية",
            "الدوال العددية (النهايات)",
            "التزايد المقارن",
            "المتتاليات العددية",
            "الدوال الأصلية الحساب التكاملي",
            "الإحتمالات",
            "الأعداد مركبة",
            "التحويلات النقطية",
            "الهندسة في الفضاء",
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "العدد المشتق والمماس تعريف استمرار دالة على مجال",
                "مبرهنة القيم المتوسطة واستعمالها في إثبات وجود حلول للمعادلة f(x) = k ",
                " المشتقات المتتابعة، حساب مشتق دالة مر ّكبة",
                "استعمال المشتقات لدراسة خواص دالة والمنحنى الممثل لها (اتجاه تغيّر دالة على مجال، التقريب الخطي، نقطة االنعطاف،...)",
                "توظيف المشتقات لدراسة اتجاه تغيّر دوال ناطقة، صماء)",
                "توظيف المشتقات لدراسة الدوال المثلثية x -> sin x ; x -> cos x ; t -> a sin (wt + b)",
                "توظيف المشتقات لدراسة الدوال المثلثية",
                "ايجاد حلا لمعادلة تفاضلية من الشكل حيث دالة مألوفة"
                ),
            groupList[1] to listOf(
                "دراسة الدالة الأّسية النيبرية وتوظيف خواصها في حل معادالت ومتراجحات.",
                "توظيف خواص دوال أسية",
                "exp°uدراسة الدالة",
                "دراسة الدالة اللوغاريتمية النيبرية وتوظيف خواصها في حل معادالت ومتراجحات.",
                "دراسة الدالة ln°u ، تعريف اللوغاريتم العشري",
                "حل معادالت تفاضلية من الشكل y'=ay+b",
            ),
            groupList[2] to listOf(
                " حساب نهاية منتهية أو غير منتهية لدالة عند الحدود (المنتهية أو غير المنتهية), لمجالات مجموعة تعريف. المستقيمات المقاربة الموازية للمحورين.",
                "حساب نهاية باستعمال المبرهنات المتعلقة بالعمليات على النهايات",
                "حساب نهاية باستعمال المقارنة أو الحصر ومركب دالتين",
                "دراسة السلوك التقاربي لدالة، المستقيم المقارب المائل",
            ),
            groupList[3] to listOf(
                "دراسة القوى والجذور النونية وتوظيف خواصهما.",
                "معرفة وتفسير النهايات",
                "دراسة دوال كثيرات حدود، ناطقة، صماء، دوال القوى. وحل مشكالت باستعمالها",
                "دراسة دوال أسية، اللوغاريتم، دوال القوى وحل مشكالت باستعمالها",
                ),
            groupList[4] to listOf(
                "توليد متتالية عددية: استعمال التمثيل البياني لتخمين سلوك ونهاية متتالية عددية",
                "استعمال التمثيل البياني لتخمين سلوك ونهاية متتالية عددية.",
                "التذكير بالمتتالية الحسابية والمتتالية الهندسية",
                "االستدالل بالتراجع: إثبات خاصية بالتراجع",
                "خواص المتتاليات: دراسة سلوك ونهاية متتالية",
                "المتتاليتان المتجاورتان: تعريف ومفهوم متتاليتين متجاورتين",
            ),
            groupList[5] to listOf(
                "تعرف الدالة الأصلية لدالة على مجال والخواص.",
                "تعيين دالة أصلية لدالة مستمرة على مجال. تعيين دوال أصلية لدوال مألوفة",
                "تعيين الدالة أصلية التي تأخذ قيمة y0 من أجل قيمة x0 للمتغيّر",
                "حل معادالت تفاضلية من الشكل:y'' = f(x),y' = f(x)",
                "المقاربة والتعريف",
                "توظيف خواص التكامل لحساب مساحة سطح معطى",
                "مفهوم القيمة المتوسطة لدالة على مجال وحصرها",
                "استعمال التكامل بالتجزئة",
                "توظيف الحساب التكاملي لحساب دوال أصلية",
                "حساب حجم لمجسمات بسيطة",
                "توظيف الحساب التكاملي لحل مشكلات بسيطة",
            ),
            groupList[6] to listOf(
                "الإحتمالات المتساوية على مجموعة منتهية: إيجاد قانون احتمال لمتغيّر عشوائي. ",
                "حل مسائل في الإحتمالات توظف المتغيرات العشوائية، قانون احتمالها، التباين، االنحراف المعياري واألمل الرياضياتي ",
                "المبدأ الأساسي للعد: العّد باستخدام المبدأ األساسي للعّد )المجموع وال ُجداء(",
                "إستخراج بعض قوانين التحليل التوفيقي (التوفيقات)",
                "دستور ثنائي الحّد.",
                "الإحتمالات الشرطية: التعّرف على استقلال أو ارتباط حادثتين.",
                "توظيف دستور الإحتمالات الكلية لحل مسائل في الإحتمالات تتعلق بسحب أكثر من وعاء",
            ),
            groupList[7] to listOf(
                "المجموعة C : إجراء العمليات الحسابية على األعداد المرّكبة",
                "استعمال خواص مرافق عدد مر ّكب، حساب طويلة عدد مرّكب.",
                "تعيين الجذرين التربيعيين لعدد مركب.",
                "حل في C، معادلة من الدرجة الثانية ذات معاملات حقيقية.",
                "حل في C، معادالت يؤول حلها إلى حل معادلة من الدرجة الثانية ذات معاملات حقيقية.",
                "الشكل المثلثي لعدد مركب غير معدوم: حساب عمدة لعدد مركب غير معدوم",
                "الإنتقال من الشكل الجبري إلى الشكل المثلثي و العكس.",
                "ترميز أولير:exp(i a)",
                "التعبير عن خواص ألشكال هندسية باستعمال األعداد المرّكبة.",
                " توظيف خواص الطويلة والعمدة لحل مسائل في الأعداد المر ّكبة وفي الهندسة",
                "توظيف دستور موافر لحل مسائل في األعداد المر ّكبة وفي الهندسة.",

                ),
            groupList[8] to listOf(
                "تعيين الكتابة المركبة للتحويلات النقطية الإنسحاب, التحاكي,الدوران.",
                "التعرف عن تحويل انطلاقا من الكتابة المركبة",
                "توظيف الأعداد المركبة لبرهان خواص الانسحاب, تحاكيات, الدوران و التحاكي.",
                "التشابهات المستوية المباشرة",
                "التعبير عن تشابه مباشر بالأعداد المركبة",
                "تركيب تشابهين مباشرين",
                "تعيين التحليل القانوني لتشابه مباشر بواسطة الأعداد المركبة",
            ),
            groupList[9] to listOf(
                "الجداء السلمي",
                "توظيف الجداء السلمي لتعيين معادلة لمستو",
                "توظيف الجداء السلمي لحساب المسافة بين نقطة و مستو",
                "توظيف الجداء السلمي لتعيين مجموعات نقط",
                "المستقيمات والمستويات في الفضاء",
                "الإنتقال من جملة معادلتين لمستقيم أو معادلة لمستو إلى تمثيل وسيطي والعكس",
                "الأوضاع النسبية: تحديد الوضع النسبي لمستويين، لمستقيم ومستو، لمستقيمين",
                "تعيين تقاطع مستويين، مستقيم ومستو، مستقيمين. تقاطع 3 مستويات",
            )
        )
        return Pair(groupList, items)
    }
    open fun getPhysicsProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الوحدة الأولى: المتابعة الزمنية لتحول كميائي",
            "الوحدة الخامسة: تطور جملة ميكانيكية",
            "الوحدة الثالثة: دراسة ظواهر كهربائية",
            "الوحدة الرابعة: تطور جملة كميائية نحو حالة التوازن",
            "الوحدة الثانية: دراسة تحولات نووية",
            "الوحدة السادسة: مراقبة تطور جملة كميائية"
        )

// Define your itemsList (elements) for the ExpandableListView adapter
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "مفاهيم أساسية في الكمياء",
                "المدة المستغرقة في تحول جملة كيميائية",
                "التحولات السريعة والتحولات البطيئة",
                "المتابعة الزمنية لتحول كميائي",
                "متابعة تحول كيميائي عن طريق قياس الناقلية",
                "متابعة تطور جملة كيميائية عن طريق المعايرة",
                "سرعة التفاعل",
                "زمن نصف التفاعل",
                "العوامل الحركية",
                "التركيز الابتدائي المتفاعل",
                "التفسير المجهري",
                "الوساطة",
                "أهمية العوامل الحركية",
                "تأثير التركيز المولي للمتفاعلات",
                "تأثير درجة الحرارة",
                "أهمية الوسيط"
            ),
            groupList[1] to listOf(
                "مفاهيم أساسية في الميكانيك والطاقة",
                "مقاربة تاريخية للميكانيك نيوتن",
                "القوانين الثلاثة لنيوتن ومفهوم التسارع",
                "الدراسة العامة للحركة",
                "حركة الأقمار الاصطناعية والكواكب",
                "خواص الحركة الدائرية المنتظمة",
                "الحركة الدائرية المنتظمة للكواكب والأقمار الاصطناعية",
                "قوانين كبلر",
                "دراسة حركة السقوط الشاقولي لجسم صلب في الهواء",
                "دراسة حركة السقوط الحقيقي لجسم صلب في الهواء",
                "دراسة حركة السقوط الحر",
                "تطبيق القانون الثاني لنيوتن",
                "تطبيق مبدأ انحفاظ الطاقة",
                "السقوط الحقيقي للأجسام في الهواء",
                "السقوط الحر والقذائف",
                "حركة مركز عطالة جسم على مستوى",
                "حدود ميكانيك نيوتن",
                "النسبية من غاليلي إلى إينشتاين",
                "التطور الكمي"
            ),
            groupList[2] to listOf(
                "مفاهيم أساسية في الكهرباء",
                "ثنائي القطب RC",
                "المكثفات وثنائي القطب RC",
                "خصائص المكثفة",
                "شحن وتفريغ مكثفة",
                "تطور التوتر الكهربائي بين طرفي المكثفة",
                "الطاقة المخزنة في مكثفة",
                "ثنائي القطب RL",
                "الوشائع وثنائي القطب RL",
                "وصف الوشيعة وتصرفها في جزء من دارة",
                "تطور شدة التيار الكهربائي المار في وشعية تحريضية",
                "الطاقة المخزنة في وشيعة"
            ),
            groupList[3] to listOf(
                "الأحماض والأسس والتوازن الكميائي",
                "محلول مائي PH",
                "تعريف ال PH والخصائص المميزة له",
                "طرق تعيين PH محلول مائي",
                "محلول حمضي ومحلول أساسي",
                "حمض قوي وحمض ضعيف",
                "أساس قوي وأساس ضعيف",
                "تطور جملة كيميائية نحو حالة التوازن",
                "مقارنة التقدم النهائي والتقدم الأعظمي",
                "تأثير الحالة الابتدائية للجملة على حالة التوازن",
                "حالة التوازن الديناميكي لجملة كيميائية",
                "التحولات المرافقة بتفاعلات حمض-أساس",
                "التحولات حمض أساس",
                "المحاليل المائية",
                "ثوابت الحموضة Ka وpKa للثنائيات",
                "تطبيق على الكاشف الملون",
                "المعاية PH مترية"
            ),
            groupList[4] to listOf(
                "التفكك الإشعاعي",
                "البنية النووية",
                "النموذج النووي",
                "النظائر",
                "القوة النووية القوية",
                "النشاط الاشعاعي",
                "الاستقرار النووي",
                "الخواص المؤينة وكشف الاشعاعات",
                "التناقص الإشعاعي",
                "الطابع العشوائي للتناقص الاشعاعي",
                "البكريل كوحدة قياس النشاط الاشعاعي bq",
                "نصف العمر",
                "قانون التناقص الاشعاعي",
                "التفاعلات النووية المستحدثة",
                "التفعلات التلقائية والتفاعلات المفتعلة",
                "المظهر الطاقوي للتفاعلات النووية",
                "الانشاطر والاندماج",
                "طاقة الربط لكل نوية",
                "الانشطار",
                "الاندماج"
            ),
            groupList[5] to listOf(
                "مفاهيم أساسية في الكيمياء العضوية",
                "جهة التطور التلقائي لجملة كميائية",
                "جهة التحول التلقائي",
                "التحول أسترة – إماهة",
                "مراقبة تطور جملة كميائية",
                "التحولات القسرية"
            )
        )
        return Pair(groupList, itemsList)
    }
    open fun getHisProgram() : Pair<List<String>, HashMap<String, List<String>>>{

        val groupList = listOf(
            "الوحدة الأولى: تطور العالم في ظل القطبية الثنائية 1945 – 1989",
            "الوحدة الثانية: الجزائر بين 1945 – 1989",
            "الوحدة الثالثة: تطور العالم الثالث بين 1945 – 1989"
        )
        val itemsList = hashMapOf(
            // Elements for the first unit
            groupList[0] to listOf(
                "بروز الصراع وتشكل العالم",
                "مساعي الانفراج الدولي",
                "من الثنائية إلى الأحادية القطبية"
            ),

            // Elements for the second unit
            groupList[1] to listOf(
                "العمل المسلح ورد فعل الاستعمار",
                "استعادة السيادة الوطنية وبناء الدولة الجزائرية",
            ),

            // Elements for the third unit
            groupList[2] to listOf(
                "العالم الثالث بين تراجع الاستعمار التقليدي واستمرارية حركات التحرر",
                "فلسطين من تصفية الاستعمار التقليدي إلى الهيمنة الأحادية والتواطؤ الدولي"
            )
        )

        return Pair(groupList, itemsList)
    }
    open fun getGeoProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        // Define your groupList (titles) for the ExpandableListView adapter
        val groupList = listOf(
            "الوحدة التعلمية الأولى: واقع الاقتصاد العالمي",
            "الوحدة التعلمية الثانية: القوى الاقتصادية الكبرى في العالم",
            "الوحدة التعلمية الثالثة: الاقتصاد و التنمية في دول الجنوب"
        )

        val itemsList = hashMapOf(
            // Elements for the first unit
            groupList[0] to listOf(
                "اشكالية التقدم و التخلف",
                "المبادلات و التنقلات في العالم"
            ),

            // Elements for the second unit
            groupList[1] to listOf(
                "مصادر القوة الاقتصادية للولايات المتحدة اللامريكية وتأثيرها على الللاقتصاد العلمي",
                "ظاهرة التكتل في قوة الاتحاد الأوروبي",
                "العلاقة بين السكان و التنمية في شرق جنوب اسيا"
            ),

            // Elements for the third unit
            groupList[2] to listOf(
                "الاقتصاد الجزائري في العالم",
                "التنمية في البرازيل"
            )
        )

        return Pair(groupList, itemsList)

    }
    open fun getFrenchProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "Rédiger un texte pour informer d'un fait d'histoire",
            "Produire un texte historique en y introduisant des témoignages et des commentaires",
            "Rédiger un texte pour débattre d'un sujet en concédant ou en réfutant une opinion",
            "Rédiger un appel",
        )
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "Exprimer et présenter un événement.",
                "Les indices temporels."
            ),
            groupList[1] to listOf(
                "Lexique des sentiments.",
                "Les marques de subjectivité.",
                "Lexique de la cause et de la conséquence."
            ),
            groupList[2] to listOf(
                "Lexique de la concession.",
                "Lexique de la réfutation.",
                "La modalisation."
            ),
            groupList[3] to listOf(
                "Les verbes performatifs.",
                "Les verbes de modalité.",
                "L’expression de but.",
                "L’impératif."
            ),
        )
        return Pair(groupList, itemsList)
    }
    open fun getEnlgishProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "Ethics in business",
            "Advertising, consumers and safety",
            "Astronomy and the Solar System",
            "Feelings, Emotions, Humour and related topics",
        )
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "Expressing condition using Provided/providing that/as long as",
                "Expressing wish and desire with 'wish' , 'It's high time'",
                "Asking for / Giving advice / warning using : should, ought to, had better",
                "Present simple and present continuous passive",
                "Expressing cause and result using : because, so, as a result, thus, consequently",
                "Expressing result using : 'so + adj + that', 'such+noun/noun phrase+that'",
                "Expressing obligation and necessity with must/have",
                "Morphology : Forming nouns by adding suffix 'ty' and 'ity' to adjectives \n Forming opposites by adding prefixes: 'dis-', 'il-'..",
                "Phonology : Pronouncing words ending in 'ics', stress shift"),
            groupList[1] to listOf(
                "May, might, could, can, used to express hypotheses",
                "Expression of certainty & doubt",
                "Dependent prepositions",
                "Present simple",
                "present simple gerund",
                "because, since, as, because of, owing to, ...",
                "as a result, consequently, therefore, ...",
                "reported speech with simple tenses and modals",
                "reporting requests, orders, ...",
                "expressions of concession",
                "conditional type 1 + unless",
                "Imperative",
                "excessive quantifiers (too many...)",
                "Morphology: Forming adjectives with the suffix “y”",
                "Phonology: Silent letter"
            ),
            groupList[2] to listOf(
                "Concession: however, even though, etc.",
                "expressing similarities and differences using: whereas, while, like, unlike, etc.",
                "state and action verbs",
                "Morphology: plural form, Word formation",
                "Phonology: pronunciation of final “ed”"
            ),
            groupList[3] to listOf(
                "Present simple",
                "Past simple, past perfect",
                "Enjoy/like/dislike +gerund",
                "I’d rather do... I’d rather do ...than... I’d prefer ...to...",
                "Should, ought to, if I were you",
                "articles: omission before abstract nouns( love, anger...)",
                "Quantifiers: a lot of, a great deal of , few, little, all of us...",
                "each other, one another",
                "Morphology: forming adjectives from nouns with: -ful, -ic, -ous",
                "Morphology: forming nouns with: -ness, -ty",
                "Morphology: forming verbs with –en",
                "Morphology: Self + noun/adj",
                "Phonology:Pronunciation of the final “ed” - Silent letters"
            ),
        )
        return Pair(groupList, itemsList)
    }
    open fun getArabProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "نثر الحركة العلمية",
            "من نثر الحركة العلمية",
            "النزعة الانسانية في الشعر المهجري",
            "شعر النهضة و مواقفه من حضارة الغرب",
            "الشعر الملتزم و قضايا التحرر",
            "فلسطين في الشعر العربي المعاصر",
            "الثورة التحريرية في الشعر العربي",
            "الشعر الاجتماعي و قضايا العصر",
            "مظاهر ازدهار الكتابة الفنية المقالة",
        )
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "نشأة الشعر التعليمي",
                "بلاغة المجاز المرسل",
                "تلخيص نصوص ذات أنماط مختلفة",
            ),
            groupList[1] to listOf(
                "حركة التأليف في عصر المماليك",
            ),
            groupList[2] to listOf(
                "النزعة الإنسانية في الشعر العربي الحديث",
                "معاني إذا, إذاً, و إعرابها",
                "معاني إذا,  حينئذ و إعرابها",
            ),
            groupList[3] to listOf(
                "",
            ),
            groupList[4] to listOf(
                "الالتزام في الشعر",
                "الخبر و أنواعه(المفرد, الجملة, شبه الجملة)",
                "الجمل التي لها محل الاعراب",
            ),
            groupList[5] to listOf(
                "الجمل التي لا محل لها من الاعراب",
                "بلاغة التشبيه",
            ),
            groupList[6] to listOf(
                "أحكام التمييز و الحال و الفرق بينهما",
                "أحكام البدل و العطف",
                "البيان",
            ),
            groupList[7] to listOf(
                "بلاغة الإستعارة",
            ),
            groupList[8] to listOf(
                "المقالة و الصحافة و دورهما في نهضة الفكر و الأدب",
                "أحكام لولا, لولا, لوما و إعرابها",
            ),
        )
        return Pair(groupList, itemsList)
    }
    open fun getIslamicProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "العقيدة الإسلامية و أثرها على الفرد و المجتمع",
            "وسائل القرآن الكريم في تثبيت العقيدة الإسلامية",
            "الإسلام و الرسالات السماوية",
            "العقل في القرآن الكريم",
            "مقاصد الشريعة الإسلامية",
            "منهج الإسلام في محاربة الانحراف و الجريمة",
            "المساواة أمام أحكام الشريعة الإسلامية",
            "الصحة النفسية و الجسمية في القرآن الكريم",
            "من مصادر التشريع الإسلامي: الإجماع",
            "من مصادر التشريع الإسلامي: القياس",
            "من مصادر التشريع الإسلامي: المصلحة المرسلة",
            "القيم في القرآن الكريم",
            "الوقف في الإسلام",
            "من أحكام الأسرة في الإسلام: مدخل إلى علم الميراث",
            "الربا و أحكامه",
            "من المعاملات المالية الجائزة: (بيع الصرف, بيع المرابحة, بيع التقسيط)",
            "الحرية الشخصية",
            "من أحكام الأسرة في الإسلام: النسب, التبني و الكفالة",
            "العلاقات الاجتماعية بين المسلمين و غيرهم",
            "خطبة الرسول صلى اللّه عليه و سلم في حجة الوداع",
        )
        val itemsList = hashMapOf<String, List<String>>()
        for (i in groupList.indices){
            itemsList.put(groupList[i], listOf(groupList[i]))
        }
        return Pair(groupList, itemsList)
    }
    open fun getPhiloProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الإشكالية الفلسفية والمشكلةالعلمية",
            "في فلسفة الرياضيات",
            "في علوم المادة الجامدة و علوم المادة الحية",
            "في العلوم الإنسانية",
            "الشعور بالأنا والشعور بالغير",
            "الحرية و المسؤولية",
            "العنف و التسامح",
            "انطباق الفكر مع نفسه",
        )
        val itemsList = hashMapOf<String, List<String>>()
        for (i in groupList.indices){
            itemsList.put(groupList[i], listOf(groupList[i]))
        }
        return Pair(groupList, itemsList)
    }

    // Gestion
    open fun getGestionFinanciereProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        return Pair(listOf(""), hashMapOf("" to listOf("")))

    }
    open fun getLoiProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        return Pair(listOf(""), hashMapOf("" to listOf("")))

    }
    open fun getEconomieProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        return Pair(listOf(""), hashMapOf("" to listOf("")))

    }
    open fun getDefaultProgram() : List<ModuleProgress> {
        return SCI_MODULES_PROGRESS
    }

    // Lang
    open fun getGermanProgram(): Pair<List<String>, HashMap<String, List<String>>> {
        return Pair(listOf(""), hashMapOf("" to listOf("")))
    }
    open fun getSpanishProgram(): Pair<List<String>, HashMap<String, List<String>>> {
        return Pair(listOf(""), hashMapOf("" to listOf("")))
    }

    // Math tech
    open fun getElectricProgram(): Pair<List<String>, HashMap<String, List<String>>> {
        return Pair(listOf(""), hashMapOf("" to listOf("")))
    }

    open fun calculateProgress(moduleProgress : ModuleProgress): Double {
        val progressPercentage: Double = when (moduleProgress.moduleName) {
            Constants.SCIENCE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getScienceProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.MATHEMATIQUE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getMathProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.PHYSIQUE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getPhysicsProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.HISTOIRE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getHisProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.GEOGRAPHIE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getGeoProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.ANGLAIS -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getEnlgishProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.FRANCAIS -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getFrenchProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.ARABE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getArabProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.S_ISLAMIQUE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getIslamicProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.PHILO -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getPhiloProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }

            else -> {
                0.0
            }
        }
        // Format the result to have two decimal points
        val decimalFormat = DecimalFormat("#.##")
        return replaceCommaWithPoint(decimalFormat.format(progressPercentage)).toDouble()
    }

    fun replaceCommaWithPoint(number : String) : String{
        return number.replace(',', '.')
    }

}