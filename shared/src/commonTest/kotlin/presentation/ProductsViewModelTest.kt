package presentation

import app.cash.turbine.test
import data.ShopRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import domain.CURRENT_DISCOUNT
import favoriteFixture
import kotlinx.coroutines.runBlocking
import productsMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel
    private lateinit var repository: ShopRepository

    @BeforeTest
    fun setUp() {
        repository = mock<ShopRepository> {
            everySuspend { getProducts() } returns productsMock
            everySuspend { saveFavorite(any(), any()) } returns Unit
            everySuspend { removeFavorite(any()) } returns Unit
            everySuspend { addToCheckout(any()) } returns Unit
            everySuspend { getFavoriteByTitle(any()) } returns favoriteFixture.first()
        }
        viewModel = ProductsViewModel("Clothes", repository)
    }

    @Test
    fun `GIVEN category clothes WHEN viewModel is created THEN fetch all products`() = runBlocking {
        viewModel.state.test {
            val item = awaitItem()
            assertEquals(item.products, productsMock)
            assertEquals(item.products?.size, productsMock.size)
        }
    }

    @Test
    fun `GIVEN screen is loaded WHEN user clicks on favorite THEN add product to favorites`() =
        runBlocking {
            viewModel.saveFavorite(productsMock.first())
            verify(mode = VerifyMode.exactly(1)) {
                repository.saveFavorite(
                    productsMock.first(),
                    CURRENT_DISCOUNT
                )
            }
        }

    @Test
    fun `GIVEN screen is loaded WHEN user removes from favorite THEN remove product from favorites`() =
        runBlocking {
            viewModel.removeFavorite(productsMock.first())
            verify(mode = VerifyMode.exactly(1)) { repository.removeFavorite(any()) }
        }

    @Test
    fun `GIVEN screen is loaded WHEN user adds to checkout THEN add product to checkout`() {
        viewModel.addToCheckout(productsMock.first())
        verify(mode = VerifyMode.exactly(1)) { repository.addToCheckout(any()) }
    }

    @Test
    fun `GIVEN screen just loaded WHEN products are loading THEN show products tha are in favorites`() {
        val result = viewModel.checkIfIsFavorite(productsMock.first().title)
        verify(mode = VerifyMode.exactly(1)) { repository.getFavoriteByTitle(any()) }
        assertEquals(result, true)
    }
}