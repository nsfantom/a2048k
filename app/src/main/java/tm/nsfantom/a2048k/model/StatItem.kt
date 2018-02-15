package tm.nsfantom.a2048k.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatItem(@field:SerializedName("result")
               @field:Expose
               var result: Long,
               @field:SerializedName("date")
               @field:Expose var date: Long)
