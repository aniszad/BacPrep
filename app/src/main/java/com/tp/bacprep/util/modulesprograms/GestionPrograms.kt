package com.tp.bacprep.util.modulesprograms

import android.os.Parcelable
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.util.Constants
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat


@Parcelize
open class GestionPrograms : BranchProgram(),Parcelable {
    override val BRANCH_NAME = Constants.GESTION_BRANCH
    val GESTION_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Economie et gestion", Constants.GESTION_BRANCH, listOf()),
        ModuleProgress("Mathématique", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Loi", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Gestion comptable et financière", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Histoire", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Géographie", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Anglais", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Français", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Arabe", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("S.Islamique", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Philo", Constants.GESTION_BRANCH,listOf())
    )
    fun calculateAverage(
        noteCompta : Double,
        noteEco : Double,
        noteMath : Double,
        noteArab : Double,
        noteFr : Double,
        noteDroit : Double,
        noteAng : Double,
        notePhilo : Double,
        noteIsl : Double,
        noteHisGeo : Double,
        noteTamazight : Double?,
        noteSport : Double?,
        ) : Double {
        if (noteTamazight == null && noteSport == null){
            val sum = ((noteCompta * 6) + (noteEco * 5) + (noteMath * 5) + (noteArab * 3) + (noteDroit*2)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2))
            return String.format("%.2f", sum/33).toDouble()
        }else if(noteTamazight == null && noteSport!=null){
            val sum = ((noteCompta * 6) + (noteEco * 5) + (noteMath * 5) + (noteArab * 3) + (noteDroit*2)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport)
            return String.format("%.2f", sum/34).toDouble()
        }else if(noteSport == null && noteTamazight != null){
            val sum = ((noteCompta * 6) + (noteEco * 5) + (noteMath * 5) + (noteArab * 3) + (noteDroit*2)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) + (noteTamazight * 2))
            return String.format("%.2f", sum/35).toDouble()
        }else if(noteSport != null && noteTamazight != null){
            val sum = ((noteCompta * 6) + (noteEco * 5) + (noteMath * 5) + (noteArab * 3) + (noteDroit*2)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport + (noteTamazight * 2))
            return String.format("%.2f", sum/36).toDouble()
        }else{
            return 0.0
        }
    }
    override fun getMathProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "المتتاليات العددية",
            "الإشتقاقية والاستمرارية على مجال",
            "النهايات",
            "دراسة الدوال",
            "الدوال الأصلية التكاملات",
            "الدوال الأسية واللوغاريتمية",
            "الإحصاء",
            "الإحتمالات",
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "الإستدلال بالتراجع",
                "المتتاليات المحدودة",
                "المتتاليات الرتيبة",
                "المتتاليات المتقاربة",
                "التعرف على متتالية معرفة بالعلاقة: Un+1 = a*Un + b f",
                "تقارب متتالية تراجعية Un+1 = f(Un) بالإستعانة بالدالة f",
            ),
            groupList[1] to listOf(
                "المماس",
                "الدوال المشتقة",
                "توظيف المشتقات في دراسة اتجاه تغير دالة",
                "المشتقات و القيم الحدية المحلية",
                "مركب دالتين, واشتقاق دالة مركبة",
                "الإستمرارية: مبرهنة القيم المتوسطة",

                ),
            groupList[2] to listOf(
                "العمليات على النهايات: نهاية دالة مركبة و النهاية بالمقارنة",
                "المستقيمات المقاربة",
                ),
            groupList[3] to listOf(
                "إثبات وجود مستقيم مقارب مائل بالنسبة إلى منحن ممثل لدالة و تعيين معادلة له"
            ),
            groupList[4] to listOf(
                "الدوال الأصلية لدالة على مجال",
                "حساب دوال أصلية لدوال بسيطة",
                "تكامل دالة",
                "خواص التكامل: الخطية, علاقة شال, الترتيب",
                "توظيف التكامل في حساب المساحات",
            ),
            groupList[5] to listOf(
                "الدالة اللوغاريتمية النيبيرية",
                "نهايات الدالة اللوغاريتمية",
                "دراسة دوال من الشكل ln°u",
                "الدوال الأسية: الخواص المميزة",
                "حل معادلات و متراجحات تتظمن أسيات",
                "نهايات الدالة الأسية",
                "دراسة دوال من الشكل exp°u",
                "حل مشكلات تتعلق بإيداع أو تسديد تتدخل فيها اللوغاريتميات أوالاسيات",
                "",
            ),
            groupList[6] to listOf(
                "تعريف سلسة إحصائية لمتغيرين حقيقين",
                "تمثيل سلسة إحصائية لمتغيرين حقيقيين بسحابة نقط",
                "تعيين إحداثيي النقطة المتوسطة",
                "إنشاء مستقيم تعديل خطي",
                "أمثلة لسلاسل إحصائية",
            ),
            groupList[7] to listOf(
                "قانون احتمال مرفق بتجربة عشوائية",
                "الأمل الرياضياتي و التباين و الإنحراف المعياري المرفق بقانون احتمال عددي",
                "الإحتمال الشرطي",
                "الشجرة المتوازنة",
                "استعمال أشجار متوازنة أو دستور الاحتمالات الكلية",
                "استقلال حادثتين",
            ),

        )
        return Pair(groupList, items)
    }
    override fun getHisProgram() : Pair<List<String>, HashMap<String, List<String>>>{

        val groupList = listOf(
            "الوحدة الأولى: تطور العالم في ظل القطبية الثنائية 1945 – 1989",
            "الوحدة الثانية: الجزائر بين 1945 – 1989",
            "الوحدة الثالثة: تطور العالم الثالث بين 1945 – 1989"
        )
        val itemsList = hashMapOf(
            // Elements for the first unit
            groupList[0] to listOf(
                "بروز الصراع وتشكل العالم",
                "الأزمات الدولية في ظل الصراع بين الشرق والغرب",
                "مساعي الانفراج الدولي",
                "من الثنائية إلى الأحادية القطبية"
            ),

            // Elements for the second unit
            groupList[1] to listOf(
                "من تبلور الوعي الوطني الجزائري إلى الثورة التحريرية",
                "العمل المسلح ورد فعل الاستعمار",
                "استعادة السيادة الوطنية وبناء الدولة الجزائرية",
                "تأثير الجزائر وإسهامها في حركة التحرر العالمية"
            ),

            // Elements for the third unit
            groupList[2] to listOf(
                "العالم الثالث بين تراجع الاستعمار التقليدي واستمرارية حركات التحرر",
                "فلسطين من تصفية الاستعمار التقليدي إلى الهيمنة الأحادية والتواطؤ الدولي"
            )
        )

        return Pair(groupList, itemsList)
    }
    override fun getGeoProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
                "الجزائر في حوض البحر الأبيض المتوسط",
                "السكان و التنمية في الهند",
                "السكان و التنمية في البرازيل",
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
    override fun getGestionFinanciereProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "أعمال نهاية السنة - التسويات",
            "إعداد الكشوف المالية و تحليلها",
            "تمويل و اختيار المشاريع الاستثمارية",
            "حساب و تحليل التكاليف الكلية",
            "التكاليف الجزئية",
        )
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "تقديم أعمال نهاية السنة",
                "الاهتلاكات و نقص قيمة التثبيتات",
                "تسوية المخزونات",
                "تسوية عناصر الأصول الأخرى",
                "مؤونات حسابات الخصوم غير الجارية",
                "تسوية الأعباء و المنتوجات",


            ),
            groupList[1] to listOf(
                "إعداد حساب النتائج و الميزانية الختامية",
                "تحليل النتائج حسب الطبيعة",
                "تحليل النتائج حسب الوظيفة",
                "إعداد و تحليل الميزانية الوظيفية",

            ),
            groupList[2] to listOf(
                "مدخول لتمويل التثبيتات",
                "القروض العادية المسددة على دفعات ثابتة بفوائد مركبة",
                "اختيار المشاريع الإستثمارية",
            ),
            groupList[3] to listOf(
                "معالجة الأعباء المحملة للتكاليف",
                "حساب التكاليف و النتيجة التحليلية",
            ),
            groupList[4] to listOf(
                "طريقة التكاليف و المتغيرة",
                "طريقة التحميل العقلاني للأعباء الثابتة",
            ),
        )
        return Pair(groupList, itemsList)
    }
    override fun getLoiProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "المجال 01: العقود و الشركات التجارية",
            "المجال 02: علاقات العمل",
            "المجال 03: المالية العامة",
        )
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "عقد البيع",
                "عقد الشركة",
                "شركة التضامن",
                "شركات المساهمة و ش ذ م م",
            ),
            groupList[1] to listOf(
                "علاقات العمل الفردية",
                "علاقات العمل الجماعية",
                ),
            groupList[2] to listOf(
                "الميزانية العامة للدولة و قانون المالية",
                "الضرائب على الدخل الإجمالي",
                "",
            )
        )
        return Pair(groupList, itemsList)
    }
    override fun getEconomieProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الميكانزيمات الإقتصادية",
            "المبادلات الدولية",
            "الإختلالات الإقتصادية",
            "المانحمنت",
            "تسيير موارد المؤسسة",
        )

        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "النقود",
                "السوق و الأسعار",
                "النظام المصرفي",
            ),
            groupList[1] to listOf(
                "التجارة الخارجية",
                "الصرف",
            ),
            groupList[2] to listOf(
                "البطالة",
                "التضخم",
            ),
            groupList[3] to listOf(
                "القيادة",
                "الإتصال",
                "الرقابة",
            ),
            groupList[4] to listOf(
                "التمويل",
            ),
        )
        return Pair(groupList, itemsList)
    }

    override fun getDefaultProgram() : List<ModuleProgress> {
        return GESTION_MODULES_PROGRESS
    }

    override fun calculateProgress(moduleProgress : ModuleProgress): Double {
        val progressPercentage: Double = when (moduleProgress.moduleName) {
            Constants.GESTION_COMPTABLE_ET_FINANCIERE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getGestionFinanciereProgram().second) {
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
            Constants.LOI -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getLoiProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.ECONOMIE_ET_GESTION -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getEconomieProgram().second) {
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
