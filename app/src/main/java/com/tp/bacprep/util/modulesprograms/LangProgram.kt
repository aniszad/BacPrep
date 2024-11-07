package com.tp.bacprep.util.modulesprograms

import android.os.Parcelable
import com.tp.bacprep.domain.models.ModuleProgress
import com.tp.bacprep.util.Constants
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat

@Parcelize
open class LangProgram : BranchProgram(), Parcelable {
    override val BRANCH_NAME = Constants.LANGUES_BRANCH
    val LANG_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Mathématique", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Allemand", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Espagnol", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Histoire", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Géographie", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Anglais", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Français", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Arabe", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("S.Islamique", Constants.GESTION_BRANCH,listOf()),
        ModuleProgress("Philo", Constants.GESTION_BRANCH,listOf())
    )
     fun calculateAverage(
        noteThirdLang : Double,
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
            val sum = ((noteThirdLang * 4) + (noteArab * 5)
                    + (noteFr * 5) + (noteAng * 5) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2))
            return String.format("%.2f", sum/28).toDouble()
        }else if(noteTamazight == null && noteSport!=null){
            val sum = ((noteThirdLang * 4) + (noteArab * 5)
                    + (noteFr * 5) + (noteAng * 5) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport)
            return String.format("%.2f", sum/29).toDouble()
        }else if(noteSport == null && noteTamazight != null){
            val sum = ((noteThirdLang * 4) + (noteArab * 5)
                    + (noteFr * 5) + (noteAng * 5) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) + (noteTamazight * 2))
            return String.format("%.2f", sum/30).toDouble()
        }else if(noteSport != null && noteTamazight != null){
            val sum = ((noteThirdLang * 4) + (noteArab * 5)
                    + (noteFr * 5) + (noteAng * 5) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport + (noteTamazight * 2))
            return String.format("%.2f", sum/31).toDouble()
        }else{
            return 0.0
        }
    }
    override fun getGermanProgram(): Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "Deutschland u.Algerien heute",
            "Künstlerleben",
            "Der technische Fortschritt",
            "Umweltprobleme",
            "Massenmedien",
            "Kommunikationsmittel",
            "Jugendprobleme und Arbeitswelt",
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "Deutschland, ein geografischer Überblick",
                "Präpositionen mit Verben \n Futur + Wortbildung",
                "Die deutsche \n Wirtschaft \n Passiv",
                "Passiv",
            "Algerien heute",
            "Passiv mit Modalverben",
            "Österreich",
            "Die Schweiz",
            "Arbeit am Projekt und Übungen",
        ),
            groupList[1] to listOf(
                "Goethe",
                "Nur/Erst + Adjektivdekl. im Nom. und Akk.",
                "Mozart, das Wunderkind",
                "Adjektivdeklination im Dativ",
                "Benbadis",
                "Adjektivdekl. + Mehrteilige Konjunktionen "
        ),
            groupList[2] to listOf(
                "Der technische Fortschritt",
                "Finalsatz",
                "Computer, eine technische Revolution",
                "Sinne der Modalverben Suffixe",
                "Wie werden die Menschen im Jahr 3000 leben?",
                "Dass-Satz Wiederholung zum Perfekt"
        ),
            groupList[3] to listOf(
                "Um uns die Umwelt Suffixe",
                "Relativsatz im nominative + Akkus. ",
                "Der Umweltschutz",
                "Relativsatz im Dativ",
                "Begräbt uns der Müll? ",
                "Imperativ Sinne der Modalverben",
                "Unser Klima",
                "Arbeit am Projekt"
        ),
            groupList[4] to listOf(
                "Die Massenmdien",
                "Sinne der Modalverben",
                "Mehrteilige Konjunktionen",
                "Neue Medien",
                "Konjunktiv II",
                "Zum Schreiben",
                "Ubungen ",
        ),
            groupList[5] to listOf(
                "Kommunikationsmittel",
                "Erst eine SMS,dann Zâhne putzen",
                "Suffix(bar) Temporalsatz",
                "Das Internet",
                "Konzessivsatz Relativsatz (Genitiv)",
                "Schriftzug Post ",
                ),
            groupList[6] to listOf(
                "Jugend und Drogen ",
                "Komposita und Konditionalsatz",
                "Modalververben im Perfekt",
                "Temporalsätze ",
                "Konjunktiv 2 ",
                "Konjunktiv II Vergangenheit",
                "Perfekt mit haben oder sein ",
                "Komparativ und Superlativ",
                "Suffixe : heit-keit-schaft-ung -los",
                "Die Traumberufe",
                ),

        )
        return Pair(groupList, items)
    }
    override fun getSpanishProgram(): Pair<List<String>, HashMap<String, List<String>>> {
        val groupList = listOf(
            "Ámbito Personal",
            "Ámbito educativo",
            "Ámbito científico y tecnológico",
            "Ámbito Laboral",
            "Páginas Eternas"
        )
        val items = hashMapOf(
            groupList[0] to listOf(
                "Los jóvenes de hoy",
                "Los Sueños",
                "Ortografía y fonética-Los signos de puntuación"
            ),
            groupList[1] to listOf(
                "El mundo de la education",
                "La educacion de un buen ciudadano",
            ),
            groupList[2] to listOf(
                "Los inventos",
                "El pro y el contra de la tecnologia",
            ),
            groupList[3] to listOf(
                "Construir el manana",
                "el mundo del trabajo",
            ),
            groupList[4] to listOf(
                "RUTAS LITERARIAS Y CULTURALES",
                "RUTAS HISTORICAS",
            ),
            )
        return Pair(groupList, items)
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
                "الإعراب اللفظي.",
                "التظمين.",
                "تلخيص نص.",
            ),
            groupList[1] to listOf(
                "الإعراب التقديري.",
                "النثر العلمي في العصر المملوكي.",
            ),
            groupList[2] to listOf(
                "المجاز المرسل.",
                "بلاغة التشبيه و الإستعارة",
            ),
            groupList[3] to listOf(
                "معاني: إذ,إذا,إذن, حينئذ.",
                "الجمل التي لها محل من الإعراب.",
                "الكنابة.",
                "النزعة الإنسانية في الشعر المهجري.",
            ),
            groupList[4] to listOf(
                "الالتزام في الشعر.",
                "الجمل التي لا محل لها من الإعراب.",
                "الرجز في الشعر الحر.",
                "المتقارب في الشعر الحر.",
                "مظاهر التجديد في المدرسة الرومانسية.",
            ),
            groupList[5] to listOf(
                "أحكام التمييز و الحال و الفرق بينهما.",
                "الهمزة المذبذة في أول الأمر.",
                "الرمل في الشعر العربي الحر.",
                "الكامل في الشعر الحر",
            ),
            groupList[6] to listOf(
                "خصائص شعر الحزن و الألم عند الشعراء العرب.",
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
                "أحكام : لو, لولا, لوما.",
                "تحليل قصة قصيرة.",
                "تعريف المقال و تطوره و مراحله.",
            ),
            groupList[9] to listOf(
                "معاني الأحرف المشبهة بالفعل.",
            ),
            groupList[10] to listOf(
                "تحليل مسرحية من خلال الخصائص الفنية.",
            ),
            groupList[11] to listOf(
                "الأدب المسرحي الجزائري.",
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
            "السؤال بين المشكلة و الإشكالية",
            "العلاقات بين الناس",
            "فلسفة العلوم",
            "انطباق الفكر مع نفسه",
        )
        val itemsList = hashMapOf<String, List<String>>(
            groupList[0] to listOf(
                "الإشكالية الفلسفية والمشكلةالعلمية",
            ),
            groupList[1] to listOf(
                "اللغة و الفكر",
                "الشعور بالأنا والشعور بالغير",
                "الحرية و المسؤولية",
                "العنف و التسامح",
                "التنوع الثقافي و العولمة"
                ),
            groupList[2] to listOf(
                "فلسفة الرياضيات",
                "في علوم المادة الجامدة و علوم المادة الحية",

                ),
            groupList[3] to listOf(
                "انطباق الفكر مع نفسه",
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

    override fun getDefaultProgram() : List<ModuleProgress> {
        return LANG_MODULES_PROGRESS
    }

    override fun calculateProgress(moduleProgress : ModuleProgress): Double {
        val progressPercentage: Double = when (moduleProgress.moduleName) {
            Constants.ALLEMAND ->{
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getGermanProgram().second) {
                    allProgramSize += value.size
                }
                (moduleProgress.progressList.size / allProgramSize * 100)
            }
            Constants.ESPAGNOL ->{
                var allProgramSize: Double = 0.0
                for ((_, value) in this.getGermanProgram().second) {
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