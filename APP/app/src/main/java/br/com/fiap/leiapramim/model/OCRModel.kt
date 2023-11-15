package br.com.fiap.leiapramim.model

import com.google.gson.annotations.SerializedName

data class OCRModel(
    @SerializedName("ParsedResults")
    val parsedResults: List<ParsedResult>,

    @SerializedName("OCRExitCode")
    val ocrExitCode: Int,

    @SerializedName("IsErroredOnProcessing")
    val isErroredOnProcessing: Boolean,

    @SerializedName("ProcessingTimeInMilliseconds")
    val processingTimeInMilliseconds: String,

    @SerializedName("SearchablePDFURL")
    val searchablePDFURL: String
) {
    data class ParsedResult(
        @SerializedName("TextOverlay")
        val textOverlay: TextOverlay,

        @SerializedName("TextOrientation")
        val textOrientation: String,

        @SerializedName("FileParseExitCode")
        val fileParseExitCode: Int,

        @SerializedName("ParsedText")
        val parsedText: String,

        @SerializedName("ErrorMessage")
        val errorMessage: String,

        @SerializedName("ErrorDetails")
        val errorDetails: String
    ) {
        data class TextOverlay(
            @SerializedName("Lines")
            val lines: List<Any>,

            @SerializedName("HasOverlay")
            val hasOverlay: Boolean,

            @SerializedName("Message")
            val message: String
        )
    }
}
