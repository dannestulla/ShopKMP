package presentation

import data.ShopRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import favoriteFixture
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import productsMock
import kotlin.test.BeforeTest
import kotlin.test.Test


class FavoritesViewModelTest {
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var repository: ShopRepository

    @BeforeTest
    fun setUp() {
        repository = mock<ShopRepository> {
            everySuspend { getFavorites() } returns flowOf(favoriteFixture)
            everySuspend { removeFavorite(any()) } returns Unit
        }
        viewModel = FavoritesViewModel(repository)
    }

    @Test
    fun `GIVEN screen is loaded WHEN user removes from favorite THEN remove product from favorites`() =
        runBlocking {
            viewModel.removeFavorite(productsMock.first())
            verify(mode = VerifyMode.exactly(1)) { repository.removeFavorite(any()) }
        }
}
