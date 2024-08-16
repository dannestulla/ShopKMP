package presentation.model

import data.model.Categories
import kotlin.jvm.JvmInline


@JvmInline
value class CategoriesState(
    val categories : List<Categories>? = null
)