package bz.mydns.walt.u_ryo.proplister

import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by u-ryo on 17/12/02.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class MainActivitySpec: Spek({
    given("StringBuilder") {
        val builder = StringBuilder("foo")

        on("append") {
            builder.append("bar")

            it("should return the result of appending the arg") {
                builder.toString() shouldEqual "foobar"
            }
        }
    }

    given("activity") {
        lateinit var activity: MainActivity
        on("setup") {
            it("should make no error") {
                activity = Robolectric.setupActivity(MainActivity::class.java)
                activity.findViewById<EditText>(R.id.textField)
                        .text.toString() shouldBeEqualTo ""
                activity.findViewById<ProgressBar>(R.id.progressBar)
                        .visibility shouldBe (View.GONE)
            }
        }
    }
})