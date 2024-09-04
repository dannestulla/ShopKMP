package presentation

import app.cash.turbine.test
import categoriesMock
import data.ShopRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class CategoriesViewModelTest {
    private lateinit var viewModel: CategoriesViewModel

    @BeforeTest
    fun setUp() {
        val repository = mock<ShopRepository> {
            everySuspend { getCategories() } returns categoriesMock
        }
        viewModel = CategoriesViewModel(repository)
    }

    @Test
    fun `GIVEN viewModel is created WHEN screen is starting THEN fetch all categories`() =
        runBlocking {
            // Test items loaded
            viewModel.state.test {
                val item = awaitItem()
                assertEquals(item.categories, categoriesMock)
            }
        }
}