package com.test.pexels.presentation.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import com.test.pexels.data.model.BasePaginationPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class Pagination<T> {
    private val data = arrayListOf<T>()
    private var lastPage : BasePaginationPage<T>? = null
    private var performNewPage = true
    private var loadPage : suspend (page : Int) -> BasePaginationPage<T> = ::initLoadPage
    private val mSize = mutableIntStateOf(0)

    val size by mSize

    init { processPage() }

    abstract suspend fun initLoadPage(page : Int) : BasePaginationPage<T>

    fun reload(newLoadFunction : suspend (page : Int) -> BasePaginationPage<T> = ::initLoadPage) {
        mSize.intValue = 0
        data.clear()
        lastPage = null
        performNewPage = true
        loadPage = newLoadFunction
        processPage()
    }

    protected open fun loadLimit() : Int = 3
    operator fun get(index : Int) : T {
        if(index > size - (loadLimit() + 1) && !performNewPage) processPage()
        return data[index]
    }

    private fun processPage() {
        performNewPage = true
        if((lastPage?.totalResults ?: 0) >= mSize.intValue) {
            CoroutineScope(Dispatchers.IO).launch {
                loadPage((lastPage?.page ?: 0) + 1).also { page ->
                    page.values.forEach { if(!data.contains(it)) data.add(it) }
                    mSize.intValue = data.size
                    lastPage = page
                }
                performNewPage = false
            }
        }
    }
}