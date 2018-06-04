package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri
import java.util.Date

@Entity
class Spot(@PrimaryKey(autoGenerate = true) val id: Int,
           @ColumnInfo val imagepass: Uri,
           @ColumnInfo val comment: String,
           @ColumnInfo val place: String,
           @ColumnInfo val date: Date)