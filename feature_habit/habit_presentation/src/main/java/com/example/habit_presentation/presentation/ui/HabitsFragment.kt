package com.example.habit_presentation.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.core.utils.AVATAR_URL
import com.example.core.utils.ConnectivityObserver
import com.example.habit_presentation.R
import com.example.habit_presentation.databinding.FragmentHabitsBinding
import com.example.habit_presentation.di.ArticlesComponentViewModel
import com.example.habit_presentation.presentation.BaseFragment
import com.example.habit_presentation.presentation.adapter.TabAdapter
import com.example.habit_presentation.presentation.model.TabHabitType
import com.example.habit_presentation.presentation.viewmodel.HabitsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import dagger.Lazy
import javax.inject.Inject


class HabitsFragment : BaseFragment<FragmentHabitsBinding>(){

    @Inject
    internal lateinit var articleViewModelFactory: Lazy<HabitsViewModel.Factory>

    private val viewModel: HabitsViewModel by activityViewModels {
        articleViewModelFactory.get()
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(requireActivity())
            .get<ArticlesComponentViewModel>()
            .newDetailsComponent
            .injectHabits(this)
        super.onAttach(context)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHabitsBinding {
        return FragmentHabitsBinding.inflate(inflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel){
            networkStatus.observe(viewLifecycleOwner) { status ->
                when(status){
                    ConnectivityObserver.Status.AVAILABLE ->{loadHabitRemoteList(status)}
                    ConnectivityObserver.Status.UNAVAILABLE ->{loadHabitRemoteList(status)}
                    ConnectivityObserver.Status.LOST -> { loadHabitRemoteList(status)}
                    ConnectivityObserver.Status.LOSING ->{loadHabitRemoteList(status)}
                }
            }
        }
        initBottomSheet()
        navigateNavigationView()
        setUpTabLayout()
        setOnClickListenerFabAddHabit()
        setOnClickListenerBtDrawer()
        setAvatar()
    }

    @SuppressLint("RtlHardcoded")
    private fun setOnClickListenerBtDrawer() {
        binding.btOpenNavigation.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }
    }

    private fun setAvatar() {
        val headerLayout = binding.navigationView.getHeaderView(0)
        val avatarImageView = headerLayout.findViewById<ImageView>(R.id.avatar_image)

        Glide.with(requireContext())
            .load(AVATAR_URL)
            .placeholder(R.drawable.ic_avatar_loading)
            .error(R.drawable.ic_avatar_error)
            .centerCrop()
            .transform(CircleCrop())
            .override(250, 250)
            .into(avatarImageView)
    }

    private fun initBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL
        bottomSheetBehavior.isHideable = false
        binding.bottomSheet.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setOnClickListenerFabAddHabit() {
        binding.fabAddHabits.setOnClickListener { openAddHabit() }
    }

    private fun navigateNavigationView() = with(binding){
        view?.let { navigationView.setupWithNavController(it.findNavController()) }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.inbox_item -> {
                    view?.findNavController()?.navigate(R.id.habitsFragment)
                }
                R.id.outbox_item -> {
                    view?.findNavController()?.popBackStack()
                    view?.findNavController()?.navigate(R.id.aboutAppFragment)
                }
            }
            true
        }
    }

    private fun setUpTabLayout() {
        val tabAdapter = TabAdapter(requireActivity())
        newInstanceTabType(TabHabitType.USEFUL.type).let {
            tabAdapter.addFragment(it)
        }
        newInstanceTabType(TabHabitType.HARMFUL.type).let {
            tabAdapter.addFragment(it)
        }
        binding.viewPager.adapter = tabAdapter
        initTabLayoutMediator()
    }

    private fun initTabLayoutMediator() {
        TabLayoutMediator(binding.tabLayoutHabit, binding.viewPager) { tab, position ->
            when(position){
                0 -> {
                    tab.setText(R.string.text_tab1)
                    tab.setIcon(R.drawable.ic_self)
                }
                1 -> {
                    tab.setText(R.string.text_tab2)
                    tab.setIcon(R.drawable.ic_smoke)
                }
            }
        }.attach()
    }

    private fun openAddHabit() {
        val bundle = Bundle()
        bundle.putString(SCREEN_MODE, MODE_ADD)
        //view?.findNavController()?.saveState()
        view?.findNavController()?.navigate(
            R.id.action_habitsFragment_to_habitProcessingFragment, bundle)
    }

    companion object {
        private const val MODE_ADD = "mode_add"
        private const val SCREEN_MODE = "screen_mode"
        private const val TYPE_HABITS = "type_habits"

         private fun newInstanceTabType(tabType : String) : TypeHabitsListFragment{
             val fragment = TypeHabitsListFragment()
            fragment.arguments = bundleOf(TYPE_HABITS to tabType)
            return fragment
        }
    }
}