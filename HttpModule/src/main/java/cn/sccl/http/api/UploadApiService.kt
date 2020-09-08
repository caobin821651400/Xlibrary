package cn.sccl.http.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/1 19:42
 * @Desc :
 * ====================================================
 */
interface UploadApiService {


    @Multipart
    @POST
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImRhZjMyNTM2ZGE4MDJkYzhiODIxYmIwMzljMmQ2MTY4YzE0OGU4YWE3Y2FkMDVhZGE3OWFjYTJmYmYwMTc1N2RiMmZlMDAzY2M4NzFiYTdjIn0.eyJhdWQiOiIxIiwianRpIjoiZGFmMzI1MzZkYTgwMmRjOGI4MjFiYjAzOWMyZDYxNjhjMTQ4ZThhYTdjYWQwNWFkYTc5YWNhMmZiZjAxNzU3ZGIyZmUwMDNjYzg3MWJhN2MiLCJpYXQiOjE1OTkwMzYwMjUsIm5iZiI6MTU5OTAzNjAyNSwiZXhwIjoxNjMwNTcyMDI1LCJzdWIiOiI0NDciLCJzY29wZXMiOltdfQ.Qwdp6ra7KBofNMGjGWwl4Fd7-eiJoortgkU4rkPFj9s4W5OvctkYtoEGNINL5HtVaErl84pmSROBIoYCsikJuaC200TBv48AJPzjypGabosu_pKrRycO_D2B2JOP7MTu54bUFHLTkVbai8dbc2DCGZHeI6evMKf7jUmtk27BgRIx8-WRpri3V5ySNYtenqGu7RZgirYzUCHE62lmMQGhhE83sX2bHUmUuKdNGwx2YGmGJV0fXXaXH_yvIYm13twMcrogaFM7G3WV9wa8-QAgHJYBYKVuDVrVyRM1kDMFvufWlXmGi0u8i1qHK7BWF9GSRRFsvEMp4uaxnQ4K4iPwIswWgWiyhwyp6uuG9kyqAqbJhsjeEdtBaAgh2za-nse321dIMjjVgQVx5RFLfA95OvrNwBtnb7SUtm9fzsv2eleLNRbqf4GSBkVVSy7U5nuwIujk2fPP0ROfDyzeEUtqxfobwxlijMmTITQa0mTqc_V8ygAUItnBRGNQvFozQLTNosw0F2Xy3XCJ-MVZ5E_O_yGyOEe4YzDidSBsgFNAneZtuJ17w6pta0hFVBGaA-xOGc093cD2MGfFor1eWcNF7OsvFj4XpCyO0AMD-jkLVgUKRqI5bST6l12LzC-vQ2trC-PLaPV0WjVMjrG3obCtrE3H9ZQhE2vTXF55txkZ7Jc")
    suspend fun upFileList(
            @Url url: String,
//            @HeaderMap headers: HashMap<String, String>?,
//            @QueryMap maps: Map<String, @JvmSuppressWildcards Any>,
//            @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>
            @Part files:List<MultipartBody.Part>
    ): Response<ResponseBody>

}