package de.stefanlober.stocktrace.quoteproviders

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class QuoteProviderModule {
    @Binds
    abstract fun bindQuoteProvider(
        dataProviderImpl: YahooFinanceQuoteProvider
    ): IQuoteProvider
}