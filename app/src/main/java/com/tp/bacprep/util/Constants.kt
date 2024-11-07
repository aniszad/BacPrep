package com.tp.bacprep.util

import com.tp.bacprep.domain.models.Quiz

object Constants {

    const val POSTS_DOCS = "Posts documents/"
    const val THEME_MODE = "theme mode"
    const val USER_ID: String = "user id"
    const val USER_ID_SHARED_PREF: String = "user id"

    // Shared Pref
    const val USER_BRANCH_INFO: String = "branch of the user"
    const val USER_LANGUAGE_INFO: String = "language of the user"
    const val CURRENT_USER_BRANCH: String = "current branch of the user"
    const val IS_VISITED = "is_visited"
    const val OB_SHARED_PREF = "ob_shared_pref"
    const val USER_INFO_SHARED_PREF = "user_info"

    // Google Drive folder ids
    const val HISTOIRE_DRIVE_FOLDER_ID: String = "1Fw5wItzi9xuq8mnrXz4WZgikQ2HfznQX"


    // Intents
    const val RESPONDING_TO_REQUEST: String = "responding_to_request"
    const val REQUEST_TO_ANSWER_TO: String = "request_id"
    const val MODULE_PROGRESS_TO_EDIT = "module progress to edit"
    const val FILIERE_PROGRESS_TO_EDIT = "filiere progress to edit"
    const val AUDIO_FILE_DOWNLOAD_URL: String = "audio file download url"
    const val AUDIO_FILE_NAME: String = "audio file name"
    const val MIN_SUFFIX: String = "min"
    const val FILE_MANAGER_TYPE : String = "go to subjects bank"
    // view types sent by intent to the fileAcivity
    const val DOWNLOADS : String = "downloads"
    const val FILES_BANK : String = "files bank"
    const val BOOKMARKS: String = "bookmarks"
    const val RESOURCES: String = "ressoures"
    const val FILE_VIEWER_TYPE = "file_viewer_type"
    const val OPEN_IMAGE = "open_image"
    const val OPEN_PDF = "open_pdf"
    const val IMAGE_URI = "image_uri"


    // Broadcasting actions
    const val DOWNLOAD_FILE_FROM_FIREBASE_ACTION = "download_file_from_firebase_to_file_manager"

    // Firebase collections
    const val USERS = "Users"
    const val ADMINS = "Admins"
    const val PUBLISHED_POSTS = "Published Posts"
    const val BOOKMARKED_POSTS = "Bookmarked Posts"
    const val LIKES_SUB_COLLECTION = "Likes"
    const val REQUESTS = "Requests"

    // ERRORS
    const val EMAIL_COLLISION_ERROR = "Un compte avec le même compte existe déja"
    const val WEAK_PSW = "Votre mot de passe est faible"

    // Like and unlike states
    const val LIKED = "liked"
    const val UNLIKED = "unliked"
    const val LIKE_STATE_CHANGED = "likeStateChanged"

    // User roles
    const val ROLE = "role"
    const val ADMIN_ROLE = "admin"
    const val USER_ROLE = "user"
    const val STUDENT_ROLE = "Student"
    const val TEACHER_ROLE = "Teacher"
    const val CREATOR_ROLE = "Creator"

    // Adapter Type
    const val USER = "user"
    const val ADMIN = "admin"

    // FbFileManager adapter view types
    const val LOADING_VIEW_TYPE = 0
    const val DISPLAY_VIEW_TYPE = 1
    const val NO_DATA_VIEW_TYPE = 2
    const val EMPTY_SPACE = 3



    const val REG_CREATOR = "register_creator"

    // folder_names
    const val DOWNLOADS_FOLDER_NAME = "Downloads"
    const val FILES_BANK_FOLDER_NAME = "Files Bank"

    const val SUBJECT_BANK_PATH = "/Subjects Bank"

    // Google drive Files ID's
    const val FILES_BANK_DRIVE_ID = "1ZEmBUIPWUXr_nae82N7qQHudIFwaxRe5"
    // file types suffixes
    const val FOLDER_SUFFIX = "_folder"
    const val FOLDER_WATERMARK = "<dir>"

    const val PDF_EXTENSION = ".pdf"
    const val MP3_EXTENSION = ".mp3"
    const val PNG_EXTENSION = ".png"

    // PATHS
    const val FIREBASE_PATH_PREFFIX = "gs://bacprep-20e99.appspot.com/"


    // TIMER INFOS
    const val TIMER_STUDY_DURATION: String = "timer_study_duration"
    const val TIMER_REST_DURATION: String = "timer_rest_duration"

    // MODULE NAMES
    const val SCIENCE = "Science"
    const val MATHEMATIQUE = "Mathématique"
    const val PHYSIQUE = "Physique"
    const val HISTOIRE = "Histoire"
    const val GEOGRAPHIE = "Géographie"
    const val ANGLAIS = "Anglais"
    const val FRANCAIS = "Français"
    const val ARABE = "Arabe"
    const val S_ISLAMIQUE = "S.Islamique"
    const val PHILO = "Philo"
    const val GENIE_DES_PROCEDES = "Génie des procédés"
    const val GENIE_ELECTRIQUE = "Génie électrique"
    const val TECHNOLOGIE_ELEC = "Technologie - electricité"
    const val GENIE_CIVIL = "Génie civil"
    const val GENIE_MECANIQUE = "Génie mécanique"
    const val ECONOMIE_ET_GESTION = "Economie et gestion"
    const val LOI = "Loi"
    const val GESTION_COMPTABLE_ET_FINANCIERE = "Gestion comptable et financière"
    const val ESPAGNOL = "Espagnol"
    const val ALLEMAND = "Allemand"

    // filieres
    const val SCIENCE_BRANCH = "Sciences expérimentales"
    const val MATH_BRANCH = "Math"
    const val GESTION_BRANCH = "Gestion et economie"
    const val LANGUES_BRANCH = "Langues étrangères"
    const val LETTRE_BRANCH = "Lettres et philosophie"

    const val MATH_TECH_BRANCH_MEC = "Technique mathématique - génie mécanique"
    const val MATH_TECH_BRANCH_ELE = "Technique mathématique - génie électrique"
    const val MATH_TECH_BRANCH_CIV = "Technique mathématique - génie civil"
    const val MATH_TECH_BRANCH_METH = "Technique mathématique - Ingénierie des méthodes"


    // Quiz Types
    const val QUIZ_TYPE= "quiz_type"
    const val QUIZ_SEMESTER = "semester"
    const val DATES_QUIZ = "Dates"
    const val PERSO_QUIZ = "Personalitées"
    const val TERMES_QUIZ = "Termes"

    const val SEMESTER = "semester"
    const val SEMESTER_1 = "Semestre 1"
    const val SEMESTER_2 = "Semestre 2"
    const val SEMESTER_3 = "Semestre 3"
    const val SEMESTER_ALL = "Tout les semestres"
    const val MODULE = "module"


    fun getHisPersoQuizSemester1(): Quiz {
        val sovietPerso = listOf(
            "جوزيف ستالين",
            "نيكيتا خروتشوف",
            "جورجي مالنكوف",
            "نيكولاي بولغانين",
            "ليونيد بريجنيف",
            "ميخائيل غورباتشوف",
            "أندري جدانوف"
        )
        val americanPerso = mutableListOf(
            "هاري ترومان",
            "دوايت إيزنهاور",
            "جون كينيدي",
            "ريتشارد نيكسون",
            "جيمي كارثر",
            "رونالد ريغن",
            "بوش الأب",
            "جورج مارشال",
            "ونستن تشرشل"
        )
        val quiz = Quiz(
            listOf(
                Triple(
                    "(1879-1953) رجل سياسي سوفياتي ورئيسه بين (1924-1953) تميزت فترة حكمه بالتوتر الشديد والحدة في العلاقات الدولية أدت إلى ظهور عدة أزمات ، واجه المخططات الأمريكية بمخططات مضادة كالكومنفورم –حلف وارسو.",
                    sovietPerso.filterNot { it == "جوزيف ستالين" },
                    "جوزيف ستالين"
                ),

                Triple(
                    "زعيم وسياسي للأتحاد السوفياتي خلال فترة من فترات الحرب الباردة , أحد قيادة الترويكا , صاحب مبدأ التعايش السلمي , شهدت فترته أخطر أزمة كوبا 1962.",
                    sovietPerso.filterNot { it == "نيكيتا خروتشوف" },
                    "نيكيتا خروتشوف"
                ),

                Triple(
                    "زعيم وسياسي للأتحاد السوفياتي خلال فترة من فترات الحرب الباردة , أحد قيادة الترويكا , رئيس الوزراء بعد وفاة ستالين , من معارضي سياسة خروتشوف , نفي الى كازاخستان.",
                    sovietPerso.filterNot { it == "جورجي مالنكوف" },
                    "جورجي مالنكوف"
                ),

                Triple(
                    "زعيم وسياسي للأتحاد السوفياتي خلال فترة من فترات الحرب الباردة , أحد قيادة الترويكا , وزير الدفاع بعد وفاة ستالين , كان حليفا لخروتشوف , من مؤيدي سياسة التعايش السلمي.",
                    sovietPerso.filterNot { it == "نيكولاي بولغانين" },
                    "نيكولاي بولغانين"
                ),

                Triple(
                    "زعيم وسياسي للأتحاد السوفياتي خلال فترة من فترات الحرب الباردة ,  عرفت فترته الكثير من الأزمات مثل براغ و أفغانستان 1976 , وقع سالت 1 و 2.",
                    sovietPerso.filterNot { it == "ليونيد بريجنيف" },
                    "ليونيد بريجنيف"
                ),

                Triple(
                    "زعيم وسياسي للأتحاد السوفياتي خلال فترة من فترات الحرب الباردة , صاحب اصلاحات البروتسوريكا و الغلاسنوست , تفكك السوفيات في فترته , حضر مؤتمر مالطا و باريس.",
                    sovietPerso.filterNot { it == "ميخائيل غورباتشوف" },
                    "ميخائيل غورباتشوف"
                ),

                Triple(
                    "مكلف بالأعلام الغربي الشيوعي السوفياتي خلال فترة من فترات الحرب الباردة , صاحب مبدأ جدانوف 1947 , من مؤسسي مكتب الكومنفورم.",
                    sovietPerso.filterNot { it == "جدانوف" },
                    "أندري جدانوف"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , ديموقراطي , صاحب القاء قنبلتي هيروشيما و ناكازاكي , صاحب مبدأ ترومان 1947.",
                    americanPerso.filterNot { it == "ترومان" },
                    "هاري ترومان"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , جمهوري , انهى أزمة كوريا , شهدت فترته أزمة السويس , صاحب مشروع ازنهاور 1957.",
                    americanPerso.filterNot { it == "ازنهاور" },
                    "دوايت إيزنهاور"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , ديموقراطي , شهدت فترته أزمة الصواريخ بكوبا 1962.",
                    americanPerso.filterNot { it == "جون كينيدي" },
                    "جون كينيدي"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , جمهوري , انتهت في فترته أزمة الفيتنام , و وقع سالت الأولى في 1962.",
                    americanPerso.filterNot { it == "ريتشارد نيكسون" },
                    "ريتشارد نيكسون"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , ديموقراطي , وقع اتفاقية سالت 2 في 1979 , شهدت فترته أزمة أفغانستان 1979.",
                    americanPerso.filterNot { it == "جيمي كارثر" },
                    "جيمي كارثر"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , جمهوري عرف بتشدده و تعصبه , صاحب مشروع حرب النجوم .",
                    americanPerso.filterNot { it == "رونالد ريغن" },
                    "رونالد ريغن"
                ),

                Triple(
                    "رئيس الولايات المتحدة الأمريكية , شهد فترة من فترات الحرب الباردة , جمهوري , وقع مع غورباتشوف اتفاقيات التعاون ,-باريس و مالطا - أعلن عن النظام الدولي الجديد , حرب الخليج.",
                    americanPerso.filterNot { it == "بوش الأب" },
                    "بوش الأب"
                ),

                Triple(
                    "وزير الخارجية الولايات المتحدة الأمريكية , صاحب مشروع مارشال 1947,لأعادة اعمار أوروبا بعد الحرب العالمية 2.",
                    americanPerso.filterNot { it == "جورج مارشال" },
                    "جورج مارشال"
                ),

                Triple(
                    "رئيس وزراء بريطانيا خلال الحرب العالمية الثانية , و في الفترة الأولى في الحرب الباردة شهد مؤتمر يالطا , واجه النازية في اطار حبر العالمية الثانية , معروف بمصطلح الستار الحديدي.",
                    americanPerso.filterNot { it == "ونستن تشرشل" },
                    "ونستن تشرشل"
                )
            ),
            ((sovietPerso.size + americanPerso.size) * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_1,
                QUIZ_TYPE to PERSO_QUIZ
            )
        )
        return quiz
    }

