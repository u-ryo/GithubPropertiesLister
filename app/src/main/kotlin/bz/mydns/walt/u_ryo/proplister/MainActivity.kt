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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun submit(view: View) {
        button.setOnClickListener {
            if (!TextUtils.isEmpty(textField.text)) {
                Single.using({
                    progressBar.visibility = View.VISIBLE
                }, {
                    Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create<GitHubService>(GitHubService::class.java)
                        .getRepos(textField.text.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                }, {
                    progressBar.visibility = View.GONE
                })
                        .subscribe({ result ->
                            Log.d("MainActivity", result.toString())
                            recyclerView.adapter =
                                    RecyclerViewAdapter(result.map { r -> r.name })
                            (getSystemService(Context.INPUT_METHOD_SERVICE)
                                    as InputMethodManager)
                                    .hideSoftInputFromWindow(
                                            currentFocus.windowToken,
                                            InputMethodManager.HIDE_NOT_ALWAYS)
                        }, { error ->
                            Log.e("MainActivity", "ERROR!", error)
                            Toast.makeText(this, getString(R.string.network_error),
                                    Toast.LENGTH_LONG).show()
                        })
            }
            textField.requestFocus()
        }
    }
}
