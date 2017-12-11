package bz.mydns.walt.u_ryo.proplister

import android.widget.EditText
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by u-ryo on 17/12/07.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
internal class MainActivityTest {
    private lateinit var activity: MainActivity
    @BeforeEach
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun onCreate() {
        activity.findViewById<EditText>(R.id.textField).visibility
                shouldBe(true)
    }

}