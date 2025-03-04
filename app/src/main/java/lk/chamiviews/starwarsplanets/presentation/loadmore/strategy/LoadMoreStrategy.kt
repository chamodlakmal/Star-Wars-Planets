package lk.chamiviews.starwarsplanets.presentation.loadmore.strategy

import androidx.compose.foundation.lazy.LazyListLayoutInfo

interface LoadMoreStrategy {
    fun shouldLoadMore(layoutInfo: LazyListLayoutInfo): Boolean
}