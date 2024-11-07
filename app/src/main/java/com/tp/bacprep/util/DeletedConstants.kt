package com.tp.bacprep.util

import com.tp.bacprep.domain.models.ModuleProgress

object DeletedConstants {

    // deleted code
    // from register activity
    /*handleRegistrationType(intent, savedInstanceState)
        // default code
        isScreenSizeSmall()
        setBackgFullScreen()
        setStatusAndNavigBarMargin()

        // ---observing results code
        sharedRegisterViewModel.registerResult.observe(this@RegisterActivity) { result ->
            if (result.second) {
                hideProgressDialog()
                showErrorSnackBar("Votre compte a été crée", false)
                updateUI(RegisterFragment())
            } else {
                hideProgressDialog()
                showErrorSnackBar("${result.first}", true)
            }
        }
        sharedRegisterViewModel.isCurrentUserVerified.observe(this@RegisterActivity) { result ->
            if (result.second) {
                hideProgressDialog()
                showErrorSnackBar("Votre email a été vérifié", false)
                binding?.btnRegister?.setBackgroundColor(
                    ContextCompat.getColor(
                        this@RegisterActivity,
                        R.color.yellow
                    )
                )
                binding?.btnRegister?.text = "Confirmer"
            } else {
                hideProgressDialog()
                binding?.btnRegister?.text = "Vérifier"
                showErrorSnackBar("Votre email n'a pas été vérifié", false)
            }
        }
        sharedRegisterViewModel.verifEmailSentResult.observe(this@RegisterActivity) { result ->
            if (result.second) {
                binding?.apply {
                    btnRegister.setBackgroundColor(
                        ContextCompat.getColor(
                            this@RegisterActivity,
                            R.color.yellow
                        )
                    )
                    btnRegister.isClickable = true
                }
            } else {
                binding?.apply {
                    btnRegister.setBackgroundColor(
                        ContextCompat.getColor(
                            this@RegisterActivity,
                            android.R.color.darker_gray
                        )
                    )
                    btnRegister.isClickable = false
                }
                hideProgressDialog()
                showErrorSnackBar(buildString {
                    append(result.first.toString())
                    append("Clicker sur le button en haut pour envoyer un email de vérification")
                }, true)
            }
        }
        // ---------------observing results end
        binding?.apply {
            btnRegister.setOnClickListener {
                val currentFrag =
                    supportFragmentManager.findFragmentById(R.id.fragment_container)
                val email = sharedRegisterViewModel.email.value
                val name = sharedRegisterViewModel.name.value
                val lastName = sharedRegisterViewModel.lastName.value
                val phoneNum = sharedRegisterViewModel.phoneNum.value
                val psw = sharedRegisterViewModel.psw.value
                val role = sharedRegisterViewModel.userRole.value
                if (currentFrag is RegisterFragment){
                    when {
                        name == null -> {
                            showErrorSnackBar("entrez un prenom valid" + name, null)

                        }
                        email == null -> {
                            showErrorSnackBar("entrez un email, valid" + email, null)
                        }
                        lastName == null -> {
                            showErrorSnackBar("entrez un nom valid" + lastName, null)
                        }
                        phoneNum == null -> {
                            showErrorSnackBar("entrez un numéro valid", null)
                        }
                        psw == null -> {
                            showErrorSnackBar("entrez un mot de passe valid", null)
                        }
                        role == null -> {
                            showErrorSnackBar("choissisez un role", null)
                        }
                        else -> {
                            showProgressDialog()
                            val user = User(
                                "",
                                name = name,
                                lastName = lastName,
                                email = email,
                                phoneNum = phoneNum,
                                role = role
                            )
                            sharedRegisterViewModel.registerUser(user, psw)
                        }
                    }
                }else if (currentFrag is EmailVerificationFragment){

                }
            }
        }

         */
    /*binding?.apply {
        btnRegister.setOnClickListener {
            val currentFrag =
                supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFrag is RegisterFragment) {
                val email = sharedRegisterViewModel.email.value
                val name = sharedRegisterViewModel.name.value
                val lastName = sharedRegisterViewModel.lastName.value
                val phoneNum = sharedRegisterViewModel.phoneNum.value
                val psw = sharedRegisterViewModel.psw.value
                val role = sharedRegisterViewModel.userRole.value
                if (role!=null){
                    if (role.first == Constants.USER){
                        when {
                            name == null -> {
                                showErrorSnackBar("entrez un prenom valid" + name, null)

                            }

                            email == null -> {
                                showErrorSnackBar("entrez un email, valid" + email, null)
                            }

                            lastName == null -> {
                                showErrorSnackBar("entrez un nom valid" + lastName, null)
                            }

                            phoneNum == null -> {
                                showErrorSnackBar("entrez un numéro valid", null)
                            }

                            psw == null -> {
                                showErrorSnackBar("entrez un mot de passe valid", null)
                            }

                            role == null -> {
                                showErrorSnackBar("choissisez un role", null)
                            }

                            else -> {
                                showProgressDialog()
                                val user = User(
                                    "",
                                    name = name,
                                    lastName = lastName,
                                    email = email,
                                    phoneNum = phoneNum,
                                    role = role
                                )
                                sharedRegisterViewModel.registerUser(user, psw)
                            }
                        }
                    }
                }
                if (registrationType == Constants.REG_CREATOR) {
                    when {
                        name == null -> {
                            showErrorSnackBar("entrez un prenom valid" + name, null)

                        }

                        email == null -> {
                            showErrorSnackBar("entrez un email, valid" + email, null)
                        }

                        lastName == null -> {
                            showErrorSnackBar("entrez un nom valid" + lastName, null)
                        }

                        phoneNum == null -> {
                            showErrorSnackBar("entrez un numéro valid", null)
                        }

                        psw == null -> {
                            showErrorSnackBar("entrez un mot de passe valid", null)
                        }

                        role == null -> {
                            showErrorSnackBar("choissisez un role", null)
                        }

                        else -> {
                            showProgressDialog()
                            val user = User(
                                "",
                                name = name,
                                lastName = lastName,
                                email = email,
                                phoneNum = phoneNum,
                                role = role
                            )
                            sharedRegisterViewModel.registerUser(user, psw)
                        }
                    }
                } else {
                    when {
                        name == null -> {
                            showErrorSnackBar("entrez un prenom valid" + name, null)

                        }

                        email == null -> {
                            showErrorSnackBar("entrez un email, valid" + email, null)
                        }

                        lastName == null -> {
                            showErrorSnackBar("entrez un nom valid" + lastName, null)
                        }

                        phoneNum == null -> {
                            showErrorSnackBar("entrez un numéro valid", null)
                        }

                        psw == null -> {
                            showErrorSnackBar("entrez un mot de passe valid", null)
                        }

                        else -> {
                            showProgressDialog()
                            val user = User(
                                "",
                                name = name,
                                lastName = lastName,
                                email = email,
                                phoneNum = phoneNum,
                                role = Pair(Constants.ADMIN_ROLE, true)
                            )
                            sharedRegisterViewModel.registerUser(user, psw)
                        }
                    }
                }
            } else {
                if (currentFrag is EmailVerificationFragment) {
                    try {
                        if (sharedRegisterViewModel.isCurrentUserVerified.value!!.second) {
                           startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            sharedRegisterViewModel.isCurrentUserVerified()
                        }
                    } catch (e: Exception) {
                        sharedRegisterViewModel.isCurrentUserVerified()
                    }
                } else {

                }
            }
        }
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }*/
    /* override fun onDestroy() {
     super.onDestroy()
 // Unregister the BroadcastReceiver
     unregisterReceiver(networkChangeReceiver)

 }

 override fun onNetworkConnected() {
     // Internet connection is available
     // Implement your logic when the connection is restored
     if (mAgreeDialog != null){
         hideAgreeDialog()
     }
 }

 override fun onNetworkDisconnected() {
     // No internet connection
     // Display your internet dialog or handle the scenario accordingly

     showAgreeDialog(this@UserMainActivity, "Problème de connection",
         "veuillez vérifiez que vous etes connecter et réessayez.", ContextCompat.
         getDrawable(this@UserMainActivity, R.drawable.icon_no_internet_connection)
     ) { finishAffinity() }

 }
 */

