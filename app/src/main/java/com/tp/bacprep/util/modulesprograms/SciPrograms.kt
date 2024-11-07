package com.tp.bacprep.util.modulesprograms

import android.os.Parcelable
import com.tp.bacprep.util.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
open class SciPrograms : BranchProgram(),Parcelable{
    fun calculateAverage(
        noteSci : Double,
        notePhy : Double,
        noteMath : Double,
        noteArab : Double,
        noteFr : Double,
        noteAng : Double,
        notePhilo : Double,
        noteIsl : Double,
        noteHisGeo : Double,
        noteTamazight : Double?,
        noteSport : Double?,

    ) : Double {
        if (noteTamazight == null && noteSport == null){
            val sum = ((noteSci * 6) + (notePhy * 5) + (noteMath * 5) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2))
            return String.format("%.2f", sum/29).toDouble()
        }else if(noteTamazight == null && noteSport!=null){
            val sum = ((noteSci * 6) + (notePhy * 5) + (noteMath * 5) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport)
            return String.format("%.2f", sum/30).toDouble()
        }else if(noteSport == null && noteTamazight != null){
            val sum = ((noteSci * 6) + (notePhy * 5) + (noteMath * 5) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) + (noteTamazight * 2))
            return String.format("%.2f", sum/31).toDouble()
        }else if(noteSport != null && noteTamazight != null){
            val sum = ((noteSci * 6) + (notePhy * 5) + (noteMath * 5) + (noteArab * 3)
                    + (noteFr * 2) + (noteAng * 2) + (notePhilo * 2) + (noteIsl * 2) + (noteHisGeo * 2) +
                    noteSport + (noteTamazight * 2))
            return String.format("%.2f", sum/32).toDouble()
        }else{
            return 0.0
        }
    }

    override val BRANCH_NAME = Constants.SCIENCE_BRANCH

}