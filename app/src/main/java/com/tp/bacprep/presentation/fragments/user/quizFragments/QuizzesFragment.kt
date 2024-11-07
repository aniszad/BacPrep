package com.tp.bacprep.presentation.fragments.user.quizFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.android.material.tabs.TabLayout
import com.tp.bacprep.data.roomDb.RoomDb
import com.tp.bacprep.presentation.adapters.recyclerviewsAdapters.QuizzesRvAdapter
import com.tp.bacprep.databinding.FragmentQuizzesBinding
import com.tp.bacprep.domain.models.Quiz
import com.tp.bacprep.presentation.activities.shared.QuizActivity
import com.tp.bacprep.presentation.fragments.BaseFragment
import com.tp.bacprep.presentation.viewmodels.BaseViewModel
import com.tp.bacprep.util.BounceEdgeEffectFactory
import com.tp.bacprep.util.MultiLangStrings

class QuizzesFragment : BaseFragment(), QuizzesRvAdapter.OnStartQuizClickedListener {
    private var binding: FragmentQuizzesBinding? = null
    private lateinit var db : RoomDb
    private val sharedViewModel : BaseViewModel by activityViewModels()
    private lateinit var quizzesAdapter : QuizzesRvAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizzesBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setting up quizzes recyclerView
        setUpQuizzesRv()
        // adjusting margin for tab items
        setTabItemsMargin()
        binding?.tabLayoutQuizzes?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setUpQuizzesRv()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setUpQuizzesRv() {
        val quizzesList = when(binding?.tabLayoutQuizzes?.getTabAt(binding?.tabLayoutQuizzes?.selectedTabPosition ?: 0)?.text.toString()){
            MultiLangStrings(requireContext()).HISTOIRE ->{
                MultiLangStrings(requireContext()).getAllHistoryQuizzes()
            }
            MultiLangStrings(requireContext()).GEOGRAPHIE ->{
                MultiLangStrings(requireContext()).getAllGeoQuizzes()
            }
            else -> {
                listOf()
            }
        }
        binding?.rvQuizzes?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        quizzesAdapter = QuizzesRvAdapter(requireContext(),this@QuizzesFragment, quizzesList, sharedViewModel, db)
        quizzesAdapter.setOnStartQuizClickedListener(this@QuizzesFragment)
        binding?.rvQuizzes?.adapter = quizzesAdapter
        binding?.rvQuizzes?.edgeEffectFactory = BounceEdgeEffectFactory()

    }
    private fun setTabItemsMargin() {
        binding?.apply {
            val mTabLayout = tabLayoutQuizzes
            for (i in 0 until mTabLayout.tabCount) {
                val tab = (mTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
                val p = tab.layoutParams as MarginLayoutParams
                p.setMargins(10, 10, 10, 10)
                tab.requestLayout()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = Room.databaseBuilder(
            requireContext().applicationContext,
            RoomDb::class.java, // Replace with your actual Room database class
            "local_room_database" // Replace with your desired database name
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onStartQuizClicked(quiz: Quiz) {
        val intent = Intent(requireActivity(), QuizActivity::class.java)
        intent.putExtra("Quiz", quiz)
        startActivity(intent)
    }


}
