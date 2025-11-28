package com.capstone2.data.model.session.ai


import com.google.gson.annotations.SerializedName

data class ScoreWeights(
    @SerializedName("additionalProp1")
    val additionalProp1: Double,
    @SerializedName("additionalProp2")
    val additionalProp2: Double,
    @SerializedName("additionalProp3")
    val additionalProp3: Double
)