    fun getHisPersoQuizSemester2(): Quiz {
        val algerianPerso = listOf(
            "مصطفى بن بولعيد",
            "ديدوش مراد",
            "كريم بلقاسم",
            "رابح بيطاط",
            "العربي بن مهيدي",
            "محمد بوضياف",
            "زيغود يوسف",
            "فرحات عباس",
            "بن يوسف بن خدة",
            "أحمد بن بلة",
            "حسين أيت أحمد",
            "محمد خيضر",
            "محمد بلوزداد",
            "عبان رمضان",
            "هواري بومدين",
            "مصالي الحاج",
            "شارل ديغول"
        )
        val quiz = Quiz(
            listOf(
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 و لجنة 6, قائد المنطقة الأولى الأوراس .",
                    algerianPerso.filterNot { it == "مصطفى بن بولعيد" },
                    "مصطفى بن بولعيد"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 و لجنة 6 , قائد المنطقة 2 - الشمال القسنطيطي - .",
                    algerianPerso.filterNot { it == "ديدوش مراد" },
                    "ديدوش مراد"
                ),
                // Define the other personalities in the same format
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 و لجنة 6 , قائد المنطقة 3 - القبائل - .",
                    algerianPerso.filterNot { it == "كريم بلقاسم" },
                    "كريم بلقاسم"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 و لجنة 6 , قائد المنطقة 4 - الجزائر و ضواحيها - .",
                    algerianPerso.filterNot { it == "رابح بيطاط" },
                    "رابح بيطاط"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 و لجنة 6 , قائد المنطقة 5 - وهران - .",
                    algerianPerso.filterNot { it == "العربي بن مهيدي" },
                    "العربي بن مهيدي"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 و لجنة 6 , منسق بين الداخل و الخارج .",
                    algerianPerso.filterNot { it == "محمد بوضياف" },
                    "محمد بوضياف"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل في حزب الشعب و حركة الأنتصار الحريات الديموقراطية , عضو منظمة الخاصة من مؤسسي اللجنة الثورية للوحدة و العمل , عضو مجموعة 22 , قائد هجومات الشمال الأطلسي .",
                    algerianPerso.filterNot { it == "زيغود يوسف" },
                    "زيغود يوسف"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل سياسي , خريج مدرسة الصيدلة , التيار الأدماجي , حزب الأتحاد الديموقراطي للبيان الجزائري , أول رئيس للحكومة الجزائرية المؤقتة .",
                    algerianPerso.filterNot { it == "فرحات عباس" },
                    "فرحات عباس"
                ),
                Triple(
                    "شخصية وطنية , مناضل سياسي , خريج مدرسة الصيدلة , التيار الأستقلالي , حركة الأنتصار للحريات الديموقراطية , ثاني رئيس للحكومة الجزائرية المؤقتة .",
                    algerianPerso.filterNot { it == "بن يوسف بن خدة" },
                    "بن يوسف بن خدة"
                ),
                Triple(
                    "شخصية جزائرية ثورية , ناضل في حزب الشعب الجزائري و في حركة الأنتصار , من مؤسسي النمظمة الخاصة , أحد أعضاء الوفد الخارجي + القرصنة الجوية 22 أكتوبر 1956 , رئيس الجزائر من 1962 الى 1965 .",
                    algerianPerso.filterNot { it == "أحمد بن بلة" },
                    "أحمد بن بلة"
                ),
                Triple(
                    "شخصية جزائرية ثورية , ناضل في حزب الشعب الجزائري و في حركة الأنتصار , من مؤسسي النمظمة الخاصة , أحد أعضاء الوفد الخارجي + القرصنة الجوية 22 أكتوبر 1956 , أسس حزب القوى الأشتراكية FFS سنة 1962 .",
                    algerianPerso.filterNot { it == "حسين أيت أحمد" },
                    "حسين أيت أحمد"
                ),
                Triple(
                    "شخصية جزائرية ثورية , ناضل في حزب الشعب الجزائري و في حركة الأنتصار , من مؤسسي النمظمة الخاصة , أحد أعضاء الوفد الخارجي + القرصنة الجوية 22 أكتوبر 1956 , عين وزير للحكومة الجزائرية المؤقتة .",
                    algerianPerso.filterNot { it == "محمد خيضر" },
                    "محمد خيضر"
                ),
                Triple(
                    "مناضل حزب الشعب , قيادي في حركة الأنتصار , مؤسس و رئيس المنظمة الخاصة .",
                    algerianPerso.filterNot { it == "محمد بلوزداد" },
                    "محمد بلوزداد"
                ),
                Triple(
                    "شخصية وطنية جزائرية , مناضل حزب الشعب , و حركة الأنتصار للحريات الديموقراطية , مهندس مؤتمر الصومام 20 أوت 1956.",
                    algerianPerso.filterNot { it == "عبان رمضان" },
                    "عبان رمضان"
                ),
                Triple(
                    "شخصية وطنية جزائرية , قائد الولاية 5 من 1957 الى 1960 عين وزير للدفاع بعد الألستقلال ثم رئيس مجلس الثورة , انتخب رئيسا عام 1976.",
                    algerianPerso.filterNot { it == "هواري بومدين" },
                    "هواري بومدين"
                ),
                Triple(
                    "شخصية وطنية جزائرية , قائد التيار الأستقلالي , مؤسس حزب الشعب , و حركة الأنتصار للحريات الديموقراطية , أسس حزب الحركة الوطنية الجزائرية 1954.",
                    algerianPerso.filterNot { it == "مصالي الحاج" },
                    "مصالي الحاج"
                ),
                Triple(
                    "رئيس فرنسا , جاء بمشاريع اغرائية و قمعية للقضاء على الثورة , و فشلت مشاريعه بسبب قوة الثورة الجزائرية , رضخ للتفاوض مع جبهة التحرير الوطني.",
                    algerianPerso.filterNot { it == "شارل ديغول" },
                    "شارل ديغول"
                )
            ),
            (algerianPerso.size * 8000).toLong(),
            hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_2,
                QUIZ_TYPE to PERSO_QUIZ
            )
        )
        return quiz
    }

    fun getHisPersoQuizSemester3(): Quiz {
        val persoThirdSemester = listOf(
            "أحمد سوكارنو", "المهاتما غاندي", "جواهر لال نهرو", "جوزيف بروز تيتو",
            "محمد علي جناح", "هوشي منه", "جمال عبد الناصر", "أرنستو شي غيفارا",
            "فيدال كاسترو"
        )
        val quiz = Quiz(
            listOf(
                Triple(
                    "(1901-1970) سياسي وزعيم اندونيسي ، تزعم حركة تحرير بلاده ضد الهولنديين بعد الحرب ع 2 ،تولى رئاسة اندونيسيا إلى 1967 ، منظم * مؤتمر باندونغ * 1955 وأحد مؤسسي حركة عدم الانحياز.",
                    persoThirdSemester.filterNot { it == "أحمد سوكارنو" },
                    "أحمد سوكارنو"
                ),
                Triple(
                    "(1869-1948 )داعية وطني و زعيم وديني هندي ناضل ضد الوجود البريطاني، فتعرض للسجن في العديد من المرات، أصبح زعيم الحركة الوطنية منذ 1920 ثم تفرغ لمشاكل الطبقة المنبوذة في الهند ودعم الحركة التي رفعت شعار: غادروا الهند 1942 اهتم بتخفيف الصراع بين الهندوس والمسلمين(1946 ـ 1947). اغتيل من طرف متطرف هندوسي عام 1948.",
                    persoThirdSemester.filterNot { it == "المهاتما غاندي" },
                    "المهاتما غاندي"
                ),
                Triple(
                    "(1889-1964) : رجل سياسة هندي تلميذ غاندي رئيس المؤتمر الوطني الهندي منذ 1929 ، واحد من صانعي استقلال الهند، شغل منصب الوزير الأول (1947- 1964) كان من أقطاب عدم الانحياز، حيث لعب الأدوار الأولى خلال انعقاد مؤتمر باندونغ 1955.",
                    persoThirdSemester.filterNot { it == "جواهر لال نهرو" },
                    "جواهر لال نهرو"
                ),
                Triple(
                    "(1892-1980) سياسي يوغسلافي قاد مقاومة بلاده ضد قوات المحور أثناء الحرب ع 2 فحرر بلاده ، أصبح بعدها رئيس جمهورية يوغسلافيا ، من مؤسسي حركة عدم الانحياز.",
                    persoThirdSemester.filterNot { it == "جوزيف بروز تيتو" },
                    "جوزيف بروز تيتو"
                ),
                Triple(
                    "مناضل تحرري وسياسي باكستاني ناضل في إطار الرابطة الإسلامية لتحرير شبه القارة الهندية من الوجود البريطاني كان شريكا لجانب غاندي ونهرو و من أجل إقامة دولة مستقلة للهنود المسلمين عن الهندوس شغل منصب رئيس باكستان منذ استقلالها عام 1947.",
                    persoThirdSemester.filterNot { it == "محمد علي جناح" },
                    "محمد علي جناح"
                ),
                Triple(
                    "(1890-1969) مؤسس الحزب الشيوعي الفيتنامي قاد حربا ضد الوجود الفرنسي من 1946 إلى غاية هزيمتها في ديان بيان فو 1954 ثم تزعم الحرب التي خاضها الفيتنام الشمالي ضد الحكومة العميلة والولايات المتحدة في الجنوب منذ عام 1960 وانتهت بانتصار الشماليين وانسحاب الو.م.أ وتوحيد البلاد رسميا عام في أفريل1975.",
                    persoThirdSemester.filterNot { it == "هوشي منه" },
                    "هوشي منه"
                ),
                Triple(
                    "(1918-1970) انظم لتنظيم الضباط الأحرار الذي اطاح بالملك فاروق1952 أصبح رئيسا للجمهورية عام 1956 من أهم انجازاته تأميم قناة السويس والتي كانت ذريعة للعدوان الثلاثي على مصر 1956 و انجاز السد العالي كما عمل على تحقيق الوحدة العربية ، كان أحد مؤسسي حركة عدم الانحياز .",
                    persoThirdSemester.filterNot { it == "جمال عبد الناصر" },
                    "جمال عبد الناصر"
                ),
                Triple(
                    "( 1928-1967) ثوري من أصل أرجنتيني شارك في الثورة الكوبية إلى جانب كاسترو 1956 ـ 1959 حاول التأسيس لثورة شاملة في أمريكا اللاتينية ضد الهيمنة الأمريكية و الموالين لها فقام بتدريب الثوار في بوليفيا- 1966 ـ 1967- كان متأثرا بالفكر الشيوعي الماركسي،أصبح رمزا عالميا خاصة لدى الشباب الثوري و الاشتراكي .",
                    persoThirdSemester.filterNot { it == "أرنستو شي غيفارا" },
                    "أرنستو شي غيفارا"
                ),
                Triple(
                    "ثائر و سياسي كوبي - قاد الثورة ضد - باتيستا - في كوبا استولى على السلطة 1959 قوبلت سياسته بالعداء من طرف الولايات المتحدة التي حاولت الإطاحة به - عملية خليج الخنازير 1961 - تقارب مع السفويت مما أدى إلى وقوع أزمة الصواريخ 1962 و الولايات المتحدة تفرض حصارا اقتصاديا على كوبا .كان صديقا مقربا للجزائر في عهد هواري بومدين.",
                    persoThirdSemester.filterNot { it == "فيدال كاسترو" },
                    "فيدال كاسترو"
                )
            ), (persoThirdSemester.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_3,
                QUIZ_TYPE to PERSO_QUIZ
            )
        )

        return quiz
    }

    fun getHisAllPersoQuiz(): Quiz {
        val hisPerso1 = Constants.getHisPersoQuizSemester1().questions
        val hisPerso2 = Constants.getHisPersoQuizSemester2().questions
        val hisPerso3 = Constants.getHisPersoQuizSemester3().questions
        val allPerso = hisPerso1 + hisPerso2 + hisPerso3
        val quiz = Quiz(
            allPerso, (allPerso.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_ALL,
                QUIZ_TYPE to PERSO_QUIZ
            )
        )
        return quiz
    }

    fun getHisDatesQuizSemester1(): Quiz {
        val questions = listOf(
            Triple(
                "تأسيس هيئة الأمم المتحدة",
                null,
                "1945/10/24"
            ),
            Triple(
                "الخط الأحمر بين موسكو وواشنطن",
                null,
                "1963/06/20"
            ),

            Triple(
                "مشروع مارشال",
                null,
                "1947/06/05"
            ),

            Triple(
                "قمة حركة عدم الانحياز في الجزائر",
                null,
                "1973/05/09 - 1973/09/09"
            ),

            Triple(
                "تأسيس الكومنفورم",
                null,
                "1947/10/06"
            ),

            Triple(
                "حل الكوميكون",
                null,
                "1991/06/28"
            ),

            Triple(
                "قنبلة الو.م.أ في هيروشيما",
                null,
                "1945/08/06"
            ),

            Triple(
                "زوال الاتحاد السوفييتي",
                null,
                "1991/12/25"
            ),

            Triple(
                "قنبلة الو.م.أ في ناكازاكي",
                null,
                "1945/08/09"
            ),

            Triple(
                "تصفية حلف وارسو",
                null,
                "1991/07/01"
            ),

            Triple(
                "مؤتمر يالطا",
                null,
                "1945/02/04"
            ),

            Triple(
                "مؤتمر باندونغ",
                null,
                "1955/04/18 - 1955/04/24"
            ),

            Triple(
                "مؤتمر بوتسدام",
                null,
                "1945/08/02"
            ),

            Triple(
                "اتفاقية بروتون وودز",
                null,
                "1944"
            ),

            Triple(
                "المصادقة على ميثاق الأمم المتحدة",
                null,
                "1945/06/26"
            ),

            Triple(
                "مشروع إيزنهاور",
                null,
                "1957/01/05"
            ),

            Triple(
                "إنشاء الكوميكون",
                null,
                "1949/01/25"
            ),

            Triple(
                "زيارة نيكسون لموسكو",
                null,
                "1972/05"
            ),

            Triple(
                "تأسيس حلف الناتو",
                null,
                "1949/04/04"
            ),

            Triple(
                "زيارة بريجنيف للو.م.أ",
                null,
                "1973"
            ),

            Triple(
                "تأسيس حلف جنوب شرق آسيا",
                null,
                "1954/09/08"
            ),

            Triple(
                "اتفاقية هلسنكي",
                null,
                "1975"
            ),

            Triple(
                "تأسيس حلف بغداد",
                null,
                "1955/02/24"
            ),

            Triple(
                "استقلال سوريا ولبنان",
                null,
                "1946"
            ),

            Triple(
                "تأسيس حلف وارسو",
                null,
                "1955/05/14"
            ),

            Triple(
                "استقلال الهند، بنغلادش وباكستان",
                null,
                "1947"
            ),

            Triple(
                "مبدأ ترومان",
                null,
                "1947/03/12"
            ),

            Triple(
                "استقلال إندونيسيا",
                null,
                "1949"
            ),

            Triple(
                "قيام جمهورية ألمانيا الغربية",
                null,
                "1949/05/08"
            ),

            Triple(
                "مبدأ جدانوف",
                null,
                "1947/09/22"
            ),

            Triple(
                "قيام ألمانيا الشرقية",
                null,
                "1949/10/07"
            ),

            Triple(
                "وصول غورباتشوف للسلطة",
                null,
                "1985/03"
            ),

            Triple(
                "إقامة جدار برلين",
                null,
                "1961/08/13"
            ),

            Triple(
                "تفجير أول قنبلة سوفيتية في سيبيريا",
                null,
                "1949/09/21"
            ),

            Triple(
                "اتفاقية سالت 1",
                null,
                "1972/05/26"
            ),

            Triple(
                "انتصار الثورة الشيوعية في الصين",
                null,
                "1949/10/01"
            ),

            Triple(
                "اتفاقية سالت 2",
                null,
                "1979/01"
            ),

            Triple(
                "وفاة ستالين والدعوة للتعايش السلمي",
                null,
                "1953/03/05"
            ),

            Triple(
                "تهديم جدار برلين",
                null,
                "1989/11/09"
            ),

            Triple(
                "إطلاق سبوتنك أول قمر صناعي سوفيتي",
                null,
                "1957/10/04"
            ),

            Triple(
                "توحيد الألمانيتين",
                null,
                "1990/10/03"
            ),

            Triple(
                "تدخل الاتحاد السوفيتي في أفغانستان",
                null,
                "1979"
            ),

            Triple(
                "قمة مالطا",
                null,
                "1989/12/03 - 1989/12/04"
            ),

            Triple(
                "مشروع حرب النجوم",
                null,
                "1983/03"
            ),

            Triple(
                "خروج فرنسا من حلف الناتو",
                null,
                "1966"
            ),

            Triple(
                "غاغارين يدور حول الأرض",
                null,
                "1961/04/21"
            ),
            Triple(
                "حل مكتب الكومنفورم",
                null,
                "1956/04/17"
            ),

            Triple(
                "مؤتمر التعاون الاقتصادي الدولي",
                null,
                "1975/12"
            ),

            Triple(
                "تأسيس جامعة الدول العربية",
                null,
                "1945/03/22"
            ),

            Triple(
                "وصول أرمسترونغ لسطح القمر",
                null,
                "1969"
            ),

            Triple(
                "قيام الو.م.أ بإنشاء جسر جوي لفك الحصار على برلين",
                null,
                "1948/06/28 - 1949/05/12"
            ),

            Triple(
                "حصار الاتحاد السوفيتي لبرلين الغربية",
                null,
                "1948/06/23"
            ),

            Triple(
                "اندلاع الحرب الأهلية بين الكوريتين",
                null,
                "1950/06/25"
            ),

            Triple(
                "انتهاء الحرب الأهلية الكورية",
                null,
                "1953/07/27"
            ),

            )
        val quiz = Quiz(
            questions, (questions.size * 12000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_1,
                QUIZ_TYPE to DATES_QUIZ
            )
        )
        return quiz
    }

    fun getHisDatesQuizSemester2(): Quiz {
        val questions = listOf(
            Triple("تأسيس حزب نجم شمال إفريقيا", null, "20/06/1926"),
            Triple("سلم الشجعان", null, "23/10/1958"),
            Triple("تأسيس حزب الشعب", null, "11/03/1937"),
            Triple("مفاوضات إيفيان الثانية", null, "07/03/1962 - 18/03/1962"),
            Triple("تأسيس حركة انتصار الحريات الدمقراطية", null, "02/11/1946"),
            Triple("فترة رئاسة أحمد بن بلة", null, "1962 - 19/06/1965"),
            Triple("دستور 1947", null, "20/09/1947"),
            Triple("فترة رئاسة الهواري بومدين", null, "1965 - 27/12/1978"),
            Triple("إنشاء المنظمة الخاصة", null, "15/02/1947"),
            Triple("فترة رئاسة الشاذلي بن جديد", null, "07/02/1979 - 11/01/1992"),
            Triple("إنشاء اللجنة الثورية للوحدة والعمل", null, "23/03/1954"),
            Triple("إنشاء المجالس الشعبية البلدية", null, "1967"),
            Triple("اكتشاف المنظمة الخاصة", null, "28/03/1950"),
            Triple("إنشاء المجالس الشعبية الولائية", null, "1969"),
            Triple("اجتماع مجموعة 22", null, "25/06/1954"),
            Triple("ترأس الجزائر لمؤتمر حركة عدم الانحياز", null, "1973"),
            Triple("اجتماع لجنة الستة", null, "10/10/1954"),
            Triple("تأسيس الاتحاد العام للعمال الجزائريين", null, "24/02/1956"),
            Triple("كتابة بيان أول نوفمبر", null, "24/10/1954"),
            Triple("القرصنة الجوية (اختطاف 5 مناضلين)", null, "20/08/1956"),
            Triple("مؤتمر الصومام", null, "20/08/1956"),
            Triple("قنبلة ساقية سيدي يوسف (تونس)", null, "08/02/1958"),
            Triple("ميثاق طرابلس", null, "27/05/1962"),
            Triple("بداية إضراب الثمانية أيام", null, "28/01/1957"),
            Triple("أزمة حركة الانتصار", null, "1953"),
            Triple("بداية مؤتمر باندونغ", null, "18/04/1955"),
            Triple("الانقلاب العسكري 'التصحيح الثوري'", null, "19/06/1965"),
            Triple("مظاهرات شعبية في الجزائر", null, "11/12/1960"),
            Triple("تأسيس منظمة الوحدة الإفريقية", null, "25/05/1963"),
            Triple("مظاهرات المهاجرين في فرنسا", null, "17/10/1961"),
            Triple("بدأ مفاوضات إيفيان الأولى", null, "20/05/1961"),
            Triple("محادثات مولان", null, "27/06/1960"),
            Triple("استفتاء تقرير المصير", null, "01/07/1962"),
            Triple("لقاء لوسارن بسويسرا", null, "20/02/1961"),
            Triple("معركة وهران", null, "01/08/1958"),
            Triple("محاولة المعمرين للانقلاب", null, "22/04/1962"),
            Triple("تأسيس الحكومة المؤقتة", null, "19/09/1958"),
            Triple("الإعلان عن قيام الجمهورية الجزائرية", null, "26/09/1962"),
            Triple("هجومات الشمال القسنطيني", null, "20/08/1955"),
            Triple("تمرد الجيش الفرنسي بقيادة ماسو", null, "13/05/1958"),
            Triple("رئاسة ديغول للحكومة الفرنسية", null, "01/06/1958"),
            Triple("إضراب الطلبة الجزائريين", null, "19/05/1956"),
            Triple("رئاسة ديغول للجمهورية الفرنسية", null, "08/01/1959"),
            Triple("اعتراف ديغول باستقلال الجزائر", null, "03/07/1962"),
            Triple("مشروع قسنطينة", null, "03/10/1958"),
            Triple("إعلان حالة الطوارئ في الجزائر", null, "03/04/1955"),
            Triple("اقتراح فرنسا للاستفتاء", null, "16/09/1959"),
            Triple("استعادة جمعية العلماء المسلمين", null, "21/07/1946")
        )
        val quiz = Quiz(
            questions, (questions.size * 12000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_2,
                QUIZ_TYPE to DATES_QUIZ
            )
        )
        return quiz
    }

    fun getHisDatesQuizSemester3(): Quiz {
        val questions = listOf(
            Triple("تأميم قناة السويس", null, "26/07/1956"),
            Triple("العدوان الثلاثي على مصر", null, "29/10/1956"),
            Triple("بداية الحرب الفيتنامية ضد فرنسا", null, "1946"),
            Triple("معركة ديان بيان فو", null, "13/03/1954 - 07/05/1954"),
            Triple("انهزام الولايات المتحدة ضد فيتنام", null, "27/01/1973"),
            Triple("قيام الثورة المصرية (الانقلاب)", null, "23/07/1952"),
            Triple("توحيد فيتنام", null, "30/04/1975"),
            Triple("الحرب العربية الإسرائيلية", null, "06/10/1973"),
            Triple("بداية الاستعمار البريطاني للهند", null, "1857"),
            Triple("قرار وقف إطلاق النار بين العرب وإسرائيل", null, "22/10/1973"),
            Triple("استقلال الهند", null, "15/08/1947"),
            Triple("تأسيس الحركة الصهيونية", null, "1897"),
            Triple("انفصال الهند وباكستان", null, "1965"),
            Triple("انطلاق الثورة الكوبية", null, "1958"),
            Triple("انفصال باكستان وبنغلادش", null, "1971"),
            Triple("إطلاق سراح فيدل كاسترو", null, "1955"),
            Triple("ظهور تنظيم الضباط الأحرار", null, "1948"),
            Triple("نجاح كاسترو في الإطاحة بنظام الحكم", null, "1959"),
            Triple("توقيع اتفاقية كامب ديفيد", null, "17/09/1978"),
            Triple("مؤتمر جنيف المتعلق بالهند الصينية", null, "20/07/1954"),
            Triple("معاهدة السلام بين مصر وإسرائيل", null, "26/03/1979"),
            Triple("خضوع مصر للاحتلال البريطاني", null, "1881"),
            Triple("الهجوم الإسرائيلي على القوات المصرية في غزة وسيناء", null, "29/10/1956"),
            Triple("قصف بورسعيد من طرف فرنسا وبريطانيا", null, "05/11/1956"),
            Triple("بداية الاستعمار الفرنسي للفيتنام", null, "1858"),
            Triple("قيام منظمة الفرانكفونية", null, "20/03/1970"),
            Triple("استيلاء موبوتو على الحكم", null, "24/01/1965"),
            Triple("قيام جمهورية الكونغو", null, "01/08/1885"),
            Triple("إعلان النظام الجمهوري في مصر", null, "18/01/1953"),
            Triple("تأسيس منظمة التحرير الفلسطينية", null, "28/05/1964"),
            Triple("مذبحة دير ياسين", null, "19/04/1949"),
            Triple("معركة الكرامة", null, "21/03/1986"),
            Triple("الغارات الجوية الإسرائيلية على المطارات المصرية", null, "05/06/1967"),
            Triple("مجازر صبرا وشاتيلا", null, "18/09/1982"),
            Triple("زيارة أنور السادات للقدس", null, "19/11/1977")

        )
        val quiz = Quiz(
            questions, (questions.size * 12000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_3,
                QUIZ_TYPE to DATES_QUIZ
            )
        )
        return quiz
    }

    fun getHisAllDatesQuiz(): Quiz {
        val hisDates1 = Constants.getHisDatesQuizSemester1().questions
        val hisDates2 = Constants.getHisDatesQuizSemester2().questions
        val hisDates3 = Constants.getHisDatesQuizSemester3().questions
        val allDates = hisDates1 + hisDates2 + hisDates3
        val quiz = Quiz(
            allDates, (allDates.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_ALL,
                QUIZ_TYPE to DATES_QUIZ
            )
        )
        return quiz
    }

    fun getHisTermesQuiz1(): Quiz {
        val termesTitlesList = listOf(
            "سياسة المشاريع",
            "الازمات الدولية",
            "الحرب الباردة",
            "الدعاية المغرضة",
            "الانفراج الدولي",
            "مكتب الكومنفورم",
            "حركةعدم الانحياز",
            "الهاتف الأحمر",
            "الأحادية القطبية",
            "الشرعية الدولية",
            "النظام الدولي",
            "المنظمات غيرالحكومية",
            "المنظمة العالمية للتجارة",
            "التعايش السلمي",
            "مبدا ترومان",
            "مشروع مارشال",
            "مشروع إيزنهاور",
            "مبدأ جدا نوف",
            "منظمة الكوميكون",
            "عالم الشمال",
            "عالم الجنوب",
            "العالم الثالث",
            "العالم المتقدم",
            "الليبرالية",
            "الشيوعية",
            "الاشتراكية",
            "الإمبريالية",
            "الشرق",
            "الغرب",
            "الحلف الأطلسي",
            "حلف وارسو",
            "الستار الحديدي",
            "الصراع الأيديولوجي",
            "سياسة ملء الفراغ",
            "سياسة الاحتواء",
            "التوازن النووي"
        )
        val questions = listOf(
            Triple(
                "مجموعة من الدول التي تمثل العالم المتقدم الصناعي والغني وتضم الدول الاشتراكية والرأسمالية وتقع في نصف الكرة الشمالي شاملة أوربا الولايات المتحدة الأمريكية وكندا.",
                termesTitlesList,
                "عالم الشمال"
            ),
            Triple(
                "مجموعة من الدول التي تمثل العالم المتخلف الواقعة جنوب خطي عرض 30 °شمال في أمريكا و 35 ° بين أوروبا وإفريقيا تشترك في التخلف والتعرض للاستعمار...",
                termesTitlesList,
                "عالم الجنوب"
            ),
            Triple(
                "أطلق عليه للتفريق بين العالم الاول الذي هو الغرب والثاني المعسكر الشرقي يقع معظمه جنوب خط الاستواء , دوله تتفاوت في التخلف بين متخلفة و في طريق النمو ونامية تحمل خصائص عالم الجنوب .",
                termesTitlesList,
                "العالم الثالث"
            ),
            Triple(
                "جموع الدول  الصناعية التي تعرف حالة من الازدهار الاقتصادي و لرفاه الاجتماعي و الاستقرار السياسي و هي تقع في النصف الشمالي .",
                termesTitlesList,
                "العالم المتقدم"
            ),
            Triple(
                "مذهب اقتصادي واجتماعي يرتكز على مبدأ الحرية الفردية، ويستبعد أي تدخل للدولة في الشؤون الاقتصادية.",
                termesTitlesList,
                "الليبرالية"
            ),
            Triple(
                "أسمى مرحلة من الاشتراكية وضعه كارل ماركس من أبرز خصائصه زوال مفهوم الدولة وزوال العملة كوسيلة تداول.",
                termesTitlesList,
                "الشيوعية"
            ),
            Triple(
                "وهي نظام سياسي اقتصادي واجتماعي يعتمد على الملكية الجماعية لوسائل تبناها الاتحاد السوفييتي سنة 1917 بعد نجاح الثورة البلشفية.",
                termesTitlesList,
                "الاشتراكية"
            ),
            Triple(
                "هيمنة اقتصادية وعسكرية وثقافية وسياسية لدولة أو عدة دول على دولة أو عدة دول , ونهب وثرواتها وهي صورة أخرى للاستعمار التقليدي و هي المرحلة العليا لتطور الرأسمالية .",
                termesTitlesList,
                "الإمبريالية"
            ),
            Triple(
                "يقصد به الدول التي تقع في شرق أوربا و التي طبقت النظام الاشتراكي  غداة الحرب الباردة.",
                termesTitlesList,
                "الشرق"
            ),
            Triple(
                "الدول الديمقراطية الرأسمالية التي تقع غرب أوربا وكذلك الولايات المتحدة الأمريكية واستعمل أيضا غداة الحرب الباردة.",
                termesTitlesList,
                "الغرب"
            ),
            Triple(
                "حلف تابع للمعسكر الشيوعي انشأ في ماي 1955 م واتخذت العاصمة البولندية مقراً له وضم الاتحاد السوفييتي , تشيكوسلوفاكيا, المانيا الغربية, المجر, بولندا, رومانيا,بلغارياوألبانيا ( يهدف إلى مواجهة الأحلاف الغربية وسياسة الحصار السياسي والعسكري المفروض عليه من قبل الغرب وحق الدفاع المشترك ضد أي هجوم عسكري محتمل على الدول الأعضاء.)",
                termesTitlesList,
                "حلف وارسو"
            ),
            Triple(
                "مفهوم استعمله رئيس وزراء بريطانيا ونستون تشرشل لما تحدث عن أطماع الاتحاد السوفياتي التوسعية في أوروبا الشرقية فقال إنها أسدلت ستارا حديديا على أوروبا من منطقة ستايتن على بحر البلطيق إلى ميناء تريستيا الإيطالي .",
                termesTitlesList,
                "الستار الحديدي"
            ),
            Triple(
                "صراع فكري حضاري بين الشيوعية والرأسمالية بحيث يحبذ كل منهما القضاء على الآخر وفرض مذهبه عليه.",
                termesTitlesList,
                "الصراع الأيديولوجي"
            ),
            Triple(
                "سياسة استعمارية تبنتها الولايات المتحدة الأمريكية ووظفتها بعد ضعف وانسحاب القوى الاستعمارية التقليدية (فرنسا وبريطانيا ) وملء الفراغ السياسي المتروك.   قابلها الاتحاد السوفيتي بدعمه للحركات التحررية .",
                termesTitlesList,
                "سياسة ملء الفراغ"
            ),
            Triple(
                "تقوم هذه السياسة على فكرة إنشاء سلسلة من الأحلاف والقواعد العسكرية , بهدف تطويق وعزل  وخنق الإتحاد السوفييتي ومنع انتشار نفوذه وإيديولوجيته إلى الدول المجاورة و سائر مناطق العالم .",
                termesTitlesList,
                "سياسة الاحتواء"
            ),
            Triple(
                "( توازن الرعب ) توازن عسكري نووي دولي منبثق من امتلاك كل من الو م أ و الإتحاد السوفياتي  للسلاح النووي  تستطيع بها تدمير القوة الأخرى  وهكذا نشأت حالة من قوة الردع النووي العسكري المتبادلة مما حال دون اندلاع مواجهة عسكرية بينهما  يتعذر فيها تحقيق نصرا على الأخرى.",
                termesTitlesList,
                "التوازن النووي"
            ),
            Triple(
                "سياسة ظهرت في الحرب الباردة تمثلت في سعي كل طرف إلى احتواء واستقطاب اكبر عدد من الدول في إطار مواجهته للمعسكر الأخر من خلال جملة مشاريع منها مشروع مارشال , مشروع ترومان , ومشروع أيزنهاور ورد عليها الاتحاد السوفيياتي بجملة من المشاريع المماثلة كالكومنفورم , ومبدأ جدانوف ومنظمة الكوميكون.",
                termesTitlesList,
                "سياسة المشاريع"
            ),
            Triple(
                "هي وصف للحالات التي مرت بها العلاقات الدولية أثناء الحرب الباردة والتي ميزها اللااستقرار والتوتر السياسي الحاد الذي لم يصل لمستوى الحر بالفعلية و إن كان ينذر باحتمال وقوعها و من أبرزها الأزمة الكورية والكوبية و أزمة برلين.",
                termesTitlesList,
                "الازمات الدولية"
            ),
            Triple(
                "هي صراع اديولوجي حضاري و مصلحي عرفه العالم بعد الح ع 2 حتى سنة 1990م  بين المعسكرين استعملت فيه مختلف الوسائل باستثناء المواجهةالعسكرية المباشرة بين الو م أ و الاتحاد السوفيتي.",
                termesTitlesList,
                "الحرب الباردة"
            ),
            Triple(
                "من بين ابرز الوسائل المستخدمة في الحرب الباردة وتقوم على نشر الأفكار من أجل التأثير على السلوك الإنساني والدفع  به إلى تقبل فلسفة ما أو معاداتها بوسائل الإذاعات ـ الصحف ـ القنوات التليفزيونية ـ الإشهار ـ النشريات السرية .",
                termesTitlesList,
                "الدعاية المغرضة"
            ),
            Triple(
                "هي وصف لطبيعة علاقات المعسكرين بعد تسوية أزمة كوبا الخطيرة حيثتخلصخلالهاالمعسكرانمنالشدةوالضيقاللذانوصلإليهمابفعلاشتدادأزماتالحربو قد عرفها بعض الخبراء بذوبان الجليد بين المعسكرين.",
                termesTitlesList,
                "الانفراج الدولي"
            ),
            Triple(
                "او مكتب الاعلام الشيوعي اعلن عنه في 5 / 6 أكتوبر 1947 ممهمته التنسيق بين الأحزاب الشيوعية في العالم و الدعاية لمناهضة الرأسمالية",
                termesTitlesList,
                "مكتب الكومنفورم"
            ),
            Triple(
                "نشاط سياسي دولي،تأسست في مؤتمر بلغراد المنعقد بين 1- 6 سبتمبر1961 م،حاءت لتجسيد مبدأ الحيادالايجابي وفكرة عدم الانحياز وتعبيراعن موقف دول العالم الثالث الرفض لصراع المعسكرين . تضم حاليا 113 دولة .",
                termesTitlesList,
                "حركةعدم الانحياز"
            ),
            Triple(
                "من مظاهر الانفراج الدولي أنشأ بموجب اتفاق وقع في 30/06/1963  م و هوعبارة عن خط اتصال مباشر بين مكتب الرئيس الأمريكي ( في البيت الأبيض ) والسوفياتي ( في الكرملين )  .",
                termesTitlesList,
                "الهاتف الأحمر"
            ),
            Triple(
                "نظام دولي جديد فيه قيادة عالم و توجيه العلاقات الدولية لدولة واحدة هي الو , م , أ القوى الكبرى فيالعالم, وقد ظهر بعد انهيارالمعسكر الشيوعي وزوال القطبيةا لثنائية .",
                termesTitlesList,
                "الأحادية القطبية"
            ),
            Triple(
                "يقصد بها الالتزام  و تنفيذ القرارات الصادرة عن الهيئات الدولية كالأمم المتحدة وأجهزتها .بما يخدم مصالح الو م أ و حلفائها .",
                termesTitlesList,
                "الشرعية الدولية"
            ),
            Triple(
                "هو مجموعة المبادئ، والقيم، والضوابط التي تسير العلاقات الدولية في مختلف المجالات كميثاق ملزم للجميع في اتخاذ القرار وفي تنفيذهو يشرف على ذلك الهيئات والمنظمات الدولية كالأمم المتحدة و المؤسسات التابعة لها , برز بعد لقاء مالطا 1989 م و انهيار المعسكر الشيوعي.",
                termesTitlesList,
                "النظام الدولي"
            ),
            Triple(
                "هي منظمات أو هيئات خيرية عالمية غير تجارية تعرف بالمجتمع المدني ويعمل فيها موظفون متطوعون تنشط في كافة الميادين ذات الطابع الإنساني كالبيئة, أطباء بلاحدود , حقوق الإنسان, جهود الاغاثة ,الرعاية الصحية والطفولة...",
                termesTitlesList,
                "المنظمات غيرالحكومية"
            ),
            Triple(
                "منظمة دولية أنشئت سنة 1995 م , خلفا لمنظمة – الغات GATTتعمل على وضع القواعد والقوانين التي تنظم التجارة العالمية وتنظيم جولات المفاوضات التجارية متعددة الأطراف و فض النزاعات التجارية بين 149 بلدا سنة 2004 م مقرها جنيف بسويسرا .",
                termesTitlesList,
                "المنظمة العالمية للتجارة"
            ),
            Triple(
                "هو مصطلح سياسي يقصد به إبعاد شبح الحرب الساخنة كوسيلة لتسوية الخلافات الدولية وحلها بالطرق السلميةو الحوار مع القبول بازدواجية النظام الدولي في ظل التعايش و تبادل المصالح والمنافع خاصة في الفترة الممتدة ما بين1956 م / 1977 م",
                termesTitlesList,
                "التعايش السلمي"
            ),
            Triple(
                "نسبة للرئيس الأمريكي هاري ترومان اعلن عته في 12 مارس 1947 م , يقضي بتقديم مساعدات مالية بقيمة 400 مليون دولار لبعض الدول لمواجهة الزحف الشيوعي ولقد استفادت منه خاصة اليونان وتركيا.",
                termesTitlesList,
                "مبدا ترومان"
            ),
            Triple(
                "نسبة الى  وزير الخارجية الأمريكي جورج مارشال , اعلن عنه ذلك  المشروع في 5 جوان 1947 م وتمثل في تقديم مساعدات مالية لاوربا ( 12 مليار دولار ) من اجل إعادة تعميرها وانعاشها اقتصاديا جراء مالحق بها خلال الحرب العالمية الثانية .",
                termesTitlesList,
                "مشروع مارشال"
            ),
            Triple(
                "نسبة للرئيس الأمريكي إيزنهاور في 05 جانفي 1957 م , يقضي بتقديم مساعدات بقيمة 200 مليون دولار لدول آسيا والمشرق العربي في إطار كسب دول حليفة في المنطقة وصد الشيوعية .",
                termesTitlesList,
                "مشروع إيزنهاور"
            ),
            Triple(
                "نسبة للسياسي السوفيتي جدا نوف وهو رد على مشروع مارشال تم الإعلان عنه في 05 أكتوبر 1947م.",
                termesTitlesList,
                "مبدأ جدا نوف"
            ),
            Triple(
                "او منظمة التعاون والتبادل الحر تأسست في 25 جانفي 1949 جاءت كرد فعل على مشروع مارشال وتضم الاتحاد السوفياتي واوربا الشرقية ومن أهدافها تنشيط التبادل التجاري بين دول المعسكر وإقامة سوق حر للتبادل بين دول المعسكر , حلت في 28 جوان 1991 م .",
                termesTitlesList,
                "منظمة الكوميكون"
            )
        )
        return Quiz(
            questions, (termesTitlesList.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_1,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
    }

    fun getHisTermesQuiz2(): Quiz {
        val termesList = listOf(
            "حرب التحرير",
            "الثورة التحريرية",
            "الأحزاب الوطنية",
            "سياسة الإغراء",
            "سياسة القمع",
            "التيار الثوري",
            "المد التحرري",
            "الميثاق",
            "البرنامج",
            "البيان",
            "الدبلوماسية",
            "مشروع سوستال",
            "المكاتب العربية",
            "المحتشدات",
            "تقرير المصير",
            "مشروع قسنطينة",
            "المناطق المحرمة",
            "مخططا شال وموريس",
            "القوة الثالثة",
            "سلم الشجعان",
            "حركة عدم الانحياز"
        )
        val questions = listOf(
            Triple(
                "يقصد بها مجموع الجهود العسكرية والسياسية التي شهدتها الجزائر في الفترة الممتدة بين 1954 إلى غاية 1962 بهدف تحقيق الاستقلال واسترجاع السيادة وإخراج الاحتلال.",
                termesList,
                "حرب التحرير"
            ),
            Triple(
                "هي فعل تحرري شامل ورد شعبي عنيف بهدف السيادة والاستقلال من خلال مجموع العمليات العسكرية والسياسية لجيش التحرير وجبهة التحرير وهذا حق أكدت عليه الجمعية العامة للأمم المتحدة في قراراتها.",
                termesList,
                "الثورة التحريرية"
            ),
            Triple(
                "وهي مجموع التنظيمات ذات الطابع السياسي التي ظهرت في الجزائر بعد الحرب ع 1 إلى 1954 و المتشكلة في أربعة اتجاهات رئيسية هي دعاة المساواة , دعاة الإدماج , دعاة الاستقلال , دعاة الإصلاح.",
                termesList,
                "الأحزاب الوطنية"
            ),
            Triple(
                "تتمثل في تلك الإصلاحات والمشاريع السياسية والاقتصادية(مثل مشروع قسنطينة ) التي أقرتها فرنسا لتسير أوضاع الجزائر بهدف تمزيق مجتمعها و السيطرة و كسب عملاء لها منه.",
                termesList,
                "سياسة الإغراء"
            ),
            Triple(
                "هي سياسة معاكسة تتمثل في جملة القوانين و الإجراءات التعسفية الإجرامية التي أقدمت عليها فرنسا في حق الشعب الجزائري مستعملة في ذلك أقصى وسائل التنكيل و الإبادة الجماعية و التهجير و التجهيل من اجل إخضاعه و قتل روح المقاومة فيه بالمجازر و الإبادة الجماعية.",
                termesList,
                "سياسة القمع"
            ),
            Triple(
                "مجموعة الشباب المتحمس المؤمن بضرورة العمل المسلح و الثورة و هم أفراد المنظمة الخاصة وكل من يؤيد العمل المسلح.",
                termesList,
                "التيار الثوري"
            ),
            Triple(
                "هو رد الفعل النضالي الوطني للشعوب المستعمرة ضد قوى الاستعمار و الذي امتد زمنيا من 1945 – إلى 1965 في قارتي افريقيا واسيا.",
                termesList,
                "المد التحرري"
            ),
            Triple(
                "هو دستور يشتمل على الخطة التي تنظم أي بلد والقواعد التي تحكمه ،ويشتمل على مواد توضح الغايات والمبادئ والمعتقدات الأساسية والطرق التي يعمل بها البلد.",
                termesList,
                "الميثاق"
            ),
            Triple(
                "هو مجموع الأطروحات و القرارات المتفق عليها و هو عبارة عن خطة عمل بأهداف وإجراءات وأجال محددة ودقيقة.",
                termesList,
                "البرنامج"
            ),
            Triple(
                "هو وثيقة صادرة عن هيئة سياسية أو إدارية تتضمن الحقوق والمطالب الأساسية لجماعة أو شعب ما ومن أمثلة ذلك بيان نوفمبر.",
                termesList,
                "البيان"
            ),
            Triple(
                "المقصود بها تلك الجهود التي بذلتها الثورة بإنشاء جهاز دبلوماسي يتحرك بين عواصم الدول و المحافل الدولية لكسب الدعم السياسي و التعاطف العالمي مع الثورة الجزائرية.",
                termesList,
                "الدبلوماسية"
            ),
            Triple(
                "نسبة إلى صاحبه جاك سوستال ذو الأصل اليهودي الذي عين واليا عاما على الجزائر في 15 فيفري 1955 و قد تناول مشروع سوستال عدة جوانب إصلاحية إدارية واقتصادية واجتماعية وثقافية الهدف منه الوصول إلى دمج الجزائريين بفرنسا ولكن الشعب الجزائري رد عليه بهجومات 20 أوت 1955 كما أن الكولون أنفسهم رفضوا دمج الجزائريين ، ففشل المشروع.",
                termesList,
                "مشروع سوستال"
            ),
            Triple(
                "جهاز إداري خاص أقامته الإدارة الفرنسية يهتم بشؤون الجزائريين ويهدف إلى ضرب الثورة.",
                termesList,
                "المكاتب العربية"
            ),
            Triple(
                "مراكز مسيجة ومغلقة و محروسة وهى إحدى الوسائل القمعية الرهيبة التي لجأت إليها فرنسا لخنق الثورة عن طريق عزل الشعب عنها وقد عمت أرجاء الوطن وضمت قرابة 3 مليون جزائري.",
                termesList,
                "المحتشدات"
            ),
            Triple(
                "حق الشعب في اختيار مستقبله السياسي أو شكل النظام الذي يريد أن يخضع له ما يتم عن طريق الاستفتاء الشعبي العام وتحت رقابة دولية.",
                termesList,
                "تقرير المصير"
            ),
            Triple(
                "هو مشروع استعماري دعائي أعلنه الجنرال ديغول في 4 أكتوبر 1958 بقسنطينة ويتضمن بناء 200 ألف مسكن لإيواء مليون شخص. توزيع 250 ألف هكتار من الأراضي على الجزائريين و توظيفهم . وبناء المدارس . وهو في الحقيقة مشروع استعماري هدفه إفشال الثورة وإبعاد وفصل الشعب عنها و عن جيش التحرير الوطني وإقناعه بضرورة الاندماج في فرنسا . و خلق فئة من المتغربين الجزائريين يحكم من خلالها الجزائر.",
                termesList,
                "مشروع قسنطينة"
            ),
            Triple(
                "مناطق جغرافية محددة تقوم فرنسا باخلائها من السكان تحت التهديد واستخدام القوة تمهيدا لقنبلتها وحرقها لعزل الثوار وتشديد الخناق والحصار عليهم.",
                termesList,
                "المناطق المحرمة"
            ),
            Triple(
                "و هو عبارة عن إجراءات عسكرية شاملة تهدف للقضاء على الثورة من خلال تكثيف العمليات العسكرية وعزل وحدات الجيش ومنع تواصلها مع غلق الحدود تونسية والمغربية بخطين ( كهرباء حراسة ألغام ) لشل تحرك الثوار ووقف الدعم عنهم.",
                termesList,
                "مخططا شال وموريس"
            ),
            Triple(
                "طبقة برجوازية عميلة في المجتمع الجزائري دعمتها فرنسا كبديل عن الثورة و جهازها السياسي جبهة التحرير الوطني ولتغليط الرأي العام العالمي.",
                termesList,
                "القوة الثالثة"
            ),
            Triple(
                "هو عبارة عن مناورة سياسية و حرب نفسية أطلقها الجنرال ديغول يوم 23 – 10 - 1958 تقضي باستسلام الثوار وتسليم أسلحتهم مقابل ضمان حريتهم و سلامتهم و قد هدف إلى إفراغ الثورة من محتواها وإظهارها إلى العالم على أنها ثورة جياع و تمزق صفها.",
                termesList,
                "سلم الشجعان"
            ),
            Triple(
                "تنظيم سياسي تأسس على اثر مؤتمر بلغراد في سبتمبر 1961 تضم الدول حديثة الاستقلال التي اختارت سياسة الحياد الايجابي و عدم الميل لأي من المعسكرين المتصارعين في إطار الحرب الباردة.",
                termesList,
                "حركة عدم الانحياز"
            )
        )
        return Quiz(
            questions, (termesList.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_2,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
    }

    fun getHisTermesQuiz3(): Quiz {
        val termesList = listOf(
            "حركات التحرر",
            "الاستعمار التقليدي",
            "الاستعمار المقنع",
            "الانتداب",
            "التحرر السياسي",
            "التحرر الشامل",
            "الكفاح المسلح",
            "التبعية",
            "الفرانكفونية",
            "الكومنولث",
            "الحركة الصهيونية",
            "وعد بلفور",
            "حركة فتح",
            "منظمة التحرير الفلسطينية",
            "الوكالة الصهيونية",
            "الانتفاضة"
        )
        val questions = listOf(
            Triple(
                "ظاهر سياسية و عسكرية شهدها العالم بعد الحرب العالمية 2 – 1965 – 1965 متمثلة في كفاح ونضال الشعوب المستعمرة ضد الوجود الاستعماري مما أسهم في تراجع وتصفية الاستعمار التقليدي في أغلب المستعمرات.",
                termesList,
                "حركات التحرر"
            ),
            Triple(
                "هو القائم على أساليب الإخضاع الشامل باستخدام القوة و الحملات العسكرية لتحقيق الهيمنة التامة و نهب ثروات و موارد المستعمرات و استغلال شعوبها استغلالا مباشرا.",
                termesList,
                "الاستعمار التقليدي"
            ),
            Triple(
                "نتيجة تزايد وعي شعوب المستعمرات و موجة حركات التحرر في النصف الثاني من القرن 20 عمدت الدول الاستعمارية إلى إخفاء تغلغلها بأقنعة جديدة منها الاتفاقيات السياسية و الاقتصادية أو الثقافية و التغلغل باستخدام المساعدات الإنسانية و عن طريق وسائل الإعلام.",
                termesList,
                "الاستعمار المقنع"
            ),
            Triple(
                "أسلوب استعماري جاءت به عصبة الأمم ويقضي بوضع دولة تحت إشراف دولة أخرى كبرى و قوية بزعم أن هذه الدولة غير قادرة على تسيير شؤونها بنفسها.",
                termesList,
                "الانتداب"
            ),
            Triple(
                "هو أهم هدف ضحت من اجله حركات التحرر و هو استرجاع الحرية و الاستقلال و إخراج قوات المستعمر و بناء دولة وطنية تحظى بالاعتراف الدولي.",
                termesList,
                "التحرر السياسي"
            ),
            Triple(
                "هو دعم الاستقلال السياسي بالاستقلال الاقتصادي بحيث تشرف الدولة وتتحكم في جميع ثرواتها الاقتصادية مما يعطيها الحرية والسيادة التامة في مواقفها واختياراتها الداخلية والخارجية.",
                termesList,
                "التحرر الشامل"
            ),
            Triple(
                "حق أكدت عليه الجمعية العامة للأمم المتحدة في 14/12/1974م على حق الشعوب في الكفاح المسلح لتتحرر من الاحتلال وأكدت أن أي محاولة لقمع الكفاح المسلح ضد السيطرة الأجنبية والاحتلال هي مخالفة لميثاق الأمم المتحدة.",
                termesList,
                "الكفاح المسلح"
            ),
            Triple(
                "هي علاقات غير متكافئة تقوم بين الدول الاستعمارية الكبرى ومستعمراتها السابقة في المجال الاقتصادي و السياسي و الثقافي تجعلها في حالة ضعف وعجز كما هو الحال في منظمتي الكومنولث والفرانكفونية التي تربط بين بريطانيا و فرنسا ومستعمراتهما السابقة.",
                termesList,
                "التبعية"
            ),
            Triple(
                "هي منظمة تظم المستعمرات الفرنسية السابقة و الشعوب الناطقة بالفرنسية تأسست في 20 مارس 1970 تحول اسمها إلى الوكالة الفرانكفونية سنة1995.",
                termesList,
                "الفرانكفونية"
            ),
            Triple(
                "هي منظمة تظم الدول التي كانت تابعة للاستعمار البريطاني تأسست بموجب قانون وستمنستر 11-11- 1931 و تشمل هذه المنظمة بريطانيا ومعها 53 دولة و بقيت تابعة لها اقتصاديا.",
                termesList,
                "الكومنولث"
            ),
            Triple(
                "حركة سياسية عنصرية ظهرت سنة 1897 عمل زعماؤها على المطالبة بإقامة وطن قومي لليهود في فلسطين.",
                termesList,
                "الحركة الصهيونية"
            ),
            Triple(
                "وثيقة حكومية بريطانية صادرة عن وزير الخارجية آرثر جيمس بلفور يوم 02 نوفمبر 1917 نصت على إقامة وطن قومي لليهود بفلسطين ،مقابل حصول بريطانيا على الدعم المالي اليهودي أثناء الحرب العالمية الأولى.",
                termesList,
                "وعد بلفور"
            ),
            Triple(
                "تعني حركة تحرير فلسطين تأسست في 1957 بالكويت على يد ياسر عرفات ومجموعة من رفاق النضال وذلك في إطار التحضير للكفاح المسلح.",
                termesList,
                "حركة فتح"
            ),
            Triple(
                "تأسست في 28 ماي 1964 بالقاهرة وهي الممثل الشرعي للشعب الفلسطيني تضم المنظمات الفدائية والشعبية والاتحادات وتهيمن عليها حركة فتح باعتبارها اكبر المنظمات الفدائية هدفها استرجاع فلسطين التي اغتصبها الصهاينة منذ 1948.",
                termesList,
                "منظمة التحرير الفلسطينية"
            ),
            Triple(
                "منظمة صهيونية أسسها حاييم وايزمان تعمل على تشجيع الهجرة اليهودية من مختلف أنحاء العالم إلى فلسطين والاستيطان بها وتدعم مشروع الدولة اليهودية ،مركز نشاطها القدس.",
                termesList,
                "الوكالة الصهيونية"
            ),
            Triple(
                "شكل من أشكال المقاومة ورفض الاحتلال ،لها عدة أساليب ووسائل ، مثل انتفاضة الشعب الفلسطيني التي انطلقت من عمق الأراضي الفلسطينية وأخذت طابع جماهيري الأولى في 1987 والثانية في 2000.",
                termesList,
                "الانتفاضة"
            ),
        )
        return Quiz(
            questions, (termesList.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_3,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
    }

    fun getHisAllTermesQuiz(): Quiz {
        val hisTermes1 = Constants.getHisTermesQuiz1().questions
        val hisTermes2 = Constants.getHisTermesQuiz2().questions
        val hisTermes3 = Constants.getHisTermesQuiz3().questions
        val allDates = hisTermes1 + hisTermes2 + hisTermes3
        val quiz = Quiz(
            allDates, (allDates.size * 8000).toLong(), hashMapOf(
                MODULE to HISTOIRE,
                QUIZ_SEMESTER to SEMESTER_ALL,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
        return quiz
    }

    fun getAllHistoryQuizzes(): List<Quiz> {
        return listOf(
            Constants.getHisPersoQuizSemester1(),
            Constants.getHisDatesQuizSemester1(),
            Constants.getHisTermesQuiz1(),
            Constants.getHisPersoQuizSemester2(),
            Constants.getHisDatesQuizSemester2(),
            Constants.getHisTermesQuiz2(),
            Constants.getHisPersoQuizSemester3(),
            Constants.getHisDatesQuizSemester3(),
            Constants.getHisTermesQuiz3(),
            Constants.getHisAllPersoQuiz(),
            Constants.getHisAllDatesQuiz(),
            Constants.getHisAllTermesQuiz()
        )
    }

    fun getGeoTermes1(): Quiz {
        val termesNamesList = listOf(
            "العالم الثالث",
            "العالم المتقدم",
            "تبيض الأموال",
            "اتفاقية لومي",
            "التقدم",
            "التخلف",
            "منظمة الاوبيك",
            "الشركات المتعددة الجنسيات",
            "منظمة التجارة العالمية",
            "صندوق النقد الدولي",
            "البنك العالمي",
            "المعيار",
            "رؤوس الاموال",
            "الاستثمارات",
            "البنوك",
            "البورصة",
            "السندات",
            "الأسهم",
            "العملة",
            "اقتصاد السوق",
            "العولمة",
            "التنمية",
            "الميزان التجاري",
            "التضخم",
            "المؤشر",
            "مؤشر التنمية البشرية",
            "ناتج داخلي خام",
            "ناتج داخلي خام للفرد",
            "ناتج وطني خام"
        )
        val questions = listOf(
            Triple(
                "ظهر هذا المصطلح سنة 1952 على لسان الاقتصادي الفرنسي \"ألفريد صوفي\" للتمييز بينه وبين العالم الأول (الرأسمالي) والثاني (الاشتراكي) يضم دول قارة أسيا وإفريقيا وأمريكا اللاتينية.",
                termesNamesList, "العالم الثالث"
            ),
            Triple(
                "هو مجموعة الدول المتطورة في الميادين الاقتصادية التكنولوجية الاجتماعية الثقافية والسياسية ويقع معظمه شمال دائرتي عرض 30° شمالًا في أمريكا و35° شمالًا في أوروبا وآسيا بالإضافة إلى جنوب إفريقيا وأستراليا ونيوزيلندا الواقعة في الجنوب.",
                termesNamesList, "العالم المتقدم"
            ),
            Triple(
                "المقصود عملية غسيل الأموال أي تحويل أموال مكتسبة بطريقة غير شرعية مثل أموال المخدرات إلى أموال شرعية عن طريق عمليات بنكية وتجارية تحت غطاء المشروعية.",
                termesNamesList, "تبيض الأموال"
            ),
            Triple(
                "اتفاقية وقعت سنة 1975 بين الاتحاد الأوروبي ودول في إفريقيا ومنطقتي البحر الكاريبي والمحيط الهادي وتمنح الاتفاقية للصادرات الإفريقية إلى الاتحاد الأوروبي مزايا منها إعفاءً من الرسوم الجمركية.",
                termesNamesList, "اتفاقية لومي"
            ),
            Triple(
                "هي الحركة التي تسير نحو الأهداف المنشودة والمقبولة والتي يتحقق من ورائها تطور إيجابي نحو وضع أفضل وينطوي التقدم على مراحل أرقى من التطور من حيث الثقافة والقدرة الإنتاجية والسيطرة والاستغلال الأمثل للموارد الطبيعية.",
                termesNamesList, "التقدم"
            ),
            Triple(
                "ظاهرة تختصّ بها البلدان التي تعاني من ضعف النمو الاقتصادي وبطء التنمية البشرية والتركيز على إنتاج المحاصيل التجارية واستخراج المواد الأوّلية الباطنية مقابل ضعف الأنشطة الصناعية التحويلية ونقص الخدمات.",
                termesNamesList, "التخلف"
            ),
            Triple(
                "منظمة الدول المنتجة والمصدرة للنفط تأسست في 1960 ببغداد بهدف حماية مصالح الأعضاء والوقوف في وجه الشركات الاحتكارية وتحقيق أسعار مناسبة للمحروقات تضم: العراق، السعودية، الكويت، فنزويلا، إيران، قطر، إندونيسيا، الإمارات العربية، الجزائر، نيجيريا.",
                termesNamesList, "منظمة الاوبيك"
            ),
            Triple(
                "وهي الشركات التي ملكيتها تخضع لسيطرة جنسيات متعددة وتمارس نشاطها في بلاد أجنبية. تتمتع بقدر كبير من حرية تحريك ونقل الموارد ورؤوس المال والتكنولوجيا بين الدول المختلفة وهي تساهم في بلورة خصائص وآليات النظام الاقتصادي العالمي الجديد والتأكيد على عالميته وتعد من العوامل الأساسية في ظهور العولمة.",
                termesNamesList, "الشركات المتعددة الجنسيات"
            ),
            Triple(
                "تأسست 1995 تعمل على وضع قواعد التبادل التجاري وتحرير التجارة العالمية برفع الحواجز الجمركية وذلك قصد تسهيل تدفق السلع والخدمات عبر العالم بدون قيود كما تتولى فض النزاعات التجارية بين البلدان الأعضاء.",
                termesNamesList, "منظمة التجارة العالمية"
            ),
            Triple(
                "مؤسسة مالية دولية تأسست في 1945 للعمل على تسير النظام النقدي الدولي منذ ح ح 2 وضمان احترام الدول لقواعد مؤتمر بروتن وودز وتقديم الاستشارة والدعم للدول التي تواجه مشاكل مالية واقتصادية.",
                termesNamesList, "صندوق النقد الدولي"
            ),
            Triple(
                "انشأ في 25 - 06 - 1946 يقدم القروض للدول وخاصة النامية قصد تمويل المشاريع التنموية والمساعدات التقنية.",
                termesNamesList, "البنك العالمي"
            ),
            Triple(
                "معطيات قاعدية لضبط مواصفات موحدة لظاهرة ما أو هو أداة أو وسيلة تسمح بالإشارة أو الدلالة على وضعية معينة ويعبر عنه كذلك بأنه قيمة أو إشارة رقمية تسمح بتقييم حالة أو وضعية مجال اقتصادي معين.",
                termesNamesList, "المعيار"
            ),
            Triple(
                "هي الموارد المختلفة التي يمكن استخدامها في العملية الإنتاجية لغرض تحسين إنتاجية العمل ويتأتى رأس المال من تراكم فائض عمل سابق.",
                termesNamesList, "رؤوس الاموال"
            ),
            Triple(
                "تمثل مجموع الأموال المنفقة لغرض إنماء رأس المال بمعنى توظيف المال في شراء المعدات والآلات والعقارات لغرض الإنتاج. والاستثمار يأخذ شكلين مباشر ويكون بواسطة التمويلات الذاتية أو عن طريق الشراكة أو عن طريق الاقتراض، وغير مباشر حيث تقوم به الدولة مثل إقامة الطرقات العامة أو المدارس.",
                termesNamesList, "الاستثمارات"
            ),
            Triple(
                "هو منشأة مالية تتاجر بالنقود ولها غرض رئيسي هو العمل كوسيط بين رؤوس الأموال التي تسعى للبحث عن مجالات الاستثمار وبين مجالات الاستثمار التي تسعى للبحث عن رؤوس الأموال. وهي أنواع بنوك عامة وخاصة ومختلطة وتجارية زراعية صناعية ومن حيث مصدر الأموال هناك البنوك المركزية وبنوك الودائع وبنوك الأعمال والاستثمار.",
                termesNamesList, "البنوك"
            ),
            Triple(
                "هي سوق إصدار الأوراق المالية يتم في نطاقها التعامل مع الأوراق المالية عن طريق الاكتتاب سواء تعلق ذلك بإصدار الأسهم أو بإصدار السندات والاكتتاب في الأسهم قد يكون مقصورا على المؤسسين وقد يكون عاما.",
                termesNamesList, "البورصة"
            ),
            Triple(
                "هي عبارة عن قروض يقدمها المستثمرون إلى المؤسسات والحكومات حيث يقوم المستثمر بالحصول على سعر فائدة محددة نظير إقراض أمواله وفي المقابل تحصل الحكومة أو الشركة على الأموال.",
                termesNamesList, "السندات"
            ),
            Triple(
                "تمثل ملكية في شركة ما وهي أوراق تحمل قيمة نقدية متغيرة وتاريخيا تميل الأسهم إلى الارتفاع في القيمة بمرور الوقت والأسهم تكون عرضة لتقلبات سعرية ويتم تداولها في السوق كأي سلعة تخضع للعرض والطلب.",
                termesNamesList, "الأسهم"
            ),
            Triple(
                "يقصد بالعملة الشكل القانوني للنقد المتداول وهي تشمل النقود المعدنية وأوراق البنكنوت. ويطلق تعبير العملة الصعبة الذي أصبح مرادفا للدولار الأميركي على كل العملات القابلة للتحويل إلى الدولار.",
                termesNamesList, "العملة"
            ),
            Triple(
                "اقتصاد تبادلي مبني على الحرية الاقتصادية المطلقة للأشخاص والمؤسسات وعلى تحرير التجارة ، يخضع فقط إلى قانون العرض والطلب.",
                termesNamesList, "اقتصاد السوق"
            ),
            Triple(
                "تعني عالمية السوق إنتاجا واستهلاكا على حد سواء وهي تتعارض مع الاقتصاد الوطني وتتعارض مع مبدأ السيادة الوطنية للدول ، وتعتبر الشركات متعددة الجنسيات والمؤسسات المالية العالمية أكبر من يقف من وراء العولمة ويستفيد منها والعولمة تؤثر من خلال الإعلام سلبا على المجتمعات الوطنية وعلى تماسكها وتقاليدها فالعولمة توجه عالمي نحو إزالة الحواجز أمام انتقال السلع والخدمات ورؤوس الأموال والمعلومات وأنماط العيش مابين مختلف الدول.",
                termesNamesList, "العولمة"
            ),
            Triple(
                "مصطلح اقتصادي يعني مجموع القرارات والإجراءات والمشاريع التي تهدف إلى الاستغلال الأمثل للإمكانات المادية والبشرية بهدف تحقيق التطور الاقتصادي والرفاهية الاجتماعية.",
                termesNamesList, "التنمية"
            ),
            Triple(
                "وهو الفرق ما بين قيمة الصادرات والواردات لبلد ما.",
                termesNamesList, "الميزان التجاري"
            ),
            Triple(
                "هو انخفاض في القيمة الحقيقية لوحدة النقد ،كما يعرف بأنه الارتفاع في المستوى العام للأسعار والسلع والخدمات.",
                termesNamesList, "التضخم"
            ),
            Triple(
                "عبارة عن رقم إحصائي يمثل ظاهرة معينة خلال فترة زمنية محددة وهو احد أدوات الدراسات الاجتماعية الديموغرافية والاقتصادية والصحية و هو وسيلة لتقييم الواقع والمستقبل وقياس التغيرات على مر الزمن و من خلاله يمكن تشكيل انطباع أو إصدار حكم بالإيجاب أو السلب.",
                termesNamesList, "المؤشر"
            ),
            Triple(
                "مقياس تأليفي تتراوح قيمته من 0 إلى 1 يعتمد لقياس درجة تقدم الأقطار يتألف من أمل الحياة عند الولادة ونسبة التمدرس والناتج الداخلي الخام للفرد.",
                termesNamesList, "مؤشر التنمية البشرية"
            ),
            Triple(
                "جملة الدخل الذي تحققه عوامل الإنتاج(مصانع خدمات) ببلد ما إضافة إلي المداخيل المتأتية من الخارج (تحويلات العمال المهاجرين و عائدات الاستثمارات.",
                termesNamesList, "ناتج وطني خام"
            ),
            Triple(
                "هو حاصل قسمة الناتج الداخلي الخام على عدد السكان وهو مقياس نسبي ويكون إما مرتفعا أو منخفضا وهذا حسب تغير عدد السكان والناتج الداخلي الخام.",
                termesNamesList, "ناتج داخلي خام للفرد"
            ),
            Triple(
                "قيمة ما تنتجه مختلف القطاعات الاقتصادية داخل البلد الواحد خلال سنة.",
                termesNamesList, "ناتج داخلي خام"
            ),
        )
        val quiz = Quiz(
            questions, (termesNamesList.size * 8000).toLong(), hashMapOf(
                MODULE to GEOGRAPHIE,
                QUIZ_SEMESTER to SEMESTER_1,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
        return quiz
    }

    fun getGeoTermes2(): Quiz {
        val termesNamesList = listOf(
            "الهيمنة والنفوذ",
            "الاستقطاب",
            "التكتل",
            "معاهدة روما",
            "البينيلوكس",
            "معاهدة ماستريخت",
            "مجموعة التنينات الأسيوية",
            "مجموعة النمور الأسيوية",
            "الوزن الديمغرافي",
            "مجمع المدن",
            "الاتحاد الاقتصادي",
            "منطقة التبادل الحر",
            "ركود اقتصادي",
            "الدول الثماني الصناعية",
            "تعريفات جمركية",
            "نمو اقتصادي",
            "التأميم"
        )
        val questions = listOf(
            Triple(
                "عملية السيطرة والاستغلال التي تمارسها الدول القوية ذات الإمكانيات الضخمة مثل الو م أ على الدول الأضعف وعلى المؤسسات السياسية والمالية العالمية.",
                termesNamesList,
                "الهيمنة والنفوذ"
            ),
            Triple(
                "عملية جذب وإغراء للإفراد أو الاستثمارات أو الشركات نحو دولة أو منطقة أو مدينة ما بسبب الفوائد والخدمات والضمانات والحماية الممنوحة.",
                termesNamesList,
                "الاستقطاب"
            ),
            Triple(
                "توحيد لكل الإمكانيات المتوفرة في دولتين أو دول واستغلالها باستراتيجية مشتركة لرفع القدرة الإنتاجية، قد يكون تكتل سياسي أو اقتصادي أو عسكري.",
                termesNamesList,
                "التكتل"
            ),
            Triple(
                "معاهدة موقعة في 25 مارس 1957 بروما الإيطالية تنص على إنشاء السوق الأوربية المشتركة ومقرها بروكسل ببلجيكا، وقد انضمت إليها دول أوربية أخرى.",
                termesNamesList,
                "معاهدة روما"
            ),
            Triple(
                "اتحاد اقتصادي تأسس عام 1944 بين ثلاث دول بلجيكا وهولندا ولوكسمبورغ، وتحول إلى اتحاد البنلوكس الاقتصادي في عام 1960.",
                termesNamesList,
                "البينيلوكس"
            ),
            Triple(
                "الاتفاقية المؤسسة للاتحاد الأوروبي وأهم تغيير في تاريخه منذ تأسيس السوق الأوروبية المشتركة، دخلت حيز التنفيذ في 1 نوفمبر 1993.",
                termesNamesList,
                "معاهدة ماستريخت"
            ),
            Triple(
                "لقب يطلق على اقتصاد دول تايوان وسنغافورة وهونكونغ وكوريا الجنوبية لتحقيقها معدل نمو اقتصادي كبير وتصنيع سريع.",
                termesNamesList,
                "مجموعة التنينات الأسيوية"
            ),
            Triple(
                "تشمل دول تايلاندا وماليزيا وإندونيسيا والفيليبين، دخلت عهد التصنيع وحققت نمو اقتصادي كبير.",
                termesNamesList,
                "مجموعة النمور الأسيوية"
            ),
            Triple(
                "القوة البشرية التي تشكل عامل قوة لدول تمتلك تعداد سكاني كبير وتحسن استثماره وتوظيفه في عملية الإنتاج والتنمية.",
                termesNamesList,
                "الوزن الديمغرافي"
            ),
            Triple(
                "تجمع حضري يتشكل من عدة مدن ذات تعداد وكثافة ديمغرافية كبيرة تتميز بتعدد أنشطتها الاقتصادية ووفرة خطوط المواصلات بينها وتمتد على شكل شريط شبه متصل لمئات الكيلومترات.",
                termesNamesList,
                "مجمع المدن"
            ),
            Triple(
                "يمثل أقصى ما يصل إليه التكامل الاقتصادي حيث يتدرج من منطقة تجارة حرة إلى الاتحاد الجمركي ثم مرحلة السوق المشتركة وأخيرا مرحلة الانخراط في اتحاد اقتصادي.",
                termesNamesList,
                "الاتحاد الاقتصادي"
            ),
            Triple(
                "فضاء يتكون من مجموعة بلدان قامت بإلغاء القيود الجمركية فيما بينها فيتشكل اتحاد للتبادل الحر.",
                termesNamesList,
                "منطقة التبادل الحر"
            ),
            Triple(
                "مصطلح يعبر عن الهبوط في النمو الاقتصادي لمنطقة أو سوق معين، تحدث بسبب أن الإنتاج يفوق الاستهلاك الأمر الذي يؤدي إلى كساد البضاعة وانخفاض في الأسعار.",
                termesNamesList,
                "ركود اقتصادي"
            ),
            Triple(
                "الدول الأكثر تصنيعًا في العالم، تضم دول: فرنسا – اليابان – الولايات المتحدة – ايطاليا – ألمانيا – بريطانيا – كندا وروسيا.",
                termesNamesList,
                "الدول الثماني الصناعية"
            ),
            Triple(
                "جملة الشروط والموانع التي تفرضها دولة ما على دخول البضائع الأجنبية إليها والهدف من ذلك توفير إيرادات للخزينة وحماية الصناعة المحلية من المنافسة.",
                termesNamesList,
                "تعريفات جمركية"
            ),
            Triple(
                "زيادة كمية تسجلها المنتجات والخدمات في بلد وتقاس تلك الزيادة بعدد من المؤشرات أهمها الناتج الوطني.",
                termesNamesList,
                "نمو اقتصادي"
            ),
            Triple(
                "عملية اقتصادية يتم من خلالها تحويل الملكية الخاصة إلى ملكية عامة، خدمة للمصلحة العليا للبلد كما يعني التأميم أيضًا استرجاع سيادة البلاد على الثروات والموارد.",
                termesNamesList,
                "التأميم"
            )
        )
        val quiz = Quiz(
            questions, (termesNamesList.size * 8000).toLong(), hashMapOf(
                MODULE to GEOGRAPHIE,
                QUIZ_SEMESTER to SEMESTER_2,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
        return quiz
    }

    fun getGeoTermes3(): Quiz {
        val termesNamesList = listOf(
            "التنمية المستدامة",
            "الخوصصة",
            "الشراكة",
            "الاحتكارات",
            "المديونية",
            "هياكل قاعدية",
            "مثلث العطش",
            "المثلث الحيوي",
            "التبعية الاقتصادية",
            "الأمن الغذائي",
            "الثورة الخضراء",
            "معيقات التنمية"
        )
        val questions = listOf(
            Triple(
                "هي الاستغلال الأمثل للموارد الطبيعية و البشرية المتاحة بشكل متوازن اقتصادياً واجتماعياً بما يضمن تلبية حاجيات الأجيال الحالية و الأجيال المقبلة بصورة مستمرة مع الحافظ على البيئة من التلوث و التخريب .",
                termesNamesList,
                "التنمية المستدامة"
            ),
            Triple(
                "سياسة نقل ملكية المنشآت العامة أو إدارتها من القطاع العام إلى القطاع الخاص و هي عملية تهدف إلى تقليص دور الحكومة و دعم القطاع الخاص في نشاط ما أو ملكية وسائل الإنتاج .",
                termesNamesList,
                "الخوصصة"
            ),
            Triple(
                "تعني القيام بتعاون بين أطراف لها أهداف مشتركة ويؤسس هذا التعاون على اتفاقات تحدد أهداف الشراكة ومبادئها ومجالاتها وتحفظ لكل طرف مصالحه وتلبي احتياجاته ضمن مبادئ المساواة والعدالة المبنية على الندية مثال ذلك الشراكة الأورو المتوسطية.",
                termesNamesList,
                "الشراكة"
            ),
            Triple(
                "هي ممارسات مخالفة لقانون السوق يقوم بها رجال الصناعة والبنوك والشركات الكبرى قصد الانفراد بسوق سلعة أو خدمة أو هي فعل يهدف إلى إحداث اختناقات في معدلات وفرة السلع وجودتها وأسعارها بغرض إلغاء المنافسة وإجبار المنافسين على إخلاء السوق .",
                termesNamesList,
                "الاحتكارات"
            ),
            Triple(
                "ھي ظاهرة معاصرة تتمثل في تزايد حجم الديون والعجز عن تسديدھا في المدة الزمنية المتفق عليھا .",
                termesNamesList,
                "المديونية"
            ),
            Triple(
                "جملة الأسس والتجهيزات الضرورية التي بدونها لا يكون هناك سير عمل أو نشاط ما وهي تأخذ صورا مختلفة وتشمل مختلف المجالات الاقتصادية الجوية والبحرية مثل الجسور ,موانئ شبكات المياه الكهرباء والطرق والأرصفة والاتصالات السلكية واللاسلكية .",
                termesNamesList,
                "هياكل قاعدية"
            ),
            Triple(
                "وهي ظاهرة يتميز بها القسم الشمالي الشرقي من إقليم المناخ المداري في الجهة الشمالية الشرقية بالبرازيل حيث تقل الأمطار عما يجاورها .",
                termesNamesList,
                "مثلث العطش"
            ),
            Triple(
                "وهو امتداد ثلاث مدن كبرى في البرازيل على شكل مثلث وهذا في الجهة الجنوبية الشرقية ، حيث تتركز معظم الصناعات البرازيلية وهذه المدن هي : ساوباولو- ري ودي جانيرو – بيلو أوري زنتي .",
                termesNamesList,
                "المثلث الحيوي"
            ),
            Triple(
                "ارتباط أو خضوع النشاط الاقتصادي لدولة أو مجموعة من الدول باقتصاد دولة أخرى .",
                termesNamesList,
                "التبعية الاقتصادية"
            ),
            Triple(
                "هو قدرة الدولة على تأمين المواد الغذائية اللازمة لسكانها وبالقدر الكافي وانعدام الأمن الغذائي يعكس عدم التوازن بين النمو الديمغرافي والحاجات الغذائية .",
                termesNamesList,
                "الأمن الغذائي"
            ),
            Triple(
                "وهي الإجراءات المؤدية إلى تغيير جذري لقطاع الزراعة والوصول إلى زراعة عصرية ، يتم من خلالها التخلي نهائيا عن أنماط التسيير والإنتاج والتسويق المتخلف بإتباع آليات متطورة وحديثة تؤدي إلى المردود المرتفع .",
                termesNamesList,
                "الثورة الخضراء"
            ),
            Triple(
                "وهي جملة التحديات التي باتت تعيق الأداء التنموي في الدول المتخلفة ومن أبرزها غياب الحكم الرشيد ن انتشار الأمراض والأمية وكل هذا ناتج عن التأخر في مجال التعليم والصحة باعتبارهما عماد التنمية في أي بلد.",
                termesNamesList,
                "معيقات التنمية"
            )
        )
        val quiz = Quiz(
            questions, (termesNamesList.size * 8000).toLong(), hashMapOf(
                MODULE to GEOGRAPHIE,
                QUIZ_SEMESTER to SEMESTER_3,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
        return quiz
    }

    fun getGeoAllTermes(): Quiz {
        val geoTermes1 = getGeoTermes1().questions
        val geoTermes2 = getGeoTermes2().questions
        val geoTermes3 = getGeoTermes3().questions
        val allDates = geoTermes1 + geoTermes2 + geoTermes3
        return Quiz(
            allDates, (allDates.size * 8000).toLong(), hashMapOf(
                MODULE to GEOGRAPHIE,
                QUIZ_SEMESTER to SEMESTER_ALL,
                QUIZ_TYPE to TERMES_QUIZ
            )
        )
    }

    fun getAllGeoQuizzes(): List<Quiz> {
        return listOf(
            getGeoTermes1(),
            getGeoTermes2(),
            getGeoTermes3(),
            getGeoAllTermes()
        )
    }


}