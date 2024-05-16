package com.example.habittracker.presentation.ui

import RoundedTransformation
import android.graphics.Canvas
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
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.presentation.BaseFragment
import com.example.habittracker.presentation.adapter.TabAdapter
import com.example.habittracker.presentation.app.BaseApplication
import com.example.habittracker.presentation.model.TabHabitType
import com.example.habittracker.presentation.viewmodel.HabitsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class HabitsFragment : BaseFragment<FragmentHabitsBinding>(){
    private val viewModel : HabitsViewModel by activityViewModels {
        (requireActivity().application as BaseApplication).habitsViewModelFactory
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
        initBottomSheet()
        navigateNavigationView()
        setUpTabLayout()
        setOnClickListenerFabAddHabit()
        setAvatar()
    }

    private fun setAvatar() {
        binding.btOpenNavigation.setOnClickListener {
            binding.amogus.openDrawer(Gravity.LEFT)
        }
        val headerLayout = binding.navigationView.getHeaderView(0)

        val avatarImageView = headerLayout.findViewById<ImageView>(R.id.avatar_image)

        val imageViewDimensionSize = 300

        avatarImageView.maxHeight = imageViewDimensionSize
        avatarImageView.maxWidth = imageViewDimensionSize
        avatarImageView.minimumHeight = imageViewDimensionSize
        avatarImageView.minimumWidth = imageViewDimensionSize

        Picasso.with(context)
            .load("https://placehold.co/600x400.png")
            .resize(imageViewDimensionSize, imageViewDimensionSize)
            .error(R.drawable.ic_clear)
            .placeholder(R.drawable.loading)
            .transform(RoundedTransformation(150, 4))
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
        viewModel.loadHabitRemoteList() //TODO
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