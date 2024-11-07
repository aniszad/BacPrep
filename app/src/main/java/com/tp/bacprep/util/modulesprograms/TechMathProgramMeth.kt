package com.tp.bacprep.util.modulesprograms

import android.os.Parcelable
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.util.Constants
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat


@Parcelize
open class TechMathProgramMeth : BranchProgram(),Parcelable {
    override val BRANCH_NAME = Constants.MATH_TECH_BRANCH_METH
    fun calculateAverage(
        noteTechnology : Double,
        notePhy : Double,
        noteMath : Double,
        noteArab : Double,
        noteFr : Double,
        noteAng : Double,
        notePhilo : Double,
        noteIsl : Double,
        noteHisGeo : Double,
        noteTamazight : Double?,
        noteSport : Double?,

        ) : Double {
        if (noteTamazight == null && noteSport == null){
            val sum = ((noteTechnology * 7) + (notePhy * 6) + (noteMath * 6) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2))
            return String.format("%.2f", sum/30).toDouble()
        }else if(noteTamazight == null && noteSport!=null){
            val sum = ((noteTechnology * 7) + (notePhy * 6) + (noteMath * 6) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport)
            return String.format("%.2f", sum/31).toDouble()
        }else if(noteSport == null && noteTamazight != null){
            val sum = ((noteTechnology * 7) + (notePhy * 6) + (noteMath * 6) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) + (noteTamazight * 2))
            return String.format("%.2f", sum/33).toDouble()
        }else if(noteSport != null && noteTamazight != null){
            val sum = ((noteTechnology * 7) + (notePhy * 6) + (noteMath * 6) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport + (noteTamazight * 2))
            return String.format("%.2f", sum/34).toDouble()
        }else{
            return 0.0
        }
    }

    override fun getMathProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الدوال العددية (الشتقاقية والاستمرارية)",
            "الدالتان الأسية واللوغاريتمية",
            "الدوال العددية (النهايات)",
            "التزايد المقارن",
            "المتتاليات العددية",
            "الدوال الأصلية الحساب التكاملي",
            "الأعداد و الحساب", // alhisab math tech
            "الإحصاء الإحتمالات",
            "الأعداد مركبة",
            "التحويلات النقطية",
            "الهندسة في الفضاء",
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "لعدد المشتق والمماس تعريف استمرار دالة على مجال",
                "مبرهنة القيم المتوسطة واستعمالها في إثبات وجود حلول للمعادلة f(x) = k ",
                " المشتقات المتتابعة، حساب مشتق دالة مر ّكبة",
                "استعمال المشتقات لدراسة خواص دالة والمنحنى الممثل لها (اتجاه تغيّر دالة على مجال، التقريب الخطي، نقطة االنعطاف،...)",
                "توظيف المشتقات لحل مشكالت. )دراسة اتجاه تغيّر دوال كثيرات حدود، ناطقة، صماء(",
                "توظيف المشتقات لحل مشكالت. )دراسة اتجاه تغيّر دوال كثيرات حدود، ناطقة، صماء(",
                "استعمال المشتقات لدراسة خواص دالة والمنحنى الممثل لها.",
                "توظيف المشتقات لدراسة الدوال المثلثية",

                ),
            groupList[1] to listOf(
                "دراسة الدالة األ ّسية النيبرية وتوظيف خواصها في حل معادالت ومتراجحات.",
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
                "دوال القوى والجذور النونية وتوظيف خواصهما.",
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
                " تعرف الدالة الأصلية لدالة على مجال والخواص.",
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
                "قابلية القسمة على Z: إثبات أن عددا صحيحا يقسم عددا صحيحا اخر",
                "استعمال خواص قابلية القسمة على Z",
                "القسمة الإقليدية في Z: استعمال خوارزمية إقليدس لتعيين القاسم المشترك الأكبر لعددين طبيعيين.",
                "حل مشكلات بتوظيف خواص القاسم المشترك الأكبر",
                "الموافقات في Z : تعريف و خواص",
                "التعداد : نشر عدد طبيعي وفق أساس",
                "الانتقال من نظام أساسه α الى نظام أساسه β",
                "الأعداد الأولية : التعرف على أولية عدد طبيعي.",
                "استعمال تحليل عدد طبيعي إلى جداء عوامل أولية لتعيين مضاعفاته و قواسمه",
                "المضاعف المشترك الأصغر : استعمال تحليل عدد طبيعي إلى جداء عوامل أولية لتعيين المضاعف المشترك الأصغر و القاسم المشترك الأكبر",
                "استعمال العلاقة المضاعف المشترك الأصغر و القاسم المشترك الأكبر",
                "استعمال خواص المضاعف المشترك الأصغر",
                "مبرهنة بيزو: إستعمال مبرهنة بيزو",
                "مبرهنة غوص: استعمال مبرهنة غوص و نتائجها",
            ),
            groupList[7] to listOf(
                "الإحتمالات المتساوية على مجموعة منتهية: إيجاد قانون احتمال لمتغيّر عشوائي.",
                "حل مسائل في الإحتمالات توظف المتغيرات العشوائية، قانون احتمالها، التباين، االنحراف المعياري واألمل الرياضياتي ",
                "المبدأ األساسي للعد: العّد باستخدام المبدأ األساسي للعّد )المجموع وال ُجداء(",
                "استخراج بعض قوانين التحليل التوفيقي )التوفيقات(",
                "دستور ثنائي الحّد.",
                "االحتماالت الشرطية: التعّرف على استقالل أو ارتباط حادثتين.",
                "االحتماالت الشرطية: ـ التعّرف على استقالل أو ارتباط حادثتين.",
                "توظيف دستور االحتماالت الكلية لحل مسائل في االحتماالت تتعلق بسحب أكثر من وعاء",
            ),
            groupList[8] to listOf(
                "المجموعة C : إجراء العمليات الحسابية على األعداد المرّكبة",
                "استعمال خواص مرافق عدد مر ّكب، حساب طويلة عدد مرّكب.",
                "تعيين الجذرين التربيعيين لعدد مركب.",
                "حل في C، معادلة من الدرجة الثانية ذات معاملات حقيقية.",
                "حل في C، معادالت يؤول حلها إلى حل معادلة من الدرجة الثانية ذات معاملات حقيقية.",
                "الشكل المثلثي لعدد مركب غير معدوم: حساب عمدة لعدد مركب غير معدوم",
                "الإنتقال من الشكل الجبري إلى الشكل المثلثي و العكس.",
                "ترميز أولير:exp(i a)",
                "التعبير عن خواص ألشكال هندسية باستعمال األعداد المرّكبة.",
                " توظيف خواص الطويلة والعمدة لحل مسائل في األعداد المر ّكبة وفي الهندسة",
                "توظيف دستور موافر لحل مسائل في األعداد المر ّكبة وفي الهندسة.",
            ),
            groupList[9] to listOf(
                "تعيين الكتابة المركبة للتحويلات النقطية الإنسحاب, التحاكي,الدوران.",
                "التعرف عن تحويل انطلاقا من الكتابة المركبة",
                "توظيف الأعداد المركبة لبرهان خواص الانسحاب, تحاكيات, الدوران و التحاكي.",
                "التشابهات المستوية المباشرة",
                "التعبير عن تشابه مباشر بالأعداد المركبة",
                "تركيب تشابهين مباشرين",
                "تعيين التحليل القانوني لتشابه مباشر بواسطة الأعداد المركبة",
                "توظيف خواص التشابهات المباشرة لحل مسائل هندسية",
                "z' = az + b : أنشطة حول تحويلات نقطية كتابتها المركبة",
            ),
            groupList[10] to listOf(
                "الجداء السلمي",
                "توظيف الجداء السلمي لتعيين معادلة لمستو",
                "توظيف الجداء السلمي لحساب المسافة بين نقطة و مستو",
                "توظيف الجداء السلمي لتعيين مجموعات نقط",
                "المستقيمات والمستويات في الفضاء",
                "االنتقال من جملة معادلتين لمستقيم أو معادلة لمستو إلى تمثيل وسيطي والعكس",
                "األوضاع النسبية: تحديد الوضع النسبي لمستويين، لمستقيم ومستو، لمستقيمين",
                "تعيين تقاطع مستويين، مستقيم ومستو، مستقيمين. تقاطع 3 مستويات",
            )
        )
        return Pair(groupList, items)
    }
    override fun getPhysicsProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
                "الأعمدة",
                "التحول أسترة – إماهة",
                "مراقبة تطور جملة كميائية",
                "التحولات القسرية"
            )
        )
        return Pair(groupList, itemsList)
    }
    override fun getPhiloProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
        var itemsList = hashMapOf<String, List<String>>()
        for (i in groupList.indices){
            itemsList.put(groupList[i], listOf(groupList[i]))
        }
        return Pair(groupList, itemsList)
    }
    // TODO -- add electricity, mecanic, ... programs

    override fun calculateProgress(moduleProgress : ModuleProgress): Double {
        val progressPercentage: Double = when (moduleProgress.moduleName) {
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


}