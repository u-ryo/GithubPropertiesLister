package bz.mydns.walt.u_ryo.proplister

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity: AppCompatActivity() {
    private val url = "https://api.github.com/"
    private var adapter: RecyclerViewAdapter? = null
    private val listKey = "list"
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = savedInstanceState?.getParcelable(listKey)
        if (adapter != null) {
            Log.d(tag, adapter?.toString())
            recyclerView.adapter = adapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(listKey, adapter)
        super.onSaveInstanceState(outState)
    }

    fun submit(button: View) {
        if (!TextUtils.isEmpty(textField.text)) {
            Single.using(
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
        Log.d(tag, results.toString())
        adapter = RecyclerViewAdapter(results.map { r -> r.name })
        recyclerView.adapter = adapter
        (getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager)
                .hideSoftInputFromWindow(
                        currentFocus.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun processError(error: Throwable) {
        Log.e(tag, "ERROR!", error)
        showToast(R.string.network_error)
    }

    private fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}