    /*
    private fun handleRegistrationType(intent: Intent, savedInstanceState: Bundle?) {
        if (intent.extras != null) {
            val intent = intent.getStringExtra(Constants.REG_CREATOR)
            if (intent == Constants.REG_CREATOR) {
                registrationType = Constants.REG_CREATOR
                val registerFrag = RegisterFragment()
                registerFrag.arguments = Bundle().apply {
                    putString(Constants.REG_CREATOR, Constants.REG_CREATOR)
                }
                if (savedInstanceState == null) {
                    try {
                        supportFragmentManager.beginTransaction()
                            .replace(binding?.fragmentContainer!!.id, registerFrag)
                            .commit()
                    } catch (e: NullPointerException) {
                        showErrorSnackBar("frag transaction run time error --", true)
                        Log.e(
                            "frag transaction run time error",
                            "error while fragment transaction - line 24"
                        )
                    } catch (e: Exception) {
                        showErrorSnackBar("frag transaction run time error", true)
                        Log.e(
                            "frag transaction run time error",
                            "error while fragment transaction - line 26"
                        )
                    }
                }
            } else {
                if (savedInstanceState == null) {
                    try {
                        supportFragmentManager.beginTransaction()
                            .replace(binding?.fragmentContainer!!.id, RegisterFragment())
                            .commit()
                    } catch (e: NullPointerException) {
                        showErrorSnackBar("frag transaction run time error --", true)
                        Log.e(
                            "frag transaction run time error",
                            "error while fragment transaction - line 24"
                        )
                    } catch (e: Exception) {
                        showErrorSnackBar("frag transaction run time error", true)
                        Log.e(
                            "frag transaction run time error",
                            "error while fragment transaction - line 26"
                        )
                    }

                }
            }
        } else {
            registrationType = ""
            if (savedInstanceState == null) {
                try {
                    supportFragmentManager.beginTransaction()
                        .replace(binding?.fragmentContainer!!.id, RegisterFragment())
                        .commit()
                } catch (e: NullPointerException) {
                    showErrorSnackBar("frag transaction run time error --", true)
                    Log.e(
                        "frag transaction run time error",
                        "error while fragment transaction - line 24"
                    )
                } catch (e: Exception) {
                    showErrorSnackBar("frag transaction run time error", true)
                    Log.e(
                        "frag transaction run time error",
                        "error while fragment transaction - line 26"
                    )
                }

            }
        }
    }
    private fun updateUI(fragment: Fragment) {
        binding?.apply {
            if (fragment is RegisterFragment) {
                switchFragment(fragment)
                btnRegister?.setBackgroundColor(
                    ContextCompat.getColor(
                        this@RegisterActivity,
                        android.R.color.darker_gray
                    )
                )
                btnRegister?.isClickable = false
                btnRegister?.text = resources.getString(R.string.verifier)
            }
            if (fragment is EmailVerificationFragment) {
                binding?.btnRegister?.text = getString(R.string.finir)
                switchFragment(fragment)

            }
        }
    }
    // finding the height of status bar and bottom navigation bar and setting the padding of the view
    private fun setStatusAndNavigBarMargin() {
        var statusBarHeight = 0
        var navigBarHeight = 0
        if (!isScreenSizeSmall()) {
            try {
                val resourceIdN: Int =
                    resources.getIdentifier("navigation_bar_height", "dimen", "android")
                navigBarHeight = if (resourceIdN > 0) {
                    resources.getDimensionPixelSize(resourceIdN)
                } else {
                    resources.getDimensionPixelSize(R.dimen.default_navigation_bar_height)
                }
                val resourceId =
                    resources.getIdentifier("status_bar_height", "dimen", "android")
                statusBarHeight = if (resourceId > 0) {
                    resources.getDimensionPixelSize(resourceId)
                } else {
                    resources.getDimensionPixelSize(R.dimen.default_status_bar_height)
                }
                Toast.makeText(this@RegisterActivity, navigBarHeight, Toast.LENGTH_SHORT).show()
                binding?.root?.setPadding(0, statusBarHeight + 10, 0, 0)
            } catch (e: NullPointerException) {
                navigBarHeight =
                    resources.getDimensionPixelSize(R.dimen.default_navigation_bar_height)
                statusBarHeight =
                    resources.getDimensionPixelSize(R.dimen.default_status_bar_height)
                binding?.root?.setPadding(0, statusBarHeight + 20, 0, navigBarHeight)
                Toast.makeText(this@RegisterActivity, navigBarHeight, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                navigBarHeight =
                    resources.getDimensionPixelSize(R.dimen.default_navigation_bar_height)
                statusBarHeight =
                    resources.getDimensionPixelSize(R.dimen.default_status_bar_height)
                binding?.root?.setPadding(0, statusBarHeight + 20, 0, navigBarHeight)
                Toast.makeText(this@RegisterActivity, e.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            navigBarHeight =
                resources.getDimensionPixelSize(R.dimen.default_navigation_bar_height)
            statusBarHeight = resources.getDimensionPixelSize(R.dimen.default_status_bar_height)
            binding?.root?.setPadding(0, statusBarHeight, 0, navigBarHeight - 30)
        }

    }
    */
    ////////////////////////////////



