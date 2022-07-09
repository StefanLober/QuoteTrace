package de.stefanlober.stocktrace.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stockentity")
data class StockEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "symbol") val symbol: String) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readLong(), parcel.readString()!!/*, StockQuote(parcel.readString(), parcel.readLong(), parcel.readString())*/)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeLong(id)
        parcel?.writeString(symbol)
        /*parcel?.writeString(stockQuote.name)
        stockQuote.hundredthValue.let { parcel?.writeLong(it) }
        parcel?.writeString(stockQuote.currency)*/
    }

    companion object CREATOR : Parcelable.Creator<StockEntity> {
        override fun createFromParcel(parcel: Parcel): StockEntity {
            return StockEntity(parcel)
        }

        override fun newArray(size: Int): Array<StockEntity?> {
            return arrayOfNulls(size)
        }
    }
}