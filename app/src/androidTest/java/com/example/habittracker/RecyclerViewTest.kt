package com.example.habittracker

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habit_presentation.R
import com.example.habit_presentation.presentation.ui.TypeHabitsListFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.chipgroup.KChipGroup
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.progress.KProgressBar
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewTest : TestCase() {
    object HabitsScreen : KScreen<HabitsScreen>() {
        override val layoutId: Int = R.layout.fragment_type_habits_list
        override val viewClass: Class<*> = TypeHabitsListFragment::class.java


        val rvHabits = KRecyclerView(
            builder = { withId(R.id.rv_habits) },
            itemTypeBuilder = { itemType {
                HabitItemScreen(it)
            } })

        class HabitItemScreen(matcher: Matcher<View>) : KRecyclerItem<HabitItemScreen>(matcher) {
            val title = KTextView(matcher){ withId(R.id.tv_title_habit) }
            val desc = KTextView(matcher){ withId(R.id.tv_desc_habit) }
            val line = KView(matcher){ withId(R.id.line) }
            val chipGroup = KChipGroup(matcher){ withId(R.id.chipGroup) }
            val progress = KProgressBar(matcher){ withId(R.id.linear_progress_bar) }
            val btDone = KButton(matcher){ withId(R.id.bt_habit_gone) }
        }
    }

    @get:Rule
    val scenario = ActivityScenarioRule(RootActivity::class.java)

    @Test
    fun test() = run {
        HabitsScreen {
            rvHabits.isVisible()
            Thread.sleep(2000)

            //Check elements visibility
            rvHabits {
                //Check elements visible
                children<HabitsScreen.HabitItemScreen> {
                    title.isVisible()
                    desc.isVisible()
                    line.isVisible()
                    chipGroup.isVisible()
                    progress.isVisible()
                    btDone.isVisible()
                }
            }
            //Check elements click
            rvHabits{
                firstChild<HabitsScreen.HabitItemScreen> {
                    this.isClickable()
                    this.isFocusable()
                }
                children<HabitsScreen.HabitItemScreen>{
                    btDone.isClickable()
                }
            }
            //Check progress after clicking on the button
            rvHabits{
                firstChild<HabitsScreen.HabitItemScreen> {
                    progress.hasProgress(0)
                    btDone.click()
                    Thread.sleep(1000)
                    progress.hasProgress(1)
                }
            }
        }
    }
}