    //Science programm for every filiere
    fun getScienceSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
    fun getMathSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الدوال العددية (الشتقاقية والاستمرارية)",
            "الدالتان الأسية واللوغاريتمية",
            "الدوال العددية (النهايات)",
            "التزايد المقارن ودراسة الدوال",
            "المتتاليات العددية",
            "احتمالات وإحصاء",
            "الأعداد مركبة",
            "التحويلات النقطية",
            "الدوال الأصلية",
            "الحساب التكاملي",
            "الهندسة في الفضاء"
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
                "المتتاليتان المتجاورتان: تعريف ومفهوم متتاليتين متجاورتين",
            ),
            groupList[5] to listOf(
                "االحتماالت المتساوية على مجموعة منتهية: إيجاد قانون احتمال لمتغيّر عشوائي. ",
                "حل مسائل في االحتماالت توظف المتغيرات العشوائية، قانون احتمالها، التباين، االنحراف المعياري واألمل الرياضياتي ",
                "المبدأ األساسي للعد: العّد باستخدام المبدأ األساسي للعّد )المجموع وال ُجداء(",
                "ستخراج بعض قوانين التحليل التوفيقي )التوفيقات(",
                "دستور ثنائي الحّد.",
                "االحتماالت الشرطية: التعّرف على استقالل أو ارتباط حادثتين.",
                "االحتماالت الشرطية: ـ التعّرف على استقالل أو ارتباط حادثتين.",
                "توظيف دستور االحتماالت الكلية لحل مسائل في االحتماالت تتعلق بسحب أكثر من وعاء",
            ),
            groupList[6] to listOf(
                "المجموعة C : إجراء العمليات الحسابية على األعداد المرّكبة",
                "استعمال خواص مرافق عدد مر ّكب، حساب طويلة عدد مرّكب.",
                "استعمال خواص مرافق عدد مر ّكب، حساب طويلة عدد مرّكب.",
                "حل في C، معادلة من الدرجة الثانية ذات معامالت حقيقية.",
                "حل في C، معادالت يؤول حلها إلى حل معادلة من الدرجة الثانية ذات معامالت حقيقية.",
                "الشكل المثلثي لعدد مركب غير معدوم: حساب عمدة لعدد مركب غير معدوم",
                "الشكل المثلثي لعدد مركب غير معدوم: حساب عمدة لعدد مركب غير معدوم",
                "ترميز أولير:exp(i a)",
                "التعبير عن خواص ألشكال هندسية باستعمال األعداد المرّكبة.",
                " توظيف خواص الطويلة والعمدة لحل مسائل في األعداد المر ّكبة وفي الهندسة",
                "توظيف دستور موافر لحل مسائل في األعداد المر ّكبة وفي الهندسة.",
            ),
            groupList[7] to listOf(
                "تعيين الكتابة المر ّكبة للتحويالت النقطية )االنسحاب، التحاكي، الدوران(. ـ التعّرف عن تحويل انطالقا من الكتابة المرّكبة",
                "من الكتابة المر ّكبة",
                "من الكتابة المر ّكبة",
                "التشابهات المستوية المباشرة: التعّرف على تشابه مباشر.",
                "التعبير عن تشابه مباشر باألعداد المر ّكبة",
                "تركيب تشابهين مباشرين",
                "تعيين التحليل القانوني لتشابه مباشر بواسطة األعداد المر ّكبة وتوظيفه لحل مسائل هندسية",
            ),
            groupList[8] to listOf(
                " تعرف الدالة األصلية لدالة على مجال والخواص.",
                "تعيين دالة أصلية لدالة مستمرة على مجال. تعيين دوال أصلية لدوال مألوفة",
                "تعيين الدالة أصلية التي تأخذ قيمة y0 من أجل قيمة x0 للمتغيّر",
                "حل معادالت تفاضلية من الشكل:y'' = f(x),y' = f(x)",
            ),
            groupList[9] to listOf(
                "المقاربة والتعريف",
                "المقاربة والتعريف",
                "مفهوم القيمة المتوسطة لدالة على مجال وحصرها",
                "مفهوم القيمة المتوسطة لدالة على مجال وحصرها",
                "توظيف الحساب التكاملي لحساب دوال أصلية",
                "حساب حجم لمجسمات بسيطة",
                "توظيف الحساب التكاملي لحل مشكالت بسيطة",
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
    fun getPhysicsSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
        val groupList = listOf(
            "الوحدة الأولى: المتابعة الزمنية لتحول كميائي في محلول مائي",
            "الوحدة الثانية: دراسة تحولات نووية",
            "الوحدة الثالثة: دراسة ظواهر كهربائية",
            "الوحدة الرابعة: تطور جملة كميائية نحو حالة التوازن",
            "الوحدة الخامسة: تطور جملة ميكانيكية",
            "الوحدة السادسة: مراقبة تطور جملة كميائية"
        )

// Define your itemsList (elements) for the ExpandableListView adapter
        val itemsList = hashMapOf(
            // Elements for the first unit
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

            // Elements for the second unit
            groupList[1] to listOf(
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

            // Elements for the third unit
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

            // Elements for the fourth unit
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

            // Elements for the fifth unit
            groupList[4] to listOf(
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

            // Elements for the sixth unit
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
    fun getHisSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{

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
    fun getGeoSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
    fun getFrenchSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
    fun getEnlgishSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
    fun getArabSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
    fun getIslamicSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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
        var itemsList = hashMapOf<String, List<String>>()
        for (i in groupList.indices){
            itemsList.put(groupList[i], listOf(groupList[i]))
        }
        return Pair(groupList, itemsList)
    }
    fun getPhiloSciProgram() : Pair<List<String>, HashMap<String, List<String>>>{
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

    // filiere modules
    val SCI_MODULES: List<String> = listOf("Science", "Mathématique", "Physique", "Histoire", "Géographie", "Anglais", "Français", "Arabe", "S.Islamique","Philo")
    val MATH_MODULES: List<String> = listOf("Science", "Mathématique", "Physique", "Histoire", "Géographie", "Anglais", "Français", "Arabe", "S.Islamique","Philo")
    val MATH_TECH_MODULES: List<String> = listOf("Génie des procédés", "Mathématique", "Génie électrique","Génie civil", "Génie mécanique", "Histoire", "Géographie", "Anglais", "Français", "Arabe", "S.Islamique","Philo")
    val LANG_MODULES: List<String> = listOf("Espagnol", "Mathématique", "Allemand", "Histoire", "Géographie", "Anglais", "Français", "Arabe", "S.Islamique","Philo")
    val GESTION_MODULES: List<String> = listOf("Economie et gestion", "Mathématique", "Loi", "Gestion comptable et financière", "Histoire", "Géographie", "Anglais", "Français", "Arabe", "S.Islamique","Philo")
    val LETTRES_MODULES: List<String> = listOf("Philo", "Mathématique", "Histoire", "Géographie", "Anglais", "Français", "Arabe", "S.Islamique")


    /*val MATH_TECH_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Génie des procédés", listOf()),
        ModuleProgress("Mathématique", listOf()),
        ModuleProgress("Génie électrique", listOf()),
        ModuleProgress("Génie civil", listOf()),
        ModuleProgress("Génie mécanique", listOf()),
        ModuleProgress("Histoire", listOf()),
        ModuleProgress("Géographie", listOf()),
        ModuleProgress("Anglais", listOf()),
        ModuleProgress("Français", listOf()),
        ModuleProgress("Arabe", listOf()),
        ModuleProgress("S.Islamique", listOf()),
        ModuleProgress("Philo", listOf())
    )*/

    /* val LANG_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Espagnol", listOf()),
        ModuleProgress("Mathématique", listOf()),
        ModuleProgress("Allemand", listOf()),
        ModuleProgress("Histoire", listOf()),
        ModuleProgress("Géographie", listOf()),
        ModuleProgress("Anglais", listOf()),
        ModuleProgress("Français", listOf()),
        ModuleProgress("Arabe", listOf()),
        ModuleProgress("S.Islamique", listOf()),
        ModuleProgress("Philo", listOf())
    ) */


    private val MATH_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Science", Constants.MATH_BRANCH, listOf()),
        ModuleProgress("Mathématique", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Physique", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Histoire", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Géographie", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Anglais", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Français", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Arabe", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("S.Islamique", Constants.MATH_BRANCH,listOf()),
        ModuleProgress("Philo", Constants.MATH_BRANCH,listOf())
    )


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

    /*val LETTRES_MODULES_PROGRESS: List<ModuleProgress> = listOf(
        ModuleProgress("Philo", listOf()),
        ModuleProgress("Mathématique", listOf()),
        ModuleProgress("Histoire", listOf()),
        ModuleProgress("Géographie", listOf()),
        ModuleProgress("Anglais", listOf()),
        ModuleProgress("Français", listOf()),
        ModuleProgress("Arabe", listOf()),
        ModuleProgress("S.Islamique", listOf())
    )*/

    //  Permissions request codes
    const val REQUEST_CODE_STORAGE_PERMISSION = 1
}