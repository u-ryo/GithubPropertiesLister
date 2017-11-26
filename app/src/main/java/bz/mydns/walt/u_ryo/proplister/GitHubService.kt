package bz.mydns.walt.u_ryo.proplister

import retrofit2.http.GET
import retrofit2.http.Path
import io.reactivex.Observable

/**
 * Created by u-ryo on 17/11/26.
 */
interface GitHubService {
    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username : String) : Observable<List<Repository>>
}
data class Repository(val name: String)