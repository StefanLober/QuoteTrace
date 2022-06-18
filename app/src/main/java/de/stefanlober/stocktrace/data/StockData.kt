package de.stefanlober.stocktrace.data

import android.os.Parcel
import android.os.Parcelable

data class StockData(val id: Long, val symbol: String, var stockQuote: StockQuote) : Parcelable {
    constructor(id: Long, symbol: String) : this(id, symbol, StockQuote("", 0, ""))
    constructor(parcel: Parcel) : this(parcel.readLong(), parcel.readString()!!, StockQuote(parcel.readString(), parcel.readLong(), parcel.readString()))

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeLong(id)
        parcel?.writeString(symbol)
        parcel?.writeString(stockQuote.name)
        stockQuote.hundredthValue.let { parcel?.writeLong(it) }
        parcel?.writeString(stockQuote.currency)
    }

    companion object CREATOR : Parcelable.Creator<StockData> {
        override fun createFromParcel(parcel: Parcel): StockData {
            return StockData(parcel)
        }

        override fun newArray(size: Int): Array<StockData?> {
            return arrayOfNulls(size)
        }
    }
}
