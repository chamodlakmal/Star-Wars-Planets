package lk.chamiviews.starwarsplanets.utils

import androidx.compose.foundation.lazy.LazyListLayoutInfo

/**
 * Determines whether more items should be loaded based on the lazy list state.
 */
class LoadMoreStrategyImpl(private val boundary: Int = 5) : LoadMoreStrategy {
    override fun shouldLoadMore(layoutInfo: LazyListLayoutInfo): Boolean {
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
        val totalItemCount = layoutInfo.totalItemsCount
        return lastVisibleItem != null && lastVisibleItem.index >= totalItemCount - boundary
    }
}