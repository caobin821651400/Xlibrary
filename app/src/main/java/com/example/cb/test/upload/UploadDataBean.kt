package com.example.cb.test.upload

data class UploadDataBean(
    var area: String? = "",
    var comment_num: Int? = 0,
    var content: String? = "",
    var created_at: String? = "",
    var id: Int? = 0,
    var images: List<String?>? = listOf(),
    var title: String? = "",
    var uid: Int? = 0,
    var updated_at: String? = ""
)