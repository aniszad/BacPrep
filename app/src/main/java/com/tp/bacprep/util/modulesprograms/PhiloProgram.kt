package com.tp.bacprep.util.modulesprograms

import android.os.Parcelable
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.util.Constants
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat

@Parcelize
open class PhiloProgram : BranchProgram(), Parcelable {
    override val BRANCH_NAME = Constants.LETTRE_BRANCH
    val LETTRE_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Mathématique", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Histoire", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Géographie", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Anglais", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Français", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Arabe", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("S.Islamique", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Philo", Constants.GESTION_BRANCH,listOf())
    )
     fun calculateAverage(
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
            val sum = ((noteMath * 2) + (noteArab * 6)
                    + (noteFr * 3) + (noteAng * 3) + (notePhilo * 6) + (noteIsl * 2) + (noteHisGeo * 4))
            return String.format("%.2f", sum/26).toDouble()
        }else if(noteTamazight == null && noteSport!=null){
            val sum = ((noteMath * 2) + (noteArab * 6)
                    + (noteFr * 3) + (noteAng * 3) + (notePhilo * 6) + (noteIsl * 2) + (noteHisGeo * 4) +
                    noteSport)
            return String.format("%.2f", sum/27).toDouble()
        }else if(noteSport == null && noteTamazight != null){
            val sum = ((noteMath * 2) + (noteArab * 6)
                    + (noteFr * 3) + (noteAng * 3) + (notePhilo * 6) + (noteIsl * 2) + (noteHisGeo * 4) + (noteTamazight * 2))
            return String.format("%.2f", sum/28).toDouble()
        }else if(noteSport != null && noteTamazight != null){
            val sum = ((noteMath * 2) + (noteArab * 6)
                    + (noteFr * 3) + (noteAng * 3) + (notePhilo * 6) + (noteIsl * 2) + (noteHisGeo * 4) +
                    noteSport + (noteTamazight * 2))
            return String.format("%.2f", sum/29).toDouble()
        }else{
            return 0.0
        }
    }

    override fun getArabProgram(): Pair<List<String>, HashMap<String, List<String>>> {
        val groupList = listOf(
            "الزهد و المديح النبوي.",
            "النثر العلمي في عصر المملوكي.",
            "شعر المنفى لدى شعراء الرواد.",
            "النزعة الإنسانية في الشعر المهحري.",
            "فلسطين في  الشعر العربي المعاصر.",
            "الثورة التحريرية الجزائرية في الشعر العربي.",
            "ظاهرة الحزن و الألم في الشعر العربي",
            "توظيف الرمز و الأسطورة في الشعر العربي المعاصر.",
            "من مظاهر الكتابة الفنية: المقالة",
            "القصة القصيرة في الأدب الجزائري.",
            "الفن المسرحي في الشرق.",
            "الأدب المسرحي الجزائري.",
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "الإعراب اللفظي و التقديري.",
                "التظمين.",
                "تلخيص نص.",
            ),
            groupList[1] to listOf(
                "المضاف إلى الياء المتكلم.",
                "النثر العلمي في العصر المملوكي.",
            ),
            groupList[2] to listOf(
                "نون الوقاية.",
                "المجاز المرسل.",
            ),
            groupList[3] to listOf(
                "معاني: إذ,إذا,إذن, حينئذ.",
                "الجمل التي لها محل من الإعراب.",
                "بلاغة الكنابة.",
                "النزعة الإنسانية في الشعر المهجري.",
            ),
            groupList[4] to listOf(
                "الالتزام في الشعر.",
                "الجمل التي لا محل لها من الإعراب.",
                "إعراب المسند و المسند إليه.",
                "الرجز في الشعر الحر, نشأة الشعر الحر, التفاعيل, الزحافات.",
                "المتقارب في الشعر الحر.",
                "مظاهر التجديد في المدرسة الرومانسية.",
            ),
            groupList[5] to listOf(
                "أحكام التمييز و الحال و الفرق بينهما.",
                "الفضلة و إعرابها.",
                "الرمل في الشعر العربي الحر.",
                "الكامل في الشعر الحر",
            ),
            groupList[6] to listOf(
                "صيغ منتهي الجموع, أنواعها و قياسها.",
                "المتدارك في الشعر الحر.",
                "الوافر و الهزج في الشعر الحر.",
            ),
            groupList[7] to listOf(
                "أحكام البدل و عطف البيان.",
                "الأسباب و الأوتاد في الشعر الحر.",
                "التناص.",
                "الرمز عند الشعراء المعاصرين.",
            ),
            groupList[8] to listOf(
                "معاني : لو, لولا, لوما.",
                "معاني : أما و إما.",
                "بلاغة التشبيه و الإستعارة.",
                "تحليل قصة قصيرة.",
                "تعريف المقال و تطوره و مراحله.",
            ),
            groupList[9] to listOf(
                "معاني الأحرف المشبهة بالفعل.",
            ),
            groupList[10] to listOf(
                "معاني و إعراب : أي, أيّ, إيّ",
                "معاني و إعراب: كم, كأين, و كذا.",
            ),
            groupList[11] to listOf(
                "معاني 'ما' و إعرابها.",
            ),
        )
        return Pair(groupList, items)
    }
    override fun getMathProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "المتتاليات العددية",
            "الحساب",
            "الدوال العددية",
            "الإحصاء و الإحتمالات",
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "توليد متتالية عددية: التعرف على المتتاليات من الشكل Un = f(n) أو  Un+1 = f(Un) و U0 معلوم",
                "المتتالية الحسابية: التعريف, الحد العام, الوسط الحسابي.",
                "حساب مجموع الحدود الأولى من متتالية حسابية.",
                "المتتالية الهندسية: التعريف, الحد العام, الوسط الحسابي.",
                "حساب مجموع الحدود الأولى من متتالية هندسية.",
                "التعرف على متتالية بالتراجع, حساب الحدود الأولى لمتتالية معرفة بالتراجع.",
                "مفهوم المتتالية الرتيبة: تعيين اتجاه تغير متتالية.",
                "تحديد اتجاه تغير متتالية حسابية.",
                "تحديد اتجاه تغير متتالية هندسية.",
                "استعمال المتتاليات الحسابية في حل المشكلات اليومية",
                "المتتاليات من الشكل Un+1 = a*Un +b",
            ),
            groupList[1] to listOf(
                "القسمة الإقليدية في Z: معرفة و تحديد حاصل القسمة الاقليدية و باقيها.",
                "حصر عددين مضاعفين متعاقبين لعدد صحيح.",
                "تعيين مجموعة قواسم عدد طبيعي.",
                "الموافقات في Z.",
                "معرفة خواص الموافقة و استعمالها في حل المشكلات",
                "الاستدلال بالتراجع: استعمال مبدا الاستدلال بالتراجع لاثبات صحة خاصية من أجل كل عدد طبيعي n.",
            ),
            groupList[2] to listOf(
                "تذكير حول المشتقات و معادلة المماس لمنحنى دالة.",
                "الدراسة و التمثيل البياني لدالة: تعيين اتجاه التغير باستعمال إشارة المشتقة.",
                "الدوال الكثيرات الحدود: دراسة دوال كثيرة حدود من الدرجة الثالثة على الأكثر.",
                "تعيين نقطة انعطاف.",
                "القراءة البيانية: الربط بين التمثيل البياني لدالة و جدول تغيراتها و العكس.",
                "استعمال التمثيل البياني لحل معادلات أو متراجحات.",
                "مناقشة معادلة بيانيا.",
                "الدوال التناظرية: دراسة دوال من الشكل x -> (ax+b)/(ex+d)",
                "تعيين المستقيمات المقاربة و تفسيرها البياني لدالة لتخمين النهايات عند ∞+ و ∞- وتحديدها.",
            ),
            groupList[3] to listOf(
                "الإحصاء : محاكاة تجربة عشوائية بسيطة و ذلك بملاحظة تطور التوترات.",
                "الاحتمالات: حساب احتمال حدث بسيط أو مركب.",
                "قانون الاحتمال المتعلق بتجربة عشوائية لها عدد منته من الإمكانيات.",
                "الربط بين الوسط الحسابي و الأمل الرياضياتي و بين التباين التطبيقي و التباين النظري لسلسة.",
            )
        )
        return Pair(groupList, items)
    }
    override fun getPhiloProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "إدراك العالم الخارجي.",
            "الأخلاق الموضوعية و الأخلاق النسبية.",
            "فلسفة العلوم.",
            "الفن والتصوف بين النسبي و المطلق.",
        )
        val itemsList = hashMapOf<String, List<String>>(
            groupList[0] to listOf(
                "الإحساس و الإدراك.",
                "اللغة و الفكر.",
                "الشعور و اللاشعور.",
                "الذاكرة و الخيال.",
                "العادة و الإرادة.",
            ),
            groupList[1] to listOf(
                "الأخلاق بين الثوابت و المتغيرات.",
                "الحقوق و الواجبات و العدل.",
                "الحياة الأسرية.",
                "الحياز الإقتصادية.",
                "الحياة السياسية.",
            ),
            groupList[2] to listOf(
                "الحقيقة العلمية و الفلسفية المطلقة.",
                "فلسفة الرياضيات.",
                "فلسفة العلوم التجريبية.",
                "فلسفة العلوم البيولوجية.",
                "فلسفة العلوم الإنسانية.",
            ),
            groupList[3] to listOf(
                "الفن  و التصوف بين النسبي و المطلق.",
                "التجربة الفنية و التجربة الذوقية.",
            ),
        )
        return Pair(groupList, itemsList)
    }
    override fun getFrenchProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "Rédiger un texte pour informer d'un fait d'histoire",
            "Produire un texte historique en y introduisant des témoignages et des commentaires",
            "Rédiger un texte pour débattre d'un sujet en concédant ou en réfutant une opinion",
            "Rédiger un appel",
            "Rédifer une nouvelle fantastique"
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
            groupList[4] to listOf(
                "Lexique de la peur et du fantastique",
                "La caractérisation",
                "Le récit cadre et le récit encadré",
            ),
        )
        return Pair(groupList, itemsList)
    }
    override fun getEnlgishProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "Ancient Civilizations",
            "Ethics in business",
            "Education in the world",
            "Feelings and Emotions",
        )
        val itemsList = hashMapOf(
            groupList[0] to listOf(
                "Narrating using had to/used to/ was able & tha past simple of 'to be'",
                "Simple Past tense - verb+prep / adj/prep",
                "The comparatives & superlatives of quantifiers",
                "Expressing concession - expressing time relations",
                "Deriving verbs, nouns, adj using suffixes(ic, ty ...) and prefixes(de, dis ...)",
                "Describing people, places, things using well + past participle - spelling rules of pronounciation f final 'ed' and 'ch'",
                "Writing an expository essay using guidelines",
                "Filling in a spidergram with relevant information",
                "Making & Checking hypotheses-recognize & showing a sequence of events",
                "Writing a short historical account",
                "Anticipating text content - skimming for gist - identifying key words/reference words",
                "Skimming for main ideas",
                "Writing a summary of the reading passage",
                "Writing a narrative text/story",
            ),
            groupList[1] to listOf(
                "Expressing condition using : providing/provided that/as long as",
                "Expressing regret / Expressing desire for change/ Expressing a strong feeling of regret/ Expressing a string complaint/criticism",
                "Giving advice using should/ought to/had better/if i were you",
                "Making a public statement",
                "Expressing result / the simple & continuous passive / expressing obligation / necessity, prohibition and absence of obligation",
                "Writing an opinion article",
                "Expressing opinion - responding to opinions",
                "Writing an argumentative speech",
                "Writing a policy statement",
            ),
            groupList[2] to listOf(
                "Expressing opinion, desire and wish and justifying - making hypotheses",
                "If conditional type2 / expressing warnings with unless",
                "Giving advice - expressing obligation",
                "Composing and reciting a wish poem",
                "Writing a checklist of recommendations",
                "Expressing quantity with most/all/few... - Compraing using comparatives of long & short adj",
                "Comparing & Contrasting using : similar to, like, unlike ...",
                "Present passive / Using sequencers / forming nouns from verbs by adding ing, tion, ation",
                "Pronunciation of final 's/es' - stress shftfrom verb to noun",
                "Writing a letter",
            ),
            groupList[3] to listOf(
                "Reporting questions and instructions/orders",
                "Deriving adjectives fromnouns - forming verbs - forming adjectives and nouns Pronouncing letter 'h' and cluster 'ngth'",
                "Writing a letter of advice",
                "Expressing likes and dislikes, preferences / using reciprocal pronouns/ using quantifiers",
                "recognizing and using suffixes : ness/ful/ous/lic - pronouncing final 'ed'",
                "Writing a newspaper article",
                "Writing a formal speech",
                "Distinguishing between facts and opinions",
            )
        )
        return Pair(groupList, itemsList)
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

    override fun getDefaultProgram() : List<ModuleProgress> {
        return LETTRE_MODULES_PROGRESS
    }

    override fun calculateProgress(moduleProgress : ModuleProgress): Double {
        val progressPercentage: Double = when (moduleProgress.moduleName) {
            Constants.MATHEMATIQUE -> {
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getMathProgram().second) {
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