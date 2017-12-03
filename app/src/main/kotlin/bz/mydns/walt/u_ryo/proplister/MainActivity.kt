package bz.mydns.walt.u_ryo.proplister

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import com.github.yamamotoj.pikkel.Pikkel
import com.github.yamamotoj.pikkel.PikkelDelegate
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity: AppCompatActivity(), Pikkel by PikkelDelegate() {
    private val url = "https://api.github.com/"
    private var adapter by state<RecyclerViewAdapter?>(null)
    private lateinit var single: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(timber.log.Timber.DebugTree())
        recyclerView.layoutManager = LinearLayoutManager(this)
        restoreInstanceState(savedInstanceState)
        adapter?.run {
            d { "adapter: $adapter" }
            recyclerView.adapter = adapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
    }

    fun submit(button: View) {
        if (!TextUtils.isEmpty(textField.text)) {
            single = Single.using(
                    { setProcessBar(true) },
                    { setRetrofit(url) },
                    { setProcessBar(false) })
                    .subscribe(this::processResults,
                            this::processError)
        } else {
            showToast(R.string.empty_input)
        }
        textField.requestFocus()
    }

    private fun setProcessBar(shouldShow: Boolean) {
        progressBar.visibility =
                if (shouldShow) View.VISIBLE else View.GONE
        button.isEnabled = !shouldShow
    }

    private fun setRetrofit(url: String): Single<List<Repository>> {
        return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create<GitHubService>(GitHubService::class.java)
                .getRepos(textField.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    private fun processResults(results: List<Repository>) {
        d { "results: $results" }
        adapter = RecyclerViewAdapter(results.map { r -> r.name })
        recyclerView.adapter = adapter
        (getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager)
                .hideSoftInputFromWindow(
                        currentFocus.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun processError(error: Throwable) {
        e { "ERROR: ${error.message}" }
        showToast(if (error is HttpException && error.code() == 404)
            R.string.no_such_user_error else R.string.network_error)
    }

    private fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}